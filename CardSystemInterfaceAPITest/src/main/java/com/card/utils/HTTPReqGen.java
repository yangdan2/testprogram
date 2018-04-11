package com.card.utils;

import static com.jayway.restassured.RestAssured.given;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.EncoderConfig;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

/**
 * RestAssured包装器。使用HTTP请求模板和记录处理程序对象中存储的单个记录
 * 	生成并执行一个HTTP请求。
 * 
 */
public class HTTPReqGen {
	
	public enum HttpType{
		PUT,GET,DELETE,POST
	}
  
  protected static final Logger logger = LoggerFactory.getLogger(HTTPReqGen.class);

  private RequestSpecification reqSpec;

  private String call_host = "";
  private String call_suffix = "";
  private String call_string = "";
  private HttpType call_type = null;
  private String body = "";
  private Map<String, String> headers = new HashMap<String, String>();
  private HashMap<String, String> cookie_list = new HashMap<String, String>();

  public Map<String, String> getHeaders() {
    return headers;
  }

  public String getCallString() {
    return call_string;
  }

  /**
   * 构造函数。初始化request规格(宽松的验证避免了证书错误)。
   * 
   */
  public HTTPReqGen() {

	//given().config(RestAssured.config().encoderConfig(new EncoderConfig("UTF-8","UTF-8")));
    
	//given().config(RestAssured.config().httpClient();
	reqSpec = given().relaxedHTTPSValidation();
  }

  public HTTPReqGen(String proxy) {
    reqSpec = given().relaxedHTTPSValidation().proxy(proxy);
  }

  /**
   * 从给定的记录处理程序中提取HashMap，并使用它调用主generaterequest方法。
   * 
   * @param 模板字符串，应该包含完整的模板。
   * @param record RecordHandler, 用于填充模板中存在的替换标记的输入数据。
   * @return 这个类的引用，主要是为了允许在一行中生成请求和性能。
   * @throws Exception 
   */
  public HTTPReqGen generate_request(String template, RecordHandler record) throws Exception {

    return generate_request(template, (HashMap<String, String>) record.get_map());
  }

  /**
   * 生成请求数据，使用输入记录填充模板，然后解析相关数据。
   * 要填充模板，可以识别被包围的标签，并使用记录处理程序中相应字段的文本来替换它们。
   * 替换是递归的，所以标记可能也存在于记录处理程序的字段中，只要它们还由记录处理程序表示，并且不会形成一个循环。
   * 在填充模板之后，解析生成的字符串，以准备执行HTTP请求。期望字符串以以下格式:
   * <<call_type>> <<call_suffix>>
   * Host: <<root_host_name>>
   * <<header1_name>>:<<header1_value>>
   * ...
   * <<headerN_name>>: <<headerN_value>>
   *
   * <<body_text>>
   *
   * <<call_type>> must be GET, PUT, POST, or DELETE. 
   * <<call_suffix>> must be a string with no spaces. It is appended to
   * <<root_host_name>> 作为一个完整的调用字符串，它是一个完整的调用字符串。
   * 在遇到一个空白行之后，文件的其余部分用作PUT和POST调用的文本主体。
   * 该函数还希望记录处理程序包含一个名为“VPID”的字段，该字段包含一个惟一的记录标识符，用于调试。
   * @param 模板字符串，应该包含完整的模板。
   * @param record RecordHandler, 用于填充模板中存在的替换标记的输入数据。
   * @return 这个类的引用，主要是为了允许在一行中生成请求和性能。
   * @throws Exception 
   */
  public HTTPReqGen generate_request(String template, HashMap<String, String> record) throws Exception {

    String filled_template = "";
    Boolean found_replacement = true;
    headers.clear();
    
    try {
      
      // 将模板拆分为令牌，将替换字符串分隔开
      // like <<id>>
      String[] tokens = tokenize_template(template);

      // 反复执行从记录中获取数据的替换，直到没有找到替换
      // 如果一个替换的结果是一个空字符串，它将不会抛出一个空字符串。
      // 错误(如果没有列，则会抛出一个错误)
      while(found_replacement) {
        found_replacement = false;
        filled_template = "";
  
        for(String item: tokens) {
  
          if(item.startsWith("<<") && item.endsWith(">>")) {
            found_replacement = true;
            item = item.substring(2, item.length() - 2);
            
            if( !record.containsKey(item)) {
              logger.info("Template contained replacement string whose value did not exist in input record:[" + item + "]");
            }            
            
            item = record.get(item);
          }
  
          filled_template += item;
        }
  
        tokens = tokenize_template(filled_template);
      }
      
    } catch (Exception e) {
      logger.error("Problem performing replacements from template: ", e);
    }

    try {
      
      // 将填充模板填充到BufferedReader中，这样我们就可以逐行读取它
      InputStream stream = IOUtils.toInputStream(filled_template, "UTF-8");
      BufferedReader in = new BufferedReader(new InputStreamReader(stream));
      String line = "";
      String[] line_tokens;
      
      // 第一行应该始终是调用类型，后面是调用 call suffix
      line = in.readLine();
      line_tokens = line.split(" ");
      call_type = Enum.valueOf(HttpType.class, line_tokens[0]);
      call_suffix = line_tokens[1];

      // 第二行应该包含主机，因为它是第二个标记
      line = in.readLine();
      line_tokens = line.split(" ");
      call_host = line_tokens[1];

      // 对rest放心的完整调用字符串将会连接到调用
      // host and call suffix
      call_string = call_host + call_suffix;

      // 剩余的行将包含标题，直到读行是空的
      line = in.readLine();
      while(line != null && !line.equals("")) {

        String lineP1 = line.substring(0, line.indexOf(":")).trim();
        String lineP2 = line.substring(line.indexOf(" "), line.length()).trim();

        headers.put(lineP1, lineP2);

        line = in.readLine();
      }

      // 如果读行是空的，但是下一行(s)有数据，从它们创建主体
      if(line != null && line.equals("")) {
        body = "";
        while( (line = in.readLine()) != null && !line.equals("")) {
          body += line;
        }
      }

    } catch(Exception e) {
      logger.error("Problem setting request values from template: ", e);
    }

    return this;
  }
  
  /**
   * 使用存储的请求数据执行请求，然后返回响应.
   * 
   * @return response Response, will contain entire response (response string and status code).
   */
  public Response perform_request() throws Exception {
    
    Response response = null;
    
    try {

      for(Map.Entry<String, String> entry: headers.entrySet()) {
        reqSpec.header(entry.getKey(), entry.getValue());
      }
      
      for(Map.Entry<String, String> entry: cookie_list.entrySet()) {
        reqSpec.cookie(entry.getKey(),entry.getValue());
      }
  
      switch(call_type) {
  
        case GET: {
          response = reqSpec.get(call_string);
          break;
        }
        case POST: {
          response = reqSpec.body(body).post(call_string);
          System.out.println(response.asString());
          break;
        }
        case PUT: {
          response = reqSpec.body(body).put(call_string);
          break;
        }
        case DELETE: {
          response = reqSpec.delete(call_string);
          break;
        }
  
        default: {
          logger.error("Unknown call type: [" + call_type + "]");
        }
      }
      
    } catch (Exception e) {
      logger.error("Problem performing request: ", e);
    }

    return response;
  }

  /**
   * 将一个模板字符串分割为令牌，将看起来类似"<<key>>"的记号分隔开 
   * 
   * @param template String, the template to be tokenized.
   * @return list String[], contains the tokens from the template.
   */
  private String[] tokenize_template(String template) {
    return template.split("(?=[<]{2})|(?<=[>]{2})");
  }

}
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>测试结果展现</title>
<link href="<%=request.getContextPath() %>/css/style.css" rel="stylesheet" type="text/css" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
var top_obj;
var download_url;

function getDetail(_url){
	_url = encodeURI(encodeURI(_url));//ajax中文乱码问题，前台转两次，后台转一次
	download_url = _url;
	var url = document.getElementById("urlHead").value + "/performance/onePerformanceTestDetail.do?path="+_url;
	
	xmlhttp=new XMLHttpRequest();
	xmlhttp.onreadystatechange = to_show;
	xmlhttp.open("GET",url,false);
	xmlhttp.send();
	
}

function to_show(data){
	if(xmlhttp.readyState == 4) {
        if(xmlhttp.status == 200) {
     	var data = xmlhttp.responseText;
     	if(data != null){
     	var obj = eval("("+data+")"); 
     	//timeinfo
     	var timeInfo = obj[0];
     	//测试场景说明
     	var content = obj[1];
     	//serverInfo
     	var serverInfo = obj[2];
     	//显示信息div
     	var infoDiv = top_obj.nextSibling.nextSibling;
     	var divChilds = infoDiv.childNodes;
     	var atable = divChilds[1];
     	var rows = atable.rows;

     	(rows[1].cells)[0].innerHTML=content[1].content;
     	(rows[3].cells)[1].innerHTML=serverInfo[1].system;
     	(rows[4].cells)[1].innerHTML=serverInfo[1].cpu;
     	(rows[5].cells)[1].innerHTML=serverInfo[1].memory;
     	
     	
     	for(var i=3 ; i<obj.length ; i++){
     		
     		var newInfo = obj[i];
     		var newInfoname = newInfo[0];
     		//创建新节点
     		var crow = atable.rows.length-1;
     		atable.insertRow(crow);
     		rows[crow].insertCell(0);
     		var v = rows[crow].cells;
     		v[0].setAttribute("colspan","2");
     		v[0].setAttribute("class","test_report_cont_title");
     		v[0].innerHTML = "测试结果-"+newInfoname; 	
     		/* <tr>
            <td colspan="2" class="test_report_cont_title">测试结果-委托响应</td>
            </tr> */
            for(var j=0 ; j<3 ; j++){
            	var currentR = atable.rows.length-1;
            	atable.insertRow(currentR);
            	rows[currentR].insertCell(0);
            	var v1 = rows[currentR].cells;
            	var n="";
            	var value="";
            	if(j==0){
            		n="平均时间";
            		value = newInfo[1].avgTime;
            	}
            	if(j==1){
            		n="最大时间";
            		value = newInfo[1].maxTime;
            	}
            	if(j==2){
            		n="最小时间";
            		value = newInfo[1].minTime;
            	}
            	v1[0].innerHTML = n;
            	rows[currentR].insertCell(1);
            	var v2 = rows[currentR].cells;
            	v1[1].innerHTML = value;
            	/* <tr>
                <td>平均时间</td>
                <td>0.0000000123</td>
              </tr>
              <tr>
                <td>最大时间</td>
                <td>0.00123</td>
              </tr>
              <tr>
                <td>最小时间</td>
                <td>0.00000000123</td>
              </tr> */
            }
            
     	}
     	
     	//将下载链接写入 input hidden中
     	var _rows = atable.rows;
     	var l_Row = _rows[_rows.length-1];
     	var _cells = l_Row.cells;
     	var l_cell = _cells[0];
     	l_cell.lastChild.value = download_url;
     
        }    	
       }
	}	
}

function showDetail(obj){
	var flag = (obj.nextSibling.nextSibling.style.display == "block");
	
	if(!flag){
		
	top_obj = obj;
	var table = obj.childNodes;
	var tr = table[1].rows;//table
	var td_input = tr[0];//tr
	var td = td_input.cells;
	var nodes = td[0].childNodes;
	var url = nodes[1].value;
	
	getDetail(url);		
	obj.nextSibling.nextSibling.style.display = "block";
	
	}else{
		 
		obj.nextSibling.nextSibling.style.display = "none";
		var table = obj.nextSibling.nextSibling.childNodes;
		var tr = table[1].rows;//table	
		if(tr.length>7){
			
			while(tr.length>7){
				table[1].deleteRow(tr.length-2);
			}
		} 		
	}
}

//查询
function query(){
	var startdate_text = document.getElementById("startdate");
	var enddate_text = document.getElementById("enddate");
	var ip_text = document.getElementById("ip");
	var environment_select = document.getElementById("select2");
	var spd_text = document.getElementById("spd_version");
	var system_select = document.getElementById("select");
	//查询条件
	var startdate = startdate_text.value;
	var enddate = enddate_text.value;
	var ip = ip_text.value;
	var environment = environment_select.value;
	var spd_version = spd_text.value;
	var system = system_select.value;
	
	var alldiv = document.getElementsByTagName("div");
	
	
	for(var i=0 ; i< alldiv.length; i++){
		
		var classname = alldiv[i].className;
		if(classname == "test_report"){
			
			var test_report_divs = alldiv[i].getElementsByTagName("div");
			
			//	one----test_report_summary  
			//  start
			for(var j=0 ; j<test_report_divs.length ; j=j+2){
				
				var div_test_report_summary = test_report_divs[j];			
				var tables = div_test_report_summary.getElementsByTagName("table");
				var irows = tables[0].rows;
				var cells = irows[0].cells;
				//实际值
				var time = cells[1].innerHTML;
				var host = cells[2].innerHTML;
				var env = cells[3].innerHTML;
				var spdv = cells[4].innerHTML;
				var sys = cells[5].innerHTML;
				
				//对比时间
				var t = time.split(" ");
				var day = t[0].split("-");
				var y = day[0];
				var m = day[1]-1;
				var d = day[2];
				
				var begin = startdate.split("-");
				var begin_y = begin[0];
				var begin_m = begin[1]-1;
				var begin_d = begin[2];
				
				var end = enddate.split("-");
				var end_y = end[0];
				var end_m = end[1]-1;
				var end_d = end[2];
				
				var pass = true;
				
				var stf = startdate==null || startdate=="";
				var endf = enddate==null || enddate=="";
				
				if( (!stf && !endf) && pass ){
					//alert("time1");
					//判断区间 start ---- end
					var mydate = new Date(y,m,d);
					var mylongdate = mydate.getTime();
					
					mydate = new Date(begin_y,begin_m,begin_d);
					var mylongstart =  mydate.getTime();
					
					mydate = new Date(end_y,end_m,end_d);
					var mylongend =  mydate.getTime();
					
					if(mylongstart > mylongdate || mylongend < mylongdate ){
						
						pass = false;	
					}
				}
				
				if((!stf && endf) && pass ){
					//alert("time2");
					//大于 start
					var mydate = new Date(y,m,d);
					var mylongdate = mydate.getTime();
					mydate = new Date(begin_y,begin_m,begin_d);
					var mylongstart =  mydate.getTime();
					
					if(mylongstart > mylongdate){
						pass = false;	
					}
				}
				
				//对比IP
				var ipf = (ip==null||ip=="");
				if((!ipf) && pass){
					//alert("ip");
					var sHost = host.split(".");
					var sIp = ip.split(".");
					var hostL = sHost.length;
					var ipL = sIp.length;
					if(hostL == ipL){
					if(ip != host){
						pass = false;
					}
					}else if(hostL > ipL){
						var f = true;
						for(var index=0 ;  index < ipL ; index++){
							var part = sIp[index];
							if(part != sHost[index]){
								f = false;
								break;
							}
						}
						
						if(!f){
							pass = false;
						}
					}else{
						pass = false;
					}
				}
				
				//对比环境
				var evnf = (environment == null || environment == "");
				if((!evnf) && pass){
					//alert("env");
					if(environment != env){
						pass = false;
					}
				}
				
				//对比对台版本
				var spdf = (spd_version == null || spd_version == ""); 
				if((!spdf) && pass){
					//alert("spd");
					var spdvL = spdv.length;
					var spd_versionL = spd_version.length;
					if(spdvL == spd_versionL){
					if(spd_version != spdv){
						pass = false;
					}
					}else if(spdvL > spd_versionL){
						
						if((spdv.toLowerCase()).indexOf(spd_version.toLowerCase())==-1){
							pass = false;
						}
					}else{
						pass = false;
					}
				}
				
				//对比系统
				var sysf = (system == null || system == "");
				if((!sysf) && pass){
					//alert("sys");
					if(system != sys){
						pass = false;
					}
				}
				
				//有一条不匹配则全部不匹配
				if(pass == false){
					test_report_divs[j].style.display = "none";	
					test_report_divs[j+1].style.display = "none";	
				}
			}
		
		    //  end
            //	one----test_report_summary  
	     break;
		}
	}
}

function file_change(e) {
	var strs = e.split(".");
	var endwith = strs[strs.length-1]; 
	

	if(endwith == "zip"){
	document.getElementById("file_name").value = e;
	var btn = document.getElementById("disabled_button");
	btn.disabled = false;
	}
	else{
		alert("请使用zip文件"); 
		var btn = document.getElementById("disabled_button");
		btn.disabled = true;
	} 
}

function refresh(){
	window.location.href = document.getElementById("urlHead").value + "/performance/ReportList.do";
}


</script>
</head>

<body>
<input type="hidden" value="<%=request.getContextPath()%>" id="urlHead"/>
<table cellpadding="0" cellspacing="1" id="main_table">
  <tr>
    <td id="logo_body"></td>
    <td id="navigation_body">
      <div class="navigation_button_activate"><a style="text-decoration: none;" href="<%=request.getContextPath() %>/performance/ReportList.do">压力测试结果</a></div>
      <div class="navigation_button"><a style="text-decoration: none;" href="<%=request.getContextPath() %>/JunitReport/ReportList.do">自动化结果</a></div></td>
  </tr>
  <tr>
    <td id="left_cont">
    <div>上传测试结果：
<form action="<%=request.getContextPath()%>/performance/upload.do" method="post" enctype="multipart/form-data">
  <input type="text" name="file_name" id="file_name"  style="width:162px; height:12px; margin-bottom: 2px;"/>
  <input type="file" name="btn_file" style="display: none" onchange="file_change(this.value)"/>
  <input type="button" value="浏 览" onclick="btn_file.click();" name="get_file" style="width:50px; font-size:12px; margin-left: 60px;"/>
  <input id="disabled_button" type="submit" value="提 交" name="sub" style="width:50px; font-size:12px;"/>
</form> 
      </div>
    <div>按时间段查询：("yyyy-mm-dd")
        <input id="startdate" type="text" style="width:70px; height:12px; margin-bottom: 12px; "/> -
        <input id="enddate"type="text" style="width:70px; height:12px; margin-bottom: 12px; "/>
        <br />按IP地址查询：
        <input id="ip" type="text" style="width:162px; height:12px; margin-bottom: 12px;"/>
        <br />
       按环境查询：
        <select name="select2" id="select2" style="width:166px; height:20px; line-height: 18px; margin-bottom: 12px;">
          <option selected="selected"></option>
          <option >EXCHANGE</option>
          <option>VMATCH</option>
        </select>
        <br />
       按柜台版本查询：
        <input id="spd_version" type="text" style="width:162px; height:12px; margin-bottom: 12px;"/>
        <br />
        按操作系统查询：
<label for="select"></label>
        <select name="select" id="select" style="width:166px; height:20px; line-height: 18px; margin-bottom: 12px;">
          <option selected="selected"></option>
          <option>Linux</option>
          <option>Win2008</option>
          <option>Win2003</option>
          <option>WinXP</option>
          <option>Win7</option>
          <option>Win8</option>
        </select>
        <input onclick="query()" type="button" value="查询" style="width:38px; font-size:12px; margin-left: 130px; margin-top: 20px;"/>
        <input onclick="refresh()" type="button" value="刷新结果" style="width:68px; font-size:12px; margin-left: 100px; margin-top: 20px;"/>     
      </div></td>
    <td id="right_cont"><div class="cont_title">压力测试结果</div>
    <div class="test_report_table_title">
        <table width="100%" cellpadding="0" cellspacing="0">
          <tr>
            <td>编号</td>
            <td>时间</td>
            <td>IP地址</td>
            <td>环境</td>
            <td>柜台版本</td>
            <td>操作系统</td>
          </tr>
        </table>
    </div>
    <div class="test_report">
    <c:if test="${empty performanceTestList}">没有测试结果</c:if>
    <c:if test="${not empty performanceTestList}">
        <c:forEach items="${performanceTestList}" var="performanceTest" varStatus="status">
        <div class="test_report_summary" style="cursor: pointer;" onclick="showDetail(this)">
        <table width="100%" cellpadding="0" cellspacing="0">
        <tr>
        <td>${status.index+1}<input type="hidden" value="${performanceTest[5]}"  name="hiddenUrl"/></td>
        <c:forEach items="${performanceTest}" var="test" varStatus="s">
        <c:if test="${s.index < 5}">
        <td>${test}</td>
        </c:if>
        </c:forEach>
        </tr>
        </table>
        </div>
        <div class="test_report_cont">
        	<table width="100%"  cellspacing="0" cellpadding="0">
        	 <tr>
                <td colspan="2" class="test_report_cont_title">测试场景说明</td>
              </tr>
              <tr>
              	<td colspan="2" style="white-space: normal;">说明</td>
              </tr>
              <tr>
                <td colspan="2" class="test_report_cont_title">服务器信息</td>
              </tr>
              <tr>
                <td width="20%">操作系统</td>
                <td width="70%">操作系统信息</td>
              </tr>
              <tr>
                <td>CPU</td>
                <td>CPU信息</td>
              </tr>
              <tr>
                <td>内存</td>
                <td>内存信息</td>
              </tr>
              <tr>
                <td colspan="2" class="test_report_cont_download"><a href="<%=request.getContextPath() %>/performance/download.do?path=${performanceTest[6]}" style="text-decoration: underline;cursor: pointer;" >下载测试报告</a><input type="hidden" value=""/></td>
              </tr>

            </table>
        </div> 
        
        </c:forEach> 
    </c:if>      
    	
    
    </div>
    
    
    </td>
  </tr>
</table>
</body>
</html>

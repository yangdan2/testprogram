package com.card.common;

import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;
import org.testng.Assert;

import com.card.utils.DataReader;
import com.card.utils.DataWriter;
import com.card.utils.HTTPReqGen;
import com.card.utils.StringUtil;
import com.jayway.restassured.response.Response;

public class TestAction extends init{
	private Response response;
    private double failedcase = 0;
    //Test处理过程
    public void TestDeal(String ID,String test_case,String template)
    {
    HTTPReqGen myReqGen = new HTTPReqGen();
    try {
        	myReqGen.generate_request(template, super.getMyInputData().get_record(ID));
        	response = myReqGen.perform_request();
    	} catch (Exception e) {
        Assert.fail("Problem using HTTPRequestGenerator to generate response: " + e.getMessage());
       }
    String baseline_message = super.getMyBaselineData().get_record(ID).get("Response");

    if (response.statusCode() == 200)
        try {
            DataWriter.writeData(super.getOutputSheet(), response.asString(), ID, test_case);
        	System.out.println(super.getOutputSheet().getSheetName() + "\t\t"+response.asString() + "\t\t" + ID+"\t\t"+test_case);
            baseline_message=StringUtil.RemoveNoNeedCompared(baseline_message,DataReader.ComparisonRemove(super.getComparisonRemove()));
            //JSONCompareResult result = JSONCompare.compareJSON(StringUtil.removeSpaces(baseline_message), StringUtil.removeSpaces(response.asString()), JSONCompareMode.NON_EXTENSIBLE);
            JSONCompareResult result = JSONCompare.compareJSON(StringUtil.removeSpaces(baseline_message), 
            		StringUtil.removeSpaces(StringUtil.RemoveNoNeedCompared(response.asString(),DataReader.ComparisonRemove(super.getComparisonRemove()))), JSONCompareMode.NON_EXTENSIBLE);
            //如果结果不通过，则将不通过的case的对比信息写到comparsionSheet里，同时将结果写到resultSheet里
            if (!result.passed()) {
                DataWriter.writeDatacomparsionSheet(super.getComparsionSheet(), result.getMessage(), ID, test_case);
               // System.out.println(comparsionSheet.getSheetName() + "\t\t"+result + "\t\t" + ID+"\t\t"+test_case);
                DataWriter.writeData(resultSheet, "false", ID, test_case, 0);
                failedcase++;
                Assert.fail(result.getMessage());
            } else {
               DataWriter.writeData(resultSheet, "true", ID, test_case, 0);
               //	System.out.println(resultSheet.getSheetName() + "\t\ttrue"+"\t\t"+ ID+"\t\t"+test_case + "\t\t0");
            }
        } catch (JSONException e) {
            DataWriter.writeData(super.getComparsionSheet(), "", "Problem to assert Response and baseline messages: "+e.getMessage(), ID, test_case);
            DataWriter.writeData(resultSheet, "error", ID, test_case, 0);
            failedcase++;
            Assert.fail("Problem to assert Response and baseline messages: " + e.getMessage());
        }
    else {
       DataWriter.writeData(super.getOutputSheet(), response.statusLine(), ID, test_case);

        if (baseline_message.equals(response.statusLine())) {
         DataWriter.writeData(resultSheet, "true", ID, test_case, 0);
        } else {
            DataWriter.writeData(super.getComparsionSheet(), baseline_message, response.statusLine(), ID, test_case);
            DataWriter.writeData(resultSheet, "false", ID, test_case, 0);
            failedcase++;
            Assert.assertFalse(true);
        }
    }
    }
}

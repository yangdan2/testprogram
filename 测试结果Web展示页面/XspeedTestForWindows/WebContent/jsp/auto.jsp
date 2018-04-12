<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>测试结果展现</title>
<link href="<%=request.getContextPath() %>/css/style.css" rel="stylesheet" type="text/css" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
</head>



<body>
<script type="text/javascript">
	var flag = false;

	function getfailed() {

		var aTable = document.getElementById("resultTable");
		
		var rows = aTable.rows;
		for ( var i = 1; i < rows.length; i++) {
			aRow = rows[i];
			aRow.style.display = "table-row";
		}
		for ( var i = 1; i < rows.length; i++) {
			aRow = rows[i];
			if (aRow.className != "wrong_style") {
				aRow.style.display = "none";

			}
		}
	}

	function getAll() {

		var aTable = document.getElementById("resultTable");
		var rows = aTable.rows;
		for ( var i = 1; i < rows.length; i++) {
			aRow = rows[i];
			aRow.style.display = "table-row";
		}
	}

	function getLast() {
		
		var aTable = document.getElementById("resultTable");
		var rows = aTable.rows;
		for ( var i = 1; i < rows.length; i++) {
			aRow = rows[i];
			aRow.style.display = "table-row";
		}
		
		for ( var i = 2; i < rows.length; i++) {
			
				aRow = rows[i];
				aRow.style.display = "none";
			
		}
	}



	function query() {
		var time = document.getElementById("timeText").value;
		var aTable = document.getElementById("resultTable");
		var rows = aTable.rows;
		for ( var i = 1; i < rows.length; i++) {
			aRow = rows[i];
			aRow.style.display = "table-row";
		}
		for ( var i = 1; i < rows.length; i++) {
			var cells = rows[i].cells;
			//var nodes = cells[1].childNodes;
			//var date = nodes[0].innerHTML;
			var date = cells[1].innerHTML;
			
			var except = date.substr(0, 10);
			if (except != time) {
				rows[i].style.display = "none";
			}
		}
	}
	
	
</script>
<table cellpadding="0" cellspacing="1" id="main_table">
  <tr>
    <td id="logo_body">
      </td>
    <td id="navigation_body">
      <div class="navigation_button" style="cursor: pointer"><a style="text-decoration: none;" href="<%=request.getContextPath() %>/performance/ReportList.do">压力测试结果</a></div>
      <div class="navigation_button_activate" style="cursor: pointer;"><a style="text-decoration: none;" href="<%=request.getContextPath() %>/JunitReport/ReportList.do">自动化结果</a></div>
      </td>
  </tr>
  <tr>
    <td id="left_cont">
      <div> <a href="#" style="text-decoration: none;cursor: pointer" onclick="getfailed()">只显示错误</a> </div>
      <div> <a href="#" style="text-decoration: none;cursor: pointer" onclick="getLast()">只显示最近一次结果</a> </div>
      <div> <a href="#" style="text-decoration: none;cursor: pointer" onclick="getAll()">显示全部</a> </div>
      <div> <a style="text-decoration: none;" onclick="showText()">按日期查询("yyyy.mm.dd")</a>
        <input id="timeText" type="text" style="width:100px; height:12px; line-height: 18px;"/>
        <input onclick="query(this)" type="button" value="查询" style="width:38px; font-size:12px;"/>
      </div>
      <div> <a href="<%=request.getContextPath() %>/JunitReport/ReportList.do" style="text-decoration: none;" onclick="refresh()">刷新结果列表</a> </div></td>
    <td id="right_cont">
    <div class="cont_title">自动化测试结果</div>
    
    <table cellpadding="0" cellspacing="0" class="result_show" id="resultTable">
        <tr>
          <th>&nbsp;</th>
          <th>运行日期</th>
          <th>运行版本</th>
          <th>运行环境</th>
          <th>结果展示</th>
        </tr>
        
        <c:if test="${empty reports}">
				<tr>
					<td colspan="5">没有结果文件</td>
				</tr>
		</c:if>
		
		<c:if test="${!empty reports}">
		<c:forEach items="${reports}" var="report" varStatus="s">
		<tr <c:if test="${report[3]=='failed'}"> class="wrong_style" </c:if>
						<c:if test="${report[3]=='pass'}"> </c:if>>
          <td>${s.index+1}</td>
          <c:forEach items="${report}" var="parm" begin="0" end="3" step="1"
							varStatus="status">
				
				<c:if test="${status.index == 3 && parm == 'failed'  }">

							<td><input
									type="hidden" value="${report[4]}" /><a
										href="<%=request.getContextPath()%>/JunitReport/getReport.do?date=${report[0]}&version=${report[1]}&environment=${report[2]}&result=${report[3]}&src=${report[4]}"
										>${report[5]}</a></td>
							</c:if>
							<c:if test="${status.index == 3 && parm == 'pass' }">
								<td><input type="hidden" value="${report[4]}" /><a
										href="<%=request.getContextPath()%>/JunitReport/getReport.do?date=${report[0]}&version=${report[1]}&environment=${report[2]}&result=${report[3]}&src=${report[4]}"
										>${report[5]}</a></td>
							</c:if>
				 <c:if test="${status.index != 3}">
								<td>${parm}</td>
							</c:if>
				
							
		  </c:forEach>
        </tr>
		</c:forEach>
		</c:if>
		
        </table>
        </td>
        </tr>
</table>
</body>
</html>

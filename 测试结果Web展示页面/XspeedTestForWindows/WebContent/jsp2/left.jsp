<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title>list</title>
</head>
<style type="text/css">
.tr_red {
	background-color: #900
}

.tr_green {
	background-color: #0C6
}

.tr_normal {
	background-color: #FFF
}
</style>
<script type="text/javascript">
	var flag = false;

	function getfailed() {

		var aTable = document.getElementById("resultTable");
		var rows = aTable.rows;
		for ( var i = 0; i < rows.length; i++) {
			aRow = rows[i];
			aRow.style.display = "block";
		}
		for ( var i = 0; i < rows.length; i++) {
			aRow = rows[i];
			if (aRow.className == "tr_green") {
				aRow.style.display = "none";

			}
		}
	}

	function getAll() {

		var aTable = document.getElementById("resultTable");
		var rows = aTable.rows;
		for ( var i = 0; i < rows.length; i++) {
			aRow = rows[i];
			aRow.style.display = "block";
		}
	}

	function getLast() {
		
		var aTable = document.getElementById("resultTable");
		var rows = aTable.rows;
		for ( var i = 0; i < rows.length; i++) {
			aRow = rows[i];
			aRow.style.display = "block";
		}
		
		for ( var i = 2; i < rows.length; i++) {
			
				aRow = rows[i];
				aRow.style.display = "none";
			
		}
	}

	/* function selectall(obj) {
		if (flag == false) {
			var aTable = document.getElementById("resultTable");
			var rows = aTable.rows;
			for ( var i = 1; i < rows.length; i++) {
				var cells = rows[i].cells;
				var checkbox = cells[0].childNodes;
				checkbox[0].checked = true;
			}
			flag = true;
		} else {
			var aTable = document.getElementById("resultTable");
			var rows = aTable.rows;
			for ( var i = 1; i < rows.length; i++) {
				var cells = rows[i].cells;
				var checkbox = cells[0].childNodes;
				checkbox[0].checked = false;
			}
			flag = false;
		}
	} */

	function refresh() {

		var root = document.getElementById("root").value;
		window.location.href = root + "/JunitReport/ReportList.do";
	}

	function query(text) {

		var aTable = document.getElementById("resultTable");
		var rows = aTable.rows;
		for ( var i = 0; i < rows.length; i++) {
			aRow = rows[i];
			aRow.style.display = "block";
		}
		for ( var i = 1; i < rows.length; i++) {
			var cells = rows[i].cells;
			var nodes = cells[1].childNodes;
			var date = nodes[0].innerHTML;
			var except = date.substr(0, 10);
			if (except != text) {
				rows[i].style.display = "none";
			}
		}
	}
	
	function readXML(){
		var vs = document.getElementsName("output");
		alert(vs.length);
	}
</script>

<body>
	<input type="hidden" value="<%=request.getContextPath()%>" id="root" />
	<div align="center">
		<table id="resultTable" align="center"
			style="font-size: 16px; border: #000 outset" cols="4"
			cellpadding="2px" border="1">
			<tr class="tr_normal" style="display: block">
				<td width="30px;" height="30px;" align="center"
					style="cursor: pointer" onclick="selectall(this)"></td>
				<td width="200px;" height="30px;" align="center"
					style="overflow: auto; font-size: 14px; border: 1px outset thin"><span>运行日期</span></td>
				<td width="200px;" height="30px;" align="center"
					style="overflow: auto; font-size: 14px; border: 1px outset thin"><span>运行版本</span></td>
				<td width="200px;" height="30px;" align="center"
					style="overflow: auto; font-size: 14px; border: 1px outset thin"><span>运行环境</span></td>
				<td width="250px;" height="30px;" align="center"
					style="overflow: auto; font-size: 14px; border: 1px outset thin"><span>结果展示</span></td>
			</tr>
			<c:if test="${empty reports}">
				<tr>
					<td colspan="5">没有结果文件</td>
				</tr>
			</c:if>

			<c:if test="${!empty reports}">
				<!--循环-->
				<c:forEach items="${reports}" var="report" varStatus="s">

					<tr <c:if test="${report[3]=='failed'}"> class="tr_red" </c:if>
						<c:if test="${report[3]=='pass'}"> class="tr_green" </c:if>
						style="display: block; margin-top: 1px; margin-bottom: 1px;">
						<!--jstl表达式判断pass/failed-->
						<td width="30px;" height="30px;" align="center"><c:out
								value="${s.index+1}" /></td>
						<c:forEach items="${report}" var="parm" begin="0" end="3" step="1"
							varStatus="status">
							<c:if test="${status.index == 3 && parm == 'failed'  }">

								<td width="250px;" height="30px;" align="center"
									style="overflow: auto; border: 1px outset thin"><input
									type="hidden" value="${report[4]}" /><span><a
										style="color: #000000; font-weight: inherit;"
										href="<%=request.getContextPath()%>/JunitReport/getReport.do?date=${report[0]}&version=${report[1]}&environment=${report[2]}&result=${report[3]}&src=${report[4]}"
										target="_blank"><c:out value="${report[5]}"></c:out></a></span></td>
							</c:if>
							<c:if test="${status.index == 3 && parm == 'pass' }">
								<td width="250px;" height="30px;" align="center"
									style="overflow: auto; border: 1px outset thin"><input
									type="hidden" value="${report[4]}" /><span><a
										style="color: #000000; font-weight: inherit;"
										href="<%=request.getContextPath()%>/JunitReport/getReport.do?date=${report[0]}&version=${report[1]}&environment=${report[2]}&result=${report[3]}&src=${report[4]}"
										target="_blank"><c:out value="${report[5]}"></c:out></a></span></td>
							</c:if>
							<c:if test="${status.index != 3}">
								<td width="200px;" height="30px;" align="center"
									style="overflow: auto; border: 1px outset thin"><span><c:out
											value="${parm}"></c:out></span></td>
							</c:if>

						</c:forEach>

					</tr>
				</c:forEach>
			</c:if>

		</table>
	</div>
	
</body>
<script type="text/javascript">
document.onload = readXML();
</script>
</html>
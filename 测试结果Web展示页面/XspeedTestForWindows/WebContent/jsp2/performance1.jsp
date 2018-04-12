<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title>Insert title here</title>
</head>
<script type="text/javascript">
function getDetail(obj){
	alert(obj);
}
</script>

<body>

<c:forEach items="${performanceTestList}" var="performanceTest" varStatus="status">

<div style="cursor: pointer;" onclick="getDetail(${performanceTest[5]});">
<table border="1">
<tr>
<c:forEach items="${performanceTest}"  var="test"  varStatus="status">
<c:if test="${status.index != 5}">
<td>${test}</td>
</c:if>
<c:if test="${status.index == 5}">
<a href="<%=request.getContextPath() %>/performance/onePerformanceTestDetail.do?path=${test}">${test}</a>
</c:if>
</c:forEach>
</tr>
</table>
</div>
<div style="display:none">
<table border="1">
<tr>
<td>2014/08/18 16:00:00</td><td>172.21.200.162</td><td>交易所系统VMATCH</td><td>LINUX版</td><td>Linux</td>
</tr>
</table>
</div>

</c:forEach>


</body>
</html>
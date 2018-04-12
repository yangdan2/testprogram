<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<title>${result}-${date}</title>
<style type="text/css">
.red{color:#990000; font-weight:700}
.normal{color:#000000; }
</style>
</head>
<body style="height: 100%;">
<div align="center" style="width: 100%; font-size: 14px; height: 5%;">
<span style="margin-left: 10px; margin-right: 10px;">运行日期:</span>
<span style="margin-left: 10px; margin-right: 10px;">${date}</span>
<span style="margin-left: 10px; margin-right: 10px;">版本:</span>
<span style="margin-left: 10px; margin-right: 10px;">${version}</span>
<span style="margin-left: 10px; margin-right: 10px;">测试环境:</span>
<span style="margin-left: 10px; margin-right: 10px;">${environment}</span>
<span style="margin-left: 10px; margin-right: 10px;">结果:</span>
<c:if test="${result == 'failed' }">
<span class="red" style="margin-left: 10px; margin-right: 10px;">${result}</span>
</c:if>
<c:if test="${result == 'pass' }">
<span class="normal" style="margin-left: 10px; margin-right: 10px;">${result}</span>
</c:if>
</div>

<iframe id="frame" src="<%=request.getContextPath()%>/${src}" scrolling="auto" title="1" height="95%" width="100%" frameborder="1"  ></iframe>

</body>
</html>
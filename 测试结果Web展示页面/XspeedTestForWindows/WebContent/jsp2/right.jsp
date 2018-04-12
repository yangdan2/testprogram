<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- <base target="left"> -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>controll</title>
<style type="text/css">
.font_weight_normal {
	font-weight: normal;
}

.font_weight_bold {
	font-weight: bold
}
</style>


</head>
<script type="text/javascript">
function FF_block(){
if (navigator.userAgent.indexOf('Firefox') >= 0){
	document.getElementById("text").style.height="50px";
	document.getElementById("text").style.display="block";
}
}
</script>
<body onload="FF_block()">


	<span
		style="display: block; width: 60px; height: 20px; background-color: #0C6; position: absolute; left: 1px; top: 1px; margin: 5px;"></span>
	<span
		style="width: 60px; height: 20px; position: absolute; left: 70px; top: 1px; margin: 5px;">Pass</span>


	<span
		style="display: block; width: 60px; height: 20px; background-color: #900; position: absolute; left: 1px; top: 25px; margin: 5px;"></span>
	<span
		style="width: 60px; height: 20px; position: absolute; left: 70px; top: 25px; margin: 5px;">Failed</span>



	<script type="text/javascript">
var flag = false;	
function font_weight_bold(obj){
	obj.className = "font_weight_bold";
}
function font_weight_normal(obj){
	obj.className = "font_weight_normal";
}
function showfailed(){
	window.parent.frames["left"].getfailed();	
	window.event.returnValue = false;
}
function showall(){
	window.parent.frames["left"].getAll();	
	window.event.returnValue = false;
}
function showlast(){
	window.parent.frames["left"].getLast();	
	window.event.returnValue = false;
}
function refresh(){
	window.parent.frames["left"].refresh();	
	window.event.returnValue = false;
}
function showText(){
	if (navigator.userAgent.indexOf('Firefox') == -1){
	var f = flag;
	if(f==false){
		var thisone = document.getElementById("text");
		thisone.style.display="block";
		flag = true;   	
	}
    if(f==true){
		var thisone = document.getElementById("text");
		thisone.style.display="none";
		flag = false;
	}
	
	window.event.returnValue = false;
	}
}
function query(obj){
	var nodes = obj.parentNode.childNodes;
	var text = nodes[0];
	window.parent.frames["left"].query(text.value);	
	window.event.returnValue = false;
}
/* function count(obj){
	alert(obj[0]);
} */
</script>
<div id="list" style="position:absolute; top:60px; left:1px; width:200px;">
	<div style="font-size: 12px; height:15px; width:200px; margin: 5px; float:none;">
		<a href="" style="text-decoration: none;" class="font_weight_normal"
		onmouseover="font_weight_bold(this)"
		onmouseout="font_weight_normal(this)" onclick="showfailed()">只显示错误</a>
	</div>
	<div style="font-size: 12px; height:15px; width:99%; margin: 5px;">
		<a href="" style="text-decoration: none;" class="font_weight_normal"
		onmouseover="font_weight_bold(this)"
		onmouseout="font_weight_normal(this)" onclick="showlast()">只显示最近一次结果</a>
	</div>
	<div style="font-size: 12px; height:15px; width:99%; margin: 5px;">
		<a href="" style="text-decoration: none;" class="font_weight_normal"
		onmouseover="font_weight_bold(this)"
		onmouseout="font_weight_normal(this)" onclick="showall()">显示全部</a>
	</div>
	<div style="font-size: 12px; height:15px; width:99%; margin: 5px;">
		<a href="" style="text-decoration: none;" class="font_weight_normal"
		onmouseover="font_weight_bold(this)"
		onmouseout="font_weight_normal(this)" onclick="showText()">按日期查询("yyyy.mm.dd")</a>
	</div>
	<div id="text" style="font-size: 12px; height:25px; width:99%; margin: 5px; display: none"><input type="text"/><input onclick="query(this)" type="button" value="查询"/>
	</div>
	<div style="font-size: 12px; height:15px; width:99%; margin: 5px;">
		<a href="" style="text-decoration: none;" class="font_weight_normal"
		onmouseover="font_weight_bold(this)"
		onmouseout="font_weight_normal(this)" onclick="refresh()">刷新结果列表</a>
	</div>
</div>	
</body>
</html>
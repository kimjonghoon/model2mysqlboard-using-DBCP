<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>New</title>
<script>
function goList(curPage, keyword) {
	location.href="list.do?curPage=" + curPage + "&keyword=" + keyword;
}
</script>
</head>
<body>
<h1>New</h1>
<form action="write.do" method="post">
<table>
<tr>
	<td>Title</td>
	<td><input type="text" name="title" size="50"></td>
</tr>
<tr>
	<td colspan="2">
		<textarea name="content" rows="10" cols="50"></textarea>
	</td>
</tr>
<tr>
	<td colspan="2">
		<input type="submit" value="Submit" />
		<input type="reset" value="Reset" />
		<input type="button" value="Back to the List" onclick="javascript:goList('${param.curPage }','${param.keyword }')" />
	</td>
</tr>
</table>
</form>  
</body>
</html>
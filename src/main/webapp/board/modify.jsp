<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>Modify Article</title>
<script>
function goView(no,curPage,keyword) {
	location.href="view.do?no=" + no + "&curPage=" + curPage + "&keyword=" + keyword;
} 
</script>
</head>
<body>
<h1>Modify Article</h1>
<form action="modify.do" method="post">
<input type="hidden" name="no" value="${param.no }">
<input type="hidden" name="curPage" value="${param.curPage }">
<input type="hidden" name="keyword" value="${param.keyword }">
<table>
<tr>
	<td>Title</td>
	<td><input type="text" name="title" size="50" value="${article.title }" /></td>
</tr>
<tr>
	<td colspan="2">
		<textarea name="content" rows="10" cols="50">${article.content }</textarea>
	</td>
</tr>
<tr>
	<td colspan="2">
		<input type="submit" value="Submit">
		<input type="reset" value="Reset">
		<input type="button" value="View Details" onclick="javascript:goView('${param.no }','${param.curPage }','${keyword }')" />
	</td>
</tr>
</table>
</form>
</body>
</html>
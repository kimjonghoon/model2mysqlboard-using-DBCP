<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>List</title>
<style type="text/css">
html,body {
	font-size: 0.9em;
}
table {
	width: 100%;
	border-collapse: collapse;
}
th {
	text-align: left;
	border: 1px solid grey;
}
td {
	border: 1px solid grey;
}
#paging {
	text-align: center;
}
</style>
</head>
<body>
<h1>List</h1>

<table>
<tr>
	<th style="width: 100px;">NO</th>
	<th>TITLE</th>
	<th style="width: 104px;">DATE</th>
</tr>
<c:forEach var="article" items="${list }" varStatus="status">
<tr>
	<td>
		${listItemNo - status.index }
	</td>
	<td>
		<c:forEach begin="1" end="${article.indent}">
			&nbsp;&nbsp;
		</c:forEach>
		<c:if test="${article.indent > 0 }">
			┗
		</c:if>
		<a href="view.do?no=${article.articleNo }&curPage=${param.curPage }&keyword=${param.keyword }">${article.title }</a>
	</td>
	<td>${article.regdate }</td>
</tr>
</c:forEach>
</table>

<div id="paging">
	<c:if test="${prevPage > 0 }">
		<a href="list.do?curPage=${prevPage }&keyword=${param.keyword }">[Prev]</a>
	</c:if>
	
	<c:forEach var="i" begin="${firstPage }" end="${lastPage }">
		<c:choose>
			<c:when test="${param.curPage == i }">
			<strong>${i }</strong>
			</c:when>
			<c:otherwise>
			<a href="list.do?curPage=${i }&keyword=${param.keyword }">[ ${i } ]</a>
			</c:otherwise>
		</c:choose>
	</c:forEach>
	
	<c:if test="${nextPage > 0 }">
		<a href="list.do?curPage=${nextPage }&keyword=${keyword }">[Next]</a>
	</c:if>
</div>                

<div id="list-menu">
	<a href="write.do?curPage=${param.curPage }&keyword=${keyword }">New</a>
</div>

<form method="get">
	<input type="hidden" name="curPage" value="1" />
	<input type="text" size="10" maxlength="30" name="keyword" />
	<input type="submit" value="Search" />
</form>    

</body>
</html>
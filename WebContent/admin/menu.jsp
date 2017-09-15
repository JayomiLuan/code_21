<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<a target="frmMain" href="${pageContext.request.contextPath}/productServlet?method=findAll">分页显示商品信息</a><br/>
	<a target="frmMain" href="${pageContext.request.contextPath}/productServlet?method=findByCondition">条件查询商品信息</a>
</body>
</html>

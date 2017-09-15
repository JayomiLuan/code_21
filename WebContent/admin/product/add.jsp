<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加商品</title>
</head>
<body>
	<!-- 添加商品的表单 -->
	<form action="${pageContext.request.contextPath }/productServlet" method="post">
		<input type="hidden" name="method" value="add" /><!-- 用于提交一些程序员需要但不能给用户看到的表单项 -->
		<table align="center">
			<tr>
				<td>商品ID：</td>
				<td><input type="text" name="id"/></td>
			</tr>
			<tr>
				<td>商品名称：</td>
				<td><input type="text" name="name"/></td>
			</tr>
			<tr>
				<td>商品分类：</td>
				<td>
					<select name="category_id">
						<option>--请选择--</option>
						<c:forEach var="c" items="${categoryMap }">
							<option value="${c.key }">${c.value }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>商品价格：</td>
				<td><input type="text" name="price"></td>
			</tr>
			<tr>
				<td>商品图片：</td>
				<td><input type="text" name="img_path"/></td>
			</tr>
			<tr>
				<td>商品描述：</td>
				<td>
					<textarea name="description"></textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" value="添加">
					<input type="reset" value="重填">
				</td>
			</tr>
		
		</table>
	
	
	</form>


</body>
</html>
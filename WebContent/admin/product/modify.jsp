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
		<input type="hidden" name="method" value="modify" /><!-- 用于提交一些程序员需要但不能给用户看到的表单项 -->
		<table align="center">
			<tr>
				<td>商品ID：</td>
				<!-- 把requst中product对象的数据显示到输入框中，即表单回填 -->
				<td><input type="text" name="id" value="${product.id }" readonly/></td>
			</tr>
			<tr>
				<td>商品名称：</td>
				<td><input type="text" name="name" value="${product.name }"/></td>
			</tr>
			<tr>
				<td>商品分类：</td>
				<td>
					<select name="category_id">
						<option>--请选择--</option>
						<c:forEach var="c" items="${categoryMap }">
							<option value="${c.key }" ${ product.category_id==c.key?"selected":""}>${c.value }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>商品价格：</td>
				<td><input type="text" name="price" value="${product.price }"></td>
			</tr>
			<tr>
				<td>商品图片：</td>
				<td><input type="text" name="img_path" value=${product.img_path }/></td>
			</tr>
			<tr>
				<td>商品描述：</td>
				<td>
					<textarea name="description">${product.description }</textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" value="修改">
					<input type="reset" value="重填">
				</td>
			</tr>
		</table>
	</form>

</body>
</html>
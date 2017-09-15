<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品信息列表</title>
<style>
	td>img {width:100px;}
</style>
</head>
<body>
	<form action="${pageContext.request.contextPath }/productServlet" method="post">
		<input type="hidden" name="method" value="findByCondition"/><!-- 提交的分发方法 -->
		商品分类：<select name="category_id">
			<!-- 实现查询条件的回填 -->
			<!-- “请选择”中必须指value="",否则会把"--请选择--"当成值提交到服务器，无法实现没有类型限定的效果 -->
			<option value="">--请选择--</option>
			<c:forEach var="c" items="${categoryMap }">
				<option value="${c.key }"  ${condition.category_id==c.key?"selected":"" }>${c.value }</option>
			</c:forEach>
		</select>
		&nbsp;
		商品名称：<input type="text" name="name" value="${condition.name }"/>
		<input type="submit" value="查询"/>
	</form>
	<table border="1" width="600" align="center">
		<tr>
			<th><input type="checkbox" id="checkAll"></th>
			<th>图片</th>
			<th>名称</th>
			<th>分类</th>
			<th>价格</th>
			<th>描述</th>
			<th>操作</th>
		</tr>
		<c:forEach var="p" items="${allProduct }"><!-- 商品对象被封装在PageBean的data属性中（List集合） -->
		<tr>
			<td><input type="checkbox" name="id" value="${p.id }"></td>
			<td><img src="${pageContext.request.contextPath}${p.img_path }"></td>
			<td>${p.name }</td>
			<!-- 设置一个临时变量，值为分类的ID,以此值为Key取得Map中对应的名字 -->
			<c:set var="t" value="${p.category_id }"/>
			<td>${categoryMap[t] }</td>
			<td>${p.price }</td>
			<td>${p.description }</td>
			<td>
				<a href="${pageContext.request.contextPath}/productServlet?method=delete&id=${p.id}">删除</a>
				<a href="${pageContext.request.contextPath}/productServlet?method=modifyUI&id=${p.id}">修改</a>
			</td>
		</tr>
		</c:forEach>
	</table>
	<!-- 发送请求到Servlet，转跳到添加商品信息的页面 -->
	<input type="button" value="添加" onclick="goAdd();" />
	
	
	
	
</body>
</html>
<script>
	function goAdd(){
		location.href = '${pageContext.request.contextPath}/productServlet?method=addUI';
	}

	//拼接一个URL，访问ProductServlet，并传入页号和页大小
	function goPage( pageNumber ){
		var pageSize = document.getElementsByName('pageSize')[0].value;
		//发送请求到Servlet
		location.href="${pageContext.request.contextPath}"+
		"/productServlet?method=findAll&pageNumber="+pageNumber+"&pageSize="+pageSize;
		
	}
</script>


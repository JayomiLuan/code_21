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
		<c:forEach var="p" items="${pageBean.data }"><!-- 商品对象被封装在PageBean的data属性中（List集合） -->
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
	
	
	<!-- 分页的部分 -->
	<!-- 判断当前页是否是第一页，是则不能操作上一页，否则可以 -->
	<a href="#" onclick="goPage(1);">&lt;&lt;</a>
	<a href="#" onclick="goPage(${ pageBean.pageNumber>1?pageBean.pageNumber-1:'#' });">&lt;</a>
	<!-- for( int i = 1 ; i< totalPage ; i++ ) -->
	<c:forEach var="i" begin="1" end="${pageBean.totalPage }">
		<a href="#" onclick="goPage(${i})">${i }</a> <!-- <a href="#" onclick="goPage(2)">2</a> -->
	</c:forEach>
	<!-- 判断当前页是否是最后一页，是则不能操作下一页，否则可以 -->
	<a href="#" onclick="goPage(${ pageBean.pageNumber<pageBean.totalPage?pageBean.pageNumber+1:'#' });">&gt;</a>	
	<a href="#" onclick="goPage(${pageBean.totalPage});">&gt;&gt;</a>
	<!-- 让用户自 己选择页大小 -->
	<select name="pageSize">
		<option value="3" ${pageBean.pageSize==3?"selected":"" }>3</option>
		<option value="5" ${pageBean.pageSize==5?"selected":"" }>5</option>
		<option value="10"${pageBean.pageSize==10?"selected":"" }>10</option>
	</select>
	商品总数：${pageBean.totalCount }
	<input type="text" id="pageNumber" size="3"><input type="button" value="go" onclick="goPage( document.getElementById('pageNumber').value );"/>
	
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


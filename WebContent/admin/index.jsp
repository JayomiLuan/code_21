<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<frameset rows="100,*">
	<frame src="logo.jsp" /><!-- LOGO -->
	<frameset cols="200,*">
		<frame src="menu.jsp" /><!-- Menu -->
		<frame src="welcome.jsp" name="frmMain" /><!-- 主显示区-->
	</frameset>
</frameset>


</html>
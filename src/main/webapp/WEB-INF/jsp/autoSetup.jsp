<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>AutoSetup Setup Form</title>
<link rel="stylesheet" type="text/css" href="/css/style.css">
<style>
.error {
	color: red;
	font-weight: bold;
}
</style>
</head>
<body>
	<div align="center">
		<h2>Auto Configuration Setup Form for ${projectName}</h2>
		<table border="0" width="50%">
			<form:form action="autoSetup" modelAttribute="autoSetupForm">
				<tr>
					<th align="left">Auto Generation of MVC Views for :</th>
					<td><form:checkbox path="autogenerateViews"/></td>
				</tr>
				<tr>
					<th align="left">Auto Generation of JPA Repositories:</th>
					<td><form:checkbox path="autogenerateJpaRepos" /></td>
				</tr>
				<tr>
					<th align="left">Auto Generation of Service layer:</th>
					<td><form:checkbox path="autogenerateServices" /></td>
				</tr>
				<tr>
					<th align="left" valign="center">Auto Generation of JSON and Swagger capabilities:<img src="/img/swagger.png" height="30px" width="30px"></th>
					<td><form:checkbox path="autogenerateJsonRemoting" />&nbsp;&nbsp;<form:input
							path="autogenerateJsonRemotingPackage" size="20" placeholder="Package Name eg. /api"/></td>
				</tr>
				<tr>
					<th align="left">Language:</th>
					<td><form:select path="language">
							<form:option value="en" style="background-image:url(/img/en.png);">English</form:option>
							<form:option value="es" style="background-image:url(/img/es.png);">Spanish</form:option>
						</form:select></td>
				</tr>
				<tr>
					<td align="center"><br> <a class="myButton"
						href="/">HOME</a></td>
					<td align="center"><input type="submit" value="Next"
						class="myButton" /></td>
					<td></td>
				</tr>
			</form:form>
		</table>
	</div>
</body>
</html>
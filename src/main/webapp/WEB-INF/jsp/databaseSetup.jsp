<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Database Setup Form</title>
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
		<h2>Database Setup Form</h2>
		<table border="0" width="50%">
			<form:form action="databaseSetup" modelAttribute="databaseSetupForm">
				<tr>
					<th align="left">Database Provider:</th>
					<td ><form:select path="databaseProvider">
							<form:option value="">Select</form:option>
							<form:option value="HIBERNATE">HIBERNATE</form:option>
						</form:select>
					<td><form:errors path="databaseProvider" cssClass="error" /></td>
				</tr>
				<tr>
					<th align="left">Database: </th>
					<td><form:select path="database">
							<form:option value="">Select</form:option>
							<form:option value="HYPERSONIC_IN_MEMORY">HYPERSONIC_IN_MEMORY</form:option>
							<form:option value="HYPERSONIC_PERSISTENT">HYPERSONIC_PERSISTENT</form:option>
							<form:option value="H2_IN_MEMORY">H2_IN_MEMORY</form:option>
						</form:select></td>
					<td><form:errors path="database" cssClass="error" /></td>
				</tr>
				<tr>
					<th align="left">DB User Name:</th>
					<td><form:input path="userName" placeholder="DB username"/></td>
					<td><form:errors path="userName" cssClass="error" /></td>
				</tr>
				<tr>
					<th align="left">DB Password:</th>
					<td><form:password path="password" placeholder="DB password"/></td>
					<td><form:errors path="password" cssClass="error" /></td>
				</tr>
				<tr>
					<th align="left">DB Host Url:</th>
					<td><form:input path="hostUrl" size="47" placeholder="jdbc:oracle:thin:@<DB NAME>:<PORT>:<INSTANCE ID>"/></td>
					<td><form:errors path="hostUrl" cssClass="error" /></td>
				</tr>
				<tr>
					<th align="left">DB Name:</th>
					<td><form:input path="dbName" placeholder="Database Name"/></td>
					<td><form:errors path="dbName" cssClass="error" /></td>
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
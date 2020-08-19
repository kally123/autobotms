<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/css/style.css">
<title>Project Details Form</title>
<style>
.error {
	color: red;
	font-weight: bold;
}
</style>
</head>
<body>
	<div align="center">
		<h2>Project Details Form</h2>
		<table border="0" width="90%">
			<form:form action="projectDetails" modelAttribute="projectDetailsForm">
				<tr>
					<th align="left">Project Name:</th>
					<td align="left" width="20%"><form:input path="projectName"
							size="40" placeholder="Project Name min 5 to 40 chars"/></td>
					<td align="left" width="60%"><form:errors path="projectName"
							cssClass="error" /></td>
				</tr>
				<tr>
					<th align="left">Package Name:</th>
					<td><form:input path="packageName" size="40" placeholder="Trigram Name min 6 to 20 chars"/></td>
					<td><form:errors path="packageName" cssClass="error" /></td>
				</tr>
				<tr>
					<th align="left">PackagingVersion:</th>
					<td><form:input path="packagingVersion" placeholder="SNAPSHOT version"/></td>
					<td><form:errors path="packagingVersion" cssClass="error" /></td>
				</tr>
				<tr>
					<th align="left">Description:</th>
					<td><form:input path="description" placeholder="Description"/></td>
					<td><form:errors path="description" cssClass="error" /></td>
				</tr>
				<tr>
					<td><br> <a class="myButton" href="/">HOME</a>
					</td>
					<td><br>
					<input type="submit" value="Next" class="myButton" /></td>
					<td></td>
				</tr>
			</form:form>
		</table>
	</div>
</body>
</html>
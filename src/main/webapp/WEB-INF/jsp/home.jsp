<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false"%>
<html>
<head>
<title>Spring Services Project Creator</title>
<link rel="stylesheet" type="text/css" href="/css/style.css">

<script type="text/javascript">
	function appendToLink() {
		var e = document.getElementById("sampleFileList");
		var selectedValue = e.options[e.selectedIndex].value;
		var generateProject = document.getElementById("generateProject");
		generateProject.setAttribute("href", generateProject.getAttribute("href") + selectedValue);
	}
</script>
</head>
<body>
	<h1 align="center">Welcome to Spring Services Project Creator</h1>

	<div align="center">
		<a class="myButton" href="/projectDetails">Click to create
			Customised Spring Project</a> <br> <br> <a class="myButton"
			href="/databaseSetup">Test Database Setup</a> <br> <br> <a
			class="myButton" href="/entityDetails">Test Entity Setup</a> <br>
		<br> <a class="myButton" href="/autoSetup">Test Auto
			Configuration Setup</a> <br>
		<h2>Generate Sample Projects</h2>
		<form:form action="loadProject" modelAttribute="autoBotForm"
			method="POST">
			<form:select id="sampleFileList" path="projectDetails.projectName"
				items="${sampleProjectList}" onchange="this.form.submit();" />
			<a class="myButton" id="generateProject" href="/runRoo">Generate Project</a>
		</form:form>
		<br> <br>
	</div>
</body>
</html>

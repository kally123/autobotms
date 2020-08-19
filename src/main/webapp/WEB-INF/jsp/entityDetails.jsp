<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/css/style.css">
<title>Entity Details Form for ${projectName}</title>
<style>
.error {
	color: red;
	font-weight: bold;
}
</style>

<script type="text/javascript">
	function addRow(tableID) {
		var table = document.getElementById(tableID);
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);
		var counts = rowCount - 1;
		var cell1 = row.insertCell(0);
		var fieldName = document.createElement("input");
		fieldName.type = "text";
		fieldName.name = "fields[" + counts + "].fieldName";
		fieldName.size = "24";
		fieldName.placeholder="Field Name min 5 -20 chars";
		cell1.appendChild(fieldName);

		var cell2 = row.insertCell(1);
		var types = document.getElementById("typeList");
		var selectType = document.createElement("select");
		selectType.name = "fields[" + counts + "].fieldType";
		for (i = 0; i < types.length; i = i + 1) {
			var option = document.createElement("option");
			option.text = types[i].value;
			option.value = types[i].value;
			selectType.add(option)
		}
		cell2.appendChild(selectType);
		
		var cell3 = row.insertCell(2);
		cell3.innerHTML = "<select onchange = 'showDiv(this,"
				+ counts
				+ ");' id='relationTypes"
				+ counts
				+ "' name='fields["
				+ counts
				+ "].relationType'><option value=''></option><option value='reference'>reference</option><option value='set'>set</option></select>";

		var div2Cell = row.insertCell(3);
		var div = document.createElement("div");

		var cell4 = row.insertCell(3);
		var constraints = [ "PrimaryKey", "ForiegnKey", "UniqueKey" ];
		var selectConstraint = document.createElement("select");
		selectConstraint.name = "fields[" + counts + "].fieldConstraints";
		var option = document.createElement("option");
		option.text = "Select";
		option.value = "";
		selectConstraint.add(option)
		for (i = 0; i < constraints.length; i = i + 1) {
			var option = document.createElement("option");
			option.text = constraints[i];
			option.value = constraints[i];
			selectConstraint.add(option)
		}
		cell4.appendChild(selectConstraint);

		var cell5 = row.insertCell(4);
		cell5.innerHTML = "<input type='button' name= 'deleteDep'" + counts
			+ " value='Delete' onclick='deleteRow(this);'/>";
	}

	function deleteRow(btn) {
		var row = btn.parentNode.parentNode;
		row.parentNode.removeChild(row);
	}
</script>
</head>
<body>
	<div align="center">
		<h2>Entity Details Form for ${projectName}</h2>

		<form:form action="entityDetails" modelAttribute="entityDetailsForm"
			method="POST">
			<form:label cssStyle="font-weight: bold;" path="entityName">Entity Type : </form:label>
			<form:select path="entityType">
				<form:option value="entity">Entity</form:option>
				<form:option value="enum">Enum</form:option>
			</form:select>
			<form:label cssStyle="font-weight: bold;" path="entityName">Entity Name : </form:label>
			<form:input path="entityName" size="24" placeholder="Field Name min 5 -20 chars"/>
			<form:errors path="entityName" cssClass="error" />
			<br>
			<br>
			<form:label cssStyle="font-weight: bold;" path="packageName">Package Name : </form:label>
			<form:input path="packageName" size="45" placeholder="What ever given, Will be appended by Project package"/>
			<form:errors path="packageName" cssClass="error" />
			<br>
			<br>
			<form:label cssStyle="font-weight: bold;" path="packageName">Force Update Entity : </form:label>
			<form:checkbox path="forceUpdateEntity" checked="checked" />
			<br>
			<br>
			<table id="fieldsTable">
				<tr>
					<td><form:label cssStyle="font-weight: bold;"
							path="fields[0].fieldName">Field Name&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</form:label></td>
					<td><form:label cssStyle="font-weight: bold;"
							path="fields[0].fieldType">Field Type&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</form:label></td>
							<td><form:label cssStyle="font-weight: bold;"
							path="fields[0].fieldType">Reference Type&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</form:label></td>
					<td><form:label cssStyle="font-weight: bold;"
							path="fields[0].fieldConstraints">Field Constraints</form:label>
					</td>
				</tr>
				<tr>
					<td><form:input path="fields[0].fieldName" size="24" placeholder="Field Name min 5 -20 chars"/> <form:errors
							path="fields[0].fieldName" cssClass="error"/></td>
					<td><form:select path="fields[0].fieldType" id="typeList">
							<form:option value="string">string</form:option>
							<form:option value="date">date</form:option>
							<form:option value="integer">integer</form:option>
							<form:option value="number">number</form:option>
							<form:options items="${enumList}"/>
							<form:options items="${daoClassNameList}"/>
						</form:select></td>
					<td><form:select path="fields[0].relationType"
							id="relationTypes" >
							<form:option value=""></form:option>
							<form:option value="reference">reference</form:option>
							<form:option value="set">set</form:option>
						</form:select></td>
					<td><form:select path="fields[0].fieldConstraints">
							<form:option value="">Select</form:option>
							<form:option value="PrimaryKey">PrimaryKey</form:option>
							<form:option value="ForiegnKey">ForiegnKey</form:option>
							<form:option value="UniqueKey">UniqueKey</form:option>
						</form:select></td>
					<td><form:button type="button" onclick="addRow('fieldsTable')">Add Field</form:button></td>
				</tr>
			</table>
			<br>
			<br>
			<a class="myButton" href="/">HOME</a>&nbsp;&nbsp;
			<input type="submit" value="Save Entity" class="myButton" />&nbsp;&nbsp;
			<a class="myButton" href="/autoSetup">Next</a>
		</form:form>
		<br>
		<a class="myButton" href="/entityRelations">Create Relations For Entity</a>&nbsp;&nbsp;
		<a class="myButton" href="/runRoo">Generate Project</a> <br> <br>
	</div>
</body>
</html>
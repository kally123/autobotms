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
<title>Create Entity Relations Form for ${projectName}</title>
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
	fieldName.size = "20";
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

	var aggregationTypes = document.getElementById("aggreagationList");
	var selectAggregationType = document.createElement("select");
	selectAggregationType.name = "fields[" + counts + "].isAggregation";
	for (i = 0; i < aggregationTypes.length; i = i + 1) {
		var aggOption = document.createElement("option");
		aggOption.text = aggregationTypes[i].value;
		aggOption.value = aggregationTypes[i].value;
		selectAggregationType.add(aggOption)
	}
	div.appendChild(selectAggregationType);

	var fieldName = document.createElement("input");
	fieldName.type = "text";
	fieldName.name = "fields[" + counts + "].joinColumnName";
	fieldName.size = "25";
	div.appendChild(fieldName);

	div.id = "referenceTypeDiv" + counts;
	div.style = "display: block";
	div2Cell.appendChild(div);

	var div5Cell = row.insertCell(4);
	var div = document.createElement("div");
	div.style = "display: none";

	var fieldName = document.createElement("input");
	fieldName.type = "text";
	fieldName.name = "fields[" + counts + "].joinTable";
	fieldName.size = "20";
	div.appendChild(fieldName);

	var fieldName = document.createElement("input");
	fieldName.type = "text";
	fieldName.name = "fields[" + counts + "].joinColumns";
	fieldName.size = "20";
	div.appendChild(fieldName);

	var fieldName = document.createElement("input");
	fieldName.type = "text";
	fieldName.name = "fields[" + counts + "].referencedColumns";
	fieldName.size = "20";
	div.appendChild(fieldName);

	var fieldName = document.createElement("input");
	fieldName.type = "text";
	fieldName.name = "fields[" + counts + "].inverseJoinColumns";
	fieldName.size = "20";
	div.appendChild(fieldName);

	var fieldName = document.createElement("input");
	fieldName.type = "text";
	fieldName.name = "fields[" + counts + "].inverseReferencedColumns";
	fieldName.size = "20";
	div.appendChild(fieldName);

	div.id = "setTypeDiv" + counts;
	div5Cell.appendChild(div);

	var cell5 = row.insertCell(5);
	cell5.innerHTML = "<input type='button' name= 'deleteDep'" + counts
			+ " value='Delete' onclick='deleteRow(this);'/>";

}

function deleteRow(btn) {
	var row = btn.parentNode.parentNode;
	row.parentNode.removeChild(row);
}

function showDiv(obj, counts) {
	var relationTypesSelectBox = document.getElementById(obj.id);
	var relationTypesSelected = relationTypesSelectBox.options[relationTypesSelectBox.selectedIndex].value;
	if (relationTypesSelected == 'reference') {
		document.getElementById('setTypeDiv' + counts).style.display = "none";
		document.getElementById('referenceTypeDiv' + counts).style.display = "block";
		document.getElementById('setTypeDiv_1' + counts).style.display = "none";
		document.getElementById('referenceTypeDiv_1' + counts).style.display = "block";
	} else {
		document.getElementById('setTypeDiv' + counts).style.display = "block";
		document.getElementById('referenceTypeDiv' + counts).style.display = "none";
		if (document.getElementById('setTypeDiv_1' + counts)) {
			var inputDiv = document.getElementById('setTypeDiv_1' + counts);
			inputDiv.style.display = "block";
			
		}
		if (document.getElementById('referenceTypeDiv_1' + counts)) {
			document.getElementById('referenceTypeDiv_1' + counts).style.display = "none";
		}
	}
	if (counts != '') {
		document.getElementById('setTypeDiv').style.display = "block";
		document.getElementById('referenceTypeDiv').style.display = "block";
	}
}
</script>

</head>
<body>
	<div align="center">
		<h2>Create Entity Relations Form</h2>

		<form:form action="entityRelations" modelAttribute="entityRelationsForm">
			<form:label cssStyle="font-weight: bold;" path="entityName">Entity Name : </form:label>
			<form:select path="entityName" items="${daoClassNameList}" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<form:button type="button" onclick="addRow('fieldsTable')">Add Field</form:button>

			<br>
			<br>
			<table id="fieldsTable" border="0">
				<tr>
					<td><form:label cssStyle="font-weight: bold;"
							path="fields[0].fieldName">Field Name&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</form:label></td>
					<td><form:label cssStyle="font-weight: bold;"
							path="fields[0].fieldName">Field Type&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</form:label></td>
					<td><form:label cssStyle="font-weight: bold;"
							path="fields[0].fieldType">Relation Type&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</form:label></td>
					<td colspan="2">
						<div id="referenceTypeDiv">
							<form:label cssStyle="font-weight: bold;"
								path="fields[0].fieldType">Is Aggregation</form:label>
							&nbsp;&nbsp;
							<form:label cssStyle="font-weight: bold;"
								path="fields[0].fieldType">Join Column Name&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</form:label>

						</div>
					</td>
					<td colspan="5">
						<div id="setTypeDiv" style="display: none">
							<form:label cssStyle="font-weight: bold;"
								path="fields[0].fieldType">Join Table&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</form:label>
							<form:label cssStyle="font-weight: bold;"
								path="fields[0].fieldType">Join Columns&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</form:label>
							<form:label cssStyle="font-weight: bold;"
								path="fields[0].fieldType">Referenced Columns&nbsp;&nbsp;&nbsp;</form:label>
							<form:label cssStyle="font-weight: bold;"
								path="fields[0].fieldType">Inverse Join Columns&nbsp;&nbsp;&nbsp;&nbsp;</form:label>
							<form:label cssStyle="font-weight: bold;"
								path="fields[0].fieldType">Inverse Referenced Columns&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</form:label>
						</div>
					</td>
				</tr>
				<tr>
					<td><form:input path="fields[0].fieldName" size="24" placeholder="Field Name min 5 -20 chars"/> <form:errors
							path="fields[0].fieldName" cssClass="error"/></td>
					<td><form:select path="fields[0].fieldType" id="typeList">
							<c:forEach items="${daoClassNameList}" var="fieldType">
								<form:option value="${fieldType}">${fieldType}</form:option>
							</c:forEach>
							<form:option value="string">string</form:option>
							<form:option value="date">date</form:option>
							<form:option value="integer">integer</form:option>
							<form:option value="number">number</form:option>
						</form:select> <form:errors path="fields[0].fieldType" cssClass="error" /></td>
					<td><form:select path="fields[0].relationType"
							id="relationTypes" onchange="showDiv(this,'');">
							<form:option value=""></form:option>
							<form:option value="reference">reference</form:option>
							<form:option value="set">set</form:option>
						</form:select></td>
					<td colspan="2">
						<div id="referenceTypeDiv_1">
							<form:select path="fields[0].isAggregation" id="aggreagationList">
								<form:option value="true">true</form:option>
								<form:option value="false">false</form:option>
							</form:select>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<form:input path="fields[0].joinColumnName" size="25"  placeholder="Mapping Table Column Name"/>
						</div>
					</td>
					<td colspan="5">
						<div id="setTypeDiv_1" style="display: none">
							<form:input path="fields[0].joinTable" size="20"  placeholder="Mapping table"/>
							<form:input path="fields[0].joinColumns" size="20"  placeholder="Mapping coulmn Name"/>
							<form:input path="fields[0].referencedColumns" size="20"  placeholder="Referenced Column"/>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<form:input path="fields[0].inverseJoinColumns" size="20"  placeholder="Default is id"/>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<form:input path="fields[0].inverseReferencedColumns" size="20" placeholder="Default is id"/>
						</div>
					</td>
				</tr>
			</table>
			<br>
			<br>
			<a class="myButton" id="home" href="/">HOME</a>
			<input type="submit" value="Next" class="myButton" />
		</form:form>
		<br>
		<a class="myButton" href="/entityRelations">Create Relations For Entity</a>&nbsp;&nbsp;
		<a class="myButton" href="/runRoo">Generate Project</a> <br> <br>
	</div>
</body>
</html>
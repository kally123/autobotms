package com.autobot.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EntityRelationField {

    @NotNull
    @Size(min = 5, max = 20, message = "Please enter Field Name with 5 to 20 characters maximum")
    private String fieldName;

    @NotNull
    private String fieldType;

    private String relationType;

    private String isAggregation;

    private String joinColumnName;

    private String joinTable;

    private String joinColumns;

    private String referencedColumns;

    private String inverseJoinColumns;

    private String inverseReferencedColumns;

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getIsAggregation() {
        return isAggregation;
    }

    public void setIsAggregation(String isAggregation) {
        this.isAggregation = isAggregation;
    }

    public String getJoinColumnName() {
        return joinColumnName;
    }

    public void setJoinColumnName(String joinColumnName) {
        this.joinColumnName = joinColumnName;
    }

    public String getJoinTable() {
        return joinTable;
    }

    public void setJoinTable(String joinTable) {
        this.joinTable = joinTable;
    }

    public String getJoinColumns() {
        return joinColumns;
    }

    public void setJoinColumns(String joinColumns) {
        this.joinColumns = joinColumns;
    }

    public String getReferencedColumns() {
        return referencedColumns;
    }

    public void setReferencedColumns(String referencedColumns) {
        this.referencedColumns = referencedColumns;
    }

    public String getInverseJoinColumns() {
        return inverseJoinColumns;
    }

    public void setInverseJoinColumns(String inverseJoinColumns) {
        this.inverseJoinColumns = inverseJoinColumns;
    }

    public String getInverseReferencedColumns() {
        return inverseReferencedColumns;
    }

    public void setInverseReferencedColumns(String inverseReferencedColumns) {
        this.inverseReferencedColumns = inverseReferencedColumns;
    }

}

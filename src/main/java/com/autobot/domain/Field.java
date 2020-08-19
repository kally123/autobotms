package com.autobot.domain;

import javax.validation.constraints.NotNull;

public class Field {

    @NotNull
    private String fieldName;

    private String fieldType;

    private String relationType;

    private String fieldConstraints;

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

    public String getFieldConstraints() {
        return fieldConstraints;
    }

    public void setFieldConstraints(String fieldConstraints) {
        this.fieldConstraints = fieldConstraints;
    }

    @Override
    public String toString() {
        return "Field [fieldName=" + fieldName + ", fieldType=" + fieldType + ", fieldConstraints=" + fieldConstraints
                + "]";
    }

}

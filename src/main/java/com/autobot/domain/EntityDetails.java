package com.autobot.domain;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class EntityDetails {

    private String entityType;

    @NotNull
    @Size(min = 5, max = 20, message = "Please enter Entity Name with 5 to 20 characters maximum")
    private String entityName;

    @NotNull
    @Size(min = 5, max = 20, message = "Please enter Package Name with 5 to 20 characters maximum")
    private String packageName = "domain";

    @Valid
    private List<Field> fields;

    private boolean forceUpdateEntity;

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public boolean isForceUpdateEntity() {
        return forceUpdateEntity;
    }

    public void setForceUpdateEntity(boolean forceUpdateEntity) {
        this.forceUpdateEntity = forceUpdateEntity;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        return "EntityDetails [entityName=" + entityName + ", packageName=" + packageName + ", fields=" + fields + "]";
    }

}

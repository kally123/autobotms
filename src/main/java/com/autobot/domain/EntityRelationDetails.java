package com.autobot.domain;

import javax.validation.Valid;
import java.util.List;

public class EntityRelationDetails {

    private String entityName;

    @Valid
    private List<EntityRelationField> fields;

    public List<EntityRelationField> getFields() {
        return fields;
    }

    public void setFields(List<EntityRelationField> fields) {
        this.fields = fields;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

}

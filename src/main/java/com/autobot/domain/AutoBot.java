package com.autobot.domain;

import com.autobot.util.AutobotUtil;

public class AutoBot {

    private AutobotUtil autobotUtil = new AutobotUtil();
    private ProjectDetails projectDetails;
    private DatabaseSetup databaseSetup;
    private EntityDetails entityDetails;
    private EntityRelationDetails entityRelationDetails;
    private AutoSetup autoSetup;

    public AutoBot() {
        this.projectDetails = new ProjectDetails();
        this.databaseSetup = new DatabaseSetup();
        this.entityDetails = autobotUtil.initializeEntityDetails();
        this.entityRelationDetails = autobotUtil.initializeEntityRelationsForm();
        this.autoSetup = new AutoSetup();
    }

    public ProjectDetails getProjectDetails() {
        return projectDetails;
    }

    public void setProjectDetails(ProjectDetails projectDetails) {
        this.projectDetails = projectDetails;
    }

    public DatabaseSetup getDatabaseSetup() {
        return databaseSetup;
    }

    public void setDatabaseSetup(DatabaseSetup databaseSetup) {
        this.databaseSetup = databaseSetup;
    }

    public EntityDetails getEntityDetails() {
        return entityDetails;
    }

    public void setEntityDetails(EntityDetails entityDetails) {
        this.entityDetails = entityDetails;
    }

    public AutoSetup getAutoSetup() {
        return autoSetup;
    }

    public void setAutoSetup(AutoSetup autoSetup) {
        this.autoSetup = autoSetup;
    }

    public EntityRelationDetails getEntityRelationDetails() {
        return entityRelationDetails;
    }

    public void setEntityRelationDetails(EntityRelationDetails entityRelationDetails) {
        this.entityRelationDetails = entityRelationDetails;
    }

}

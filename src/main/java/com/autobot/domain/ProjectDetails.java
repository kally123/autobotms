package com.autobot.domain;

import javax.validation.constraints.NotNull;

public class ProjectDetails {

    @NotNull
    private String projectName;

    private String packagingVersion;

    @NotNull
    private String packageName;

    private String description;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPackagingVersion() {
        return packagingVersion;
    }

    public void setPackagingVersion(String packagingVersion) {
        this.packagingVersion = packagingVersion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public String toString() {
        return "ProjectDetails [projectName=" + projectName + ", packagingVersion=" + packagingVersion
                + ", packageName=" + packageName + ", description=" + description + "]";
    }

}

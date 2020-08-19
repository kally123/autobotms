package com.autobot.domain;

import java.util.List;

public class AutoSetup {

    private List<String> entityNames;

    private boolean autogenerateViews;

    private boolean autogenerateJpaRepos;

    private boolean autogenerateServices;

    private boolean autogenerateJsonRemoting;

    private String autogenerateJsonRemotingPackage;

    private String language;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAutogenerateJsonRemotingPackage() {
        return autogenerateJsonRemotingPackage;
    }

    public void setAutogenerateJsonRemotingPackage(String autogenerateJsonRemotingPackage) {
        this.autogenerateJsonRemotingPackage = autogenerateJsonRemotingPackage;
    }

    public boolean isAutogenerateViews() {
        return autogenerateViews;
    }

    public void setAutogenerateViews(boolean autogenerateViews) {
        this.autogenerateViews = autogenerateViews;
    }

    public boolean isAutogenerateJpaRepos() {
        return autogenerateJpaRepos;
    }

    public void setAutogenerateJpaRepos(boolean autogenerateJpaRepos) {
        this.autogenerateJpaRepos = autogenerateJpaRepos;
    }

    public boolean isAutogenerateServices() {
        return autogenerateServices;
    }

    public void setAutogenerateServices(boolean autogenerateServices) {
        this.autogenerateServices = autogenerateServices;
    }

    public List<String> getEntityNames() {
        return entityNames;
    }

    public void setEntityNames(List<String> entityNames) {
        this.entityNames = entityNames;
    }

    public boolean isAutogenerateJsonRemoting() {
        return autogenerateJsonRemoting;
    }

    public void setAutogenerateJsonRemoting(boolean autogenerateJsonRemoting) {
        this.autogenerateJsonRemoting = autogenerateJsonRemoting;
    }

    @Override
    public String toString() {
        return "AutoSetup [entityNames=" + entityNames + ", autogenerateViews=" + autogenerateViews
                + ", autogenerateJpaRepos=" + autogenerateJpaRepos + ", autogenerateServices=" + autogenerateServices
                + ", autogenerateJsonRemoting=" + autogenerateJsonRemoting + ", autogenerateJsonRemotingPackage="
                + autogenerateJsonRemotingPackage + ", language=" + language + "]";
    }

}

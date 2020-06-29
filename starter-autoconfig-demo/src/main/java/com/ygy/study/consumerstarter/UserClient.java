package com.ygy.study.consumerstarter;

public class UserClient {

    public UserClient(){}

    public UserClient(UserProfiles userProfiles) {
        this.userProfiles = userProfiles;
    }

    private UserProfiles userProfiles;

    public UserProfiles getUserProfiles() {
        return userProfiles;
    }

    public void setUserProfiles(UserProfiles userProfiles) {
        this.userProfiles = userProfiles;
    }
}

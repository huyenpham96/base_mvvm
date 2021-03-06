package com.utildev.arch.basemvvm.model.local;

import com.utildev.arch.basemvvm.common.base.BaseModel;

public class Contact extends BaseModel {
    private String name, company, mobile, email, groupName, url;

    public Contact(String name, String company, String mobile, String email, String groupName, String url) {
        this.name = name;
        this.company = company;
        this.mobile = mobile;
        this.email = email;
        this.groupName = groupName;
        this.url = url;
    }

    public String getName() {
        return name != null ? name : "";
    }

    public String getCompany() {
        return company != null ? company : "";
    }

    public String getMobile() {
        return mobile != null ? mobile : "";
    }

    public String getEmail() {
        return email != null ? email : "";
    }

    public String getGroupName() {
        return groupName != null ? groupName : "";
    }

    public String getUrl() {
        return url != null ? url : "";
    }
}

package com.example.contactapp.models;

import java.util.ArrayList;

public class GroupModel {
    String uid;
    String group_name;
    String group_description;
    String group_image;
    ArrayList<String> group_members;

    public GroupModel() {
    }

    public GroupModel(String group_name, String group_description, String group_image) {

        this.group_name = group_name;
        this.group_description = group_description;
        this.group_image = group_image;

    }
    public GroupModel(String uid,String group_name, String group_description, String group_image) {
        this.uid = uid;
        this.group_name = group_name;
        this.group_description = group_description;
        this.group_image = group_image;

    }

    public GroupModel(String uid,String group_name, String group_description, String group_image, ArrayList<String> group_members) {
        this.uid = uid;
        this.group_name = group_name;
        this.group_description = group_description;
        this.group_image = group_image;
        this.group_members = group_members;
    }

    public String getUid() {
        return uid;
    }

    public String getGroup_name() {
        return group_name;
    }

    public String getGroup_description() {
        return group_description;
    }

    public String getGroup_image() {
        return group_image;
    }

    public ArrayList<String> getGroup_members() {
        return group_members;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public void setGroup_description(String group_description) {
        this.group_description = group_description;
    }

    public void setGroup_image(String group_image) {
        this.group_image = group_image;
    }

    public void setGroup_members(ArrayList<String> group_members) {
        this.group_members = group_members;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

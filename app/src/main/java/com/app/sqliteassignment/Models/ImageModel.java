package com.app.sqliteassignment.Models;

public class ImageModel {
    int ID;
    String location;
    String locationDescription;
    String path;
    public static int userID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocationDescription() {
        return locationDescription;
    }

    public void setLocationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static int getUserID() {
        return userID;
    }

    public static void setUserID(int userID) {
        ImageModel.userID = userID;
    }

}

package com.app.sqliteassignment.Models;

import android.graphics.Bitmap;

public class ImageModel {
    int ID;
    String location;
    String locationDescription;
    String image;
    int userID;

    public ImageModel(int ID, String location, String locationDescription, String image, int userID) {
        this.ID = ID;
        this.location = location;
        this.locationDescription = locationDescription;
        this.image = image;
        this.userID = userID;
    }

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

    public void setImage(String image) {
        this.image = image;
    }
    public String getImage() {
        return image;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

}

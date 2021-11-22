package com.app.sqliteassignment.Models;


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


    public String getLocation() {
        return location;
    }


    public String getLocationDescription() {
        return locationDescription;
    }


    public String getImage() {
        return image;
    }


}

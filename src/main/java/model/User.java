package model;

import com.fasterxml.jackson.databind.JsonNode;

public class User {
    private String id;
    private String email;
    private String givenName;
    private String familyName;
    private String date;

    public User(JsonNode userDetails) {
        if (userDetails.has("userID")) {
            setID(userDetails.get("userID").textValue());
        }

        if (userDetails.has("userEmail")) {
            setEmail(userDetails.get("userEmail").textValue());
        }

        if (userDetails.has("userGivenName")) {
            setGivenName(userDetails.get("userGivenName").textValue());
        }

        if (userDetails.has("userFamilyName")) {
            setFamilyName(userDetails.get("userFamilyName").textValue());
        }

        if (userDetails.has("created")) {
            setDate(userDetails.get("created").textValue());
        }
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
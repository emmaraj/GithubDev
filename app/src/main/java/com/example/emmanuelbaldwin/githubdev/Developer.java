package com.example.emmanuelbaldwin.githubdev;

/**
 * Created by EmmanuelBaldwin on 9/4/2017.
 */

public class Developer {
    private String developerUserName;
    private String developerImage;
    private String developerURL;

    public Developer(String developerUserName, String developerImage, String developerURL) {
        this.developerImage = developerImage;
        this.developerUserName = developerUserName;
        this.developerURL = developerURL;
    }

    public String getDeveloperURL() {
        return developerURL;
    }

    public void setDeveloperURL(String developerURL) {
        this.developerURL = developerURL;
    }


    public String getDeveloperUserName() {
        return developerUserName;
    }

    public void setDeveloperUserName(String developerUserName) {
        this.developerUserName = developerUserName;
    }

    public String getDeveloperImage() {
        return developerImage;
    }

    public void setDeveloperImage(String developerImage) {
        this.developerImage = developerImage;
    }
}

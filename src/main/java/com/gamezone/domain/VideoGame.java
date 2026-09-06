package com.gamezone.domain;
public class VideoGame extends Product{
    private String platform;
    private String genre;
    private String ageRating;


    public String getPlatform(){
        return platform;
    }
    public void setPlatform(String platform){
        this.platform=platform;
    }


    public String getGenre(){
        return genre;
    }
    public void setGenre(String genre){
        this.genre=genre;
    }


    public String getAgeRating(){
        return ageRating;
    }
    public void setAgeRating(String ageRating){
        this.ageRating=ageRating;
    }

    @Override 
    public String getDescription(){
        return getTitle() + " - " + platform + " - " + genre + " - " + " - Edad: "+ ageRating; 
    }
}

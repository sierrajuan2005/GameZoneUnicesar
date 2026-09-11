package com.gamezone.model;
/*
Represents a video game product.
 */

public class VideoGame extends Product{
    private String platform;
    private String genre;
    private String ageRating;

    /*Creates a video game product.    
    @param identifier product identifier
    @param title product title
    @param price product price
    @param availableQuantity available quantity
    @param platform video game platform
    @param genre video game genre
    @param ageRating video game age rating
     */

    public VideoGame(String identifier, String title, double price, int availableQuantity, String platform, String genre, String ageRating) {
        super(identifier, title, price, availableQuantity);
        this.platform = platform;
        this.genre = genre;
        this.ageRating = ageRating;
    }

    /*@return video game platform */
    public String getPlatform(){
        return platform;
    }
    /*@param platform new platform */
    public void setPlatform(String platform){
        this.platform=platform;
    }

    /*@return video game genre */
    public String getGenre(){
        return genre;
    }
    /*@param genre new genre */
    public void setGenre(String genre){
        this.genre=genre;
    }

    /*@return video game age rating */
    public String getAgeRating(){
        return ageRating;
    }
    /*@param ageRating new age rating */
    public void setAgeRating(String ageRating){
        this.ageRating=ageRating;
    }

    /*
    Gets the video game description.
    @return video game description
     */
    @Override 
    public String getDescription(){
        return getTitle() + " - " + platform + " - " + genre + " - " + " - Edad: "+ ageRating; 
    }
}

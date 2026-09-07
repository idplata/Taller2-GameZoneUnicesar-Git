package com.gamezone.model;

/**
 * Represents a video game product.
 */
public class VideoGame extends Product {
    
    private static final long serialVersionUID = 1L;
    
    private String platform;
    private String genre;
    private String ageRating;
    
    public VideoGame(String id, String title, double price, int quantity,
                     String platform, String genre, String ageRating) {
        super(id, title, price, quantity);
        this.platform = platform;
        this.genre = genre;
        this.ageRating = ageRating;
    }
    
    public String getPlatform() { return platform; }
    public String getGenre() { return genre; }
    public String getAgeRating() { return ageRating; }
    
    @Override
    public String getDescription() {
        return "VideoGame: " + getTitle() + 
               " | Platform: " + platform + 
               " | Genre: " + genre + 
               " | Age Rating: " + ageRating + 
               " | Price: $" + getPrice() + 
               " | Stock: " + getQuantity();
    }
}
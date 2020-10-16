package movieapp;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Alexandra
 */
public class Movie {
    private SimpleStringProperty movieName;
    private SimpleStringProperty shelfNumber;
    
    public Movie(String movieName, String shelfNumber) {
        this.movieName = new SimpleStringProperty(movieName);
        this.shelfNumber = new SimpleStringProperty(shelfNumber);
    }
    
    public String getMovieName() {
        return movieName.get();
    }
    
    public void setMovieName(SimpleStringProperty movieName) {
        this.movieName = movieName;
    }
    
    public String getShelfNumber() {
        return shelfNumber.get();
    }
    
    public void setShelfNumber(SimpleStringProperty shelfNumber) {
        this.shelfNumber = shelfNumber;
    }
}

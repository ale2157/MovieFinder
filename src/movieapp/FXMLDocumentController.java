package movieapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLDocumentController implements Initializable {
    // config table
    @FXML private TableView<Movie> tableView;
    @FXML private TableColumn<Movie, String> movieNameColumn;
    @FXML private TableColumn<Movie, String> shelfNumberColumn;
    // search box
    @FXML private final Label searchLabel = new Label();
    @FXML private TextField searchText = new TextField();
    // Observable List to store data
    private final ObservableList<Movie> movieData = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //sets up the columns in the table
        movieNameColumn.setCellValueFactory(new PropertyValueFactory<>("movieName"));
        shelfNumberColumn.setCellValueFactory(new PropertyValueFactory<>("shelfNumber"));
        // load data
        getMovieFromFile();
        // search for movie from loaded data
        searchBox();         
    }    
    
    // This method will get list of movies from file, store in Movie object, and add to our ObservableList
    public Movie getMovieFromFile() {
        Movie movie = null;
        int countLine = 0;
        int maxLine = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("MovieFile.txt")));
            String line;
            String[] array;
            while((line = br.readLine()) != null && countLine <= 38) {
                maxLine += 1;
                while(countLine <= maxLine - 1) {
                   countLine+=1;
                   array = line.split("#");            
                   movie = new Movie((array[0]), (array[1]));
                   movieData.addAll(movie);
                }
                
            }
            br.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return movie;
    }
    
    // This method will predict search as you type and allow you to find a specific movie
    public void searchBox() {
        FilteredList<Movie> filteredData = new FilteredList<>(movieData, p -> true);
        searchText.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(movie -> {
               if(newValue == null || newValue.isEmpty()) {
                   return true;
               }
                if(movie.getMovieName().toLowerCase().contains(newValue.toLowerCase())) {
                    return true;
                } else if(movie.getShelfNumber().toLowerCase().contains(newValue.toLowerCase())) {
                    return true;
                } else {
                    return false;
               }
            });
        });
        SortedList<Movie> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
    }
             
    
}

package Model;

import java.util.LinkedList;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class MovieWrapper {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "movie")
    private LinkedList<Movie> movies;

    public MovieWrapper() {}

    public MovieWrapper(LinkedList<Movie> movieList) {
        this.movies = movieList;
    }

    public LinkedList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(LinkedList<Movie> movies) {
        this.movies = movies;
    }
}


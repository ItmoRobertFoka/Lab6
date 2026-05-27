package common;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Класс-обёртка для коллекции фильмов.
 *
 * Используется для корректной сериализации и десериализации
 * списка объектов Movie при работе с XML.
 */

public class MovieWrapper {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "movie")
    private List<Movie> movies;

    public MovieWrapper() {}

    public MovieWrapper(List<Movie> movieList) {
        this.movies = movieList;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(LinkedList<Movie> movies) {
        this.movies = movies;
    }
}


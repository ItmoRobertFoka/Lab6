package Common;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Базовый класс, описывающий фильм
 * Содержит основные характеристики фильма
 */


public class Movie implements Comparable<Movie>, Serializable {
    private int id;
    private String name;
    private Coordinates coordinates;
    @JsonFormat (pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime creationDate;
    private int oscarsCount;
    private Long goldenPalmCount;
    private MovieGenre genre;
    private MpaaRating mpaaRating;
    private Person director;

    public Movie() {}

    public Movie(String name,
                 Coordinates coordinates,
                 int oscarsCount,
                 long goldenPalmCount,
                 MovieGenre genre,
                 MpaaRating mpaaRating,
                 Person director
    ){
        if (name == null) throw new IllegalArgumentException("Введите значение, имя не могут быть null");
        if (name.length() == 0) throw new IllegalArgumentException("Длинна имени должна быть больше 0");
        if (coordinates == null) throw new IllegalArgumentException("Введите значение, координаты не могут быть null");
        if (oscarsCount <= 0) throw new IllegalArgumentException("Количество оскаров должно быть больше 0");
        if (goldenPalmCount <= 0) throw new IllegalArgumentException("Количество золотых пальм должно быть больше 0");
        if (director == null) throw new IllegalArgumentException("Введите значение, поле директор не может быть null");

        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDateTime.now();
        this.oscarsCount = oscarsCount;
        this.goldenPalmCount = goldenPalmCount;
        this.genre = genre;
        this.mpaaRating = mpaaRating;
        this.director = director;
        this.creationDate = LocalDateTime.now();
    }

    public String getName(){
        return name;
    }

    public int getId(){
        return id;
    }

    public int getOscarsCount(){
        return oscarsCount;
    }

    public long getGoldenPalmCount(){
        return goldenPalmCount;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setOscarsCount(int oscarsCount) {
        this.oscarsCount = oscarsCount;
    }

    public void setGoldenPalmCount(long goldenPalmCount) {
        this.goldenPalmCount = goldenPalmCount;
    }

    public void setGenre(MovieGenre genre) {
        this.genre = genre;
    }

    public void setMpaaRating(MpaaRating mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    public void setDirector(Person director) {
        this.director = director;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }


    public Person getDirector(){
        return director;
    }

    @Override
    public String toString(){
        return ("Movie " + "\n" +
                "name: " + name + "\n" +
                "id: " + id + "\n" +
                "coordinates: " + coordinates + "\n" +
                "creationDate: " + creationDate + "\n" +
                "oscarCount: " + oscarsCount + "\n" +
                "goldenPalmCount: " + goldenPalmCount + "\n" +
                "genre: " + genre + "\n" +
                "mpaaRating: " + mpaaRating + "\n" +
                "director: " + director);
    }

    @Override
    public int compareTo(Movie anotherMovie){
        return Integer.compare(this.getOscarsCount(),anotherMovie.getOscarsCount());
    }
}

package Model;

import java.time.LocalDateTime;

public class Movie implements Comparable<Movie> {
    private int id; // Должно генерироваться, сделай
    private String name;
    private Coordinates coordinates;
    private java.time.LocalDateTime creationDate; // Должно генерироваться, сделай
    private int oscarsCount;
    private Long goldenPalmCount;
    private MovieGenre genre;
    private MpaaRating mpaaRating;
    private Person director;

    public Movie(String name,
                 Coordinates coordinates,
                 int oscarsCount,
                 Long goldenPalmCount,
                 MovieGenre genre,
                 MpaaRating mpaaRating,
                 Person director
    ){
        if (name == null) throw new IllegalArgumentException("Введите значение, имя не могут быть null");
        if (name.length() == 0) throw new IllegalArgumentException("Длинна имени должна быть больше 0");
        if (coordinates == null) throw new IllegalArgumentException("Введите значение, координаты не могут быть null");
        if (oscarsCount <= 0) throw new IllegalArgumentException("Количество оскаров должно быть больше 0");
        if (goldenPalmCount == null) throw new IllegalArgumentException("Введите значение, количество золотых пальм не может быть null");
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

package Model;

import java.util.Random;

public class Movie {
    private int id; // Должно генерироваться, сделай
    private String name;
    private Coordinates coordinates;
    private java.time.LocalDateTime creationDate; // Должно генерироваться, сделай
    private int oscarCount;
    private Long goldenPalmCount;
    private MovieGenre genre;
    private MpaaRating mpaaRating;
    private Person director;

    public Movie(int id,
                 String name,
                 Coordinates coordinates,
                 java.time.LocalDateTime creationDate,
                 int oscarCount,
                 Long goldenPalmCount,
                 MovieGenre genre,
                 MpaaRating mpaaRating,
                 Person director
    ){
        if (id <= 0) throw new IllegalArgumentException("id должен быть больше 0");
        if (name == null) throw new IllegalArgumentException("Введите значение, имя не может быть null");
        if (name.length() == 0) throw new IllegalArgumentException("Длинна имени должна быть больше 0");
        if (coordinates == null) throw new IllegalArgumentException("Введите значение, координаты не могут быть null");
        if (creationDate == null) throw new IllegalArgumentException("Введите значение, дата создания не может быть null");
        if (oscarCount <= 0) throw new IllegalArgumentException("Количество оскаров должно быть больше 0");
        if (goldenPalmCount == null) throw new IllegalArgumentException("Введите значение, количество золотых пальм не может быть null");
        if (goldenPalmCount == 0) throw new IllegalArgumentException("Количество золотых пальм должно быть больше 0");
        if (director == null) throw new IllegalArgumentException("Введите значение, поле директор не может быть null");

        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.oscarCount = oscarCount;
        this.goldenPalmCount = goldenPalmCount;
        this.genre = genre;
        this.mpaaRating = mpaaRating;
        this.director = director;
    }

    @Override
    public String toString(){
        return ("Movie:\n" +
                "id: " + id + "\n" +
                "coordinates: " + coordinates + "\n" +
                "creationDate: " + creationDate + "\n" +
                "oscarCount: " + oscarCount + "\n" +
                "goldenPalmCount: " + goldenPalmCount + "\n" +
                "genre: " + genre + "\n" +
                "mpaaRating: " + mpaaRating + "\n" +
                "director: " + director);
    }
}

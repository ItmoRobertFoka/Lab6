package Input;

import Model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class MovieMaker {
    private Scanner scanner;

    public MovieMaker(Scanner scanner){
        this.scanner = scanner;
    }

    public Movie createMovie(){
        String name = inputName();
        Coordinates coordinates = inputCoordinates();
        int oscarCount = inputOscarCount();
        long goldenPalmCount = inputGoldenPalmCount();
        MovieGenre movieGenre = inputMovieGenre();
        MpaaRating mpaaRating = inputMpaaRating();
        Person director = inputDirector();

        return new Movie(name, coordinates, oscarCount, goldenPalmCount, movieGenre, mpaaRating, director);
    }

    public String inputName(){
        System.out.println("Введите название фильма");
        String name;
        while (true){
            name = scanner.nextLine().trim();
            if (name.isEmpty()){
                System.out.println("Ошибка, длина имени должна быть больше 0");
                continue;
            }
            break;
        }

        return name;
    }


    public Coordinates inputCoordinates(){
        System.out.println("Введите координату х:");
        Float x;
        while (true){
            try {
                x = Float.parseFloat(scanner.nextLine().trim());
                if (x > 191){
                    System.out.println("Ошибка х должен быть меньше 191");
                    continue;
                }
                break;
            } catch (NumberFormatException e){
                System.out.println("Ошибка, введите число");
            }
        }

        System.out.println("Введите координату у:");
        Double y;
        while (true) {
            try {
                y = Double.parseDouble(scanner.nextLine().trim());
                if (y > 500){
                    System.out.println("Ошибка y должен быть меньше 500");
                    continue;
                }
                break;
            } catch (NumberFormatException e){
                System.out.println("Ошибка, введите число");
            }
        }

        Coordinates coordinates;
        while (true){
            try {
                coordinates = new Coordinates(x,y);
                break;
            } catch (IllegalArgumentException e){
                System.out.println("Ошибка: " + e.getMessage());
            }
        }

        return coordinates;
    }

    public int inputOscarCount(){
        System.out.println("Введите число Оскаров:");
        int oscarCount;
        while (true){
            try{
                oscarCount = Integer.parseInt(scanner.nextLine().trim());
                if (oscarCount <= 0){
                    System.out.println("Колличество Оскоров должно быть больше 0");
                    continue;
                }
                break;
            } catch (NumberFormatException e){
                System.out.println("Введите число, ошибка: " + e.getMessage());
            }
        }

        return oscarCount;
    }


    public long inputGoldenPalmCount(){
        System.out.println("Введите число Золотых пальм:");
        Long goldenPalmCount;
        while (true){
            try {
                goldenPalmCount = Long.parseLong(scanner.nextLine().trim());
                if (goldenPalmCount <= 0) {
                    System.out.println("Количество Золотых пальм должно быть больше 0");
                    continue;
                }
                break;
            } catch (NumberFormatException e){
                System.out.println("Введите число, ошибка: " + e.getMessage());
            }
        }

        return goldenPalmCount;
    }


    public MovieGenre inputMovieGenre(){
        System.out.println("Выберете из доступных жанров:");
        for (MovieGenre movieGenre : MovieGenre.values()){
            System.out.println("- " + movieGenre);
        }
        MovieGenre movieGenre;
        while (true){
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.isEmpty()){
                movieGenre = null;
                break;
            }
            try {
                movieGenre = MovieGenre.valueOf(input);
                break;
            } catch (IllegalArgumentException e){
                System.out.println("Ошибка, введите жанр из доступных");
            }
        }

        return movieGenre;
    }


    public MpaaRating inputMpaaRating(){
        System.out.println("Выберите из доступных рейтингов:");
        for (MpaaRating mpaaRating : MpaaRating.values()){
            System.out.println("- " + mpaaRating);
        }
        MpaaRating mpaaRating;
        while (true){
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.isEmpty()){
                mpaaRating = null;
                break;
            }
            try {
                mpaaRating = MpaaRating.valueOf(input);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка, введите рейтинг из доступных");
            }
        }

        return mpaaRating;
    }


    public Person inputDirector(){
        System.out.println("Введите директора:");
        Person director;

        System.out.println("Введите имя:");
        String directorName;
        while (true){
            directorName = scanner.nextLine().trim();
            if (directorName.isEmpty()){
                System.out.println("Ошибка, имя не может быть длинны 0, введите имя");
                continue;
            }
            break;
        }


        Date birthday;
        SimpleDateFormat simplDate = new SimpleDateFormat("dd.MM.yyyy");
        while (true){
            System.out.println("Введите дату рождения(в формате dd.MM.yyyy):");
            String input = scanner.nextLine().trim();
            try{
                birthday = simplDate.parse(input);
                break;
            } catch (ParseException e){
                System.out.println("Ошибка, введите дату в формате dd.MM.yyyy");
            }
        }


        System.out.println("Выберите цвет волос из предложенных:");
        for (Color color : Color.values()){
            System.out.println("- " + color);
        }
        Color color;
        while (true){
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.isEmpty()){
                color = null;
                break;
            }
            try {
                color = Color.valueOf(input);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка, введите цвет волос из доступных");
            }
        }

        System.out.println("Введите национальность из доступных:");
        for (Country country : Country.values()){
            System.out.println("- " + country);
        }
        Country nationality;
        while (true){
            String input = scanner.nextLine().trim().toUpperCase();
            try {
                nationality = Country.valueOf(input);
                break;
            } catch (IllegalArgumentException e){
                System.out.println("Ошибка, введите национальность из доступных");
            }
        }

        System.out.println("Введите локацию:");
        Location location;
        System.out.println("Введите х:");
        double locationX;
        while (true){
            try {
                locationX = Double.parseDouble(scanner.nextLine().trim());
                break;
            } catch (NumberFormatException e){
                System.out.println("Ошибка, введите значение в формате double");
            }
        }

        System.out.println("Введите y:");
        float locationY;
        while (true){
            try {
                locationY = Float.parseFloat(scanner.nextLine().trim());
                break;
            } catch (NumberFormatException e){
                System.out.println("Ошибка, введите значение в формате float");
            }
        }

        System.out.println("Введите название места:");
        String locationName;
        while (true){
            locationName = scanner.nextLine().trim();
            if (locationName.isEmpty()){
                break;
            }
            break;
        }

        location = new Location(locationX, locationY, locationName);
        director = new Person(directorName, birthday, color, nationality, location);

        return director;
    }
}

package Input;

import Managers.InputManager;
import Model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Создает объекты Movie на основе пользовательского ввода
 */
public class MovieMaker {
    private InputManager inputManager;

    public MovieMaker(InputManager inputManager){
        this.inputManager = inputManager    ;
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
        if (!inputManager.isScriptMode()) {
            System.out.println("Введите название фильма");
        }
        String name;
        while (true){
            name = inputManager.nextLine().trim();
            if (name.isEmpty()){
                System.out.println("Ошибка, длина имени должна быть больше 0");
                continue;
            }
            break;
        }

        return name;
    }


    public Coordinates inputCoordinates(){
        if (!inputManager.isScriptMode()) {
            System.out.println("Введите координату х:");
        }
        Float x;
        while (true){
            try {
                x = Float.parseFloat(inputManager.nextLine().trim());
                if (x > 191){
                    System.out.println("Ошибка х должен быть меньше 191");
                    continue;
                }
                break;
            } catch (NumberFormatException e){
                System.out.println("Ошибка, введите число");
            }
        }

        if (!inputManager.isScriptMode()) {
            System.out.println("Введите координату У:");
        }
        Double y;
        while (true) {
            try {
                y = Double.parseDouble(inputManager.nextLine().trim());
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
        if (!inputManager.isScriptMode()) {
            System.out.println("Введите число Оскаров:");
        }
        int oscarCount;
        while (true){
            try{
                oscarCount = Integer.parseInt(inputManager.nextLine().trim());
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
        if (!inputManager.isScriptMode()) {
            System.out.println("Введите число Золотых пальм:");
        }
        Long goldenPalmCount;
        while (true){
            try {
                goldenPalmCount = Long.parseLong(inputManager.nextLine().trim());
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
        if (!inputManager.isScriptMode()) {
            System.out.println("Выберете из доступных жанров:");
            for (MovieGenre movieGenre : MovieGenre.values()){
                System.out.println("- " + movieGenre);
            }
        }

        MovieGenre movieGenre;
        while (true){
            String input = inputManager.nextLine().trim().toUpperCase();
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
        if (!inputManager.isScriptMode()) {
            System.out.println("Выберите из доступных рейтингов:");
            for (MpaaRating mpaaRating : MpaaRating.values()){
                System.out.println("- " + mpaaRating);
            }
        }

        MpaaRating mpaaRating;
        while (true){
            String input = inputManager.nextLine().trim().toUpperCase();
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
        if (!inputManager.isScriptMode()) {
            System.out.println("Введите директора:");
        }
        Person director;

        if (!inputManager.isScriptMode()) {
            System.out.println("Введите имя:");
        }
        String directorName;
        while (true) {
            directorName = inputManager.nextLine().trim();
            if (directorName.isEmpty() || directorName == null) {
                System.out.println("Ошибка, имя не может быть длинны 0, введите имя");
                continue;
            }
            break;
        }

        LocalDate birthday;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        while (true){
            if (!inputManager.isScriptMode()) {
                System.out.println("Введите дату рождения(в формате dd.MM.yyyy):");
            }
            String input = inputManager.nextLine().trim();
            try{
                birthday = LocalDate.parse(input, formatter);
                if (birthday.isAfter(LocalDate.now())) {
                    System.out.println("Дата рождения не может быть из будущего");
                } else {
                    break;
                }
            } catch (DateTimeParseException e){
                System.out.println("Ошибка, введите дату в формате dd.MM.yyyy");
            }
        }

        if (!inputManager.isScriptMode()) {
            System.out.println("Выберите цвет волос из предложенных:");
            for (Color color : Color.values()){
                System.out.println("- " + color);
            }
        }

        Color color;
        while (true){
            String input = inputManager.nextLine().trim().toUpperCase();
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

        if (!inputManager.isScriptMode()) {
            System.out.println("Введите национальность из доступных:");
            for (Country country : Country.values()){
                System.out.println("- " + country);
            }
        }

        Country nationality;
        while (true){
            String input = inputManager.nextLine().trim().toUpperCase();
            try {
                nationality = Country.valueOf(input);
                break;
            } catch (IllegalArgumentException e){
                System.out.println("Ошибка, введите национальность из доступных");
            }
        }

        if (!inputManager.isScriptMode()) {
            System.out.println("Введите локацию:");
        }
        Location location;
        if (!inputManager.isScriptMode()) {
            System.out.println("Введите x:");
        }
        double locationX;
        while (true){
            try {
                locationX = Double.parseDouble(inputManager.nextLine().trim());
                break;
            } catch (NumberFormatException e){
                System.out.println("Ошибка, введите значение в формате double");
            }
        }

        if (!inputManager.isScriptMode()) {
            System.out.println("Введите y:");
        }
        float locationY;
        while (true){
            try {
                locationY = Float.parseFloat(inputManager.nextLine().trim());
                break;
            } catch (NumberFormatException e){
                System.out.println("Ошибка, введите значение в формате float");
            }
        }

        if (!inputManager.isScriptMode()) {
            System.out.println("Введите название места:");
        }
        String locationName;
        while (true){
            locationName = inputManager.nextLine().trim();
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

package Model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

/**
 * Базовый класс,представляющий человека.
 * Содержит характеристики человека.
 */

public class Person implements Comparable<Person>{
    private String name;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate birthday;
    private Color hairColor;
    private Country nationality;
    private Location location;

    public Person() {}

    public Person(String name, LocalDate birthday, Color hairColor, Country nationality, Location location){
        if (name == null) throw new IllegalArgumentException("Введите значение, имя не может быть пустым");
        if (name.length() == 0) throw new IllegalArgumentException("Имя должно быть длиннее 0 символов");
        if (nationality == null) throw new IllegalArgumentException("Введите значение,национальность должна быть установлена");
        this.name = name;
        this.birthday = birthday;
        this.hairColor = hairColor;
        this.nationality = nationality;
        this.location = location;
    }

    public Person(String name, Country nationality) {
        this.name = name;
        this.nationality = nationality;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public void setHairColor(Color color) {
        this.hairColor = color;
    }

    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public Country getNationality() {
        return nationality;
    }

    @Override
    public String toString(){
        return ("name: " + name + "\n" +
                "          birthday: " + birthday + "\n" +
                "          hair color: " + hairColor + "\n" +
                "          nationality: " + nationality + "\n"+
                "          location: " + location + "\n");

    }

    @Override
    public int compareTo(Person anotherPerson){
        if (anotherPerson == null || anotherPerson.getName() == null) return 1;
        if (this.name == null) return -1;
        return this.name.compareToIgnoreCase(anotherPerson.getName());
    }
}



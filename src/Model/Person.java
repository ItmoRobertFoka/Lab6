package Model;

public class Person {
    private String name;
    private java.util.Date birthday;
    private Color hairColor;
    private Country nationality;
    private Location location;

    public Person(String name, java.util.Date birthday, Color hairColor, Country nationality, Location location){
        if (name == null) throw new IllegalArgumentException("Введите значение, имя не может быть пустым");
        if (name.length() == 0) throw new IllegalArgumentException("Имя должно быть длиннее 0 символов");
        if (birthday == null) throw new IllegalArgumentException("Введите значение, день рождения должен быть установлен");
        if (hairColor == null) throw new IllegalArgumentException("Введите значение, цвет волос должен быть установлен");
        if (nationality == null) throw new IllegalArgumentException("Введите значение,национальность должна быть установлена");
        if (location == null) throw new IllegalArgumentException("Введите значение, локация должна быть установлена");
        this.name = name;
        this.birthday = birthday;
        this.hairColor = hairColor;
        this.nationality = nationality;
        this.location = location;
    }

    @Override
    public String toString(){
        return ("Person" + "\n" +
                "name: " + name + "\n" +
                "birthday: " + birthday + "\n" +
                "hair color: " + hairColor + "\n" +
                "nationality: " + nationality + "\n"+
                "location: " + location + "\n");

    }
}



package Model;

public class Location {
    private double x;
    private Float y;
    private String name;

    public Location(double x, Float y, String name){
        if (y == null) throw new IllegalArgumentException("Введите значение, координата y не может быть null");
        if (name == null) throw new IllegalArgumentException("Введите значение, имя должно быть установлено");
        if (name.length() == 0) throw new IllegalArgumentException("Длинна места должна быть больше 0");
        this.x = x;
        this.y = y;
        this.name = name;
    }
}

package Model;

/**
 * Базовый класс, содержит информацию о локации
 */
public class Location {
    private double x;
    private Float y;
    private String name;

    public Location() {}

    public Location(double x, Float y, String name){
        if (y == null) throw new IllegalArgumentException("Введите значение, координата y не может быть null");
        if (name.isEmpty() == false && name.length() == 0) throw new IllegalArgumentException("Длинна места должна быть больше 0");
        this.x = x;
        this.y = y;
        this.name = name;
    }


    public void setX(double x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return "x = " + x + ", y = " + y + ", location name: " + name;
    }
}

package Model;

public class Coordinates {
    private Float x;
    private double y;

    public Coordinates(Float x, double y){
        if (x == null) throw new IllegalArgumentException("Введите значение, x не может быть null");
        if (x > 191) throw new IllegalArgumentException("Значение x не может превышать 191");
        if (y > 500) throw new IllegalArgumentException("Значение y не может превышать 500");
        this.x = x;
        this.y = y;
    }
}

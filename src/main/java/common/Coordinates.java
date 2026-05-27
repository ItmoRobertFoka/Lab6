package common;

import java.io.Serializable;

/**
 * Базовый класс, описывающий локацию
 */
public class Coordinates implements Serializable {
    private Float x;
    private double y;

    public Coordinates() {}

    public Coordinates(Float x, double y){
        this.x = x;
        this.y = y;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Float getX() {
        return x;
    }

    public Double getY() {
        return y;
    }


    public String toString() {
        return "x = " + x + ", y = " + y;
    }
}

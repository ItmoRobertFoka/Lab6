package Client;

import Common.Movie;

import java.io.Serializable;

/**
 * Валидатор для проверки корректности объектов Movie.
 * Используется при загрузке из XML и вводе через скрипты.
 */

public class MovieValidator {

    public boolean validate(Movie m) {
        if (m == null) return false;

        if (m.getId() <= 0) return false;

        if (m.getName() == null || m.getName().isBlank())
            return false;

        if (m.getOscarsCount() <= 0)
            return false;

        if (m.getGoldenPalmCount() <= 0)
            return false;

        if (m.getCreationDate() == null)
            return false;

        if (m.getCoordinates() != null) {
            if (m.getCoordinates().getX() != null &&
                    m.getCoordinates().getX() > 191)
                return false;

            if (m.getCoordinates().getY() > 580)
                return false;
        }

        if (m.getDirector() == null)
            return false;

        if (m.getDirector().getName() == null ||
                m.getDirector().getName().isBlank())
            return false;

        return true;
    }
}
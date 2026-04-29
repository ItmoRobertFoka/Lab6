package Managers;

import Model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MovieValidator {

    public boolean validate(Movie m) {
        if (m == null) return false;

        if (m.getId() <= 0) return false;

        if (m.getName() == null || m.getName().isBlank()) return false;

        if (m.getCoordinates() == null || !validateCoordinates(m.getCoordinates()))
            return false;

        if (m.getCreationDate() == null ||
                m.getCreationDate().isAfter(LocalDateTime.now()))
            return false;

        if (m.getOscarsCount() <= 0) return false;

        if (m.getGoldenPalmCount() <= 0)
            return false;

        if (m.getDirector() == null || !validatePerson(m.getDirector()))
            return false;

        return true;
    }


    private boolean validateCoordinates(Coordinates c) {
        if (c == null) return false;

        if (c.getX() == null || c.getX() > 191) return false;

        if (c.getY() > 580) return false;

        return true;
    }


    private boolean validatePerson(Person p) {
        if (p == null) return false;

        if (p.getName() == null || p.getName().isBlank())
            return false;

        if (p.getNationality() == null)
            return false;

        if (p.getBirthday() != null &&
                p.getBirthday().isAfter(LocalDate.now()))
            return false;

        if (p.getLocation() != null &&
                !validateLocation(p.getLocation()))
            return false;

        return true;
    }


    private boolean validateLocation(Location l) {
        if (l == null) return false;

        if (l.getY() == null) return false;

        if (l.getName() != null && l.getName().isBlank())
            return false;

        return true;
    }
}
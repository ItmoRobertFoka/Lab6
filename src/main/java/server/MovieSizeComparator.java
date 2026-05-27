package server;

import common.Movie;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.Comparator;

public class MovieSizeComparator implements Comparator<Movie> {
    @Override
    public int compare(Movie m1, Movie m2) {
        return Integer.compare(getSize(m1), getSize(m2));
    }

    private int getSize(Movie movie) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(movie);
            oos.flush();
            return baos.toByteArray().length;
        } catch (Exception e) {
            return 0;
        }
    }
}
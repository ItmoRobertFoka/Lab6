package Server;

import Client.MovieValidator;
import Common.Movie;
import Common.MovieWrapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class XmlManager {
    List<Movie> movieList;
    int currentId = 0;
    String fileName;

    public XmlManager(String fileName, List<Movie> movieList) {
        this.movieList = movieList;
        this.fileName = fileName;
    }


    public void loadFromFile(String fileName) {

        try {
            Scanner scanner = new Scanner(new File(fileName));
            StringBuilder xml = new StringBuilder();

            while (scanner.hasNextLine()) {
                xml.append(scanner.nextLine()).append("\n");
            }

            XmlMapper mapper = new XmlMapper();
            mapper.findAndRegisterModules();

            MovieWrapper wrapper = mapper.readValue(xml.toString(), MovieWrapper.class);

            if (wrapper == null || wrapper.getMovies() == null) {
                System.out.println("Файл пустой или битый");
                return;
            }

            MovieValidator validator = new MovieValidator();

            for (Movie m : wrapper.getMovies()) {

                if (m == null) continue;

                if (!validator.validate(m)) {
                    System.out.println("INVALID: " + m.getName());
                    continue;
                }

                if (containsId(m.getId())) continue;

                movieList.add(m);
            }

            updateCurrentId();

            System.out.println("Коллекция загружена");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private boolean containsId(int id) {
        if (movieList.size() > 0) {
            for (Movie m : movieList) {
                if (m.getId() == id) {
                    return true;
                }
            }
        }
        return false;
    }


    public void updateCurrentId() {
        int max = 0;
        for (Movie movie : movieList) {
            if (movie.getId() > max) {
                max = movie.getId();
            }
        }
        currentId = max + 1;
    }

    public int getCurrentId() {
        return currentId;
    }
}

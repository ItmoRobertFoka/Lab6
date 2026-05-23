package Common;

import java.io.Serializable;
import java.util.List;

public class Response implements Serializable {
    private static final long serialVersionUID = 1L;
    private final boolean status;
    private final String message;
    private final List<Movie> movieCollection;

    public Response(boolean status, String message, List<Movie> movieCollection) {
        this.status = status;
        this.message = message;
        this.movieCollection = movieCollection;
    }

    public boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<Movie> getMovieCollection() {
        return movieCollection;
    }
}

package entities.video;

import fileio.MovieInputData;

import java.util.ArrayList;
import java.util.List;

public class Movie extends Video {
    private final int duration;
    private double rating;
    private List<Double> ratings;
    private int numberFavorite;


    public Movie(final String title, final ArrayList<String> cast,
                 final List<String> genres, final int year,
                 final int duration) {
        super(title, year, cast, genres);
        this.duration = duration;
        this.ratings = new ArrayList<>();
        this.numberFavorite = 0;
    }

    public Movie(final MovieInputData movie) {
        super(movie.getTitle(), movie.getYear(), movie.getCast(), movie.getGenres());
        this.duration = movie.getDuration();
        this.ratings = new ArrayList<>();
        this.numberFavorite = 0;
    }

    /**
     * calculeaza ratingul la movie
     */
    public void ratingMovie() {
        double sum = 0;

        for (int i = 0; i < ratings.size(); ++i) {
            sum += ratings.get(i);
        }

        if (sum == 0) {
            this.rating = 0;
        } else {
            this.rating = sum / ratings.size();
        }
    }

    /**
     * adauga la favorite
     */
    public final void addFavorite() {
        this.numberFavorite += 1;
    }

    public final int getNumberFavorite() {
        return numberFavorite;
    }

    public final double getRating() {
        return rating;
    }

    public final List<Double> getRatings() {
        return ratings;
    }

    public final int getDuration() {
        return duration;
    }

}

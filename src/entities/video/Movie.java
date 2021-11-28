package entities.video;

import fileio.MovieInputData;

import java.util.ArrayList;
import java.util.List;

public class Movie extends Video {
    private int duration;
    private double rating;
    private List<Double> ratings;

    public Movie(String title, ArrayList<String> cast,
                 List<String> genres, int year,
                 int duration) {
        super(title, year, cast, genres);
        this.duration = duration;
        this.ratings = new ArrayList<>();
    }

    public Movie(MovieInputData movie) {
        super(movie.getTitle(), movie.getYear(), movie.getCast(), movie.getGenres());
        this.duration = movie.getDuration();
        this.ratings = new ArrayList<>();
    }

    public void rating_movie() {
        double sum = 0;

        for(int i = 0; i < ratings.size(); ++i) {
            sum += ratings.get(i);
        }

        this.rating = sum / ratings.size();
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<Double> getRatings() {
        return ratings;
    }

    public void setRatings(List<Double> ratings) {
        this.ratings = ratings;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}

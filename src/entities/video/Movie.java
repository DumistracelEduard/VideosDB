package entities.video;

import fileio.MovieInputData;

import java.util.ArrayList;
import java.util.List;

public class Movie extends Video {
    private int duration;
    private double rating;
    private List<Double> ratings;
    int number_favorite;

    public Movie(String title, ArrayList<String> cast,
                 List<String> genres, int year,
                 int duration) {
        super(title, year, cast, genres);
        this.duration = duration;
        this.ratings = new ArrayList<>();
        this.number_favorite = 0;
    }

    public Movie(MovieInputData movie) {
        super(movie.getTitle(), movie.getYear(), movie.getCast(), movie.getGenres());
        this.duration = movie.getDuration();
        this.ratings = new ArrayList<>();
        this.number_favorite = 0;
    }

    public void rating_movie() {
        double sum = 0;

        for(int i = 0; i < ratings.size(); ++i) {
            sum += ratings.get(i);
        }

        if(sum == 0)
            this.rating = 0;
        else
            this.rating = sum / ratings.size();
    }

    public void add_favorite() {
        this.number_favorite += 1;
    }

    public int getNumber_favorite() {
        return number_favorite;
    }

    public void setNumber_favorite(int number_favorite) {
        this.number_favorite = number_favorite;
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

package entities;

import command.Command;
import entities.video.Movie;
import entities.video.TvShow;
import fileio.UserInputData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    private String username;
    private String subscriptionType;
    private Map<String, Integer> movie_seen;
    private ArrayList<String> favoriteMovies;
    private HashMap<String, Integer> movie_rating;
    private HashMap<String, Integer> tvshow_rating;
    private Integer number_ratings;

    public User(String username, String subscriptionType,
                Map<String, Integer> movie_seen,
                ArrayList<String> favoriteMovies) {
        this.username = username;
        this.subscriptionType = subscriptionType;
        this.movie_seen = movie_seen;
        this.favoriteMovies = favoriteMovies;
        this.movie_rating = new HashMap<>();
        this.tvshow_rating = new HashMap<>();
        this.number_ratings = 0;
    }

    public User(UserInputData user) {
        this.username = user.getUsername();
        this.subscriptionType = user.getSubscriptionType();
        this.movie_seen = user.getHistory();
        this.favoriteMovies = user.getFavoriteMovies();
        this.movie_rating = new HashMap<>();
        this.number_ratings = 0;
        this.tvshow_rating = new HashMap<>();
    }

    public void add_view(Command command) {
        Integer view;
        if(movie_seen.containsKey(command.getTitle()))
            view = movie_seen.get(command.getTitle()) + 1;
        else
            view = 1;
        movie_seen.put(command.getTitle(), view);
        command.setMessage("success -> " + command.getTitle() +" was viewed with total views of " + view);
    }

    public void add_favorite(Command command, HashMap<String, Movie> ListMovie, HashMap<String, TvShow> ListTvShow) {
        if(movie_seen.containsKey(command.getTitle())){
            for(int i = 0; i < favoriteMovies.size(); ++i) {
                if(favoriteMovies.get(i).equals(command.getTitle())){
                    command.setMessage("error -> " + command.getTitle() + " is already in favourite list");
                    return;
                }
            }
            if(ListMovie.containsKey(command.getTitle())) {
                ListMovie.get(command.getTitle()).add_favorite();
            } else if(ListTvShow.containsKey(command.getTitle())) {
                ListTvShow.get(command.getTitle()).add_favorite();
            }
            this.favoriteMovies.add(command.getTitle());
            command.setMessage("success -> " + command.getTitle() + " was added as favourite");
        } else {
            command.setMessage("error -> " + command.getTitle() + " is not seen");
        }
    }

    public void add_ratings_tvshow(Command command, HashMap<String, TvShow> ListTvShow) {
        if(movie_seen.containsKey(command.getTitle())) {
            String key = command.getTitle().concat(String.valueOf(command.getSeasonNumber()));
            if(!tvshow_rating.containsKey(key)){
                this.number_ratings += 1;
                tvshow_rating.put(key, 0);
                ListTvShow.get(command.getTitle()).add_rating(command);
                ListTvShow.get(command.getTitle()).rating_total();
                command.setMessage("success -> " + command.getTitle() + " was rated with "+ command.getGrade()+" by " + command.getUsername());
            } else {
                command.setMessage("error -> " + command.getTitle() + " has been already rated");
            }
        } else {
            command.setMessage("error -> " + command.getTitle() + " is not seen");
        }



    }

    public void add_ratings_video(Command command, HashMap<String, Movie> ListMovie){
        if(!movie_rating.containsKey(command.getTitle())) {
            if (movie_seen.containsKey(command.getTitle())) {
                this.number_ratings += 1;
                movie_rating.put(command.getTitle(), 0);
                ListMovie.get(command.getTitle()).getRatings().add(command.getGrade());
                ListMovie.get(command.getTitle()).rating_movie();
                command.setMessage("success -> " + command.getTitle() + " was rated with "+ command.getGrade()+" by " + command.getUsername());
            } else {
                command.setMessage("error -> " + command.getTitle() + " is not seen");
            }
        } else {
            command.setMessage("error -> " + command.getTitle() + " has been already rated");
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public Map<String, Integer> getMovie_seen() {
        return movie_seen;
    }

    public void setMovie_seen(Map<String, Integer> movie_seen) {
        this.movie_seen = movie_seen;
    }

    public ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }

    public void setFavoriteMovies(ArrayList<String> favoriteMovies) {
        this.favoriteMovies = favoriteMovies;
    }

    public Integer getNumber_ratings() {
        return number_ratings;
    }

    public void setNumber_ratings(Integer number_ratings) {
        this.number_ratings = number_ratings;
    }

    public HashMap<String, Integer> getMovie_rating() {
        return movie_rating;
    }

    public void setMovie_rating(HashMap<String, Integer> movie_rating) {
        this.movie_rating = movie_rating;
    }

    public HashMap<String, Integer> getTvshow_rating() {
        return tvshow_rating;
    }

    public void setTvshow_rating(HashMap<String, Integer> tvshow_rating) {
        this.tvshow_rating = tvshow_rating;
    }
}

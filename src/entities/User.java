package entities;

import command.Command;
import entities.video.Movie;
import entities.video.TvShow;
import fileio.UserInputData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    private final String username;
    private final String subscriptionType;
    private final Map<String, Integer> movieSeen;
    private final ArrayList<String> favoriteMovies;
    private final HashMap<String, Integer> movieRating;
    private HashMap<String, Integer> tvshowRating;
    private Integer numberRatings;

    public User(final String username, final String subscriptionType,
                final Map<String, Integer> movieSeen,
                final ArrayList<String> favoriteMovies) {
        this.username = username;
        this.subscriptionType = subscriptionType;
        this.movieSeen = movieSeen;
        this.favoriteMovies = favoriteMovies;
        this.movieRating = new HashMap<>();
        this.tvshowRating = new HashMap<>();
        this.numberRatings = 0;
    }

    public User(final UserInputData user) {
        this.username = user.getUsername();
        this.subscriptionType = user.getSubscriptionType();
        this.movieSeen = user.getHistory();
        this.favoriteMovies = user.getFavoriteMovies();
        this.movieRating = new HashMap<>();
        this.numberRatings = 0;
        this.tvshowRating = new HashMap<>();
    }

    /**
     * Adauga de cate ori s-a vazut un video in movieSeen
     */
    public void addView(final Command command) {
        Integer view;
        if (movieSeen.containsKey(command.getTitle())) {
            view = movieSeen.get(command.getTitle()) + 1;
        } else {
            view = 1;
        }
        movieSeen.put(command.getTitle(), view);
        command.setMessage("success -> " + command.getTitle()
                + " was viewed with total views of " + view);
    }

    /**
     * Adauga un video la favorite daca este vazut. Daca este deja favorit da mesajul corespunzator
     * sau daca nu este vizualizat. Daca este adaugat crestem numarul de vizualizari ale
     * videoclipului.
     */
    public void addFavorite(final Command command, final HashMap<String, Movie> listMovie,
                            final HashMap<String, TvShow> listTvShow) {
        if (movieSeen.containsKey(command.getTitle())) {
            for (int i = 0; i < favoriteMovies.size(); ++i) {
                if (favoriteMovies.get(i).equals(command.getTitle())) {
                    command.setMessage("error -> " + command.getTitle()
                            + " is already in favourite list");
                    return;
                }
            }
            if (listMovie.containsKey(command.getTitle())) {
                listMovie.get(command.getTitle()).addFavorite();
            } else if (listTvShow.containsKey(command.getTitle())) {
                listTvShow.get(command.getTitle()).addFavorite();
            }
            this.favoriteMovies.add(command.getTitle());
            command.setMessage("success -> " + command.getTitle() + " was added as favourite");
        } else {
            command.setMessage("error -> " + command.getTitle() + " is not seen");
        }
    }

    /**
     * In aceasta functie adaugam la serial rating pe  un sezon din lista de seriale daca nu a mai
     * facut asta acest utilizator.
     * @param command lista de comenzi
     * @param listTvShow lista de seriale
     */
    public void addRatingsTvshow(final Command command, final HashMap<String, TvShow> listTvShow) {
        if (movieSeen.containsKey(command.getTitle())) {
            String key = command.getTitle().concat(String.valueOf(command.getSeasonNumber()));
            if (!tvshowRating.containsKey(key)) {
                this.numberRatings += 1;
                tvshowRating.put(key, 0);
                listTvShow.get(command.getTitle()).addRating(command);
                listTvShow.get(command.getTitle()).ratingTotal();
                command.setMessage("success -> " + command.getTitle() + " was rated with "
                         + command.getGrade() + " by " + command.getUsername());
            } else {
                command.setMessage("error -> " + command.getTitle() + " has been already rated");
            }
        } else {
            command.setMessage("error -> " + command.getTitle() + " is not seen");
        }
    }

    /**
     * Adauga rating la movie daca nu a mai facut asta acest utilizator.
     * @param command comanda
     * @param listMovie lista de filme
     */
    public void addRatingsVideo(final Command command, final HashMap<String, Movie> listMovie) {
        if (!movieRating.containsKey(command.getTitle())) {
            if (movieSeen.containsKey(command.getTitle())) {
                this.numberRatings += 1;
                movieRating.put(command.getTitle(), 0);
                listMovie.get(command.getTitle()).getRatings().add(command.getGrade());
                listMovie.get(command.getTitle()).ratingMovie();
                command.setMessage("success -> " + command.getTitle() + " was rated with "
                        + command.getGrade() + " by " + command.getUsername());
            } else {
                command.setMessage("error -> " + command.getTitle() + " is not seen");
            }
        } else {
            command.setMessage("error -> " + command.getTitle() + " has been already rated");
        }
    }

    public final String getUsername() {
        return username;
    }

    public final String getSubscriptionType() {
        return subscriptionType;
    }

    public final Map<String, Integer> getMovieSeen() {
        return movieSeen;
    }

    public final ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }

    public final Integer getNumberRatings() {
        return numberRatings;
    }
}

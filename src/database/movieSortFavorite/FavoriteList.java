package database.movieSortFavorite;

import command.Command;
import entities.video.Movie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class FavoriteList {
    public ArrayList<FavoriteMovie> favoriteMovies;

    public FavoriteList() {
        favoriteMovies = new ArrayList<>();
    }

    public void sort_list(Command command) {
        Comparator<FavoriteMovie> movieComparatorLexico = new Comparator<FavoriteMovie>() {
            @Override
            public int compare(FavoriteMovie o1, FavoriteMovie o2) {
                return o1.getName().compareTo(o2.getName());
            }
        };
        Comparator<FavoriteMovie> movieComparatorFavorite = new Comparator<FavoriteMovie>() {
            @Override
            public int compare(FavoriteMovie o1, FavoriteMovie o2) {
                return o1.getNumber() < o2.getNumber() ? -1
                        : o1.getNumber() > o2.getNumber()? 1
                        : 0;
            }
        };
        this.favoriteMovies.sort(movieComparatorLexico);
        this.favoriteMovies.sort(movieComparatorFavorite);

        if(command.getSortType().equals("desc")) {
            Collections.reverse(this.favoriteMovies);
        }

        if(favoriteMovies.size() < command.getNumber()) {
            command.setNumber(favoriteMovies.size());
        }
        ArrayList<FavoriteMovie> copyArray = new ArrayList<>();
        for(int i = 0; i < command.getNumber(); ++i) {
            copyArray.add(favoriteMovies.get(i));
        }

        this.favoriteMovies = copyArray;
    }

    public void generate_list(HashMap<String, Movie> ListMovie, Command command) {
        for(String key : ListMovie.keySet()) {
            Movie movie = ListMovie.get(key);
            if (movie.getRating() == 0) {
                continue;
            }
            if(movie.genre_exist(command) == 0 || movie.year_exist(command) == 0) {
                continue;
            }
            FavoriteMovie favoriteMovie = new FavoriteMovie(movie.getTitle(), movie.getNumber_favorite());
            this.favoriteMovies.add(favoriteMovie);
        }
    }

    @Override
    public String toString() {
        return favoriteMovies + "]";
    }

    public void message(Command command) {
        command.setMessage("Query result: "  + favoriteMovies.toString());
    }
}

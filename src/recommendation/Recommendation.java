package recommendation;

import command.Command;
import entities.Actor;
import entities.User;
import entities.video.Movie;
import entities.video.TvShow;

import java.util.ArrayList;
import java.util.HashMap;

public class Recommendation {
    private ArrayList<RecommendationObject> objectArrayList;
    private HashMap<String, Integer> genresTop;
    private HashMap<String, User> ListUser;
    private HashMap<String, Movie> ListMovie;
    private HashMap<String, TvShow> ListShows;
    private HashMap<String, Actor> ListActor;
    private ArrayList<Movie> movieRecom;
    private ArrayList<TvShow> tvShowsRecom;


    public Recommendation(ArrayList<RecommendationObject> objectsArrayList, HashMap<String, User> ListUser,
                          HashMap<String, Movie> ListMovie,
                          HashMap<String, TvShow> ListShows,ArrayList<TvShow> tvShowsRecom, ArrayList<Movie> movieRecom) {
        this.genresTop = new HashMap<>();
        this.ListShows = ListShows;
        this.ListMovie = ListMovie;
        this.ListUser = ListUser;
        this.objectArrayList = objectsArrayList;
        this.tvShowsRecom = tvShowsRecom;
        this.movieRecom = movieRecom;
    }

    public void RecommendationRun(Command command) {
        if(command.getType().equals("standard")) {
            Standard standard = new Standard(ListUser);
            standard.StandardRun(command, tvShowsRecom, movieRecom);
        } else if(command.getType().equals("best_unseen")) {
            BestUnseen bestUnseen = new BestUnseen(ListUser, objectArrayList, ListMovie, ListShows);
            bestUnseen.updateRating();
            bestUnseen.BestUnseenRun(command);
        } else if(command.getType().equals("popular")) {
            if(ListUser.get(command.getUsername()).getSubscriptionType().equals("PREMIUM")) {
                Popular popular = new Popular(objectArrayList);
                popular.sortGenres(command, ListUser, movieRecom, tvShowsRecom);
                return;
            }
            command.setMessage("PopularRecommendation cannot be applied!");
        } else if(command.getType().equals("favorite")) {
            if(ListUser.get(command.getUsername()).getSubscriptionType().equals("PREMIUM")) {
                FavoriteRecommendation favoriteRecommendation = new FavoriteRecommendation(objectArrayList, ListUser,
                        ListMovie, ListShows);
                favoriteRecommendation.updateList();
                favoriteRecommendation.FavoriteRun(command);
                return;
            }
            command.setMessage("FavoriteRecommendation cannot be applied!");
        } else if(command.getType().equals("search")) {
            if(ListUser.get(command.getUsername()).getSubscriptionType().equals("PREMIUM")) {
                Search search = new Search(objectArrayList, ListUser, ListMovie, ListShows);
                search.SearchRun(command);
                return;
            }
            command.setMessage("SearchRecommendation cannot be applied!");
        }
    }
}

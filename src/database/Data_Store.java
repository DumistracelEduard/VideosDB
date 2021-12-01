package database;

import command.Command;
import database.FilterDescription.FilterDescription;
import database.Longest.ListLongestMovie;
import database.Longest.ListLongestShow;
import database.MostViewed.ListViewsMovie;
import database.MostViewed.ListViewsShows;
import database.NumberOfRatings.ListUserRatings;
import database.QueryFavorite.ListFavoriteMovie;
import database.QueryFavorite.ListFavoriteShows;
import entities.Actor;
import entities.User;
import entities.video.Movie;
import entities.video.TvShow;
import fileio.*;
import recommendation.Recommendation;
import recommendation.RecommendationObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static utils.Utils.stringToAwards;

public class Data_Store {
    private HashMap<String, TvShow> ListTvShow;
    private HashMap<String, Movie> ListMovie;
    private HashMap<String, Actor> ListActor;
    private HashMap<String, User> ListUser;
    private HashMap<Integer, Command> ListCommands;
    private ArrayList<RecommendationObject> objectsArrayList;
    private ArrayList<Movie> movieRecom;
    private ArrayList<TvShow> tvShowsRecom;

    public Data_Store() {
        this.ListActor = new HashMap<>();
        this.ListTvShow = new HashMap<>();
        this.ListUser = new HashMap<>();
        this.ListMovie = new HashMap<>();
        this.ListCommands = new HashMap<>();
        this.objectsArrayList = new ArrayList<>();
        this.movieRecom = new ArrayList<>();
        this.tvShowsRecom = new ArrayList<>();
    }

    public void AddTvShow(List<SerialInputData> serials) {
        for (SerialInputData serialInputData : serials) {
            TvShow tvShow = new TvShow(serialInputData);
            RecommendationObject object = new RecommendationObject(tvShow.getTitle(), tvShow.getGenres());
            this.objectsArrayList.add(object);
            this.ListTvShow.put(tvShow.getTitle(), tvShow);
            this.tvShowsRecom.add(tvShow);
        }
    }

    public void AddMovie(List<MovieInputData> movies) {
        for(MovieInputData movieInputData : movies) {
            Movie movie = new Movie(movieInputData);
            RecommendationObject object = new RecommendationObject(movie.getTitle(), movie.getGenres());
            this.objectsArrayList.add(object);
            this.ListMovie.put(movie.getTitle(), movie);
            this.movieRecom.add(movie);
        }
    }

    public void AddActor(List<ActorInputData> actors) {
        for(ActorInputData actorInputData : actors) {
            Actor actor = new Actor(actorInputData);
            this.ListActor.put(actor.getName(), actor);
        }
    }

    public void AddUser(List<UserInputData> users) {
        for(UserInputData userInputData : users) {
            User user = new User(userInputData);
            this.ListUser.put(user.getUsername(), user);
        }
    }

    public void AddCommands(List<ActionInputData> commands) {
        for(ActionInputData actionInputData : commands) {
            Command command = new Command(actionInputData);
            this.ListCommands.put(command.getActionid(), command);
        }
    }

    public void whichCommands(){
        for(int key : ListCommands.keySet()) {
            if(ListCommands.get(key).getActionType().equals("command")) {
                commandsRun(ListCommands.get(key));
            }else if(ListCommands.get(key).getActionType().equals("query")) {
                queryRun(ListCommands.get(key));
            } else if(ListCommands.get(key).getActionType().equals("recommendation")) {
                Recommendation recommendation = new Recommendation(objectsArrayList, ListUser, ListMovie, ListTvShow, tvShowsRecom, movieRecom);
                recommendation.RecommendationRun(ListCommands.get(key));
            }
        }
    }

    public void commandsRun(Command command) {
        if(command.getType().equals("favorite")) {
            ListUser.get(command.getUsername()).add_favorite(command, ListMovie, ListTvShow);
        }

        if(command.getType().equals("view")) {
            ListUser.get(command.getUsername()).add_view(command);
        }

        if(command.getType().equals("rating")) {
            if(ListMovie.containsKey(command.getTitle())) {
                ListUser.get(command.getUsername()).add_ratings_video(command, ListMovie);
            } else if(ListTvShow.containsKey(command.getTitle())) {
                ListUser.get(command.getUsername()).add_ratings_tvshow(command, ListTvShow);
            }
        }
    }

    public void queryRun(Command command){
        if(command.getObjectType().equals("actors")) {
            queryRunActor(command);
        } else if(command.getObjectType().equals("movies") || command.getObjectType().equals("shows")) {
            queryRunVideo(command);
        } else if(command.getObjectType().equals("users")) {
            ListUserRatings listUserRatings = new ListUserRatings(ListUser);
            listUserRatings.sort(command);
        }
    }

    public void awards(Command command) {
        AwardsSort awardsSort = new AwardsSort();
        for(String key : ListActor.keySet()) {
            int ok = 0;
            for(int i = 0; i < command.getFilters().get(3).size(); ++i) {
                if (!ListActor.get(key).getAwards().containsKey(stringToAwards(command.getFilters().get(3).get(i)))) {
                    ok = 1;
                    break;
                }
            }
            if(ok == 0) {
                ActorAwards actorAwards = new ActorAwards(ListActor.get(key).getName(), ListActor.get(key).getNumber_awards());
                awardsSort.getActorAwards().add(actorAwards);
            }
        }
        awardsSort.sort(command);
    }

    public void queryRatings(Command command) {
        RatingSort ratingSort = new RatingSort();
        if(command.getObjectType().equals("movies")) {
            for (String key : ListMovie.keySet()) {
                if (ListMovie.get(key).getRating() == 0) {
                    continue;
                }
                if(ListMovie.get(key).genre_exist(command) == 0 || ListMovie.get(key).year_exist(command) == 0) {
                    continue;
                }
                VideoRating videoRating = new VideoRating(ListMovie.get(key).getTitle(), ListMovie.get(key).getRating());
                ratingSort.getVideoRatings().add(videoRating);
            }
            ratingSort.sort_rating(command);
            ratingSort.message(command);
            return;
        }
        for (String key : ListTvShow.keySet()) {
            if(ListTvShow.get(key).getRating() == 0) {
                continue;
            }
            if(ListTvShow.get(key).genre_exist(command) == 0) {
                continue;
            }
            if(ListTvShow.get(key).year_exist(command) == 0) {
                continue;
            }
            VideoRating videoRating = new VideoRating(ListTvShow.get(key).getTitle(), ListTvShow.get(key).getRating());
            ratingSort.getVideoRatings().add(videoRating);
        }
        ratingSort.sort_rating(command);
        ratingSort.message(command);
    }

    public void favorite() {
        for(String key : ListUser.keySet()) {
            User user = ListUser.get(key);
            for(int i = 0; i < user.getFavoriteMovies().size(); ++i) {
                if(ListMovie.containsKey(user.getFavoriteMovies().get(i))) {
                    ListMovie.get(user.getFavoriteMovies().get(i)).add_favorite();
                }
                if(ListTvShow.containsKey(user.getFavoriteMovies().get(i))) {
                    ListTvShow.get(user.getFavoriteMovies().get(i)).add_favorite();
                }
            }
        }
    }

    public void viewedCalc() {
        for(String key : ListUser.keySet()) {
            User user = ListUser.get(key);
            for(String key2 : user.getMovie_seen().keySet()) {
                if(ListMovie.containsKey(key2)) {
                    ListMovie.get(key2).addViews(user.getMovie_seen().get(key2));
                }
                if(ListTvShow.containsKey(key2)) {
                    ListTvShow.get(key2).addViews(user.getMovie_seen().get(key2));
                }
            }
        }
    }

    public void queryRunVideo(Command command) {
        if ((command.getCriteria().equals("ratings"))) {
            queryRatings(command);
        } else if (command.getCriteria().equals("favorite")) {
            favorite();
            if(command.getObjectType().equals("movies")) {
                ListFavoriteMovie favoriteMovie= new ListFavoriteMovie();
                favoriteMovie.ListGenerate(command, ListMovie);
                favoriteMovie.sort(command);
            } else {
                ListFavoriteShows favoriteList = new ListFavoriteShows();
                favoriteList.ListGenerate( command, ListTvShow);
                favoriteList.sort(command);
            }
        } else if (command.getCriteria().equals("longest")) {
            if(command.getObjectType().equals("movies")) {
                ListLongestMovie listLongestMovie = new ListLongestMovie(ListMovie, command);
                listLongestMovie.sortLongest(command);
            } else {
                ListLongestShow listLongestShow = new ListLongestShow(ListTvShow, command);
                listLongestShow.sortLongest(command);
            }
        } else if (command.getCriteria().equals("most_viewed")) {
            viewedCalc();
            if(command.getObjectType().equals("movies")) {
                ListViewsMovie listViewsMovie = new ListViewsMovie();
                listViewsMovie.generateList(ListMovie, command);
                listViewsMovie.listSort(command);
            } else {
                ListViewsShows listViewsShows = new ListViewsShows();
                listViewsShows.generateList(ListTvShow, command);
                listViewsShows.listSort(command);
            }
        }
    }

    public void queryRunActor(Command command) {
        if(command.getCriteria().equals("average")) {
            for(String key : ListActor.keySet()) {
                ListActor.get(key).average_get(ListMovie, ListTvShow);
            }
            AverageSort averageSort = new AverageSort(ListActor, command);
            averageSort.message(command);
        } else if(command.getCriteria().equals("awards")) {
            awards(command);
        } else if(command.getCriteria().equals("filter_description")) {
            FilterDescription filterDescription = new FilterDescription();
            filterDescription.sort(ListActor, command);
        }
    }

    public HashMap<Integer, Command> getListCommands() {
        return ListCommands;
    }

    public void setListCommands(HashMap<Integer, Command> listCommands) {
        ListCommands = listCommands;
    }

    public HashMap<String, TvShow> getListTvShow() {
        return ListTvShow;
    }

    public void setListTvShow(HashMap<String, TvShow> listTvShow) {
        ListTvShow = listTvShow;
    }

    public HashMap<String, Movie> getListMovie() {
        return ListMovie;
    }

    public void setListMovie(HashMap<String, Movie> listMovie) {
        ListMovie = listMovie;
    }

    public HashMap<String, Actor> getListActor() {
        return ListActor;
    }

    public void setListActor(HashMap<String, Actor> listActor) {
        ListActor = listActor;
    }

    public HashMap<String, User> getListUser() {
        return ListUser;
    }

    public void setListUser(HashMap<String, User> listUser) {
        ListUser = listUser;
    }
}

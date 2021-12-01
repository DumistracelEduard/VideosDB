package database;

import actor.ActorsAwards;
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
import fileio.ActionInputData;
import fileio.ActorInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import recommendation.Recommendation;
import recommendation.RecommendationObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utils.Utils.stringToAwards;

public class DataStore {
    private HashMap<String, TvShow> listTvShow;
    private HashMap<String, Movie> listMovie;
    private HashMap<String, Actor> listActor;
    private HashMap<String, User> listUser;
    private HashMap<Integer, Command> listCommands;
    private ArrayList<RecommendationObject> objectsArrayList;
    private ArrayList<Movie> movieRecom;
    private ArrayList<TvShow> tvShowsRecom;

    public DataStore() {
        this.listActor = new HashMap<>();
        this.listTvShow = new HashMap<>();
        this.listUser = new HashMap<>();
        this.listMovie = new HashMap<>();
        this.listCommands = new HashMap<>();
        this.objectsArrayList = new ArrayList<>();
        this.movieRecom = new ArrayList<>();
        this.tvShowsRecom = new ArrayList<>();
    }

    /**
     * Salveaza serialele
     * @param serials
     */
    public void addTvShow(final List<SerialInputData> serials) {
        for (SerialInputData serialInputData : serials) {
            TvShow tvShow = new TvShow(serialInputData);
            RecommendationObject object = new RecommendationObject(tvShow.getTitle(),
                    tvShow.getGenres());
            this.objectsArrayList.add(object);
            this.listTvShow.put(tvShow.getTitle(), tvShow);
            this.tvShowsRecom.add(tvShow);
        }
    }

    /**
     * salveaza filmele
     * @param movies
     */
    public void addMovie(final List<MovieInputData> movies) {
        for (MovieInputData movieInputData : movies) {
            Movie movie = new Movie(movieInputData);
            RecommendationObject object = new RecommendationObject(movie.getTitle(),
                    movie.getGenres());
            this.objectsArrayList.add(object);
            this.listMovie.put(movie.getTitle(), movie);
            this.movieRecom.add(movie);
        }
    }

    /**
     * salveaza actorii
     * @param actors
     */
    public void addActor(final List<ActorInputData> actors) {
        for (ActorInputData actorInputData : actors) {
            Actor actor = new Actor(actorInputData);
            this.listActor.put(actor.getName(), actor);
        }
    }

    /**
     * salveaza userii
     * @param users
     */
    public void addUser(final List<UserInputData> users) {
        for (UserInputData userInputData : users) {
            User user = new User(userInputData);
            this.listUser.put(user.getUsername(), user);
        }
    }

    /**
     * salveaza comentariile
     * @param commands
     */
    public void addCommands(final List<ActionInputData> commands) {
        for (ActionInputData actionInputData : commands) {
            Command command = new Command(actionInputData);
            this.listCommands.put(command.getActionid(), command);
        }
    }

    /**
     * vede tipul comandei
     */
    public void whichCommands() {
        for (int key : listCommands.keySet()) {
            if (listCommands.get(key).getActionType().equals("command")) {
                commandsRun(listCommands.get(key));
            } else if (listCommands.get(key).getActionType().equals("query")) {
                queryRun(listCommands.get(key));
            } else if (listCommands.get(key).getActionType().equals("recommendation")) {
                Recommendation recommendation = new Recommendation(objectsArrayList, listUser,
                        listMovie, listTvShow, tvShowsRecom, movieRecom);
                recommendation.recommendationRun(listCommands.get(key));
            }
        }
    }

    /**
     * vede ce comanda sa ruleze
     * @param command
     */
    public void commandsRun(final Command command) {
        if (command.getType().equals("favorite")) {
            listUser.get(command.getUsername()).addFavorite(command, listMovie, listTvShow);
        }

        if (command.getType().equals("view")) {
            listUser.get(command.getUsername()).addView(command);
        }

        if (command.getType().equals("rating")) {
            if (listMovie.containsKey(command.getTitle())) {
                listUser.get(command.getUsername()).addRatingsVideo(command, listMovie);
            } else if (listTvShow.containsKey(command.getTitle())) {
                listUser.get(command.getUsername()).addRatingsTvshow(command, listTvShow);
            }
        }
    }

    /**
     * vede ce query ruleaza
     * @param command
     */
    public void queryRun(final Command command) {
        if (command.getObjectType().equals("actors")) {
            queryRunActor(command);
        } else if (command.getObjectType().equals("movies")
                || command.getObjectType().equals("shows")) {
            queryRunVideo(command);
        } else if (command.getObjectType().equals("users")) {
            ListUserRatings listUserRatings = new ListUserRatings(listUser);
            listUserRatings.sort(command);
        }
    }

    public static final int VAR = 3;
    /**
     * Creeaza lista de cu actoriicare au premiile mentionate in filtru si il sorteaza
     * @param command
     */
    public void awards(final Command command) {
        AwardsSort awardsSort = new AwardsSort();
        for (String key : listActor.keySet()) {
            int ok = 0;
            for (int i = 0; i < command.getFilters().get(VAR).size(); ++i) {
                Actor actor = listActor.get(key);
                Map<ActorsAwards, Integer> mapAwards = actor.getAwards();
                if (!mapAwards.containsKey(stringToAwards(command.getFilters().get(VAR).get(i)))) {
                    ok = 1;
                    break;
                }
            }
            if (ok == 0) {
                ActorAwards actorAwards = new ActorAwards(listActor.get(key).getName(),
                        listActor.get(key).getNumberAwards());
                awardsSort.getActorAwards().add(actorAwards);
            }
        }
        awardsSort.sort(command);
    }

    /**
     * creeaza lista de videoclipuri dupa filtre si o sorteaza
     * @param command
     */
    public void queryRatings(final Command command) {
        RatingSort ratingSort = new RatingSort();
        if (command.getObjectType().equals("movies")) {
            for (String key : listMovie.keySet()) {
                if (listMovie.get(key).getRating() == 0) {
                    continue;
                }
                if (listMovie.get(key).genreExist(command) == 0
                        || listMovie.get(key).yearExist(command) == 0) {
                    continue;
                }
                VideoRating videoRating = new VideoRating(listMovie.get(key).getTitle(),
                        listMovie.get(key).getRating());
                ratingSort.getVideoRatings().add(videoRating);
            }
            ratingSort.sortRating(command);
            ratingSort.message(command);
            return;
        }
        for (String key : listTvShow.keySet()) {
            if (listTvShow.get(key).getRating() == 0) {
                continue;
            }
            if (listTvShow.get(key).genreExist(command) == 0) {
                continue;
            }
            if (listTvShow.get(key).yearExist(command) == 0) {
                continue;
            }
            VideoRating videoRating = new VideoRating(listTvShow.get(key).getTitle(),
                    listTvShow.get(key).getRating());
            ratingSort.getVideoRatings().add(videoRating);
        }
        ratingSort.sortRating(command);
        ratingSort.message(command);
    }

    /**
     * calculeaza nr de favorite pt fiecare video
     */
    public void favorite() {
        for (String key : listUser.keySet()) {
            User user = listUser.get(key);
            for (int i = 0; i < user.getFavoriteMovies().size(); ++i) {
                if (listMovie.containsKey(user.getFavoriteMovies().get(i))) {
                    listMovie.get(user.getFavoriteMovies().get(i)).addFavorite();
                }
                if (listTvShow.containsKey(user.getFavoriteMovies().get(i))) {
                    listTvShow.get(user.getFavoriteMovies().get(i)).addFavorite();
                }
            }
        }
    }

    /**
     * calculeaza vizualizarile pt fiecare video
     */
    public void viewedCalc() {
        for (String key : listUser.keySet()) {
            User user = listUser.get(key);
            for (String key2 : user.getMovieSeen().keySet()) {
                if (listMovie.containsKey(key2)) {
                    listMovie.get(key2).addViews(user.getMovieSeen().get(key2));
                }
                if (listTvShow.containsKey(key2)) {
                    listTvShow.get(key2).addViews(user.getMovieSeen().get(key2));
                }
            }
        }
    }

    /**
     * vede ce comanda de query pe video sa ruleze
     * @param command
     */
    public void queryRunVideo(final Command command) {
        if ((command.getCriteria().equals("ratings"))) {
            queryRatings(command);
        } else if (command.getCriteria().equals("favorite")) {
            favorite();
            if (command.getObjectType().equals("movies")) {
                ListFavoriteMovie favoriteMovie = new ListFavoriteMovie();
                favoriteMovie.listGenerate(command, listMovie);
                favoriteMovie.sort(command);
            } else {
                ListFavoriteShows favoriteList = new ListFavoriteShows();
                favoriteList.listGenerate(command, listTvShow);
                favoriteList.sort(command);
            }
        } else if (command.getCriteria().equals("longest")) {
            if (command.getObjectType().equals("movies")) {
                ListLongestMovie listLongestMovie = new ListLongestMovie(listMovie, command);
                listLongestMovie.sortLongest(command);
            } else {
                ListLongestShow listLongestShow = new ListLongestShow(listTvShow, command);
                listLongestShow.sortLongest(command);
            }
        } else if (command.getCriteria().equals("most_viewed")) {
            viewedCalc();
            if (command.getObjectType().equals("movies")) {
                ListViewsMovie listViewsMovie = new ListViewsMovie();
                listViewsMovie.generateList(listMovie, command);
                listViewsMovie.listSort(command);
            } else {
                ListViewsShows listViewsShows = new ListViewsShows();
                listViewsShows.generateList(listTvShow, command);
                listViewsShows.listSort(command);
            }
        }
    }

    /**
     * ce comanda query pe actor sa ruleze
     * @param command
     */
    public void queryRunActor(final Command command) {
        if (command.getCriteria().equals("average")) {
            for (String key : listActor.keySet()) {
                listActor.get(key).averageGet(listMovie, listTvShow);
            }
            AverageSort averageSort = new AverageSort();
            averageSort.averageSort(listActor, command);
            averageSort.message(command);
        } else if (command.getCriteria().equals("awards")) {
            awards(command);
        } else if (command.getCriteria().equals("filter_description")) {
            FilterDescription filterDescription = new FilterDescription();
            filterDescription.sort(listActor, command);
        }
    }

    public final HashMap<Integer, Command> getListCommands() {
        return listCommands;
    }
}

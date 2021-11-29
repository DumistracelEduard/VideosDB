package database;

import command.Command;
import entities.Actor;
import entities.User;
import entities.video.Movie;
import entities.video.TvShow;
import fileio.*;
import utils.Utils;

import java.util.HashMap;
import java.util.List;

import static utils.Utils.stringToAwards;

public class Data_Store {
    private HashMap<String, TvShow> ListTvShow;
    private HashMap<String, Movie> ListMovie;
    private HashMap<String, Actor> ListActor;
    private HashMap<String, User> ListUser;
    private HashMap<Integer, Command> ListCommands;

    public Data_Store() {
        this.ListActor = new HashMap<>();
        this.ListTvShow = new HashMap<>();
        this.ListUser = new HashMap<>();
        this.ListMovie = new HashMap<>();
        this.ListCommands = new HashMap<>();
    }

    public void AddTvShow(List<SerialInputData> serials) {
        for (SerialInputData serialInputData : serials) {
            TvShow tvShow = new TvShow(serialInputData);
            this.ListTvShow.put(tvShow.getTitle(), tvShow);
        }
    }

    public void AddMovie(List<MovieInputData> movies) {
        for(MovieInputData movieInputData : movies) {
            Movie movie = new Movie(movieInputData);
            this.ListMovie.put(movie.getTitle(), movie);
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

            }
        }
    }

    public void commandsRun(Command command) {
        if(command.getType().equals("favorite")) {
            ListUser.get(command.getUsername()).add_favorite(command);
            if(ListUser.get(command.getUsername()).getMovie_seen().containsKey(command.getTitle())) {
                if(ListMovie.containsKey(command.getTitle())) {
                    ListMovie.get(command.getTitle()).add_favorite();
                } else if(ListTvShow.containsKey(command.getTitle())) {
                    ListTvShow.get(command.getTitle()).add_favorite();
                }
            }
        }

        if(command.getType().equals("view")) {
            ListUser.get(command.getUsername()).add_view(command);
        }

        if(command.getType().equals("rating")) {
            if(ListMovie.containsKey(command.getTitle())) {
                ListUser.get(command.getUsername()).add_ratings_video(command);
                ListMovie.get(command.getTitle()).getRatings().add(command.getGrade());
                ListMovie.get(command.getTitle()).rating_movie();
            } else if(ListTvShow.containsKey(command.getTitle())) {
                ListUser.get(command.getUsername()).add_ratings_tvshow(command);
                ListTvShow.get(command.getTitle()).add_rating(command);
                ListTvShow.get(command.getTitle()).rating_total();
            }
        }
    }

    public void queryRun(Command command){
        if(command.getObjectType().equals("actors")) {
            queryRunActor(command);
        } else if(command.getObjectType().equals("movies") || command.getObjectType().equals("shows")) {
            queryRunVideo(command);
        } else if(command.getObjectType().equals("users")) {

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

    public void queryRunVideo(Command command) {
        if ((command.getCriteria().equals("ratings"))) {
            queryRatings(command);
        } else if ("favorite".equals(command.getCriteria())) {
            if(command.getObjectType().equals("movies")) {

            } else {

            }
        } else if ("longest".equals(command.getCriteria())) {
        } else if ("most_viewed".equals(command.getCriteria())) {
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

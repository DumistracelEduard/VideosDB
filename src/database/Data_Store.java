package database;

import command.Command;
import entities.Actor;
import entities.User;
import entities.video.Movie;
import entities.video.TvShow;
import fileio.*;

import java.util.HashMap;
import java.util.List;

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

            } else if(ListCommands.get(key).getActionType().equals("recommendation")) {

            }
        }
    }

    public void commandsRun(Command command) {
        if(command.getType().equals("favorite")) {
            ListUser.get(command.getUsername()).add_favorite(command);
        }

        if(command.getType().equals("view")) {
            ListUser.get(command.getUsername()).add_view(command);
        }

        if(command.getType().equals("rating")) {
            if(ListMovie.containsKey(command.getTitle())) {
                ListUser.get(command.getUsername()).add_ratings_video(command);
                ListMovie.get(command.getTitle()).getRatings().add(command.getGrade());
            } else if(ListTvShow.containsKey(command.getTitle())) {
                ListUser.get(command.getUsername()).add_ratings_tvshow(command);
                ListTvShow.get(command.getTitle()).add_rating(command);
            }
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

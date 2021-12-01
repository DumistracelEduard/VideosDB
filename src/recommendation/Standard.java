package recommendation;

import command.Command;
import entities.User;
import entities.video.Movie;
import entities.video.TvShow;

import java.util.ArrayList;
import java.util.HashMap;

public class Standard {
    private ArrayList<Movie> movieRecom;
    private ArrayList<TvShow> tvShowsRecom;
    private HashMap<String, User> ListUser;

    public Standard(HashMap<String, User> ListUser) {
        this.ListUser = ListUser;
    }

    public void StandardRun(Command command, ArrayList<TvShow> tvShowsRecom, ArrayList<Movie> movieRecom) {
        User user = ListUser.get(command.getUsername());
        for(int i = 0; i < movieRecom.size(); ++i) {
            if(!user.getMovie_seen().containsKey(movieRecom.get(i).getTitle())) {
                command.setMessage("StandardRecommendation result: " + movieRecom.get(i).getTitle());
                return;
            }
        }
        for(int i = 0; i < tvShowsRecom.size(); ++i) {
            if(!user.getMovie_seen().containsKey(tvShowsRecom.get(i).getTitle())) {
                command.setMessage("StandardRecommendation result: " + tvShowsRecom.get(i).getTitle());
                return;
            }
        }
        command.setMessage("StandardRecommendation cannot be applied!");
    }
}

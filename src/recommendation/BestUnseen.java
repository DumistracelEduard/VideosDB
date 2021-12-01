package recommendation;

import command.Command;
import entities.User;
import entities.video.Movie;
import entities.video.TvShow;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.ListIterator;

public class BestUnseen {
    private HashMap<String, User> ListUser;
    private HashMap<String, Movie> ListMovie;
    private HashMap<String, TvShow> ListShows;
    private ArrayList<RecommendationObject> Videos;

    public BestUnseen(HashMap<String, User> ListUser, ArrayList<RecommendationObject> Videos,
                      HashMap<String, Movie> ListMovie, HashMap<String, TvShow> ListShows) {
        this.ListUser = ListUser;
        this.Videos = Videos;
        this.ListMovie = ListMovie;
        this.ListShows = ListShows;
    }

    public void updateRating() {
        for(int i = 0; i < Videos.size(); ++i) {
            if(ListMovie.containsKey(Videos.get(i).getName())) {
                ListMovie.get(Videos.get(i).getName()).rating_movie();
                this.Videos.get(i).setRating(ListMovie.get(Videos.get(i).getName()).getRating());
            }
            if(ListShows.containsKey(Videos.get(i).getName())) {
                ListShows.get(Videos.get(i).getName()).rating_total();
                this.Videos.get(i).setRating(ListShows.get(Videos.get(i).getName()).getRating());
            }
        }
    }

    public void BestUnseenRun(Command command) {
        Comparator<RecommendationObject> objectComparator = new Comparator<RecommendationObject>() {
            @Override
            public int compare(RecommendationObject o1, RecommendationObject o2) {
                return o1.getRating() < o2.getRating() ? 1
                        : o1.getRating() > o2.getRating() ? -1
                        : o1.getPos() < o2.getPos() ? -1
                        : o1.getPos() > o2.getPos() ? 1
                        :0;
            }
        };
        this.Videos.sort(objectComparator);
        for(RecommendationObject object: this.Videos) {
            User user = ListUser.get(command.getUsername());
            if(!user.getMovie_seen().containsKey(object.getName())) {
                command.setMessage("BestRatedUnseenRecommendation result: " + object.getName());
                return;
            }
        }
        command.setMessage("BestRatedUnseenRecommendation cannot be applied!");
    }
}

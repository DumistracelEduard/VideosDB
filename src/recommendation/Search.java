package recommendation;

import command.Command;
import entities.User;
import entities.video.Movie;
import entities.video.TvShow;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class Search {
    private ArrayList<RecommendationObject> ListSearch;
    private HashMap<String, User> ListUser;
    private HashMap<String, Movie> ListMovie;
    private HashMap<String, TvShow> ListShows;

    public Search(ArrayList<RecommendationObject> ListSearch, HashMap<String, User> ListUser,
                  HashMap<String, Movie> ListMovie, HashMap<String, TvShow> ListShows) {
        this.ListMovie= ListMovie;
        this.ListSearch = ListSearch;
        this.ListUser = ListUser;
        this.ListShows = ListShows;
    }

    public void SearchRun(Command command) {
        Comparator<RecommendationObject> comparable = new Comparator<RecommendationObject>() {
            @Override
            public int compare(RecommendationObject o1, RecommendationObject o2) {
                return o1.getRating() < o2.getRating() ? -1
                        : o1.getRating() > o2.getRating() ? 1
                        :0;
            }
        };
        Comparator<RecommendationObject> comparatorLexico = new Comparator<RecommendationObject>() {
            @Override
            public int compare(RecommendationObject o1, RecommendationObject o2) {
                return o1.getName().compareTo(o2.getName());
            }
        };
        this.ListSearch.sort(comparatorLexico);
        this.ListSearch.sort(comparable);

        User user = ListUser.get(command.getUsername());
        ArrayList<RecommendationObject> copyList = new ArrayList<>();
        for(RecommendationObject object : this.ListSearch) {
            if(!user.getMovie_seen().containsKey(object.getName())) {
                for(int i = 0; i < object.getGenres().size(); ++i) {
                    if(object.getGenres().get(i).equals(command.getGenre())) {
                        copyList.add(object);
                    }
                }
            }
        }
        if(copyList.size() == 0) {
            command.setMessage("SearchRecommendation cannot be applied!");
            return;
        }
        command.setMessage("SearchRecommendation result: " + copyList.toString());
    }

    @Override
    public String toString() {
        return ListSearch + "]";
    }
}

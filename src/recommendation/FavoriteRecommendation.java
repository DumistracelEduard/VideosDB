package recommendation;

import command.Command;
import entities.User;
import entities.video.Movie;
import entities.video.TvShow;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class FavoriteRecommendation {
    private ArrayList<RecommendationObject> FavoriteList;
    private HashMap<String, User> ListUser;
    private HashMap<String, Movie> ListMovie;
    private HashMap<String, TvShow> ListShows;

    public FavoriteRecommendation(ArrayList<RecommendationObject> FavoriteList, HashMap<String, User> ListUser,
                  HashMap<String, Movie> ListMovie, HashMap<String, TvShow> ListShows) {
        this.FavoriteList = FavoriteList;
        this.ListMovie= ListMovie;
        this.ListUser = ListUser;
        this.ListShows = ListShows;
    }

    public void favorite() {
        for(String key : ListUser.keySet()) {
            User user = ListUser.get(key);
            for(int i = 0; i < user.getFavoriteMovies().size(); ++i) {
                if(ListMovie.containsKey(user.getFavoriteMovies().get(i))) {
                    ListMovie.get(user.getFavoriteMovies().get(i)).add_favorite();
                }
                if(ListShows.containsKey(user.getFavoriteMovies().get(i))) {
                    ListShows.get(user.getFavoriteMovies().get(i)).add_favorite();
                }
            }
        }
    }

    public void updateList() {
        favorite();
        for(int i = 0; i < this.FavoriteList.size(); ++i) {
            if(ListMovie.containsKey(this.FavoriteList.get(i).getName())) {
                this.FavoriteList.get(i).setFavorite(ListMovie.get(this.FavoriteList.get(i).getName()).getNumber_favorite());
                this.FavoriteList.get(i).setPos(i);
            }
            if(ListShows.containsKey(this.FavoriteList.get(i).getName())) {
                this.FavoriteList.get(i).setFavorite(ListShows.get(this.FavoriteList.get(i).getName()).getNumber_favorite());
                this.FavoriteList.get(i).setPos(i);
            }
        }
    }

    public void FavoriteRun(Command command) {
        Comparator<RecommendationObject> comparator = new Comparator<RecommendationObject>() {
            @Override
            public int compare(RecommendationObject o1, RecommendationObject o2) {
                return o1.getFavorite() < o2.getFavorite() ? 1
                        : o1.getFavorite() > o2.getFavorite() ? -1
                        : o1.getPos() < o2.getPos() ? -1
                        : o1.getPos() > o2.getPos()? 1
                        : 0;
            }
        };

        this.FavoriteList.sort(comparator);
        User user = ListUser.get(command.getUsername());
        ArrayList<RecommendationObject> copyList = new ArrayList<>();
        for(int i = 0; i < this.FavoriteList.size(); ++i) {
            if(!user.getMovie_seen().containsKey(this.FavoriteList.get(i).getName()) ) {
                if(this.FavoriteList.get(i).getFavorite() != 0) {
                    command.setMessage("FavoriteRecommendation result: " + this.FavoriteList.get(i).getName());
                    return;
                } else {
                    copyList.add(this.FavoriteList.get(i));
                }

            }
        }
        if(copyList.size() != 0) {
            command.setMessage("FavoriteRecommendation result: " + copyList.get(0).getName());
            return;
        }
        command.setMessage("FavoriteRecommendation cannot be applied!");
    }
}

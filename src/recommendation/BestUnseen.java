package recommendation;

import command.Command;
import entities.User;
import entities.video.Movie;
import entities.video.TvShow;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class BestUnseen {
  private HashMap<String, User> listUser;
  private HashMap<String, Movie> listmovie;
  private HashMap<String, TvShow> listshows;
  private ArrayList<RecommendationObject> videos;

  public BestUnseen(
      final HashMap<String, User> listUser,
      final ArrayList<RecommendationObject> videos,
      final HashMap<String, Movie> listmovie,
      final HashMap<String, TvShow> listshows) {
    this.listUser = listUser;
    this.videos = videos;
    this.listmovie = listmovie;
    this.listshows = listshows;
  }

  /**
   * updateaza la fiecare video ratingul
   */
  public void updateRating() {
    for (int i = 0; i < videos.size(); ++i) {
      if (listmovie.containsKey(videos.get(i).getName())) {
        listmovie.get(videos.get(i).getName()).ratingMovie();
        this.videos.get(i).setRating(listmovie.get(videos.get(i).getName()).getRating());
      }
      if (listshows.containsKey(videos.get(i).getName())) {
        listshows.get(videos.get(i).getName()).ratingTotal();
        this.videos.get(i).setRating(listshows.get(videos.get(i).getName()).getRating());
      }
    }
  }

  /**
   * ordoneaza lista dupa rating si pos si returneaza primul video nevazut
   * @param command
   */
  public void bestUnseenRun(final Command command) {
    Comparator<RecommendationObject> objectComparator = new Comparator<RecommendationObject>() {
          @Override
          public int compare(final RecommendationObject o1, final RecommendationObject o2) {
            return o1.getRating() < o2.getRating() ? 1
                    : o1.getRating() > o2.getRating() ? -1
                    : o1.getPos() < o2.getPos() ? -1 : o1.getPos() > o2.getPos() ? 1 : 0;
          }
    };
    this.videos.sort(objectComparator);
    for (RecommendationObject object : this.videos) {
      User user = listUser.get(command.getUsername());
      if (!user.getMovieSeen().containsKey(object.getName())) {
        command.setMessage("BestRatedUnseenRecommendation result: " + object.getName());
        return;
      }
    }
    command.setMessage("BestRatedUnseenRecommendation cannot be applied!");
  }
}

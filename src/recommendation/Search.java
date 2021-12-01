package recommendation;

import command.Command;
import entities.User;
import entities.video.Movie;
import entities.video.TvShow;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class Search {
  private final ArrayList<RecommendationObject> listSearch;
  private final HashMap<String, User> listUser;
  private final HashMap<String, Movie> listMovie;
  private final HashMap<String, TvShow> listshows;

  public Search(
      final ArrayList<RecommendationObject> listSearch,
      final HashMap<String, User> listUser,
      final HashMap<String, Movie> listMovie,
      final HashMap<String, TvShow> listshows) {
    this.listMovie = listMovie;
    this.listSearch = listSearch;
    this.listUser = listUser;
    this.listshows = listshows;
  }

  /**
   * sorteaza si verifica daca apartine unui gen
   * @param command
   */
  public void searchRun(final Command command) {
    Comparator<RecommendationObject> comparable =
        new Comparator<RecommendationObject>() {
          @Override
          public int compare(final RecommendationObject o1, final RecommendationObject o2) {
            return o1.getRating() < o2.getRating() ? -1 : o1.getRating() > o2.getRating() ? 1 : 0;
          }
        };
    Comparator<RecommendationObject> comparatorLexico =
        new Comparator<RecommendationObject>() {
          @Override
          public int compare(final RecommendationObject o1, final RecommendationObject o2) {
            return o1.getName().compareTo(o2.getName());
          }
        };
    this.listSearch.sort(comparatorLexico);
    this.listSearch.sort(comparable);

    User user = listUser.get(command.getUsername());
    ArrayList<RecommendationObject> copyList = new ArrayList<>();
    for (RecommendationObject object : this.listSearch) {
      if (!user.getMovieSeen().containsKey(object.getName())) {
        for (int i = 0; i < object.getGenres().size(); ++i) {
          if (object.getGenres().get(i).equals(command.getGenre())) {
            copyList.add(object);
          }
        }
      }
    }
    if (copyList.size() == 0) {
      command.setMessage("SearchRecommendation cannot be applied!");
      return;
    }
    command.setMessage("SearchRecommendation result: " + copyList.toString());
  }

  @Override
  public final String toString() {
    return listSearch + "]";
  }
}

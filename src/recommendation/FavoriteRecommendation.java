package recommendation;

import command.Command;
import entities.User;
import entities.video.Movie;
import entities.video.TvShow;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class FavoriteRecommendation {
  private final ArrayList<RecommendationObject> favoriteList;
  private final HashMap<String, User> listUser;
  private final HashMap<String, Movie> listMovie;
  private final HashMap<String, TvShow> listShows;

  public FavoriteRecommendation(
      final ArrayList<RecommendationObject> favoriteList,
      final HashMap<String, User> listUser,
      final HashMap<String, Movie> listMovie,
      final HashMap<String, TvShow> listShows) {
    this.favoriteList = favoriteList;
    this.listMovie = listMovie;
    this.listUser = listUser;
    this.listShows = listShows;
  }

  /**
   * recalculeaza videoclipurile favorite
   */
  public void favorite() {
    for (String key : listUser.keySet()) {
      User user = listUser.get(key);
      for (int i = 0; i < user.getFavoriteMovies().size(); ++i) {
        if (listMovie.containsKey(user.getFavoriteMovies().get(i))) {
          listMovie.get(user.getFavoriteMovies().get(i)).addFavorite();
        }
        if (listShows.containsKey(user.getFavoriteMovies().get(i))) {
          listShows.get(user.getFavoriteMovies().get(i)).addFavorite();
        }
      }
    }
  }

  /**
   * actualizeaza lista de video
   */
  public void updateList() {
    favorite();
    for (int i = 0; i < this.favoriteList.size(); ++i) {
      if (listMovie.containsKey(this.favoriteList.get(i).getName())) {
        this.favoriteList.get(i)
            .setFavorite(listMovie.get(this.favoriteList.get(i).getName()).getNumberFavorite());
        this.favoriteList.get(i).setPos(i);
      }
      if (listShows.containsKey(this.favoriteList.get(i).getName())) {
        this.favoriteList.get(i)
            .setFavorite(listShows.get(this.favoriteList.get(i).getName()).getNumberFavorite());
        this.favoriteList.get(i).setPos(i);
      }
    }
  }

  /**
   *  ordoneaza lista de favorite si scoate primul video nevazut cu numar de fav dif de 0
   *  daca un nu exista sa aiba nr de fav dif de 0 adauga intr-o lista si scoate primul elem
   * @param command comanda
   */
  public void favoriteRun(final Command command) {
    Comparator<RecommendationObject> comparator =
            (o1, o2) -> o1.getFavorite() < o2.getFavorite() ? 1
                    : o1.getFavorite() > o2.getFavorite()  ? -1
                    : Integer.compare(o1.getPos(), o2.getPos());

    this.favoriteList.sort(comparator);
    User user = listUser.get(command.getUsername());
    ArrayList<RecommendationObject> copyList = new ArrayList<>();
    for (RecommendationObject recommendationObject : this.favoriteList) {
      if (!user.getMovieSeen().containsKey(recommendationObject.getName())) {
        if (recommendationObject.getFavorite() != 0) {
          command.setMessage(
                  "FavoriteRecommendation result: " + recommendationObject.getName());
          return;
        } else {
          copyList.add(recommendationObject);
        }
      }
    }
    if (copyList.size() != 0) {
      command.setMessage("FavoriteRecommendation result: " + copyList.get(0).getName());
      return;
    }
    command.setMessage("FavoriteRecommendation cannot be applied!");
  }
}

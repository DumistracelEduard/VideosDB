package recommendation;

import command.Command;
import entities.User;
import entities.video.Movie;
import entities.video.TvShow;

import java.util.ArrayList;
import java.util.HashMap;

public class Recommendation {
  private final ArrayList<RecommendationObject> objectArrayList;
  private final HashMap<String, Integer> genresTop;
  private final HashMap<String, User> listUser;
  private final HashMap<String, Movie> listMovie;
  private final HashMap<String, TvShow> listShows;
  private final ArrayList<Movie> movieRecom;
  private final ArrayList<TvShow> tvShowsRecom;

  public Recommendation(
      final ArrayList<RecommendationObject> objectsArrayList,
      final HashMap<String, User> listUser,
      final HashMap<String, Movie> listMovie,
      final HashMap<String, TvShow> listShows,
      final ArrayList<TvShow> tvShowsRecom,
      final ArrayList<Movie> movieRecom) {
    this.genresTop = new HashMap<>();
    this.listShows = listShows;
    this.listMovie = listMovie;
    this.listUser = listUser;
    this.objectArrayList = objectsArrayList;
    this.tvShowsRecom = tvShowsRecom;
    this.movieRecom = movieRecom;
  }

  /**
   * vad ce comanda din  recomandari am de rulat
   * @param command
   */
  public void recommendationRun(final Command command) {
    if (command.getType().equals("standard")) {
      Standard standard = new Standard(listUser);
      standard.standardRun(command, tvShowsRecom, movieRecom);
    } else if (command.getType().equals("best_unseen")) {
      BestUnseen bestUnseen = new BestUnseen(listUser, objectArrayList, listMovie, listShows);
      bestUnseen.updateRating();
      bestUnseen.bestUnseenRun(command);
    } else if (command.getType().equals("popular")) {
      if (listUser.get(command.getUsername()).getSubscriptionType().equals("PREMIUM")) {
        Popular popular = new Popular(objectArrayList);
        popular.sortGenres(command, listUser, movieRecom, tvShowsRecom);
        return;
      }
      command.setMessage("PopularRecommendation cannot be applied!");
    } else if (command.getType().equals("favorite")) {
      if (listUser.get(command.getUsername()).getSubscriptionType().equals("PREMIUM")) {
        FavoriteRecommendation favoriteRecommendation =
            new FavoriteRecommendation(objectArrayList, listUser, listMovie, listShows);
        favoriteRecommendation.updateList();
        favoriteRecommendation.favoriteRun(command);
        return;
      }
      command.setMessage("FavoriteRecommendation cannot be applied!");
    } else if (command.getType().equals("search")) {
      if (listUser.get(command.getUsername()).getSubscriptionType().equals("PREMIUM")) {
        Search search = new Search(objectArrayList, listUser, listMovie, listShows);
        search.searchRun(command);
        return;
      }
      command.setMessage("SearchRecommendation cannot be applied!");
    }
  }
}

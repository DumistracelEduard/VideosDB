package recommendation;

import command.Command;
import entities.User;
import entities.video.Movie;
import entities.video.TvShow;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Popular {
  private final HashMap<String, Integer> genres;
  private final ArrayList<RecommendationObject> listVideo;
  private final List<String> listgenres;
  private final ArrayList<PopularObject> listGenre;

  /**
   * Punem in genres fiecare aparitie unui gen
   * @param listVideo
   */
  public Popular(final ArrayList<RecommendationObject> listVideo) {
    this.listVideo = listVideo;
    this.listgenres = new ArrayList<>();
    this.genres = new HashMap<>();
    this.listGenre = new ArrayList<>();

    for (RecommendationObject object : listVideo) {
      for (int i = 0; i < object.getGenres().size(); ++i) {
        if (!genres.containsKey(object.getGenres().get(i))) {
          genres.put(object.getGenres().get(i), 1);
        } else {
          genres.put(object.getGenres().get(i), genres.get(object.getGenres().get(i)) + 1);
        }
      }
    }
  }

  /**
   * Din genres bagam elementele intr-o lista pe care o ordonam descrescator.
   * Apoi parcurgem lista de genuri in ordine si comparam cu lista de video si
   * dacaun video a fost vazut deja.Returnam primul video nevazut si din acel gen.
   * @param command comanda
   * @param listUser lista de user
   * @param listMovie lista de filme
   * @param listShows lista de seriale
   */
  public void sortGenres(
      final Command command,
      final HashMap<String, User> listUser,
      final ArrayList<Movie> listMovie,
      final ArrayList<TvShow> listShows) {
    for (String key : this.genres.keySet()) {
      PopularObject object = new PopularObject(key, this.genres.get(key));
      this.listGenre.add(object);
    }

    Comparator<PopularObject> comparator =
        new Comparator<PopularObject>() {
          @Override
          public int compare(final PopularObject o1, final PopularObject o2) {
            return o1.getNumber() < o2.getNumber() ? 1 : o1.getNumber() > o2.getNumber() ? -1 : 0;
          }
        };

    this.listGenre.sort(comparator);
    User user = listUser.get(command.getUsername());
    for (PopularObject popularObject : this.listGenre) {
      for (Movie movie : listMovie) {
        List<String> movieGenres = movie.getGenres();
        for (int i = 0; i < movieGenres.size(); ++i) {
          if (movieGenres.get(i).equals(popularObject.getGenre())) {
            if (!user.getMovieSeen().containsKey(movie.getTitle())) {
              command.setMessage("PopularRecommendation result: " + movie.getTitle());
              return;
            }
          }
        }
      }
      for (TvShow tvShow : listShows) {
        List<String> movieGenres = tvShow.getGenres();
        for (int i = 0; i < movieGenres.size(); ++i) {
          if (movieGenres.get(i).equals(popularObject.getGenre())) {
            if (!user.getMovieSeen().containsKey(tvShow.getTitle())) {
              command.setMessage("PopularRecommendation result: " + tvShow.getTitle());
              return;
            }
          }
        }
      }
    }
    command.setMessage("PopularRecommendation cannot be applied!");
  }
}

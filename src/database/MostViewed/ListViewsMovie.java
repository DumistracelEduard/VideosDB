package database.MostViewed;

import command.Command;
import database.QueryObject;
import entities.video.Movie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class ListViewsMovie {
  private ArrayList<QueryObject> viewedMovie;

  public ListViewsMovie() {
    this.viewedMovie = new ArrayList<>();
  }

  /**
   * genereaza lista de cele mai vizionate filme
   * @param listMovie
   * @param command
   */
  public void generateList(final HashMap<String, Movie> listMovie, final Command command) {
    for (String key : listMovie.keySet()) {
      Movie movie = listMovie.get(key);
      if (movie.getViews() == 0) {
        continue;
      }
      if (movie.genreExist(command) == 0) {
        continue;
      }

      if (movie.yearExist(command) == 0) {
        continue;
      }
      QueryObject queryObject = new QueryObject(movie.getTitle(), movie.getViews());
      this.viewedMovie.add(queryObject);
    }
  }

  /**
   * sorteaza lista si scoate primele N numere
   * @param command
   */
  public void listSort(final Command command) {
    Comparator<QueryObject> comparatorView =
        new Comparator<QueryObject>() {
          @Override
          public int compare(final QueryObject o1, final QueryObject o2) {
            return o1.getNumber() < o2.getNumber() ? -1 : o1.getNumber() > o2.getNumber() ? 1 : 0;
          }
        };
    Comparator<QueryObject> comparatorName =
        new Comparator<QueryObject>() {
          @Override
          public int compare(final QueryObject o1, final QueryObject o2) {
            return o1.getName().compareTo(o2.getName());
          }
        };

    this.viewedMovie.sort(comparatorName);
    this.viewedMovie.sort(comparatorView);

    if (command.getNumber() > this.viewedMovie.size()) {
      command.setNumber(this.viewedMovie.size());
    }

    if (command.getSortType().equals("desc")) {
      Collections.reverse(this.viewedMovie);
    }

    ArrayList<QueryObject> copyList = new ArrayList<>();
    if (this.viewedMovie.size() < command.getNumber()) {
      command.setNumber(this.viewedMovie.size());
    }
    for (int i = 0; i < command.getNumber(); ++i) {
      copyList.add(this.viewedMovie.get(i));
    }

    command.setMessage("Query result: " + copyList.toString());
  }

  @Override
  public final String toString() {
    return viewedMovie + "]";
  }
}

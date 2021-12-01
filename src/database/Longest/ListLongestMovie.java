package database.Longest;

import command.Command;
import database.QueryObject;
import entities.video.Movie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class ListLongestMovie {
  private ArrayList<QueryObject> listMovie;

  public ListLongestMovie(final HashMap<String, Movie> listMovie, final Command command) {
    this.listMovie = new ArrayList<>();

    for (String key : listMovie.keySet()) {
      listMovie.get(key).getDuration();
      if (listMovie.get(key).genreExist(command) == 0) {
        continue;
      }

      if (listMovie.get(key).yearExist(command) == 0) {
        continue;
      }
      QueryObject queryObject =
          new QueryObject(listMovie.get(key).getTitle(), listMovie.get(key).getDuration());
      this.listMovie.add(queryObject);
    }
  }

  /**
   * Sorteaza lista de movie in functie de durate si nume
   * @param command
   */
  public void sortLongest(final Command command) {
    Comparator<QueryObject> comparatorTime =
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

    this.listMovie.sort(comparatorName);
    this.listMovie.sort(comparatorTime);

    if (command.getNumber() > this.listMovie.size()) {
      command.setNumber(this.listMovie.size());
    }

    if (command.getSortType().equals("desc")) {
      Collections.reverse(this.listMovie);
    }

    ArrayList<QueryObject> copyList = new ArrayList<>();
    if (command.getNumber() > this.listMovie.size()) {
      command.setNumber(this.listMovie.size());
    }
    for (int i = 0; i < command.getNumber(); ++i) {
      copyList.add(this.listMovie.get(i));
    }

    command.setMessage("Query result: " + copyList.toString());
  }

  @Override
  public final String toString() {
    return listMovie + "]";
  }
}

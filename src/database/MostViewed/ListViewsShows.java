package database.MostViewed;

import command.Command;
import database.QueryObject;
import entities.video.TvShow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class ListViewsShows {
  private ArrayList<QueryObject> viewedShow;

  public ListViewsShows() {
    this.viewedShow = new ArrayList<>();
  }

  /**
   * genereaza lista in functie de criterii
   * @param listTvShow
   * @param command
   */
  public void generateList(final HashMap<String, TvShow> listTvShow, final Command command) {
    for (String key : listTvShow.keySet()) {
      TvShow tvShow = listTvShow.get(key);
      if (tvShow.getViews() == 0) {
        continue;
      }
      if (tvShow.genreExist(command) == 0) {
        continue;
      }

      if (tvShow.yearExist(command) == 0) {
        continue;
      }
      QueryObject queryObject = new QueryObject(tvShow.getTitle(), tvShow.getViews());
      this.viewedShow.add(queryObject);
    }
  }

  /**
   * sorteaza lista de vizualizari si scoate primele N elem
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

    this.viewedShow.sort(comparatorName);
    this.viewedShow.sort(comparatorView);

    if (command.getNumber() > this.viewedShow.size()) {
      command.setNumber(this.viewedShow.size());
    }

    if (command.getSortType().equals("desc")) {
      Collections.reverse(this.viewedShow);
    }

    ArrayList<QueryObject> copyList = new ArrayList<>();
    if (this.viewedShow.size() < command.getNumber()) {
      command.setNumber(this.viewedShow.size());
    }
    for (int i = 0; i < command.getNumber(); ++i) {
      copyList.add(this.viewedShow.get(i));
    }

    command.setMessage("Query result: " + copyList.toString());
  }

  @Override
  public final String toString() {
    return viewedShow + "]";
  }
}

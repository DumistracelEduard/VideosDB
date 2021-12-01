package database.Longest;

import command.Command;
import database.QueryObject;
import entities.video.TvShow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class ListLongestShow {
  private ArrayList<QueryObject> listShow;

  public ListLongestShow(final HashMap<String, TvShow> listShow, final Command command) {
    this.listShow = new ArrayList<>();

    for (String key : listShow.keySet()) {
      listShow.get(key).timeTotal();
      if (listShow.get(key).genreExist(command) == 0) {
        continue;
      }

      if (listShow.get(key).yearExist(command) == 0) {
        continue;
      }
      QueryObject queryObject =
          new QueryObject(listShow.get(key).getTitle(), listShow.get(key).getTime());
      this.listShow.add(queryObject);
    }
  }

  /**
   * sorteaza lista de seriale in functie de timp si returneaza primele N
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

    this.listShow.sort(comparatorName);
    this.listShow.sort(comparatorTime);

    if (command.getNumber() > this.listShow.size()) {
      command.setNumber(this.listShow.size());
    }

    if (command.getSortType().equals("desc")) {
      Collections.reverse(this.listShow);
    }

    ArrayList<QueryObject> copyList = new ArrayList<>();
    if (command.getNumber() > this.listShow.size()) {
      command.setNumber(this.listShow.size());
    }
    for (int i = 0; i < command.getNumber(); ++i) {
      copyList.add(this.listShow.get(i));
    }

    command.setMessage("Query result: " + copyList.toString());
  }

  @Override
  public final String toString() {
    return listShow + "]";
  }
}

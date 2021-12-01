package database.FilterDescription;

import command.Command;
import entities.Actor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class FilterDescription {
  private ArrayList<String> actors;

  public FilterDescription() {
    this.actors = new ArrayList<>();
  }

  /**
   * sorteaza lista de autori care au descrierea respectiva si returneaza primii N
   * @param listActor
   * @param command
   */
  public void sort(final HashMap<String, Actor> listActor, final Command command) {
    for (String key : listActor.keySet()) {
      Actor actor = listActor.get(key);
      if (actor.descriptionFilter(command) == 0) {
        continue;
      }
      this.actors.add(actor.getName());
    }
    Comparator<String> comparator = new Comparator<String>() {
          @Override
          public int compare(final String o1, final String o2) {
            return o1.compareTo(o2);
          }
        };
    this.actors.sort(comparator);

    if (command.getSortType().equals("desc")) {
      Collections.reverse(this.actors);
    }
    command.setMessage("Query result: " + this.actors.toString());
  }

  @Override
  public final String toString() {
    return actors + "]";
  }
}

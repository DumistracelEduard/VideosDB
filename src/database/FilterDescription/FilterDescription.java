package database.FilterDescription;

import command.Command;
import entities.Actor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class FilterDescription {
    ArrayList<String> actors;

    public FilterDescription() {
        this.actors = new ArrayList<>();
    }

    public void sort(HashMap<String, Actor> ListActor, Command command) {
        for(String key : ListActor.keySet()) {
            Actor actor = ListActor.get(key);
            if(actor.descriptionFilter(command) == 0) {
                continue;
            }
            this.actors.add(actor.getName());
        }
        Comparator<String> comparator = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        };
        this.actors.sort(comparator);

        if(command.getSortType().equals("desc")) {
            Collections.reverse(this.actors);
        }
        command.setMessage("Query result: " + this.actors.toString());
    }

    @Override
    public String toString() {
        return actors + "]";
    }
}

package database;

import command.Command;
import entities.Actor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;


public class AverageSort {
    private ArrayList<ActorSort> actorSorts;

    public AverageSort() {
        this.actorSorts = new ArrayList<>();
    }

    /**
     * sorteaza lista si aduce primii N , daca list.size() < N atunci returneaza toata lista
     * @param listActor
     * @param command
     */
    public void averageSort(final HashMap<String, Actor> listActor, final Command command) {
        ArrayList<ActorSort> list = new ArrayList<>();

        for (String key : listActor.keySet()) {
            Actor actor = listActor.get(key);
            if (actor.getAverage() != 0) {
                ActorSort actorSort = new ActorSort(actor.getName(), actor.getAverage());
                list.add(actorSort);
            }
        }
        Comparator<ActorSort> comparator = new Comparator<ActorSort>() {
            @Override
            public int compare(final ActorSort o1, final ActorSort o2) {
                return o1.getName().compareTo(o2.getName());
            }
        };
        list.sort(comparator);
        Collections.sort(list);
        if (command.getSortType().equals("desc")) {
            Collections.reverse(list);
        }
        if (command.getNumber() > list.size()) {
            this.actorSorts = list;
        } else {
            for (int i = 0; i < command.getNumber(); ++i) {
                this.actorSorts.add(list.get(i));
            }
        }
    }

    @Override
    public final String toString() {
        return actorSorts + "]";
    }

    /**
     * Salveaza mesajul
     * @param command
     */
    public final void message(final Command command) {
        command.setMessage("Query result: "  + actorSorts.toString());
    }
}

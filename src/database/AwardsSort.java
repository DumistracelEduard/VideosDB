package database;

import command.Command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AwardsSort {
    private ArrayList<ActorAwards> actorAwards;

    public AwardsSort() {
        actorAwards = new ArrayList<>();
    }

    /**
     * Sorteaza lista si da primii N elemente. Daca N >list.zise -> ia toate elem.
     * @param command
     */
    public void sort(final Command command) {
        Comparator<ActorAwards> comparator = new Comparator<ActorAwards>() {
            @Override
            public int compare(final ActorAwards o1, final ActorAwards o2) {
                return o1.getName().compareTo(o2.getName());
            }
        };
        this.actorAwards.sort(comparator);
        Comparator<ActorAwards> comparator1 = new Comparator<ActorAwards>() {
            @Override
            public int compare(final ActorAwards o1, final ActorAwards o2) {
                return o1.getNumberAwards() < o2.getNumberAwards() ? -1
                        : o1.getNumberAwards() > o2.getNumberAwards() ? 1
                        : 0;
            }
        };
        this.actorAwards.sort(comparator1);
        if (command.getSortType().equals("desc")) {
            Collections.reverse(this.actorAwards);
        }

        command.setMessage("Query result: [");
        int size = command.getNumber();
        if (actorAwards.size() < command.getNumber()) {
            size = actorAwards.size();
        }
        for (int i = 0; i < size; ++i) {
            if (i != 0) {
                command.setMessage(command.getMessage().concat(", "));
            }
            command.setMessage(command.getMessage().concat(actorAwards.get(i).getName()));
        }
        command.setMessage(command.getMessage().concat("]"));
    }

    public final ArrayList<ActorAwards> getActorAwards() {
        return actorAwards;
    }
}

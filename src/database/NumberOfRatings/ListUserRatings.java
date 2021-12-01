package database.NumberOfRatings;

import command.Command;
import database.QueryObject;
import entities.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class ListUserRatings {
    private ArrayList<QueryObject> listRatings;

    public ListUserRatings(final HashMap<String, User> listUser) {
        this.listRatings = new ArrayList<>();

        for (String key : listUser.keySet()) {
            User user = listUser.get(key);
            if (user.getNumberRatings() == 0) {
                continue;
            }
            QueryObject queryObject = new QueryObject(user.getUsername(), user.getNumberRatings());
            this.listRatings.add(queryObject);
        }
    }

    /**
     * sorteaza lista si scoate N elem din ea.In caz ca N este mai mare list.size()
     * atunci scoate toate elem din lista
     * @param command
     */
    public void sort(final Command command) {
        Comparator<QueryObject> comparatorLexico = new Comparator<QueryObject>() {
            @Override
            public int compare(final QueryObject o1, final QueryObject o2) {
                return o1.getName().compareTo(o2.getName());
            }
        };

        Comparator<QueryObject> comparatorNumber = new Comparator<QueryObject>() {
            @Override
            public int compare(final QueryObject o1, final QueryObject o2) {
                return o1.getNumber() < o2.getNumber() ? -1
                        : o1.getNumber() > o2.getNumber() ? 1
                        : 0;
            }
        };

        this.listRatings.sort(comparatorLexico);
        this.listRatings.sort(comparatorNumber);
        if (command.getNumber() > listRatings.size()) {
            command.setNumber(listRatings.size());
        }

        if (command.getSortType().equals("desc")) {
            Collections.reverse(this.listRatings);
        }

        ArrayList<QueryObject> copyArray = new ArrayList<>();
        for (int i = 0; i < command.getNumber(); ++i) {
            copyArray.add(this.listRatings.get(i));
        }

        command.setMessage("Query result: " + copyArray.toString());
    }

    @Override
    public final String toString() {
        return listRatings + "]";
    }

}

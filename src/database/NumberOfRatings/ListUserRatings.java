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

    public ListUserRatings(HashMap<String, User> ListUser) {
        this.listRatings = new ArrayList<>();

        for(String key : ListUser.keySet()) {
            User user = ListUser.get(key);
            if(user.getNumber_ratings() == 0) {
                continue;
            }
            QueryObject queryObject = new QueryObject(user.getUsername(), user.getNumber_ratings());
            this.listRatings.add(queryObject);
        }
    }

    public void sort(Command command) {
        Comparator<QueryObject> comparatorLexico = new Comparator<QueryObject>() {
            @Override
            public int compare(QueryObject o1, QueryObject o2) {
                return o1.getName().compareTo(o2.getName());
            }
        };

        Comparator<QueryObject> comparatorNumber = new Comparator<QueryObject>() {
            @Override
            public int compare(QueryObject o1, QueryObject o2) {
                return o1.getNumber() < o2.getNumber() ? -1
                        : o1.getNumber() > o2.getNumber()? 1
                        : 0;
            }
        };

        this.listRatings.sort(comparatorLexico);
        this.listRatings.sort(comparatorNumber);
        if(command.getNumber() > listRatings.size()) {
            command.setNumber(listRatings.size());
        }

        if(command.getSortType().equals("desc")) {
            Collections.reverse(this.listRatings);
        }

        ArrayList<QueryObject> copyArray = new ArrayList<>();
        for(int i = 0; i < command.getNumber(); ++i) {
            copyArray.add(this.listRatings.get(i));
        }

        command.setMessage("Query result: " + copyArray.toString());
    }

    @Override
    public String toString() {
        return listRatings + "]";
    }

}

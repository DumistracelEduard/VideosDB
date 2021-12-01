package database.Longest;

import command.Command;
import database.QueryObject;
import entities.video.TvShow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class ListLongestShow {
    public ArrayList<QueryObject> listShow;

    public ListLongestShow(HashMap<String, TvShow> ListShow, Command command) {
        this.listShow = new ArrayList<>();

        for(String key : ListShow.keySet()) {
            ListShow.get(key).time_total();
            if(ListShow.get(key).genre_exist(command) == 0) {
                continue;
            }

            if(ListShow.get(key).year_exist(command) == 0) {
                continue;
            }
            QueryObject queryObject = new QueryObject(ListShow.get(key).getTitle(),ListShow.get(key).getTime());
            this.listShow.add(queryObject);
        }
    }

    public void sortLongest(Command command) {
        Comparator<QueryObject> comparatorTime = new Comparator<QueryObject>() {
            @Override
            public int compare(QueryObject o1, QueryObject o2) {
                return o1.getNumber() < o2.getNumber() ? -1
                        : o1.getNumber() > o2.getNumber()? 1
                        : 0;
            }
        };
        Comparator<QueryObject> comparatorName = new Comparator<QueryObject>() {
            @Override
            public int compare(QueryObject o1, QueryObject o2) {
                return o1.getName().compareTo(o2.getName());
            }
        };

        this.listShow.sort(comparatorName);
        this.listShow.sort(comparatorTime);

        if(command.getNumber() > this.listShow.size()) {
            command.setNumber(this.listShow.size());
        }

        if(command.getSortType().equals("desc")) {
            Collections.reverse(this.listShow);
        }

        ArrayList<QueryObject> copyList = new ArrayList<>();
        if(command.getNumber() > this.listShow.size()) {
            command.setNumber(this.listShow.size());
        }
        for(int i = 0; i < command.getNumber(); ++i) {
            copyList.add(this.listShow.get(i));
        }

        command.setMessage("Query result: " + copyList.toString());
    }

    @Override
    public String toString() {
        return listShow + "]";
    }
}

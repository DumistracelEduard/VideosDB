package database.Longest;

import command.Command;
import database.QueryObject;
import entities.video.Movie;
import entities.video.TvShow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class ListLongestMovie {
    public ArrayList<QueryObject> listShow;

    public ListLongestMovie(HashMap<String, Movie> ListMovie, Command command) {
        this.listShow = new ArrayList<>();

        for(String key : ListMovie.keySet()) {
            ListMovie.get(key).getDuration();
            if(ListMovie.get(key).genre_exist(command) == 0) {
                continue;
            }

            if(ListMovie.get(key).year_exist(command) == 0) {
                continue;
            }
            QueryObject queryObject = new QueryObject(ListMovie.get(key).getTitle(),ListMovie.get(key).getDuration());
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

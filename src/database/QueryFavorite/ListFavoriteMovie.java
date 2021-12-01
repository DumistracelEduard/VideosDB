package database.QueryFavorite;

import command.Command;
import database.QueryObject;
import entities.video.Movie;

import java.awt.desktop.OpenURIEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class ListFavoriteMovie {
    ArrayList<QueryObject> list;

    public ListFavoriteMovie() {
        this.list = new ArrayList<>();
    }

    public void ListGenerate(Command command, HashMap<String, Movie> ListMovie) {
        for(String key : ListMovie.keySet()) {
            Movie movie = ListMovie.get(key);
            if(movie.getNumber_favorite() == 0) {
                continue;
            }
            if(movie.year_exist(command) == 0) {
                continue;
            }
            if(movie.genre_exist(command) == 0) {
                continue;
            }
            QueryObject queryObject = new QueryObject(movie.getTitle(), movie.getNumber_favorite());
            list.add(queryObject);
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
        this.list.sort(comparatorLexico);
        this.list.sort(comparatorNumber);
        if(command.getNumber() > list.size()) {
            command.setNumber(list.size());
        }

        if(command.getSortType().equals("desc")) {
            Collections.reverse(this.list);
        }

        ArrayList<QueryObject> copyArray = new ArrayList<>();
        for(int i = 0; i < command.getNumber(); ++i) {
            copyArray.add(this.list.get(i));
        }
        command.setMessage("Query result: " + copyArray.toString());
        }
        @Override
        public String toString() {
            return list + "]";
        }
}

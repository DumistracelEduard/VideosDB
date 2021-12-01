package database.MostViewed;

import command.Command;
import database.QueryObject;
import entities.video.Movie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class ListViewsMovie {
    private ArrayList<QueryObject> ViewedMovie;

    public ListViewsMovie() {
        this.ViewedMovie = new ArrayList<>();
    }

    public void generateList(HashMap<String, Movie> ListMovie, Command command) {
        for(String key : ListMovie.keySet()) {
            Movie movie = ListMovie.get(key);
            if(movie.getViews() == 0) {
                continue;
            }
            if(movie.genre_exist(command) == 0) {
                continue;
            }

            if(movie.year_exist(command) == 0) {
                continue;
            }
            QueryObject queryObject = new QueryObject(movie.getTitle(), movie.getViews());
            this.ViewedMovie.add(queryObject);
        }
    }

    public void listSort(Command command) {
        Comparator<QueryObject> comparatorView = new Comparator<QueryObject>() {
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

        this.ViewedMovie.sort(comparatorName);
        this.ViewedMovie.sort(comparatorView);

        if(command.getNumber() > this.ViewedMovie.size()) {
            command.setNumber(this.ViewedMovie.size());
        }

        if(command.getSortType().equals("desc")) {
            Collections.reverse(this.ViewedMovie);
        }

        ArrayList<QueryObject> copyList = new ArrayList<>();
        if(this.ViewedMovie.size() < command.getNumber()) {
            command.setNumber(this.ViewedMovie.size());
        }
        for(int i = 0; i < command.getNumber(); ++i) {
            copyList.add(this.ViewedMovie.get(i));
        }

        command.setMessage("Query result: " + copyList.toString());
    }

    @Override
    public String toString() {
        return ViewedMovie + "]";
    }
}

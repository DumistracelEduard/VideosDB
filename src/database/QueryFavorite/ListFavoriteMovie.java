package database.QueryFavorite;

import command.Command;
import database.QueryObject;
import entities.video.Movie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class ListFavoriteMovie {
    private final ArrayList<QueryObject> list;

    public ListFavoriteMovie() {
        this.list = new ArrayList<>();
    }

    /**
     * genereaza lista dupa criteriile date
     * @param command
     * @param listMovie
     */
    public void listGenerate(final Command command, final HashMap<String, Movie> listMovie) {
        for (String key : listMovie.keySet()) {
            Movie movie = listMovie.get(key);
            if (movie.getNumberFavorite() == 0) {
                continue;
            }
            if (movie.yearExist(command) == 0) {
                continue;
            }
            if (movie.genreExist(command) == 0) {
                continue;
            }
            QueryObject queryObject = new QueryObject(movie.getTitle(), movie.getNumberFavorite());
            list.add(queryObject);
        }
    }

    /**
     * sorteaza lista si returneaza primele N elem
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
        this.list.sort(comparatorLexico);
        this.list.sort(comparatorNumber);
        if (command.getNumber() > list.size()) {
            command.setNumber(list.size());
        }

        if (command.getSortType().equals("desc")) {
            Collections.reverse(this.list);
        }

        ArrayList<QueryObject> copyArray = new ArrayList<>();
        for (int i = 0; i < command.getNumber(); ++i) {
            copyArray.add(this.list.get(i));
        }
        command.setMessage("Query result: " + copyArray.toString());
        }
        @Override
        public final String toString() {
            return list + "]";
        }
}

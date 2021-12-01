package database.QueryFavorite;

import command.Command;
import database.QueryObject;
import entities.video.TvShow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;


public class ListFavoriteShows {
    private final ArrayList<QueryObject> list;

    public ListFavoriteShows() {
        this.list = new ArrayList<>();
    }

    /**
     * Generez lista cu filtrul dat.
     * @param command
     * @param listShow
     */
    public void listGenerate(final Command command, final HashMap<String,
            TvShow> listShow) {
        for (String key : listShow.keySet()) {
            TvShow tvShow = listShow.get(key);
            if (tvShow.getNumberFavorite() == 0) {
                continue;
            }
            if (tvShow.genreExist(command) == 0) {
                continue;
            }

            if (tvShow.yearExist(command) == 0) {
                continue;
            }
            QueryObject queryObject = new QueryObject(tvShow.getTitle(),
                    tvShow.getNumberFavorite());
            list.add(queryObject);
        }
    }

    /**
     * Ordonez si iau primele N elemente.
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

    public final ArrayList<QueryObject> getList() {
        return list;
    }
}

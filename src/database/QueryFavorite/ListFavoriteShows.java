package database.QueryFavorite;

import command.Command;
import database.QueryObject;
import entities.video.TvShow;

import java.util.*;

public class ListFavoriteShows {
    ArrayList<QueryObject> list;

    public ListFavoriteShows() {
        this.list = new ArrayList<>();
    }

    public void ListGenerate(Command command, HashMap<String, TvShow> ListShow) {
        for(String key : ListShow.keySet()) {
            TvShow tvShow = ListShow.get(key);
            if(tvShow.getNumber_favorite() == 0) {
                continue;
            }
            if(tvShow.genre_exist(command) == 0) {
                continue;
            }

            if(tvShow.year_exist(command) == 0) {
                continue;
            }
            QueryObject queryObject = new QueryObject(tvShow.getTitle(), tvShow.getNumber_favorite());
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

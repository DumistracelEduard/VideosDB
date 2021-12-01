package database.MostViewed;

import command.Command;
import database.QueryObject;
import entities.video.Movie;
import entities.video.TvShow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class ListViewsShows {
    private ArrayList<QueryObject> ViewedShow;

    public ListViewsShows() {
        this.ViewedShow = new ArrayList<>();
    }

    public void generateList(HashMap<String, TvShow> ListTvShow, Command command) {
        for(String key : ListTvShow.keySet()) {
            TvShow tvShow = ListTvShow.get(key);
            if(tvShow.getViews() == 0) {
                continue;
            }
            if(tvShow.genre_exist(command) == 0) {
                continue;
            }

            if(tvShow.year_exist(command) == 0) {
                continue;
            }
            QueryObject queryObject = new QueryObject(tvShow.getTitle(), tvShow.getViews());
            this.ViewedShow.add(queryObject);
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

        this.ViewedShow.sort(comparatorName);
        this.ViewedShow.sort(comparatorView);

        if(command.getNumber() > this.ViewedShow.size()) {
            command.setNumber(this.ViewedShow.size());
        }

        if(command.getSortType().equals("desc")) {
            Collections.reverse(this.ViewedShow);
        }

        ArrayList<QueryObject> copyList = new ArrayList<>();
        if(this.ViewedShow.size() < command.getNumber()) {
            command.setNumber(this.ViewedShow.size());
        }
        for(int i = 0; i < command.getNumber(); ++i) {
            copyList.add(this.ViewedShow.get(i));
        }

        command.setMessage("Query result: " + copyList.toString());
    }

    @Override
    public String toString() {
        return ViewedShow + "]";
    }
}

package entities.video;

import command.Command;

import java.util.ArrayList;
import java.util.List;

public abstract class Video {
    private String title;
    private int year;
    private List<String> cast;
    private List<String> genres;

    public Video(String title, int year,
                 List<String> cast,
                 List<String> genres){
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
    }

    public int year_exist(Command command) {
        if(command.getFilters().get(0).get(0) == null) {
            return 1;
        }
        if(command.getFilters().get(0).get(0).equals(String.valueOf(year))){
            return 1;
        }
        return 0;
    }

    public int genre_exist(Command command){
        if(command.getFilters().get(1) == null){
            return 1;
        }
        for(int i = 0; i < genres.size(); ++i) {
            if(genres.get(i).equals(command.getFilters().get(1).get(0))) {
                return 1;
            }
        }
        return 0;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<String> getCast() {
        return cast;
    }

    public void setCast(List<String> cast) {
        this.cast = cast;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }
}

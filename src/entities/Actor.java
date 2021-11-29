package entities;

import actor.ActorsAwards;
import entities.video.Movie;
import entities.video.TvShow;
import fileio.ActorInputData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Actor {
    private String name;
    private String description;
    private ArrayList<String> filmography;
    private Map<ActorsAwards, Integer> awards;
    private Double average;
    private Integer number_awards;

    public Actor (String name, String description,
                  ArrayList<String> filmography,
                  Map<ActorsAwards, Integer> awards){
        this.name = name;
        this.description = description;
        this.filmography = filmography;
        this.awards = awards;
        for(ActorsAwards key : awards.keySet()) {
            this.number_awards += awards.get(key);
        }
    }

    public Actor(ActorInputData actor) {
        this.name = actor.getName();
        this.description = actor.getCareerDescription();
        this.filmography = actor.getFilmography();
        this.awards = actor.getAwards();
        this.number_awards = 0;
        for(ActorsAwards key : awards.keySet()) {
            this.number_awards += awards.get(key);
        }
    }

    public void average_get(HashMap<String, Movie> ListMovie, HashMap<String, TvShow> ListTvShow) {
        double sum = 0;
        double number = 0;
        for (String s : filmography) {
            double rating = 0;
            if (ListMovie.containsKey(s)) {
                rating = ListMovie.get(s).getRating();
            }
            if (ListTvShow.containsKey(s)) {
                rating = ListTvShow.get(s).getRating();
            }

            if (rating != 0) {
                sum += rating;
                number++;
            }
        }
        if(sum != 0) {
            this.average = sum / number;
        } else {
            this.average = 0.0;
        }
    }

    @Override
    public String toString() {
        return name;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getFilmography() {
        return filmography;
    }

    public void setFilmography(ArrayList<String> filmography) {
        this.filmography = filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public void setAwards(Map<ActorsAwards, Integer> awards) {
        this.awards = awards;
    }

    public Integer getNumber_awards() {
        return number_awards;
    }

    public void setNumber_awards(Integer number_awards) {
        this.number_awards = number_awards;
    }
}

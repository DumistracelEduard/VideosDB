package entities;

import actor.ActorsAwards;
import fileio.ActorInputData;

import java.util.ArrayList;
import java.util.Map;

public class Actor {
    private String name;
    private String description;
    private ArrayList<String> filmography;
    private Map<ActorsAwards, Integer> awards;

    public Actor (String name, String description,
                  ArrayList<String> filmography,
                  Map<ActorsAwards, Integer> awards){
        this.name = name;
        this.description = description;
        this.filmography = filmography;
        this.awards = awards;
    }

    public Actor(ActorInputData actor) {
        this.name = actor.getName();
        this.description = actor.getCareerDescription();
        this.filmography = actor.getFilmography();
        this.awards = actor.getAwards();
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
}

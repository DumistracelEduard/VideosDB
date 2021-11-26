package entities;

import actor.ActorsAwards;

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


}

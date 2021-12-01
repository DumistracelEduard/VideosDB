package entities;

import actor.ActorsAwards;
import command.Command;
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
    private Integer numberAwards;

    public Actor(final String name, final String description,
                  final ArrayList<String> filmography,
                  final Map<ActorsAwards, Integer> awards) {
        this.name = name;
        this.description = description;
        this.filmography = filmography;
        this.awards = awards;
        for (ActorsAwards key : awards.keySet()) {
            this.numberAwards += awards.get(key);
        }
    }

    public Actor(final ActorInputData actor) {
        this.name = actor.getName();
        this.description = actor.getCareerDescription();
        this.filmography = actor.getFilmography();
        this.awards = actor.getAwards();
        this.numberAwards = 0;
        for (ActorsAwards key : awards.keySet()) {
            this.numberAwards += awards.get(key);
        }
    }

    /**
     * Calculeaza media ratingurilor in filmele/serialele in care a jucat.
     * @param listMovie
     * @param listTvShow
     */
    public void averageGet(final HashMap<String, Movie> listMovie,
                           final HashMap<String, TvShow> listTvShow) {
        double sum = 0;
        double number = 0;
        for (String s : filmography) {
            double rating = 0;
            if (listMovie.containsKey(s)) {
                rating = listMovie.get(s).getRating();
            }
            if (listTvShow.containsKey(s)) {
                rating = listTvShow.get(s).getRating();
            }

            if (rating != 0) {
                sum += rating;
                number++;
            }
        }
        if (sum != 0) {
            this.average = sum / number;
        } else {
            this.average = 0.0;
        }
    }

    /**
     * Cauta daca se gasesc cuvantul dat ca titlu in descriere.
     * @param command comanda
     * @return 0 daca nu gaseste si 1 daca gaseste
     */
    public int descriptionFilter(final Command command) {
        String text = this.description.toLowerCase();
        for (int i = 0; i < command.getFilters().get(2).size(); ++i) {
            String case1 = command.getFilters().get(2).get(i).concat(" ");
            String case2 = command.getFilters().get(2).get(i).concat(",");
            String case3 = command.getFilters().get(2).get(i).concat(".");
            if (!text.contains(case1) && !text.contains(case3) && !text.contains(case2)) {
                return 0;
            }
        }
        return 1;
    }

    @Override
    public final String toString() {
        return name;
    }

    public final Double getAverage() {
        return average;
    }

    public final void setAverage(final Double average) {
        this.average = average;
    }

    public final String getName() {
        return name;
    }

    public final void setName(final String name) {
        this.name = name;
    }

    public final String getDescription() {
        return description;
    }

    public final void setDescription(final String description) {
        this.description = description;
    }

    public final ArrayList<String> getFilmography() {
        return filmography;
    }

    public final void setFilmography(final ArrayList<String> filmography) {
        this.filmography = filmography;
    }

    public final Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public final void setAwards(final Map<ActorsAwards, Integer> awards) {
        this.awards = awards;
    }

    public final Integer getNumberAwards() {
        return numberAwards;
    }
}

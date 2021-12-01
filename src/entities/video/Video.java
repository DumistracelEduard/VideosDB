package entities.video;

import command.Command;

import java.util.List;

public abstract class Video {
    private final String title;
    private final int year;
    private final List<String> cast;
    private final List<String> genres;
    private int views;

    public Video(final String title, final int year,
                 final List<String> cast, final List<String> genres) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
    }

    /**
     * Verifica anul si returneaza 0 daca nu e sau 1 daca eacelasi
     * @param command
     * @return
     */
    public int yearExist(final Command command) {
        if (command.getFilters().get(0).get(0) == null) {
            return 1;
        }
        if (command.getFilters().get(0).get(0).equals(String.valueOf(year))) {
            return 1;
        }
        return 0;
    }

    /**
     * verifica daca contine acel gen
     * @param command
     * @return 0 daca nu contine si 1 daca contine
     */
    public int genreExist(final Command command) {
        if (command.getFilters().get(1).get(0) == null) {
            return 1;
        }
        for (int i = 0; i < genres.size(); ++i) {
            if (genres.get(i).equals(command.getFilters().get(1).get(0))) {
                return 1;
            }
        }
        return 0;
    }

    /**
     * adauga la numarul de views
     * @param number
     */
    public void addViews(final int number) {
        this.views += number;
    }

    public final String getTitle() {
        return title;
    }

    public final int getYear() {
        return year;
    }

    public final List<String> getCast() {
        return cast;
    }

    public final List<String> getGenres() {
        return genres;
    }

    public final int getViews() {
        return views;
    }
}

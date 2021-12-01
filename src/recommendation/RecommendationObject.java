package recommendation;

import java.util.List;

public class RecommendationObject {
    private String name;
    private Double rating;
    private int pos;
    private List<String> genres;
    private int favorite;

    public RecommendationObject(String name, List<String> genres) {
        this.name = name;
        this.rating = 0.0;
        this.genres = genres;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return name;
    }
}

package recommendation;

import java.util.List;

public class RecommendationObject {
  private final String name;
  private Double rating;
  private int pos;
  private final List<String> genres;
  private int favorite;

  public RecommendationObject(final String name, final List<String> genres) {
    this.name = name;
    this.rating = 0.0;
    this.genres = genres;
  }

  public final void setPos(int pos) {
    this.pos = pos;
  }
  public final int getFavorite() {
    return favorite;
  }

  public final void setFavorite(int favorite) {
    this.favorite = favorite;
  }

  public final String getName() {
    return name;
  }

  public final Double getRating() {
    return rating;
  }

  public final void setRating(Double rating) {
    this.rating = rating;
  }

  public final int getPos() {
    return pos;
  }

  public final List<String> getGenres() {
    return genres;
  }

  @Override
  public final String toString() {
    return name;
  }
}

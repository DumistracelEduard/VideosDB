package recommendation;

public class PopularObject {
  private final String genre;
  private final int number;

  public PopularObject(final String genre, final int number) {
    this.genre = genre;
    this.number = number;
  }

  public final String getGenre() {
    return genre;
  }

  public final int getNumber() {
    return number;
  }
}

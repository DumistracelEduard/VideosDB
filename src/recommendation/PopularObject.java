package recommendation;

public class PopularObject {
    private String genre;
    private int number;

    public PopularObject(String genre, int number) {
        this.genre = genre;
        this.number = number;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}

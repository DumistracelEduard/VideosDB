package database;

public class VideoRating {
    private final String name;
    private final double rating;

    public VideoRating(final String name, final double rating) {
        this.name = name;
        this.rating = rating;
    }

    public final String getName() {
        return name;
    }

    public final double getRating() {
        return rating;
    }

    @Override
    public final String toString() {
        return name;
    }
}

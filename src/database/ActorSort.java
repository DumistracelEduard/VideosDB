package database;

public class ActorSort implements Comparable<ActorSort> {
    private final String name;
    private final Double ratings;

    public ActorSort(final String name, final Double ratings) {
        this.name = name;
        this.ratings = ratings;
    }

    @Override
    public final int compareTo(final ActorSort other) {
        return this.ratings.compareTo(other.ratings);
    }

    public final String getName() {
        return name;
    }

    public final Double getRatings() {
        return ratings;
    }

    @Override
    public final String toString() {
        return name;
    }
}

package database;

public class ActorAwards {
    private final String name;
    private final Integer numberAwards;

    public ActorAwards(final String name, final Integer numberAwards) {
        this.name = name;
        this.numberAwards = numberAwards;
    }

    public final String getName() {
        return name;
    }

    public final Integer getNumberAwards() {
        return numberAwards;
    }

    @Override
    public final String toString() {
        return name;
    }
}

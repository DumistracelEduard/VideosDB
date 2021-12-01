package database;

public class QueryObject {
    private final String name;
    private final int number;

    public QueryObject(final String name, final int number) {
        this.name = name;
        this.number = number;
    }

    public final String getName() {
        return name;
    }

    public final int getNumber() {
        return number;
    }

    @Override
    public final String toString() {
        return name;
    }
}

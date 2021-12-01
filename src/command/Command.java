package command;

import fileio.ActionInputData;

import java.util.List;

public class Command {
    private final int actionid;
    private final String actionType;
    private final String type;
    private final String username;
    private final String objectType;
    private final String sortType;
    private final String criteria;
    private final String title;
    private final String genre;
    private int number;
    private final double grade;
    private final int seasonNumber;
    private final List<List<String>> filters;

    private String message;

    public Command(final ActionInputData actionInputData) {
        this.actionid = actionInputData.getActionId();
        this.actionType = actionInputData.getActionType();
        this.type = actionInputData.getType();
        this.username = actionInputData.getUsername();
        this.genre = actionInputData.getGenre();
        this.objectType = actionInputData.getObjectType();
        this.sortType = actionInputData.getSortType();
        this.criteria = actionInputData.getCriteria();
        this.number = actionInputData.getNumber();
        this.title = actionInputData.getTitle();
        this.grade = actionInputData.getGrade();
        this.seasonNumber = actionInputData.getSeasonNumber();
        this.filters = actionInputData.getFilters();
    }

    public final int getActionid() {
        return actionid;
    }

    public final void setNumber(final int number) {
        this.number = number;
    }

    public final String getMessage() {
        return message;
    }

    public final void setMessage(final String message) {
        this.message = message;
    }

    public final String getActionType() {
        return actionType;
    }

    public final String getType() {
        return type;
    }

    public final String getUsername() {
        return username;
    }

    public final String getObjectType() {
        return objectType;
    }

    public final String getSortType() {
        return sortType;
    }

    public final String getCriteria() {
        return criteria;
    }

    public final String getTitle() {
        return title;
    }

    public final String getGenre() {
        return genre;
    }

    public final int getNumber() {
        return number;
    }

    public final double getGrade() {
        return grade;
    }

    public final int getSeasonNumber() {
        return seasonNumber;
    }

    public final List<List<String>> getFilters() {
        return filters;
    }
}

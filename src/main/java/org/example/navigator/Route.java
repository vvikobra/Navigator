package org.example.navigator;

import org.example.ArrayList;

import java.util.Comparator;
import java.util.Objects;

public class Route implements Comparable<Route> {
    private static int routeCounter = 1;
    private String id;
    private int number;
    private double distance;
    private int popularity;
    private boolean isFavorite;
    private ArrayList<String> locationPoints;

    public Route(double distance, int popularity, ArrayList<String> locationPoints, boolean isFavorite) {
        this.number = routeCounter++;
        this.distance = distance;
        this.popularity = popularity;
        this.isFavorite = isFavorite;
        this.locationPoints = locationPoints;
        this.id = locationPoints.get(0) + '-' + locationPoints.get(locationPoints.size() - 1) + ' ' + distance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public ArrayList<String> getLocationPoints() {
        return locationPoints;
    }

    public void setLocationPoints(ArrayList<String> locationPoints) {
        this.locationPoints = locationPoints;
    }

    public static int getRouteCounter() {
        return routeCounter;
    }

    public static void setRouteCounter(int routeCounter) {
        Route.routeCounter = routeCounter;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return Double.compare(route.getDistance(), this.getDistance()) == 0 &&
                Objects.equals(this.getLocationPoints().get(0), route.getLocationPoints().get(0)) &&
                Objects.equals(this.getLocationPoints().get(getLocationPoints().size() - 1), route.getLocationPoints().get(route.getLocationPoints().size() - 1));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getLocationPoints().get(0), this.getLocationPoints().get(getLocationPoints().size() - 1), getDistance());
    }

    @Override
    public String toString() {
        return "Маршрут №" + number +
                ":\nрасстояние = " + distance +
                " км, популярность = " + popularity +
                ", избранный = " + isFavorite +
                ", точки назначения = " + locationPoints;
    }

    @Override
    public int compareTo(Route o) {
        if (this.isFavorite() && !o.isFavorite()) return -1;
        if (o.isFavorite() && !this.isFavorite()) return 1;
        if (this.number > o.getNumber()) return 1;
        else if (this.number < o.getNumber()) return -1;
        return 0;
    }
}

class RouteComparator implements Comparator<Route> {

    private final String startPoint;
    private final String endPoint;

    public RouteComparator(String startPoint, String endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    @Override
    public int compare(Route o1, Route o2) {
        if (o1.isFavorite() && !o2.isFavorite()) return -1;
        if (o2.isFavorite() && !o1.isFavorite()) return 1;
        if (calculateDistance(o1.getLocationPoints()) != calculateDistance(o2.getLocationPoints())) {
            return calculateDistance(o1.getLocationPoints()) - calculateDistance(o2.getLocationPoints());
        }
        if (o2.getPopularity() != o1.getPopularity()) return o2.getPopularity() - o1.getPopularity();
        return o1.compareTo(o2);
    }

    private int calculateDistance(ArrayList<String> locationPoints) {
        int startIndex = locationPoints.indexOf(startPoint);
        int endIndex = locationPoints.indexOf(endPoint);

        return endIndex - startIndex;
    }

}

class FavoriteRoutesComparator implements Comparator<Route> {

    public FavoriteRoutesComparator() {}

    @Override
    public int compare(Route o1, Route o2) {
        if (o1.getDistance() != o2.getDistance()) return (int) (o1.getDistance() - o2.getDistance());
        if (o2.getPopularity() != o1.getPopularity()) return o2.getPopularity() - o1.getPopularity();
        return o1.compareTo(o2);
    }
}

class TopComparator implements Comparator<Route> {

    public TopComparator() {
    }

    @Override
    public int compare(Route o1, Route o2) {
        if (o1.getPopularity() != o2.getPopularity()) return o2.getPopularity() - o1.getPopularity();
        if (o2.getDistance() != o1.getDistance()) return (int) (o1.getDistance() - o2.getDistance());
        if (o1.getLocationPoints().size() != o2.getLocationPoints().size())
            return o1.getLocationPoints().size() - o2.getLocationPoints().size();
        return o1.compareTo(o2);
    }
}
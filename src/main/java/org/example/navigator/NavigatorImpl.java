package org.example.navigator;

import org.example.hashtable.HashTable;

import org.example.ArrayList;

public class NavigatorImpl implements Navigator {

    private HashTable<String, Route> routes;

    public NavigatorImpl() {
        this.routes = new HashTable<>();
    }

    @Override
    public void addRoute(Route route) {
        if (!contains(route)) routes.add(route.getId(), route);
        else System.out.println("Такой маршрут уже существует!");
    }

    @Override
    public void removeRoute(String routeId) {
        Route route = routes.get(routeId);
        if (route != null) routes.remove(routeId);
    }

    @Override
    public boolean contains(Route route) {
        return routes.containsKey(route.getId());
    }

    @Override
    public int size() {
        return routes.size();
    }

    @Override
    public Route getRoute(String routeId) {
        Route route = routes.get(routeId);
        if (route == null) {
            System.out.println("Маршрут с таким идентификатором отсутствует!");
            return null;
        }
        return route;
    }

    @Override
    public void chooseRoute(String routeId) {
        Route route = routes.get(routeId);
        if (route != null) route.setPopularity(route.getPopularity() + 1);
    }

    @Override
    public Iterable<Route> searchRoutes(String startPoint, String endPoint) {
        ArrayList<Route> result = new ArrayList<>();
        for (Route route : routes.values()) {
            ArrayList<String> locationPoints = route.getLocationPoints();
            if (locationPoints != null && locationPoints.contains(startPoint) && locationPoints.contains(endPoint) &&
                    locationPoints.indexOf(startPoint) < locationPoints.indexOf(endPoint)) {
                result.add(route);
            }
        }

        result.sort(new RouteComparator(startPoint, endPoint));
        return result;
    }

    @Override
    public Iterable<Route> getFavoriteRoutes(String destinationPoint) {
        ArrayList<Route> result = new ArrayList<>();
        for (Route route : routes.values()) {
            ArrayList<String> locationPoints = route.getLocationPoints();
            if (locationPoints.contains(destinationPoint) && !locationPoints.get(0).equals(destinationPoint) && route.isFavorite()) {
                result.add(route);
            }
        }

        result.sort(new FavoriteRoutesComparator());
        return result;
    }

    @Override
    public Iterable<Route> getTop5Routes() {
        ArrayList<Route> result = new ArrayList<>();
        for (Route route : routes.values()) result.add(route);

        result.sort(new TopComparator());

        ArrayList<Route> top5 = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            top5.add(result.get(i));
        }

        return top5;
    }

    public String printRoutes() {
        ArrayList<Route> navigator = new ArrayList<>();
        for (Route route : routes.values()) navigator.add(route);
        navigator.sort(Route::compareTo);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < routes.size(); i++) result.append(navigator.get(i)).append("\n");
        return result.toString();
    }
}
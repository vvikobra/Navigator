package org.example;

import org.example.navigator.NavigatorImpl;
import org.example.navigator.Route;

import java.util.*;

public class App {

    private static final Scanner scanner = new Scanner(System.in);
    private static final NavigatorImpl navigator = new NavigatorImpl();

    public static void main(String[] args) {
        initRoutes();
        System.out.println("--- НАВИГАТОР ---");
        System.out.println();
        while (true) {
            System.out.println("1. Добавить маршрут");
            System.out.println("2. Удалить маршрут");
            System.out.println("3. Просмотреть все маршруты");
            System.out.println("4. Просмотреть количество маршрутов");
            System.out.println("5. Найти маршрут");
            System.out.println("6. Выбрать маршрут");
            System.out.println("7. Проверить, существует ли маршрут");
            System.out.println("8. Найти маршрут по точкам назначения");
            System.out.println("9. Получить список избранных маршрутов");
            System.out.println("10. Получить топ-5 популярных маршрутов");
            System.out.println("11. Выйти из программы");
            System.out.println("-----------------");
            System.out.println("Выберите действие: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    addRoute();
                    break;
                case 2:
                    deleteRoute();
                    break;
                case 3:
                    showAllRoutes();
                    break;
                case 4:
                    showRoutesQuantity();
                    break;
                case 5:
                    searchRouteById();
                    break;
                case 6:
                    chooseRoute();
                    break;
                case 7:
                    containsRoute();
                    break;
                case 8:
                    searchRouteByPoints();
                    break;
                case 9:
                    searchFavoriteRoutes();
                    break;
                case 10:
                    searchTop5Routes();
                    break;
                case 11:
                    return;
            }
        }
    }

    private static void addRoute() {
        scanner.nextLine();
        System.out.println("Введите пункты через запятую:");
        String[] inputPoints = scanner.nextLine().split(", ");
        if (inputPoints[0].equals(inputPoints[inputPoints.length - 1])) {
            System.out.println("Начало и конец маршрута не могут совпадать!");
            return;
        }
        System.out.println("Введите расстояние: ");
        double distance = scanner.nextDouble();
        System.out.println("Добавить путь в избранные? (true/false)");
        boolean isFavorite = scanner.nextBoolean();
        Route route = new Route(distance, 0, ArrayList.asList(inputPoints), isFavorite);
        navigator.addRoute(route);
        System.out.println("Маршрут успешно добавлен!");
    }

    private static void deleteRoute() {
        scanner.nextLine();
        System.out.println("Введите начальную, конечную точки и расстояние маршрута для удаления в формате: начало-конец расстояние");
        String key = scanner.nextLine();

        Route route = navigator.getRoute(key);

        if (route != null) {
            navigator.removeRoute(key);
            System.out.println("Маршрут " + key + " успешно удален!");
        }
    }

    private static void showAllRoutes() {
        System.out.println(navigator.printRoutes());
    }

    private static void showRoutesQuantity() {
        System.out.println(navigator.size());
    }

    private static void searchRouteById() {
        scanner.nextLine();
        System.out.println("Введите начальную, конечную точки и расстояние маршрута в формате: начало-конец расстояние");
        String key = scanner.nextLine();

        Route route = navigator.getRoute(key);
        if (route != null) {
            System.out.println(navigator.getRoute(key));
        }
    }

    private static void chooseRoute() {
        scanner.nextLine();
        System.out.println("Введите начальную, конечную точки и расстояние для выбора маршрута в формате: начало-конец расстояние");
        String key = scanner.nextLine();
        Route route = navigator.getRoute(key);
        if (route != null) {
            navigator.chooseRoute(key);
            System.out.println(navigator.getRoute(key));
        }
    }

    private static void containsRoute() {
        scanner.nextLine();
        System.out.println("Введите начальную, конечную точки и расстояние маршрута в формате: начало-конец расстояние");
        String key = scanner.nextLine();

        Route route = navigator.getRoute(key);
        System.out.println(navigator.contains(route));
    }

    private static void searchRouteByPoints() {
        scanner.nextLine();
        System.out.println("Введите начальную и конечную точку маршрута через запятую: ");
        String[] inputLocations = scanner.nextLine().split(", ");
        for (Route route : navigator.searchRoutes(inputLocations[0], inputLocations[1])) System.out.println(route);
        System.out.println();
    }

    private static void searchFavoriteRoutes() {
        scanner.nextLine();
        System.out.println("Введите точку назначения: ");
        String point = scanner.nextLine();
        for (Route route : navigator.getFavoriteRoutes(point)) System.out.println(route);
    }

    private static void searchTop5Routes() {
        for (Route route : navigator.getTop5Routes()) System.out.println(route);
    }

    private static void initRoutes() {
        navigator.addRoute(new Route(700, 20, ArrayList.asList("Москва", "Казань"), true));
        navigator.addRoute(new Route(700, 20, ArrayList.asList("Москва", "Казань"), true));
        navigator.addRoute(new Route(1300, 28, ArrayList.asList("Санкт-Петербург", "Москва", "Казань"), true));
        navigator.addRoute(new Route(1300, 23, ArrayList.asList("Челябинск", "Москва", "Казань", "Самара"), true));
        navigator.addRoute(new Route(1000, 23, ArrayList.asList("Челябинск", "Москва"), true));
        navigator.addRoute(new Route(1400, 23, ArrayList.asList("Новосибирск", "Тюмень", "Томск", "Омск"), false));
        navigator.addRoute(new Route(1300, 17, ArrayList.asList("Eкатеринбург", "Чебоксары", "Казань"), true));
        navigator.addRoute(new Route(1500, 25, ArrayList.asList("Москва", "Саратов", "Воронеж", "Казань"), false));
        navigator.addRoute(new Route(1500, 15, ArrayList.asList("Челябинск", "Москва", "Нижний Новгород", "Казань"), false));
        navigator.addRoute(new Route(2000, 10, ArrayList.asList("Севастополь", "Москва", "Подольск", "Казань", "Ростов"), true));
    }
}
import java.util.*;

public class DeliveryRouteManager {

    static class Graph {
        private final Map<String, List<String>> adjacencyList;

        public Graph() {
            adjacencyList = new HashMap<>();
        }

        // Add a city (node)
        public void addCity(String city) {
            adjacencyList.putIfAbsent(city, new ArrayList<>());
        }

        // Add a road (edge) between cities
        public void addRoad(String city1, String city2) {
            adjacencyList.get(city1).add(city2);
            adjacencyList.get(city2).add(city1); // Undirected graph
        }

        // Display the road network
        public void displayNetwork() {
            System.out.println("\nRoad Network:");
            for (var entry : adjacencyList.entrySet()) {
                System.out.println(entry.getKey() + " -> " + entry.getValue());
            }
        }

        // Find the shortest route between two cities using BFS
        public List<String> findShortestRoute(String start, String end) {
            Queue<List<String>> queue = new LinkedList<>();
            Set<String> visited = new HashSet<>();

            queue.add(Collections.singletonList(start));
            visited.add(start);

            while (!queue.isEmpty()) {
                List<String> path = queue.poll();
                String lastCity = path.get(path.size() - 1);

                if (lastCity.equals(end)) {
                    return path; // Shortest path found
                }

                for (String neighbor : adjacencyList.getOrDefault(lastCity, Collections.emptyList())) {
                    if (!visited.contains(neighbor)) {
                        List<String> newPath = new ArrayList<>(path);
                        newPath.add(neighbor);
                        queue.add(newPath);
                        visited.add(neighbor);
                    }
                }
            }
            return null; // No path found
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Graph graph = new Graph();
        boolean running = true;

        System.out.println("Welcome to the Delivery Route Manager!");

        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1. Add City");
            System.out.println("2. Add Road");
            System.out.println("3. Display Road Network");
            System.out.println("4. Find Shortest Route");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1: // Add City
                    System.out.print("Enter city name: ");
                    String city = scanner.nextLine();
                    graph.addCity(city);
                    System.out.println("City added!");
                    break;

                case 2: // Add Road
                    System.out.print("Enter the first city: ");
                    String city1 = scanner.nextLine();
                    System.out.print("Enter the second city: ");
                    String city2 = scanner.nextLine();
                    graph.addCity(city1); // Ensure cities exist
                    graph.addCity(city2);
                    graph.addRoad(city1, city2);
                    System.out.println("Road added between " + city1 + " and " + city2 + "!");
                    break;

                case 3: // Display Road Network
                    graph.displayNetwork();
                    break;

                case 4: // Find Shortest Route
                    System.out.print("Enter the starting city: ");
                    String startCity = scanner.nextLine();
                    System.out.print("Enter the destination city: ");
                    String endCity = scanner.nextLine();
                    List<String> route = graph.findShortestRoute(startCity, endCity);
                    if (route != null) {
                        System.out.println("Shortest route: " + route);
                    } else {
                        System.out.println("No route found between " + startCity + " and " + endCity);
                    }
                    break;

                case 5: // Exit
                    running = false;
                    System.out.println("Exiting the application. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}
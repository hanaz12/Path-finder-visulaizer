# PathFinder - Pathfinding Visualization Application

**Project Overview**

PathFinder is a JavaFX-based desktop application designed to visualize and compare pathfinding algorithms on a 20x20 grid. Users can interactively select a start cell, target cell, and obstacles (bricks) to observe how algorithms like Breadth-First Search (BFS), Depth-First Search (DFS), and Dijkstra's algorithm navigate the grid. The application provides real-time visualization, calculates path costs based on user-defined weights, and indicates whether a path exists. It leverages JavaFX for the GUI, multithreading for smooth visualization, and a modular structure for algorithm implementations.

The project is organized into distinct packages for algorithms, entities, and the main application, ensuring a clean separation of concerns. Key features include interactive grid manipulation, algorithm visualization, and result reporting.

**Technologies Used**

- **Java:** Core programming language (JDK 21+).
- **JavaFX:** For building the graphical user interface (version 21).
- **Maven:** For dependency management and project build.
- **Multithreading:** Using `Task` and `Platform.runLater` for visualization.
- **Java Collections:** `Queue`, `Map`, and `Set` for algorithm logic.

## Prerequisites
- **Java Development Kit (JDK):** JDK 21 or later.
- **Maven:** For dependency management and building the project.
- **JavaFX SDK:** Version 21 (ensure it’s configured in your environment or `pom.xml`).
- **IDE (Optional):** IntelliJ IDEA or another Java IDE for running and debugging.


 ة## Usage

The PathFinder application allows users to visualize pathfinding algorithms on a 20x20 grid. Follow these steps to use the application:

- **Launch the Application:** Start the application using `mvn javafx:run` or by running the `HelloApplication` class in your IDE. A window will open displaying a 20x20 grid with control buttons on the left.

- **Select Start Cell:** Click the "Choose start cell" button, then click a cell on the grid to set it as the starting point. The cell will turn green.

- **Select Target Cell:** Click the "Choose target cell" button, then click a cell on the grid to set it as the target. The cell will turn red.

- **Add Bricks (Optional):** Click the "Choose bricks locations" button, then click cells on the grid to add obstacles. These cells will turn gray.

- **Run Pathfinding Algorithm:** Choose an algorithm to run:
  - Click "Run BFS" to execute the Breadth-First Search algorithm.
  - Click "Run DFS" to execute the Depth-First Search algorithm.
  - Click "Run Dijkstra" to execute Dijkstra's algorithm.
  During execution, visited cells will turn light blue, and the final path (if found) will be highlighted in yellow.

- **View Results:** After the visualization completes:
  - The "Total Cost" label will display the path cost based on user-defined weights.
  - The "Final Result" label will indicate whether a path was found.
  - If no path exists, an alert will appear with the message "No path found".

- **Reset the Grid:** Click the "Reset" button to clear the grid, reset all selections, and start over.

## Project Structure

The PathFinder project is organized into a clean and modular structure to separate concerns and improve maintainability. Below is an overview of the project’s file and directory structure:

- `src/main/java/com/example/pathfinder/`
  - `Algorithms/`
    - `BFS.java`: Contains the implementation of the Breadth-First Search algorithm with visualization logic, including pathfinding and UI updates.
    - `DFS.java`: Contains the implementation of the Depth-First Search algorithm with visualization logic, similar to BFS but using a depth-first approach.
    - `Dijkstra.java`: Contains the implementation of Dijkstra's algorithm with visualization logic, focusing on weighted pathfinding (assumed provided).
    - `CommonLogic.java`: Provides utility methods used by the algorithms, including cell validation (`validCell`), neighbor retrieval (`getNeighbors`), cost calculation (`calcCost`), and handling no-path scenarios (`handleNoPathFound`).
  - `Entity/`
    - `Cell.java`: Defines the `Cell` class, representing a grid cell with `x` and `y` coordinates, along with `equals` and `hashCode` methods for proper comparison in collections.
    - `Response.java`: Defines the `Response` class, which holds pathfinding results such as the path cost (`cost`) and whether a path exists (`hasPath`).
  - `HelloApplication.java`: The main entry point of the JavaFX application, responsible for launching the GUI.
  - `HelloController.java`: The controller class for the JavaFX UI, handling user interactions, managing the grid, and coordinating algorithm execution.

- `src/main/resources/com/example/pathfinder/`
  - `hello-view.fxml`: The FXML file that defines the layout of the user interface, including the grid and control buttons.
  
   

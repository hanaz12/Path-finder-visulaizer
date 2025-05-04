package com.example.pathfinder.Algorithms;

import com.example.pathfinder.Entity.Cell;
import com.example.pathfinder.Entity.Response;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.util.*;

public class Dijekstra {
    public void runDijekstraWithVisualization(Rectangle[][] grid, Cell start, Cell end, Set<Cell> bricks,
                                        int numOfRows, int numOfCols, HashMap<Cell, Integer> cost,
                                        BFS.Callback<Response> callback) {
        Task<Response> task = new Task<>() {
            @Override
            protected Response call() throws Exception {
                PriorityQueue<Pair<Integer, Cell>> queue = new PriorityQueue<>(
                        Comparator.comparingInt(Pair::getKey)
                );
                Map<Cell, Cell> parent = new HashMap<>();
                boolean[][] visited = new boolean[numOfRows][numOfCols];
                CommonLogic commonLogic = new CommonLogic();
                queue.add(new Pair<>(0,start));
                visited[start.getX()][start.getY()] = true;
                boolean hasPath = false;
                int totalCost = 0;
                while (!queue.isEmpty()) {
                    Pair<Integer, Cell> current = queue.poll();
                    Platform.runLater(() -> {
                        Rectangle rect = grid[current.getValue().getX()][current.getValue().getY()];
                        rect.setStyle("-fx-fill: lightblue; -fx-stroke: black; -fx-stroke-width: 0.5;");
                    });

                    Thread.sleep(40);

                    if (current.getValue().equals(end)) {
                        hasPath = true;
                        break;
                    }
                    int costOfParent= current.getKey();
                    for (Cell neighbor : CommonLogic.getNeighbors(current.getValue())) {

                        if (CommonLogic.validCell(neighbor.getX(), neighbor.getY(), bricks, visited, numOfRows, numOfCols)) {
                            if (!visited[neighbor.getX()][neighbor.getY()]) {
                                queue.add(new Pair<>(costOfParent+cost.getOrDefault(neighbor,0), neighbor));
                                visited[neighbor.getX()][neighbor.getY()] = true;
                                parent.put(neighbor, current.getValue());
                            }
                        }
                    }
                }


                if (hasPath) {
                    Cell pathCell = end;
                    while (pathCell != null && parent.containsKey(pathCell)) {
                        totalCost += cost.get(pathCell);
                        Cell finalPathCell = pathCell;
                        Platform.runLater(() -> {
                            if (finalPathCell.getX() >= 0 && finalPathCell.getX() < numOfRows &&
                                    finalPathCell.getY() >= 0 && finalPathCell.getY() < numOfCols) {
                                Rectangle rect = grid[finalPathCell.getX()][finalPathCell.getY()];
                                rect.setStyle("-fx-fill: yellow; -fx-stroke: black; -fx-stroke-width: 0.5;");
                            }
                        });
//                        Thread.sleep(100);
                        pathCell = parent.get(pathCell);
                    }


                }


                return new Response(totalCost, hasPath);
            }
        };

        new Thread(task).start();

        task.setOnSucceeded(event -> {
            Response response = task.getValue();
            if (callback != null) {
                Platform.runLater(() -> callback.onResult(response));
            }
        });

        task.setOnFailed(event -> {
            task.getException().printStackTrace();
        });
    }

    @FunctionalInterface
    public interface Callback<T> {
        void onResult(T result);
    }
}

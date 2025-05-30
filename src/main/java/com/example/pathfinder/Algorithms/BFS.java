package com.example.pathfinder.Algorithms;

import com.example.pathfinder.Entity.Cell;
import com.example.pathfinder.Entity.Response;
import javafx.application.Platform;
import javafx.scene.shape.Rectangle;
import javafx.concurrent.Task;

import java.util.*;

public class BFS {
    public void runBFSWithVisualization(Rectangle[][] grid, Cell start, Cell end, Set<Cell> bricks,
                                        int numOfRows, int numOfCols, HashMap<Cell, Integer> cost,
                                        Callback<Response> callback) {
        Task<Response> task = new Task<>() {
            @Override
            protected Response call() throws Exception {
                Queue<Cell> queue = new LinkedList<>();
                Map<Cell, Cell> parent = new HashMap<>();
                boolean[][] visited = new boolean[numOfRows][numOfCols];
                CommonLogic commonLogic = new CommonLogic();
                queue.add(start);
                visited[start.getX()][start.getY()] = true;
                boolean hasPath = false;
                int totalCost = 0;
                while (!queue.isEmpty()) {
                    Cell current = queue.poll();

                    Platform.runLater(() -> {
                        Rectangle rect = grid[current.getX()][current.getY()];
                        rect.setStyle("-fx-fill: lightblue; -fx-stroke: black; -fx-stroke-width: 0.5;");
                    });

                    Thread.sleep(40);

                    if (current.equals(end)) {
                        hasPath = true;
                        break;
                    }

                    for (Cell neighbor : CommonLogic.getNeighbors(current)) {
                        if (CommonLogic.validCell(neighbor.getX(), neighbor.getY(), bricks, visited, numOfRows, numOfCols)) {
                            if (!visited[neighbor.getX()][neighbor.getY()]) {
                                queue.add(neighbor);
                                visited[neighbor.getX()][neighbor.getY()] = true;
                                parent.put(neighbor, current);
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
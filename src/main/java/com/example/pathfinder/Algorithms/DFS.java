package com.example.pathfinder.Algorithms;

import com.example.pathfinder.Entity.Cell;
import com.example.pathfinder.Entity.Response;
import javafx.application.Platform;
import javafx.scene.shape.Rectangle;
import javafx.concurrent.Task;

import java.util.*;

public class DFS {
    public void runDFSWithVisualization(Rectangle[][] grid,
                                        Cell start,
                                        Cell end,
                                        Set<Cell> bricks,
                                        int numOfRows,
                                        int numOfCols,
                                        HashMap<Cell, Integer> cost,
                                        Callback<Response> callback) {

        Task<Response> task = new Task<>() {
            private boolean[][] visited;
            private Map<Cell, Cell> parent;
            private boolean foundEnd = false;
            private int totalCost = 0;

            @Override
            protected Response call() throws Exception {
                visited = new boolean[numOfRows][numOfCols];
                parent = new HashMap<>();
                parent.put(start, null);
                foundEnd = false;
                totalCost = 0;

                dfs(start);

                if (foundEnd) {

                    Cell pathCell = end;

                    while (pathCell != null && parent.containsKey(pathCell)) {
                        if (!pathCell.equals(start)) {
                            totalCost += cost.getOrDefault(pathCell, 0);
                        }
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

                return new Response(totalCost, foundEnd);
            }

            private void dfs(Cell current) throws InterruptedException {
                if (current == null || visited[current.getX()][current.getY()] || foundEnd) {
                    return;
                }

                visited[current.getX()][current.getY()] = true;

                Platform.runLater(() -> {
                    if (current.getX() >= 0 && current.getX() < numOfRows &&
                            current.getY() >= 0 && current.getY() < numOfCols) {
                        Rectangle rect = grid[current.getX()][current.getY()];
                        rect.setStyle("-fx-fill: lightblue; -fx-stroke: black; -fx-stroke-width: 0.5;");
                    }
                });

                Thread.sleep(40);

                if (current.equals(end)) {
                    foundEnd = true;
                    return;
                }

                for (Cell neighbor : CommonLogic.getNeighbors(current)) {
                    int x = neighbor.getX();
                    int y = neighbor.getY();

                    if (CommonLogic.validCell(x, y, bricks, visited, numOfRows, numOfCols) && !foundEnd) {
                        parent.put(neighbor, current);
                        dfs(neighbor);
                    }
                }
            }
        };

        task.setOnSucceeded(event -> {
            Response response = task.getValue();
            if (callback != null) {
                Platform.runLater(() -> callback.onResult(response));
            }
        });

        task.setOnFailed(event -> {
            task.getException().printStackTrace();
        });

        new Thread(task).start();
    }

    @FunctionalInterface
    public interface Callback<T> {
        void onResult(T result);
    }
}
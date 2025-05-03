package com.example.pathfinder.Algorithms;

import com.example.pathfinder.Entity.Cell;
import javafx.application.Platform;
import javafx.scene.shape.Rectangle;
import javafx.concurrent.Task;

import java.util.*;

public class DFS {
    public void runDFSWithVisualization(Rectangle[][] grid, Cell start, Cell end, Set<Cell> bricks, int numOfRows, int numOfCols) {
        Task<Void> task = new Task<>() {
            private boolean[][] visited;
            private HashMap<Cell, Cell> parent;
            private boolean foundEnd = false;

            @Override
            protected Void call() throws Exception {
                visited = new boolean[numOfRows][numOfCols];
                parent = new HashMap<>();
                parent.put(start, null);
                foundEnd = false;

                dfs(start);
                if (foundEnd) {
                    Cell pathCell = end;
                    while (pathCell != null && parent.containsKey(pathCell)) {
                        Cell finalPathCell = pathCell;
                        Platform.runLater(() -> {
                            if (finalPathCell.getX() >= 0 && finalPathCell.getX() < numOfRows &&
                                    finalPathCell.getY() >= 0 && finalPathCell.getY() < numOfCols) {
                                Rectangle rect = grid[finalPathCell.getX()][finalPathCell.getY()];
                                rect.setStyle("-fx-fill: yellow; -fx-stroke: black; -fx-stroke-width: 0.5;");
                            }
                        });
                        pathCell = parent.get(pathCell);
                    }
                }

                return null;
            }

            private void dfs(Cell current) throws InterruptedException {
                if (current == null || visited[current.getX()][current.getY()] || foundEnd) {
                    return;
                }

                visited[current.getX()][current.getY()] = true;
                Platform.runLater(() -> {
                    if (CommonLogic.validCell(current.getX(), current.getY(), bricks,visited,numOfRows,numOfCols)) {
                        Rectangle rect = grid[current.getX()][current.getY()];
                        rect.setStyle("-fx-fill: lightblue; -fx-stroke: black; -fx-stroke-width: 0.5;");
                    }
                });

                Thread.sleep(40);

                if (current.equals(end)) {
                    foundEnd = true;
                    return;
                }

                List<Cell> neighbors = CommonLogic.getNeighbors(current);
                if (neighbors != null) {
                    for (Cell neighbor : neighbors) {
                        int x = neighbor.getX();
                        int y = neighbor.getY();
                        if (CommonLogic.validCell(x, y, bricks, visited, numOfRows, numOfCols) && !foundEnd) {
                            parent.put(neighbor, current);
                            dfs(neighbor);
                        }
                    }
                }
            }
        };

        new Thread(task).start();
    }
}
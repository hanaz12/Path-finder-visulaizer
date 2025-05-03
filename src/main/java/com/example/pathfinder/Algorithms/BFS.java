package com.example.pathfinder.Algorithms;

import com.example.pathfinder.Entity.Cell;

import javafx.application.Platform;
import javafx.scene.shape.Rectangle;
import javafx.concurrent.Task;

import java.util.*;
;

public class BFS {
    public  void runBFSWithVisualization(Rectangle[][] grid, Cell start, Cell end, Set<Cell> bricks ,int numOfRows, int numOfCols) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                Queue<Cell> queue = new LinkedList<>();
                Map<Cell, Cell> parent = new HashMap<>();
                boolean[][] visited = new boolean[20][20];

                queue.add(start);
                visited[start.getX()][start.getY()] = true;

                while (!queue.isEmpty()) {
                    Cell current = queue.poll();


                    Platform.runLater(() -> {
                        Rectangle rect = grid[current.getX()][current.getY()];
                        rect.setStyle("-fx-fill: lightblue; -fx-stroke: black; -fx-stroke-width: 0.5;");
                    });

                    Thread.sleep(40);

                    if (current.equals(end)) {
                        break;
                    }

                    for (Cell neighbor : CommonLogic.getNeighbors(current)) {
                        int x = neighbor.getX();
                        int y = neighbor.getY();

                        if (CommonLogic.validCell(x,y,bricks,visited,numOfRows,numOfCols)) {
                            queue.add(neighbor);
                            visited[x][y] = true;
                            parent.put(neighbor, current);
                        }
                    }
                }


                Cell pathCell = end;
                while (pathCell != null && parent.containsKey(pathCell)) {
                    Cell finalPathCell = pathCell;

                    Platform.runLater(() -> {
                        Rectangle rect = grid[finalPathCell.getX()][finalPathCell.getY()];
                        rect.setStyle("-fx-fill: yellow; -fx-stroke: black; -fx-stroke-width: 0.5;");
                    });

                    Thread.sleep(100);
                    pathCell = parent.get(pathCell);
                }

                return null;
            }
        };

        new Thread(task).start();
    }



}

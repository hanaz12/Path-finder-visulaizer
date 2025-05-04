package com.example.pathfinder.Algorithms;

import com.example.pathfinder.Entity.Cell;
import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class CommonLogic {
    public static boolean validCell(int x, int y, Set<Cell> bricks, boolean vis[][], int numOfRows, int numOfCols) {
        return x>=0 && x<numOfRows && y>=0 && y< numOfCols && !vis[x][y]&&!bricks.contains(new Cell(x, y));
    }
    public static List<Cell> getNeighbors(Cell cell) {
        List<Cell> neighbors = new ArrayList<>();
        int x = cell.getX();
        int y = cell.getY();

        neighbors.add(new Cell(x + 1, y));
        neighbors.add(new Cell(x - 1, y));
        neighbors.add(new Cell(x, y + 1));
        neighbors.add(new Cell(x, y - 1));

        return neighbors;
    }
    public int calcCost(List<Cell> path , HashMap<Cell,Integer> cost , Cell start){
        int pathCost = 0;
        for (Cell c:path){
            if (!c.equals(start)&&cost.containsKey(c)){
                pathCost += cost.get(c);
            }
        }
        return pathCost;
    }

}

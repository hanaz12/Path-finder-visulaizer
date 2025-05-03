package com.example.pathfinder;

import com.example.pathfinder.Entity.Cell;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.pathfinder.Algorithms.*;
public class HelloController {

    @FXML private Button chooseStartButton;
    @FXML private Button chooseTargetButton;
    @FXML private Button chooseBricksButton;
    @FXML private Button runBFSButton;
    @FXML private Button runDFSButton;
    @FXML private Button runDijkstraButton;
    @FXML private Button calcCostButton;
    @FXML private Button resetButton;
    @FXML private Label startCellLabel;
    @FXML private Label endCellLabel;
    @FXML private Label statusLabel;
    @FXML private Label totalCostLabel;
    @FXML private Label finalResultLabel;
    @FXML private GridPane gridPane;

    private Cell startPoint;
    private Cell targetPoint;
    private Set<Cell> bricks;
    private Color startPointColor = Color.GREEN;
    private Color targetPointColor = Color.RED;
    private Color brickColor = Color.GRAY;
    private boolean isChoosingStart ;
    private boolean isChoosingTarget ;
    private boolean isChoosingBrick ;
    private Rectangle[][] cells;

    public final int numOfGridRows=20;
    public final int numOfGridCols=20;

    private String toRGBCode(Color color) {
        return String.format("#%02X%02X%02X",
                (int)(color.getRed() * 255),
                (int)(color.getGreen() * 255),
                (int)(color.getBlue() * 255));
    }

    private void setDefaultCellStyle(Rectangle cell) {
        cell.setStyle("-fx-fill: white; -fx-stroke: black; -fx-stroke-width: 0.5;");
    }

    @FXML
    public void initialize() {

        initializeGrid();
        reset();
    }

    private void initializeGrid() {
        int rows = 20;
        int cols = 20;
        double cellSize = 30;
        cells = new Rectangle[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Rectangle cell = new Rectangle(cellSize, cellSize);
                gridPane.add(cell, col, row);
                setDefaultCellStyle(cell);
                cells[row][col] = cell;
                final int finalRow = row;
                final int finalCol = col;
                cell.setOnMouseClicked(e -> handleGridClick(finalRow, finalCol));
            }
        }
    }



    private void handleGridClick(int row, int col) {
        Rectangle cell = cells[row][col];
        if (isChoosingStart) {
            if (startPoint!=null) {
                setDefaultCellStyle(cells[startPoint.getX()][startPoint.getY()]);
            }
            startPoint=new Cell();
            startPoint.setX(row);
            startPoint.setY(col);
            cell.setStyle("-fx-fill: " + toRGBCode(startPointColor) + "; -fx-stroke: black; -fx-stroke-width: 0.5;");

        } else if (isChoosingTarget) {
            if (targetPoint!=null) {
                setDefaultCellStyle(cells[targetPoint.getX()][targetPoint.getY()]);
            }
            targetPoint=new Cell();
            targetPoint.setX(row);
            targetPoint.setY(col);
            cell.setStyle("-fx-fill: " + toRGBCode(targetPointColor) + "; -fx-stroke: black; -fx-stroke-width: 0.5;");
        } else if (isChoosingBrick) {
            Cell brick = new Cell(row,col);
            if ((startPoint == null || !brick.equals(startPoint)) &&
                    (targetPoint == null || !brick.equals(targetPoint))) {

                bricks.add(brick);
                cell.setStyle("-fx-fill: " + toRGBCode(brickColor) + "; -fx-stroke: black; -fx-stroke-width: 0.5;");
            }
        }
    }
    private void activeRunningButtonCondition(boolean start,boolean target,boolean brick) {
        isChoosingStart=start;
        isChoosingTarget=target;
        isChoosingBrick=brick;
    }

    @FXML
    private void reset() {
        activeRunningButtonCondition(false,false,false);
        bricks = new HashSet<>();
        startPoint=null;
        targetPoint=null;
        for (int row = 0; row < 20; row++) {
            for (int col = 0; col < 20; col++) {
                setDefaultCellStyle(cells[row][col]);
            }
        }
    }
    @FXML
    public void chooseStartCell() {
        activeRunningButtonCondition(true,false,false);
    }

    @FXML
    private void chooseTargetCell() {
        activeRunningButtonCondition(false,true,false);
    }

    @FXML
    private void chooseBricks() {
        activeRunningButtonCondition(false,false,true);
    }
private void checkForStartandTargetPoints(){
        if (startPoint==null||targetPoint==null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Missing input data");
            alert.setContentText("Start and target points should be chosen");
            alert.showAndWait();
            reset();
        }


}
    @FXML
    private void runBFS() {
        checkForStartandTargetPoints();
       // System.out.println("Bricks: " + bricks);
        BFS b=new BFS();
        b.runBFSWithVisualization(cells,startPoint,targetPoint,bricks,numOfGridRows,numOfGridCols);

    }

    @FXML
    private void runDFS() {
        checkForStartandTargetPoints();
       // System.out.println("Bricks: " + bricks);
        DFS b=new DFS();
        b.runDFSWithVisualization(cells,startPoint,targetPoint,bricks,numOfGridRows,numOfGridCols);
    }

    @FXML
    private void runDijkstra() {
        checkForStartandTargetPoints();
    }

    @FXML
    private void calcCost() {
        checkForStartandTargetPoints();
    }

}
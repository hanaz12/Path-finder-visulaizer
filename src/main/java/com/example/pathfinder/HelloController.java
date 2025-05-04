package com.example.pathfinder;

import com.example.pathfinder.Entity.Cell;
import com.example.pathfinder.Entity.Response;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

import java.util.*;

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
    private HashMap<Cell,Integer> cost;

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
        cost = new HashMap<>();
        int rows = 20;
        int cols = 20;
        double cellSize = 30;
        cells = new Rectangle[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {

                Rectangle cell = new Rectangle(cellSize, cellSize);
                setDefaultCellStyle(cell);
                cells[row][col] = cell;

                Label costLabel = new Label();
                costLabel.setStyle("-fx-font-size: 10px; -fx-text-fill: black; -fx-font-weight: bold;");
                StackPane cellPane = new StackPane();
                cellPane.getChildren().addAll(cell, costLabel);

                gridPane.add(cellPane, col, row);

                Cell c = new Cell(row, col);
                int cellCost = (int)(Math.random() * 11);
                cost.put(c, cellCost);

                costLabel.setText(String.valueOf(cellCost));

                final int finalRow = row;
                final int finalCol = col;
                cellPane.setOnMouseClicked(e -> handleGridClick(finalRow, finalCol));
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
        startCellLabel.setText("");
        endCellLabel.setText("");
        totalCostLabel.setText("");
        statusLabel.setText("");

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
        new BFS().runBFSWithVisualization(cells, startPoint, targetPoint, new HashSet<>(bricks), 20, 20, cost, response -> {
            Platform.runLater(() -> {
               afterAlgo(response.getCost(), response.isHasPath());
            });
        });
    }

    @FXML
    private void runDFS() {
        checkForStartandTargetPoints();
        new DFS().runDFSWithVisualization(cells, startPoint, targetPoint, new HashSet<>(bricks), 20, 20, cost, response -> {
            Platform.runLater(() -> {
                afterAlgo(response.getCost(), response.isHasPath());
            });
        });
    }

    @FXML
    private void runDijkstra() {
        checkForStartandTargetPoints();
        new Dijekstra().runDijekstraWithVisualization(cells, startPoint, targetPoint, new HashSet<>(bricks), 20, 20, cost, response -> {
            Platform.runLater(() -> {
                afterAlgo(response.getCost(), response.isHasPath());
            });
        });
    }

    @FXML
    private void calcCost() {
        checkForStartandTargetPoints();
    }
 public void afterAlgo(int cost, boolean path){
     startCellLabel.setText(startPoint.toString());
     endCellLabel.setText(targetPoint.toString());
     totalCostLabel.setText(cost+"");
     if (path){
         statusLabel.setText("Path Found");
     }
     else{
         statusLabel.setText("Path Not Found");
     }
 }
}
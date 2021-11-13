package com.example.game2048;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    AnchorPane anchorPane;

    @FXML
    private Button button2048;

    @FXML
    private Button buttonNewGame;

    @FXML
    private Label labelScore;

    @FXML
    private Label labelBest;

    @FXML
    private GridPane grid;

    @FXML
    private void actionButton2048() {
        System.out.println("2048 button was pressed!");
    }

    @FXML
    private void actionNewGame() {
        System.out.println("actionNewGame");
        for (int i = 0; i < grid.getColumnCount() * grid.getRowCount(); i++) {
            clearCell(((Label) grid.getChildren().get(i)));
        }
        labelScore.setText("0");
        addNewNumToCell();
        addNewNumToCell();
    }


    private void upArrowLogic() {
        mergeCellsVertically();
        moveUpAllCells();
        addNewNumToCell();
    }

    private void downArrowLogic() {
        mergeCellsVertically();
        moveDownAllCells();
        addNewNumToCell();
    }

    private void leftArrowLogic() {
        mergeCellsHorizontally();
        moveLeftAllCells();
        addNewNumToCell();
    }

    private void rightArrowLogic() {
        mergeCellsHorizontally();
        moveRightAllCells();
        addNewNumToCell();
    }

    private void mergeCellsVertically() {
        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 3; row++) {
                if (!(((Label) grid.getChildren().get(row * 4 + col)).getText().equals(""))) {
                    Label targetCell = ((Label) grid.getChildren().get(row * 4 + col));
                    Label nextCell = ((Label) grid.getChildren().get((row + 1) * 4 + col));
                    // if nextCell is empty move targetCell one row
                    if (nextCell.getText().equals("")) {
                        moveCell(targetCell, nextCell);
                    } else {
                        if (targetCell.getText().equals(nextCell.getText())) {
                            int sumOfTwoCells = getSumTwoEqualCells(targetCell, nextCell);
                            changeScore(sumOfTwoCells);
                            changeCellNumAndStyle(String.valueOf(sumOfTwoCells), targetCell);
                            moveCell(targetCell, nextCell);

                            // after merging, if row == 0 we skip two rows to check last possible merge
                            // else there are no more possible merges remained
                            if (row == 0) {
                                row++;
                            } else break;
                        }
                    }
                }
            }
        }
    }

    private void mergeCellsHorizontally() {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 3; col++) {
                if (!(((Label) grid.getChildren().get(row * 4 + col)).getText().equals(""))) {
                    Label targetCell = ((Label) grid.getChildren().get(row * 4 + col));
                    Label nextCell = ((Label) grid.getChildren().get(row * 4 + (col + 1)));
                    // if nextCell is empty move targetCell one col
                    if (nextCell.getText().equals("")) {
                        moveCell(targetCell, nextCell);
                    } else {
                        if (targetCell.getText().equals(nextCell.getText())) {
                            int sumOfTwoCells = getSumTwoEqualCells(targetCell, nextCell);
                            changeScore(sumOfTwoCells);
                            changeCellNumAndStyle(String.valueOf(sumOfTwoCells), targetCell);
                            moveCell(targetCell, nextCell);

                            // after merging, if col == 0 we skip two cols to check last possible merge
                            // else there are no more possible merges remained
                            if (col == 0) {
                                col++;
                            } else break;
                        }
                    }
                }
            }
        }
    }

    private void moveUpAllCells() {
        for (int col = 0; col < 4; col++) {
            int counter = 0;
            for (int row = 0; row < 3; row++) {
                if (((Label) grid.getChildren().get(row * 4 + col)).getText().equals("")) {
                    for (int r = row; r < 3; r++) {
                        Label emptyCell = ((Label) grid.getChildren().get(r * 4 + col));
                        Label cellToMove = ((Label) grid.getChildren().get((r + 1) * 4 + col));
                        moveCell(cellToMove, emptyCell);
                    }
                    // if there was one round of shifting we must start from the same row
                    // because there were changes in the column
                    row--;
                    counter++;
                }
                // counter prevents from infinite for loop in case of all rows are empty
                if (counter > 3) break;
            }
        }
    }

    private void moveDownAllCells() {
        for (int col = 0; col < 4; col++) {
            int counter = 0;
            for (int row = 3; row > 0; row--) {
                if (((Label) grid.getChildren().get(row * 4 + col)).getText().equals("")) {
                    for (int r = row; r > 0; r--) {
                        Label emptyCell = ((Label) grid.getChildren().get(r * 4 + col));
                        Label cellToMove = ((Label) grid.getChildren().get((r - 1) * 4 + col));
                        moveCell(cellToMove, emptyCell);
                    }
                    // if there was one round of shifting we must start from the same row
                    // because there were changes in the column
                    row++;
                    counter++;
                }
                // counter prevents from infinite for loop in case of all rows are empty
                if (counter > 3) break;
            }
        }
    }

    private void moveLeftAllCells() {
        for (int row = 0; row < 4; row++) {
            int counter = 0;
            for (int col = 0; col < 3; col++) {
                if (((Label) grid.getChildren().get(row * 4 + col)).getText().equals("")) {
                    for (int c = col; c < 3; c++) {
                        Label emptyCell = ((Label) grid.getChildren().get(row * 4 + c));
                        Label cellToMove = ((Label) grid.getChildren().get(row * 4 + (c + 1)));
                        moveCell(cellToMove, emptyCell);
                    }
                    // if there was one round of shifting we must start from the same row
                    // because there were changes in the column
                    col--;
                    counter++;
                }
                // counter prevents from infinite for loop in case of all columns in row are empty
                if (counter > 3) break;
            }
        }
    }

    private void moveRightAllCells() {
        for (int row = 0; row < 4; row++) {
            int counter = 0;
            for (int col = 3; col > 0; col--) {
                if (((Label) grid.getChildren().get(row * 4 + col)).getText().equals("")) {
                    for (int c = col; c > 0; c--) {
                        Label emptyCell = ((Label) grid.getChildren().get(row * 4 + c));
                        Label cellToMove = ((Label) grid.getChildren().get(row * 4 + (c - 1)));
                        moveCell(cellToMove, emptyCell);
                    }
                    // if there was one round of shifting we must start from the same row
                    // because there were changes in the column
                    col++;
                    counter++;
                }
                // counter prevents from infinite for loop in case of all columns in row are empty
                if (counter > 3) break;
            }
        }
    }

    private void addNewNumToCell() {
        // get number of empty cell nodes
        int numOfFreeCells = (int) grid.getChildren().stream()
                .map(node -> ((Label) node).getText())
                .filter(text -> text.equals("")).count();

        // checks if new cell position is free, if not one pos up
        if (numOfFreeCells != 0) {
            int cellPosition = randomNumber(numOfFreeCells);
            for (int i = cellPosition; i < cellPosition + 1; i++) {
                if (((Label) grid.getChildren().get(i)).getText().equals("")) {
                    System.out.println("random position free, we park on: " + cellPosition);
                } else {
                    cellPosition++;
                    System.out.println("position taken, moving one step up...");
                }
            }
            changeCellNumAndStyle("2", ((Label) grid.getChildren().get(cellPosition)));
        } else {
            System.out.println("All cells are filled => GAME OVER");
            if (Integer.parseInt(labelScore.getText()) > Integer.parseInt(labelBest.getText())) {
                labelBest.setText(labelScore.getText());
            }
        }
    }

    private int getSumTwoEqualCells(Label cellToChange, Label nextCell) {
        return Integer.parseInt(cellToChange.getText()) + Integer.parseInt(nextCell.getText());
    }

    private void changeCellNumAndStyle(String strNum, Label label) {
        clearCell(label);
        label.setText(strNum);
        switch (strNum) {
            case ("2") -> label.getStyleClass().add("activeCell2");
            case ("4") -> label.getStyleClass().add("activeCell4");
            case ("8") -> label.getStyleClass().add("activeCell8");
            case ("16") -> label.getStyleClass().add("activeCell16");
            case ("32") -> label.getStyleClass().add("activeCell32");
            case ("64") -> label.getStyleClass().add("activeCell64");
            case ("128") -> label.getStyleClass().add("activeCell128");
            case ("256") -> label.getStyleClass().add("activeCell256");
        }
    }

    private void clearCell(Label label) {
        label.setText("");
        label.getStyleClass().clear();
        label.getStyleClass().add("emptyCell");
    }

    private void moveCell(Label cellToMove, Label emptyCell) {
        changeCellNumAndStyle(cellToMove.getText(), emptyCell);
        clearCell(cellToMove);
    }

    private void changeScore(int sumOfTwoCells) {
        int lastScore = Integer.parseInt(labelScore.getText());
        labelScore.setText(String.valueOf(lastScore + sumOfTwoCells));
    }

    private int randomNumber(int freeCellsCounter) {
        Random random = new Random();
        return random.nextInt(freeCellsCounter);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        button2048.addEventHandler(KeyEvent.KEY_PRESSED,
                event -> {
                    System.out.println(event.getCode());
                    switch (event.getCode().toString()) {
                        case ("UP") -> upArrowLogic();
                        case ("DOWN") -> downArrowLogic();
                        case ("LEFT") -> leftArrowLogic();
                        case ("RIGHT") -> rightArrowLogic();
                    }
                    event.consume();
                });
    }
}



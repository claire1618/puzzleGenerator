import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import java.awt.*;
import java.util.*;

public class Nonogram extends Application {

    public void start(Stage stage1){
        Button five = new Button("5");
        Button ten = new Button("10");
        Button random = new Button("random");

        Pane home = createHome(five, ten, random);

        int ran = (int)(Math.random() * 4) + 1;
        int goWithIt = ran * 5;

        GenerateColumn fiveCol = new GenerateColumn(5);
        int[][] fiveArray = fiveCol.fill(fiveCol.getRet());
        GenerateColumn tenCol = new GenerateColumn(5);
        int[][] tenArray = tenCol.fill(tenCol.getRet());
        GenerateColumn ranCol = new GenerateColumn(goWithIt);
        int[][] ranArray = ranCol.fill(ranCol.getRet());

        Labels label5 = new Labels(fiveArray);
        int[][] side5 = label5.getSide(fiveArray);
        int[][] ret5 = label5.getTop(fiveArray);
        Labels label10 = new Labels(tenArray);
        int[][] side10 = label10.getSide(tenArray);
        int[][] ret10 = label10.getTop(tenArray);
        Labels labelRan = new Labels(ranArray);
        int[][] sideRan = labelRan.getSide(ranArray);
        int[][] retRan = labelRan.getTop(ranArray);

        Pane five5 = createGame(ret5, side5, fiveArray);
        Pane ten10 = createGame(ret10, side10, tenArray);
        Pane ranRan = createGame(retRan, sideRan, ranArray);

        Scene scene1 = new Scene(home);
        Scene scene2 = new Scene(five5);
        Scene scene3 = new Scene(ten10);
        Scene scene4 = new Scene(ranRan);


        five.setOnMouseClicked(e -> {
            stage1.setScene(scene2);
            stage1.setTitle("Five");
            stage1.show();
        });

        ten.setOnMouseClicked(e -> {
            stage1.setScene(scene3);
            stage1.setTitle("Ten");
            stage1.show();
        });

        random.setOnMouseClicked(e -> {
            stage1.setScene(scene4);
            stage1.setTitle("Random");
            stage1.show();
        });


        stage1.setScene(scene1);
        stage1.setTitle("Game");
        stage1.show();
    }

    private Pane createHome(Button five, Button ten, Button random) {
        Pane root = new Pane();
        root.setMinSize(500, 500);

        Label intro = new Label("Nonogram generator");
        Label tell = new Label("Click what size puzzle you want");

        five.relocate(200, 250);
        ten.relocate(200, 300);
        random.relocate(200, 350);
        intro.relocate(60, 150);
        tell.relocate(110, 200);

        five.setMinSize(100, 20);
        ten.setMinSize(100, 20);
        random.setMinSize(100, 20);

        five.setFont(new Font("Comic Sans MS", 20));
        ten.setFont(new Font("Comic Sans MS", 20));
        random.setFont(new Font("Comic Sans MS", 20));
        intro.setFont(new Font("Comic Sans MS", 40));
        tell.setFont(new Font("Comic Sans MS", 20));

        root.getChildren().addAll(five, ten, random, intro, tell);
        return root;
    }

    private Pane createGame(int[][] ret, int[][] side, int[][] testing) throws ConcurrentModificationException {
        Pane root = new Pane();
        root.setMinSize(500, 500);

        /*
         *
         * MAKING BORDERS
         *
         */
        Rectangle back = new Rectangle(150, 150, 300, 300);
        back.setFill(Color.rgb(255, 255, 255, 0));
        back.setStroke(Color.rgb(25, 25, 25));
        back.setStrokeWidth(5);

        Rectangle backTop = new Rectangle(150, 50, 300, 100);
        backTop.setFill(Color.rgb(255, 255, 255, 0));
        backTop.setStroke(Color.rgb(25, 25, 25));
        backTop.setStrokeWidth(5);

        Rectangle backSide = new Rectangle(50, 150, 100, 300);
        backSide.setFill(Color.rgb(255, 255, 255, 0));
        backSide.setStroke(Color.rgb(25, 25, 25));
        backSide.setStrokeWidth(5);

        /*
         *
         * FOR BUTTONS
         *
         */
        int[][] checking = new int[ret.length][ret.length];
        int[][] real = testing;

        Button mark = new Button("MARK");
        Button fill = new Button("FILL");
        Button check = new Button("CHECK");

        final Line[] one = {new Line()};
        final Line[] two = {new Line()};

        mark.relocate(20, 20);
        fill.relocate(20, 60);
        check.relocate(230, 460);

        mark.setMaxSize(100, 20);
        fill.setMaxSize(100, 20);
        check.setMaxSize(100, 20);

        root.getChildren().addAll(back, backTop, backSide, mark, fill, check);

        /*
         *
         * FOR BUTTONS
         *
         */
        double y = 150;
        double x;
        ArrayList<Rectangle> squares = new ArrayList<>();
        ArrayList<Line> lines = new ArrayList<>();
        LinkedList<Group> groups = new LinkedList<>();
        ArrayList<Rectangle> sides = new ArrayList<>();
        ArrayList<Rectangle> top = new ArrayList<>();

        /*
         *
         * MAKING SQUARES
         *
         */
        double width = (back.getWidth()) / ret.length;
        double height = (back.getWidth()) / ret.length;
        for (int j = 0; j < ret.length; j++) {
            x = 150;
            for (int k = 0; k < ret.length; k++) {
                Rectangle hold = new Rectangle(x, y, width, height);
                hold.setStroke(Color.rgb(100, 100, 100));
                hold.setFill(Color.WHITE);
                squares.add(hold);
                root.getChildren().addAll(hold);
                x += width;

            }
            y += height;
        }


        /*
         *
         * SETTING BUTTONS
         *
         */
        for (int k = 0; k < squares.size(); k++) {
            Rectangle hold = squares.get(k);
            int c1 = (int) ((hold.getX() - 150) / (back.getWidth()) * ret.length);
            int c2 = (int) ((hold.getY() - 150) / back.getHeight() * ret.length);

            squares.get(k).setOnMouseClicked(e -> {
                ListIterator<Rectangle> squareListIterator = squares.listIterator();
                ListIterator<Line> lineListIterator = lines.listIterator();

                while (squareListIterator.hasNext()) {
                    int h = squareListIterator.nextIndex();
                    Rectangle ah = squares.get(h);
                    if (!(ah.getFill().equals(Color.rgb(150, 150, 150))) &&
                            (!(root.getChildren().contains(one[0])))) {
                        ah.setFill(Color.WHITE);
                    }
                    if (ah.getFill().equals(Color.LAVENDER)) {
                        ah.setFill(Color.WHITE);
                    }
                    squareListIterator.next();
                }

                hold.setFill(Color.LAVENDER);

                fill.setOnMouseClicked(f -> {

                    checking[c2][c1] = 1;


                    hold.setFill(Color.rgb(150, 150, 150));

                    while (lineListIterator.hasNext()) {
                        int q = lineListIterator.nextIndex();
                        Line hld = lines.get(q);
                        if (Double.compare(hld.getStartX(), hold.getX()) == 0 && Double.compare(hld.getStartY(), hold.getY()) == 0) {
                            root.getChildren().removeAll(groups.get(q));
                            groups.remove(q);
                            lines.remove(q);
                        }
                        if (lineListIterator.hasNext()) {
                            lineListIterator.next();
                        }
                    }
                    reset(lineListIterator);
                });

                mark.setOnMouseClicked(f -> {

                    checking[c2][c1] = 0;


                    hold.setFill(Color.WHITE);
                    Group temp = new Group();
                    one[0] = new Line(hold.getX(), hold.getY(), hold.getX() + width, hold.getY() + height);
                    two[0] = new Line(hold.getX() + width, hold.getY(), hold.getX(), hold.getY() + height);
                    lines.add(one[0]);
                    temp.getChildren().addAll(one[0], two[0]);
                    groups.add(temp);
                    root.getChildren().addAll(temp);
                });
            });

        }

        /*
         *
         * CHECKING
         *
         */

        check.setOnMouseClicked(e -> {
            int count = 0;
            for (int k = 0; k < checking.length; k++) {
                for (int j = 0; j < checking.length; j++) {
                    if (checking[k][j] != (real[k][j])) {
                        count++;
                    }
                }
            }

            if (count == 0) {
                Label correct = new Label("YOU WON");
                Rectangle over = new Rectangle(root.getWidth(), root.getHeight());
                Rectangle newBack = new Rectangle(100, 100, root.getWidth() - 200, root.getHeight() - 200);

                correct.setFont(new Font("Comic Sans MS", 70));
                correct.relocate(75, 200);

                newBack.setFill(Color.SKYBLUE);
                over.setFill(Color.rgb(200, 200, 200, .5));


                root.getChildren().addAll(over, newBack, correct);
            } else {
                Label notQuite = new Label("NOT QUITE");
                Rectangle over = new Rectangle(root.getWidth(), root.getHeight());
                Rectangle newBack = new Rectangle(100, 100, root.getWidth() - 200, root.getHeight() - 200);

                newBack.setFill(Color.DARKRED);
                over.setFill(Color.rgb(200, 200, 200, .5));
                notQuite.setTextFill(Color.BLACK);

                notQuite.setFont(new Font("Comic Sans MS", 70));
                notQuite.relocate(50, 200);

                root.getChildren().addAll(over, newBack, notQuite);
            }
        });


        /*
         *
         * SIDE BOXES
         *
         */
        y = 150;
        x = 150;
        for (int k = 0; k < ret.length; k++) {
            Rectangle hold = new Rectangle(x, y - 100, width, 100);
            hold.setStroke(Color.rgb(100, 100, 100));
            hold.setFill(Color.WHITE);
            root.getChildren().addAll(hold);
            top.add(hold);
            x += width;
        }
        x = 50;
        for (int k = 0; k < ret.length; k++) {
            Rectangle hold = new Rectangle(x, y, 100, height);
            hold.setStroke(Color.rgb(100, 100, 100));
            hold.setFill(Color.WHITE);
            root.getChildren().addAll(hold);
            sides.add(hold);
            y += height;
        }

        /*
         *
         * MAKING LABELS
         *
         */

        for (int a = 0; a < side.length; a++) {
            double help = sides.get(0).getX();

            for (int j = 0; j < (side.length / 2) + 1; j++) {
                int on = side[j][a];
                Label hold = new Label(String.valueOf(on));

                if (on != 0) {
                    hold.relocate(help, sides.get(a).getY());
                    hold.setFont(new Font("Comic Sans MS", 20));
                    root.getChildren().addAll(hold);
                } else if (on == 0 & j == 0) {
                    hold.relocate(help, sides.get(a).getY());
                    hold.setFont(new Font("Comic Sans MS", 20));
                    root.getChildren().addAll(hold);
                }
                help += 20;
            }
        }
        for (int k = 0; k < ret.length; k++) {
            double placeHolder = top.get(0).getY();
            for (int j = 0; j < (ret.length / 2) + 1; j++) {
                int on = ret[k][j];
                Label hold = new Label(String.valueOf(on));

                if (ret[k][j] != 0) {
                    hold.relocate(top.get(k).getX(), placeHolder);
                    hold.setFont(new Font("Comic Sans MS", 20));
                    root.getChildren().addAll(hold);
                } else if (ret[k][j] == 0 & j == 0) {
                    hold.relocate(top.get(k).getX(), placeHolder);
                    hold.setFont(new Font("Comic Sans MS", 20));
                    root.getChildren().addAll(hold);
                }
                placeHolder += 20;
            }

        }

        return root;

    }




    private void reset(ListIterator<Line> lineListIterator) {
        while(lineListIterator.hasPrevious()){
            lineListIterator.previous();
        }
    }
}

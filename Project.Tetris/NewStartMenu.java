/*
@Credits to AlmasB
Original Code taken from Github.
GitHub: https://github.com/AlmasB/FXTutorials/blob/master/src/com/almasb/tutorial26/MKXMenuApp.java
*/
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;

public class NewStartMenu extends Application {
    public static final PentrisFunc p = new PentrisInterface();

    private static final Font FONT = Font.font("", FontWeight.BOLD, 18);

    private VBox menuBox;
    private int currentItem = 0;

    private int index_contrib = 0;

    private String tetris = "tetris.mp3";

    MediaPlayer mediaPlayer;

    private ScheduledExecutorService bgThread = Executors.newSingleThreadScheduledExecutor();

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(900, 600);

        Rectangle bg = new Rectangle(900, 600);

        ContentFrame titleSquare = new ContentFrame(createTitleContent(), 200);
        ContentFrame scrollSquare = new ContentFrame(createScrollContent(), 50);

        VBox hbox = new VBox(15, titleSquare, scrollSquare);
        hbox.setTranslateX(120);
        hbox.setTranslateY(50);

        MenuItem itemExit = new MenuItem("EXIT");
        itemExit.setOnActivate(() -> System.exit(0));

        menuBox = new VBox(10,
                new MenuItem("NEW GAME"),
                new MenuItem("PERFECT GAME"),
                new MenuItem("WATCH THE BOT PLAY"),
                new MenuItem("STATISTICS"),
                itemExit);
        menuBox.setAlignment(Pos.TOP_CENTER);
        menuBox.setTranslateX(350);
        menuBox.setTranslateY(350);

        Text about = new Text("Made\n\t by\n\t   Group 12");
        about.setTranslateX(50);
        about.setTranslateY(500);
        about.setFill(Color.WHITE);
        about.setFont(FONT);
        about.setOpacity(0.2);

        getMenuItem(0).setActive(true);

        root.getChildren().addAll(bg, hbox, menuBox, about);
        return root;
    }

    private Node createScrollContent() {
        final Text inbox = new Text(Pentominoes.contributors[index_contrib]);
        inbox.setFill(Color.WHITE);

        bgThread.scheduleAtFixedRate(() -> {
            Platform.runLater(() -> {
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5), inbox);
                tt.setToY(150);

                FadeTransition ft = new FadeTransition(Duration.seconds(0.3), inbox);
                ft.setToValue(0);

                ParallelTransition pt = new ParallelTransition(tt, ft);
                pt.setOnFinished(e -> {
                    if(index_contrib+1 > 6) index_contrib = 0;
                    else index_contrib++;
                    inbox.setTranslateY(-150);
                    inbox.setText(Pentominoes.contributors[index_contrib]);

                    TranslateTransition tt2 = new TranslateTransition(Duration.seconds(0.5), inbox);
                    tt2.setToY(0);

                    FadeTransition ft2 = new FadeTransition(Duration.seconds(0.3), inbox);
                    ft2.setToValue(1);

                    ParallelTransition pt2 = new ParallelTransition(tt2, ft2);
                    pt2.play();
                });
                pt.play();
            });
        }, 2, 5, TimeUnit.SECONDS);

        return inbox;
    }

    private Node createTitleContent() {
        String title = "PENTOMINO TETRIS";
        HBox letters = new HBox(0);
        letters.setAlignment(Pos.CENTER);
        for (int i = 0; i < title.length(); i++) {
            Text letter = new Text(title.charAt(i) + "");
            letter.setFont(FONT);
            letter.setFill(Color.WHITE);
            letters.getChildren().add(letter);

            TranslateTransition tt = new TranslateTransition(Duration.seconds(2), letter);
            tt.setDelay(Duration.millis(i * 50));
            tt.setToY(-25);
            tt.setAutoReverse(true);
            tt.setCycleCount(TranslateTransition.INDEFINITE);
            tt.play();
        }

        return letters;
    }

    private MenuItem getMenuItem(int index) {
        return (MenuItem)menuBox.getChildren().get(index);
    }

    private static class ContentFrame extends StackPane {
        public ContentFrame(Node content, int height) {
            setAlignment(Pos.CENTER);

            Rectangle frame = new Rectangle(700, height);
            frame.setArcWidth(25);
            frame.setArcHeight(25);
            frame.setStroke(Color.WHITESMOKE);

            getChildren().addAll(frame, content);
        }
    }

    private static class MenuItem extends HBox {
        private TriCircle c1 = new TriCircle(), c2 = new TriCircle();
        private Text text;
        private Runnable script;

        public MenuItem(String name) {
            super(15);
            setAlignment(Pos.CENTER);

            text = new Text(name);
            text.setFont(FONT);
            text.setEffect(new GaussianBlur(2));

            getChildren().addAll(c1, text, c2);
            setActive(false);
            setOnActivate(() -> choose(p, name));
        }

        public void choose(PentrisFunc p, String name){
            switch(name){
                case "NEW GAME": p.newGame(); break;
                case "PERFECT GAME": p.perfectGame(); break;
                case "WATCH THE BOT PLAY": p.letTheBotPlay(); break;
                case "STATISTICS": p.stats(); break;
            }
        }

        public void setActive(boolean b) {
            c1.setVisible(b);
            c2.setVisible(b);
            text.setFill(b ? Color.WHITE : Color.GREY);
        }

        public void setOnActivate(Runnable r) {
            script = r;
        }

        public void activate() {
            if (script != null)
                script.run();
        }
    }

    private static class TriCircle extends Parent {
        public TriCircle() {
            Shape shape1 = Shape.subtract(new Circle(5), new Circle(2));
            shape1.setFill(Color.WHITE);

            getChildren().addAll(shape1);

            setEffect(new GaussianBlur(2));
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        Media hit = new Media(new File(tetris).toURI().toString());
        mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.play();

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) {
                if (currentItem > 0) {
                    getMenuItem(currentItem).setActive(false);
                    getMenuItem(--currentItem).setActive(true);
                }
            }

            if (event.getCode() == KeyCode.DOWN) {
                if (currentItem < menuBox.getChildren().size() - 1) {
                    getMenuItem(currentItem).setActive(false);
                    getMenuItem(++currentItem).setActive(true);
                }
            }

            if (event.getCode() == KeyCode.ENTER) {
                getMenuItem(currentItem).activate();
            }
        });

        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(event -> {
            bgThread.shutdownNow();
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

interface PentrisFunc{
    void newGame();
    void perfectGame();
    void letTheBotPlay();
    void stats();
}

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import java.util.*;

public final class Knapsack extends Application {

    public static void main(String... args) {launch(args);}

    /**
     * Starts the GUI
     * Gets called by launch method
     * @param stage
     */
    @Override  public void start(final Stage stage) {

        ComboBox<String> choices = new ComboBox<>();
        choices.getItems().addAll("Maximize - Greedy", "Maximize - Combinatorics", "Fill - Shelfsort");
        choices.getSelectionModel().selectFirst();

        Button next = new Button("Continue");
        next.setPrefWidth(80); next.setPrefHeight(30);
        next.setOnAction(e -> {
            stage.close();
            if (choices.getSelectionModel()
                    .getSelectedItem().contains("Greedy")) initMax();
            else if (choices.getSelectionModel()
                    .getSelectedItem().contains("Combinatorics")) initCombi();
            else initFill();
        });

        VBox layout = new VBox(10, choices, next);
        layout.setPadding(new Insets(20, 10, 20, 10));
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 250, 100);

        stage.setScene(scene);
        release(stage, "Group 12");
    }

    /**
     * Initializes the GUI for the Greedy algorithm
     * with the user's input settings
     */
    public void initMax() {

        final Stage stage = new Stage();
        createExample();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(5); grid.setHgap(2);

        HBox pieceView = new HBox(5);
        pieceView.setAlignment(Pos.CENTER);

        TextField _a = new TextField(); _a.setPromptText("A");  grid.add(_a, 0, 0);
        TextField _b = new TextField(); _b.setPromptText("B"); grid.add(_b, 1, 0);
        TextField _c = new TextField(); _c.setPromptText("C"); grid.add(_c, 2, 0);

        TextField _f = new TextField(); _f.setPromptText("F");  grid.add(_f, 0, 1);
        TextField _i = new TextField(); _i.setPromptText("I"); grid.add(_i, 1, 1);
        TextField _l = new TextField(); _l.setPromptText("L"); grid.add(_l, 2, 1);

        TextField _n = new TextField(); _n.setPromptText("N");  grid.add(_n, 0, 2);
        TextField _p = new TextField(); _p.setPromptText("P"); grid.add(_p, 1, 2);
        TextField _t = new TextField(); _t.setPromptText("T"); grid.add(_t, 2, 2);

        TextField _u = new TextField(); _u.setPromptText("U");  grid.add(_u, 0, 3);
        TextField _v = new TextField(); _v.setPromptText("V"); grid.add(_v, 1, 3);
        TextField _w = new TextField(); _w.setPromptText("W"); grid.add(_w, 2, 3);

        TextField _x = new TextField(); _x.setPromptText("X");  grid.add(_x, 0, 4);
        TextField _y = new TextField(); _y.setPromptText("Y"); grid.add(_y, 1, 4);
        TextField _z = new TextField(); _z.setPromptText("Z"); grid.add(_z, 2, 4);

        TextField[] pieceFields = {_a, _b, _c, _f, _i, _l, _n, _p, _t, _u, _v, _w, _x, _y, _z};
        TextField width = new TextField(); width.setPromptText("Width"); grid.add(width, 0, 5);
        TextField height = new TextField(); height.setPromptText("Height"); grid.add(height, 1, 5);
        TextField depth = new TextField(); depth.setPromptText("Depth"); grid.add(depth, 2, 5);

        Button proceed = new Button("Proceed");
        proceed.setOnAction(e -> {
            try {
                int w = (int) (Double.parseDouble(width.getText()) * 2);
                int h = (int) (Double.parseDouble(height.getText()) * 2);
                int d = (int) (Double.parseDouble(depth.getText()) * 2);

                List<Parcel> pieces = new ArrayList<>();
                for (TextField tf : pieceFields)
                    if (tf.getText().length() > 0) pieces.add(
                            new Parcel(pieceShapes.get(tf.getPromptText()), Integer.parseInt(tf.getText())));
                if (pieces.isEmpty()) return;

                pieceView.getChildren().clear();
                for (Parcel pr : pieces)
                    makeCheckBoxFor(pieceView, pr.identifier);
                int[][][] solution = new int[w][h][d];

                Solver algorithm = new Solver(pieces, solution);
                display = generateContainer(algorithm.solveMax());
                report.setText("Total calculated value: " + algorithm.maxValue);

                root.getChildren().clear();
                root.getChildren().addAll(display, cameraSetting);
            } catch (Exception ne) {}
        }); proceed.setPrefWidth(80);

        VBox options = new VBox(10, grid, proceed);
        options.setAlignment(Pos.CENTER);

        root.getChildren().addAll(display, cameraSetting);
        root.setStyle("-fx-background-color: transparent;");

        SubScene _scene = new SubScene(root, 600, 350, true, SceneAntialiasing.BALANCED);
        _scene.setCamera(camera);

        VBox layout = new VBox(10, _scene, report, pieceView, options);
        layout.setPadding(new Insets(10, 10, 20, 10));
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout); makeFlex(scene);

        stage.setScene(scene);
        release(stage, "Group 12: Project 1.3 - Cargo View");
    }

    /**
     * Initializes GUI for the Recursive and Backtracking algorithm
     * with the user's input settings
     */
    public void initFill() {

        final Stage stage = new Stage();
        createExample();

        HBox pieceView = new HBox(5);
        pieceView.setAlignment(Pos.CENTER);

        HBox selection = new HBox (5,
                new CheckBox("F"), new CheckBox("I"), new CheckBox("L"),
                new CheckBox("N"), new CheckBox("P"),  new CheckBox("T"),
                new CheckBox("U"), new CheckBox("V"), new CheckBox("W"),
                new CheckBox("X"), new CheckBox("Y"), new CheckBox("Z")
        ); selection.getChildren().forEach(ch -> ((CheckBox) ch).setSelected(false));
        selection.setAlignment(Pos.CENTER);

        TextField width = new TextField(); width.setPromptText("Width");
        TextField height = new TextField(); height.setPromptText("Height");
        TextField depth = new TextField(); depth.setPromptText("Depth");
        HBox sizes = new HBox(5, width, height, depth);
        sizes.setAlignment(Pos.CENTER);

        Button proceed = new Button("Proceed");
        proceed.setOnAction(e -> {
            try {
                int _w = (int) (Double.parseDouble(width.getText()) * 2);
                int _h = (int) (Double.parseDouble(height.getText()) * 2);
                int _d = (int) (Double.parseDouble(depth.getText()) * 2);

                List<Parcel> pieces = new ArrayList<>();
                selection.getChildren().filtered(ch -> ch instanceof CheckBox).forEach(cb -> {
                    if (((CheckBox) cb).isSelected())
                        pieces.add(new Parcel(pieceShapes.get(((CheckBox) cb).getText())));
                }); if (pieces.isEmpty()) return;

                pieceView.getChildren().clear();
                for (Parcel pr : pieces)
                    makeCheckBoxFor(pieceView, pr.identifier);

                FlatSolver algorithm = new FlatSolver(pieces, new int[_w][_h][_d]);
                algorithm.launch(); int [][][] solution = algorithm.getSolution();

                if (solution == null) {
                    display = new Group();
                    report.setText("Unable to find a solution...");
                } else display = generateContainer(solution);

                root.getChildren().clear();
                root.getChildren().addAll(display, cameraSetting);
            } catch (Exception ne) {}
        }); proceed.setPrefWidth(80);

        VBox options = new VBox(10, selection, sizes, proceed);
        options.setAlignment(Pos.CENTER);

        root.getChildren().addAll(display, cameraSetting);
        root.setStyle("-fx-background-color: transparent;");

        SubScene _scene = new SubScene(root, 600, 450, true, SceneAntialiasing.BALANCED);
        _scene.setCamera(camera);

        VBox layout = new VBox(10, _scene, report, pieceView, options);
        layout.setPadding(new Insets(10, 10, 20, 10));
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout); makeFlex(scene);

        stage.setScene(scene);
        release(stage, "Group 12: Project 1.3 - Cargo View");
    }

    /**
     * Initializes the GUI for the Combinatorics algorithm
     * with the user's input settings
     */
    public void initCombi() {

        final Stage stage = new Stage();

        createExample();
        HBox pieceView = new HBox(5);
        pieceView.setAlignment(Pos.CENTER);

        TextField _a = new TextField(); _a.setPromptText("Value A");
        TextField _b = new TextField(); _b.setPromptText("Value B");
        TextField _c = new TextField(); _c.setPromptText("Value C");
        HBox values = new HBox(5, _a, _b, _c);
        values.setAlignment(Pos.CENTER);

        TextField width = new TextField(); width.setPromptText("Width");
        TextField height = new TextField(); height.setPromptText("Height");
        TextField depth = new TextField(); depth.setPromptText("Depth");
        HBox sizes = new HBox(5, width, height, depth);
        sizes.setAlignment(Pos.CENTER);

        Button proceed = new Button("Proceed");
        proceed.setOnAction(e -> {
            try {
                int _w = Integer.parseInt(width.getText())*2;
                int _h = Integer.parseInt(height.getText())*2;
                int _d = Integer.parseInt(depth.getText())*2;

                pieceView.getChildren().clear();

                int v_a = Integer.parseInt(_a.getText());
                int v_b = Integer.parseInt(_b.getText());
                int v_c = Integer.parseInt(_c.getText());

                Combinatorics combi = new Combinatorics(_w, _h, _d, v_a, v_b, v_c);
                combi.createCombinations();

                System.out.println("solution");
                List<Parcel> pieces = Combinatorics.
                        createParcels(combi.solution.quantityComb, new int[]{v_a, v_b, v_c});

                int[][][] solution = new int[_w][_h][_d];
                Solver algorithm = new Solver(pieces, solution);
                display = generateContainer(algorithm.solveMax());
                report.setText("Total calculated value: " + algorithm.maxValue);

                root.getChildren().clear();
                root.getChildren().addAll(display, cameraSetting);
            } catch (Exception ne) {return;}
        }); proceed.setPrefWidth(80);

        VBox options = new VBox(10, values, sizes, proceed);
        options.setAlignment(Pos.CENTER);

        root.getChildren().addAll(display, cameraSetting);
        root.setStyle("-fx-background-color: transparent;");

        SubScene _scene = new SubScene(root, 600, 450, true, SceneAntialiasing.BALANCED);
        _scene.setCamera(camera);

        VBox layout = new VBox(10, _scene, report, pieceView, options);
        layout.setPadding(new Insets(10, 10, 25, 10));
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout); makeFlex(scene);

        stage.setScene(scene);
        release(stage, "Group 12: Project 1.3 - Cargo View");

    }

    /**
     * Adds user-directed rotations to the scene
     * @param scene
     */
    private void makeFlex(Scene scene) {

        scene.setOnMousePressed(e -> {
            mousePosX = e.getSceneX();
            mousePosY = e.getSceneY();
        }); scene.setOnMouseDragged(e -> {
            mouseOldX = mousePosX;
            mouseOldY = mousePosY;
            mousePosX = e.getSceneX();
            mousePosY = e.getSceneY();

            if (e.isPrimaryButtonDown()) {
                cameraSetting.ry((mousePosX - mouseOldX) * 180.0 / scene.getWidth());
                cameraSetting.rx(-(mousePosY - mouseOldY) * 180.0 / scene.getHeight());
            } else if (e.isSecondaryButtonDown())
                camera.setTranslateZ(camera.getTranslateZ() + (mousePosY - mouseOldY));
        });
    }

    /**
     * Generates 3D view of the space array
     * @param space
     * @return boxes
     */
    private Group generateContainer(int[][][] space) {

        Group boxes = new Group(); boxIds.clear();
        double x = -(space.length * BOX_SIZE / 2), y = -(space[0].length * BOX_SIZE / 2),
                z = -(space[0][0].length * BOX_SIZE / 2);

        for (int i = 0; i < space.length; i++) {
            for (int j = 0; j < space[0].length; j++) {
                for (int k = 0; k < space[0][0].length; k++) {

                    Box box = new Box(BOX_SIZE, BOX_SIZE, BOX_SIZE);
                    box.setMaterial(new PhongMaterial(colors[space[i][j][k]]));
                    if (space[i][j][k] == 0) box.setDrawMode(DrawMode.LINE);

                    boxes.getChildren().add(box);
                    boxIds.put(box, space[i][j][k]);
                    box.setTranslateX(x);
                    box.setTranslateY(y);
                    box.setTranslateZ(z);

                    x+= BOX_SIZE;
                } y+= BOX_SIZE; x = -(space.length * BOX_SIZE / 2);
            } z+= BOX_SIZE; y = -(space[0].length * BOX_SIZE / 2); x = -(space.length * BOX_SIZE / 2);
        } return boxes;
    }

    /**
     * Makes all the checkboxes to hide or show
     * specific boxes
     * @param pieceView
     * @param n
     */
    private void makeCheckBoxFor(HBox pieceView, int n) {

        CheckBox piece = new CheckBox(pieceIds[n]);
        piece.setTextFill(colors[n]);
        piece.setSelected(true);

        piece.setOnAction(ev -> {
            boxIds.keySet().forEach(box -> {
                if (boxIds.get(box) == n)
                    box.setVisible(piece.isSelected());
            });
        }); pieceView.getChildren().add(piece);
    }

    /**
     * Generates the 3D view of the initial displayed cube
     */
    private void createExample() {

        int[][][] space = new int[30][30][30];
        Random trick = new Random();

        for (int i = 0; i < space.length; i++) {
            for (int j = 0; j < space[0].length; j++) {
                for (int k = 0; k < space[0][0].length; k++)
                    space[i][j][k] = trick.nextInt(14) + 1;
            }
        } display = generateContainer(space);
    }

    /**
     * Prepares the stage in order for it to be shown to the user
     * Made in order to avoid code duplication
     * @param stage
     * @param title
     */
    private void release(Stage stage, String title) {

        stage.setTitle(title);
        stage.getIcons().add(new Image("https://upload.wikimedia.org/wikipedia/commons/thumb/" +
                "2/2f/12_icon_B_%28Hungary%29.svg/2000px-12_icon_B_%28Hungary%29.svg.png"));
        stage.getScene().getRoot().setStyle("-fx-background-color: linear-gradient(white, gray);");
        stage.setResizable(false); stage.show();
    }

    Group root          = new Group();
    Group display       = new Group();

    static Label report                                 = new Label();
    final static PerspectiveCamera camera               = new PerspectiveCamera();
    final static CameraSetting cameraSetting            = new CameraSetting();

    static final int BOX_SIZE                           = 50;
    static final Map<Box, Integer> boxIds               = new HashMap<>();
    static final Map <String, int[][][]> pieceShapes    =  new HashMap<>();

    static final double CAMERA_INITIAL_DISTANCE         = -4500;
    static final double CAMERA_NEAR_CLIP                = 0.1;
    static final double CAMERA_FAR_CLIP                 = 10000.0;

    private double mousePosX, mousePosY;
    private double mouseOldX, mouseOldY;

    static Color[] colors = {
            Color.BLACK, Color.CRIMSON, Color.CYAN, Color.YELLOW, Color.GREEN, Color.BROWN,
            Color.PINK, Color.LIME, Color.BLUE, Color.ORANGE, Color.BEIGE, Color.BURLYWOOD,
            Color.GOLD, Color.PURPLE, Color.INDIGO, Color.LAVENDER, Color.TRANSPARENT
    }; static String[] pieceIds = {"", "F", "I", "L", "N", "P", "T", "U", "V", "W", "X", "Y", "Z", "A", "B", "C"};

    static {

        pieceShapes.put("F", Parcel.F); pieceShapes.put("I", Parcel.I); pieceShapes.put("L", Parcel.L);
        pieceShapes.put("N", Parcel.N); pieceShapes.put("P", Parcel.P); pieceShapes.put("T", Parcel.T);
        pieceShapes.put("U", Parcel.U); pieceShapes.put("V", Parcel.V); pieceShapes.put("W", Parcel.W);
        pieceShapes.put("X", Parcel.X); pieceShapes.put("Y", Parcel.Y); pieceShapes.put("Z", Parcel.Z);
        pieceShapes.put("A", Parcel.A); pieceShapes.put("B", Parcel.B); pieceShapes.put("C", Parcel.C);

        camera.setNearClip(CAMERA_NEAR_CLIP);
        camera.setFarClip(CAMERA_FAR_CLIP);
        camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);

        cameraSetting.getChildren().add(camera);
        report.setText("*Right-click to zoom*");
    }

}

/**
 * Class containing all the camera settings and rotation methods
 */
final class CameraSetting extends Group {

    Point3D px = new Point3D(1.0, 0.0, 0.0);
    Point3D py = new Point3D(0.0, 1.0, 0.0);
    Transform t = new Rotate();

    CameraSetting() {

        super();
        this.t = t.createConcatenation(new Rotate(-20, Rotate.X_AXIS));
        this.t = t.createConcatenation(new Rotate(-20, Rotate.Y_AXIS));
        this.getTransforms().add(t);
    }

    void rx(double angle) {
        this.t = t.createConcatenation(new Rotate(angle, px));
        this.getTransforms().clear();
        this.getTransforms().add(t);
    }

    void ry(double angle) {
        this.t = t.createConcatenation(new Rotate(angle, py));
        this.getTransforms().clear();
        this.getTransforms().add(t);
    }
}
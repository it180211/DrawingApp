
import javafx.animation.*;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.Shadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


public class MainApp extends Application {
    //deklarieren der hauptvariablen
    Pane linePane;
    Pane textPane;
    Pane circlePane;

    VBox lineconfigPane;
    VBox textconfigPane;
    VBox circleconfigPane;

    Slider lineSlider;
    ColorPicker lineColorPicker;
    CheckBox lineCheckbox;

    TextField textTextfield;
    ChoiceBox textChoiceBox;
    Slider textSlider;
    ColorPicker textColorPicker;
    CheckBox textCheckbox;

    ColorPicker circleColorPicker;
    Slider circleSlider;
    CheckBox circleCheckbox;

    @Override
    public void start(Stage primaryStage) throws Exception{
        int width = 600;
        int height = 350;

        //root
        GridPane root = new GridPane();
        root.setHgap(10);
        root.setVgap(10);
        root.setPrefSize(width, height);

        //initialisieren der hauptvariablen
        linePane = new Pane();
        textPane= new Pane();
        circlePane= new Pane();
        lineconfigPane= new VBox();
        textconfigPane= new VBox();
        circleconfigPane= new VBox();
        lineSlider = new Slider();
        lineColorPicker = new ColorPicker();
        lineCheckbox= new CheckBox();
        textTextfield = new TextField();
        textChoiceBox = new ChoiceBox();
        textSlider= new Slider();
        textColorPicker= new ColorPicker();
        textCheckbox= new CheckBox();
        circleColorPicker= new ColorPicker();
        circleSlider= new Slider();
        circleCheckbox = new CheckBox();

        //Slider einstellen
        SliderConfig(lineSlider, 0, 20);
        SliderConfig(textSlider, 2, 36);
        SliderConfig(circleSlider, 0, 100);

        //Colorpicker einstellen
        ColorPickerConfig(textColorPicker);
        ColorPickerConfig(lineColorPicker);
        ColorPickerConfig(circleColorPicker);

        //Checkboxen einstellen
        CheckboxConfig(lineCheckbox, "Transition");
        CheckboxConfig(textCheckbox, "Effekt");
        CheckboxConfig(circleCheckbox, "Timeline - Animation");

        //Choiceboxe einstellen
        String[] fontNames = new String[Font.getFontNames().size()];
        Font.getFamilies().toArray(fontNames);
        textChoiceBox.setItems(FXCollections.observableArrayList(fontNames));

        //Die Config-Elemente zu den Config-Panel hinzufügen
        lineconfigPane.getChildren().add(lineColorPicker);
        lineconfigPane.getChildren().add(lineSlider);
        lineconfigPane.getChildren().add(lineCheckbox);
        textconfigPane.getChildren().add(textColorPicker);
        textconfigPane.getChildren().add(textTextfield);
        textconfigPane.getChildren().add(textChoiceBox);
        textconfigPane.getChildren().add(textSlider);
        textconfigPane.getChildren().add(textCheckbox);
        circleconfigPane.getChildren().add(circleColorPicker);
        circleconfigPane.getChildren().add(circleSlider);
        circleconfigPane.getChildren().add(circleCheckbox);

        //Eventlistener erstellen
        lineColorPicker.valueProperty().addListener((observable, oldValue, newValue) -> drawLine());
        lineSlider.valueProperty().addListener((observable, oldValue, newValue) -> drawLine());
        lineCheckbox.selectedProperty().addListener((observable, oldValue, newValue) -> drawLine());

        textColorPicker.valueProperty().addListener((observable, oldValue, newValue) -> drawText());
        textSlider.valueProperty().addListener((observable, oldValue, newValue) -> drawText());
        textChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> drawText());
        textTextfield.textProperty().addListener((observable, oldValue, newValue) -> drawText());
        textCheckbox.selectedProperty().addListener((observable, oldValue, newValue) -> drawText());

        circleColorPicker.valueProperty().addListener((observable, oldValue, newValue) -> drawCircle());
        circleCheckbox.selectedProperty().addListener((observable, oldValue, newValue) -> drawCircle());
        circleSlider.valueProperty().addListener((observable, oldValue, newValue) -> drawCircle());

        //Die Panels zu dem Root hinzufügen
        root.add(linePane, 0, 1);
        root.add(textPane, 1, 1);
        root.add(circlePane, 2, 1);

        root.add(lineconfigPane, 0, 0);
        root.add(textconfigPane, 1, 0);
        root.add(circleconfigPane, 2, 0);

        //Scene bauen
        Scene scene = new Scene(root, width, height);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();

        //erstes Mal die Zeichen-Elemente zeichnen
        drawCircle();
        drawLine();
        drawText();
    }

    public void CheckboxConfig(CheckBox cb, String t){
        cb.setText(t);
    }
    public void SliderConfig(Slider slider,int min,int max){
        slider.setMin(min);
        slider.setMax(max);
        slider.setValue(min+1);
        slider.setShowTickLabels(true);
    }
    public void ColorPickerConfig(ColorPicker cp){
        cp.setValue(Color.BLACK);
    }

    public void drawLine(){
        final int t = (lineCheckbox.isSelected())?1000:1;
        final Line l = new Line();
        l.setStartX(0);
        l.setEndX(100);
        l.setStartY(0);
        l.setEndY(100);

        l.setStroke(lineColorPicker.getValue());
        l.setStrokeWidth(lineSlider.getValue());
        l.setFill(lineColorPicker.getValue());

        Animation animation = new Transition() {
            {
                setCycleDuration(Duration.millis(t));
            }
            @Override
            protected void interpolate(double frac) {
                emptyPane(linePane);

                l.setRotate(360 * frac);

                linePane.getChildren().add(l);
            }
        };

        animation.play();
    }
    public void drawText(){
        emptyPane(textPane);

        Text t = new Text();
        if ((textChoiceBox.getValue() == null)) {
            t.setFont(Font.font(textSlider.getValue()));
        } else {
            t.setFont(Font.font(textChoiceBox.getValue().toString(), textSlider.getValue()));
        }
        t.setText(textTextfield.getText());
        t.setFill(textColorPicker.getValue());
        t.setX(50);
        t.setY(100);

        if(textCheckbox.isSelected()){
            Shadow shadow = new Shadow();
            shadow.setBlurType(BlurType.THREE_PASS_BOX);
            shadow.setColor(textColorPicker.getValue());
            shadow.setHeight(5);
            shadow.setWidth(5);
            shadow.setRadius(5);

            t.setEffect(shadow);
        }

        textPane.getChildren().add(t);
    }
    public void drawCircle(){
        emptyPane(circlePane);

        Circle c = new Circle();
        c.setRadius(circleSlider.getValue());
        c.setFill(circleColorPicker.getValue());
        c.setCenterY(50);
        c.setCenterX(50);

        if(circleCheckbox.isSelected()){
            Timeline timeline = new Timeline();
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.setAutoReverse(true);

            KeyValue kv1 = new KeyValue(c.centerXProperty(), 100);

            KeyFrame kf = new KeyFrame(Duration.millis(500), kv1);

            timeline.getKeyFrames().add(kf);
            timeline.play();
        }

        circlePane.getChildren().add(c); 
    }

    public void emptyPane(Pane p){
        if (!p.getChildren().isEmpty()){
            p.getChildren().remove(0, p.getChildren().size());
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}

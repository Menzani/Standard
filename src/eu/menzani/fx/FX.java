package eu.menzani.fx;

import eu.menzani.struct.Percent;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class FX {
    public static final Rectangle2D SCREEN_SIZE = Screen.getPrimary().getBounds();

    public static void centerStageHorizontally(Stage stage, Percent y) {
        positionStage(stage, Percent.FIFTY, y);
    }

    public static void positionStage(Stage stage, Percent x, Percent y) {
        double widthDiff = SCREEN_SIZE.getWidth() - stage.getWidth();
        double heightDiff = SCREEN_SIZE.getHeight() - stage.getHeight();
        stage.setX(widthDiff * x.getAsFraction());
        stage.setY(heightDiff * y.getAsFraction());
    }

    public static void sizeStage(Stage stage, Percent x, Percent y) {
        stage.setWidth(SCREEN_SIZE.getWidth() * x.getAsFraction());
        stage.setHeight(SCREEN_SIZE.getHeight() * y.getAsFraction());
    }

    public static void run(Runnable runnable) {
        Platform.runLater(runnable);
    }
}

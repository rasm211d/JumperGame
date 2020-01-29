import com.almasb.fxgl.animation.Interpolators;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.awt.*;

public class Jumpindicator extends StackPane {
    private static int length = 300;
    public final int nodeX = JumperApp.hpInX;
    public final int nodeY = JumperApp.hpInY;
    PlayerComponent playerComponent = new PlayerComponent();



    private Rectangle inner;

    public Jumpindicator() {

        var outer = new javafx.scene.shape.Rectangle(nodeX, nodeY, length, 40);
        outer.setStroke(Color.BLACK);

        inner = new javafx.scene.shape.Rectangle(nodeX, nodeY, playerComponent.getDifference()/3.33,33);
        inner.setStroke(Color.RED.brighter());
        inner.setFill(Color.RED);

        getChildren().addAll(outer, inner);

    }

}

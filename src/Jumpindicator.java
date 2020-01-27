import com.almasb.fxgl.animation.Interpolators;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.awt.*;

public class Jumpindicator extends StackPane {
    private static final int RADIUS = 45;

    private IndicatorComponent playerJump;
    private Circle inner;

    public Jumpindicator(IndicatorComponent playerJump) {
        this.playerJump = playerJump;

        var outer = new Circle(RADIUS, RADIUS, RADIUS, null);
        outer.setStroke(Color.ORANGE);
        outer.setStrokeWidth(6.0);

        outer.strokeProperty().bind(
                Bindings.when(playerJump.valueProperty().divide(playerJump.getMaxJumpTime()*1.0).greaterThan(0.5)).then(Color.ORANGE).otherwise(Color.RED)
        );

        inner = new Circle(RADIUS-2, RADIUS-2, RADIUS-2, Color.YELLOW.brighter());

        inner.fillProperty().bind(
                Bindings.when(playerJump.valueProperty().divide(playerJump.getMaxJumpTime() * 1.0).greaterThan(0.25)).then(Color.YELLOW.brighter()).otherwise(Color.RED.brighter())
        );

        playerJump.valueProperty().addListener((o, old, jump) -> {
            jumpTimeChanged(jump.intValue());
        });

        getChildren().addAll(outer);
    }

    private void jumpTimeChanged(int jump) {
        var timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.seconds(0.66), new KeyValue(inner.radiusProperty(), jump * 1.0 / playerJump.getMaxJumpTime() * RADIUS, Interpolators.BOUNCE.EASE_OUT()))
        );
        timeline.play();
    }
}

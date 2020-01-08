import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.geometry.Point2D;

import javax.xml.datatype.Duration;
import java.util.Timer;

import static com.almasb.fxgl.dsl.FXGL.*;

public class PlayerComponent extends Component {
    private PhysicsComponent physics;
    private int move = 0;
    private int jumps = 1;

    public void setMove(int move) {
        this.move = move;
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(20,20));
        physics.onGroundProperty().addListener((obs, old, isOnPlatform) -> {
            if (isOnPlatform) {
                physics.setVelocityX(0);
                jumps = 1;
            }
        });
    }

    public void moveLeft() {
        move = -1;
    }
    public void moveLeftStop() {
        move = 0;
    }

    public void moveRight() {
        move = 1;
    }

    public void moveRightStop() {
        move = 0;
    }

    public void jump() {
        if (jumps == 0) {
            return;
        } else if (move == -1) {
            move = 0;
            physics.setVelocityY(-400);
            physics.setVelocityX(-200);
            jumps--;
        } else if (move == 1) {
            move = 0;
            physics.setVelocityY(-400);
            physics.setVelocityX(200);
            jumps--;
        } else  {
            move = 0;
            physics.setVelocityY(-400);
            jumps--;
        }


    }
}

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.geometry.Point2D;
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

    public void moveRight() {
        move = 1;
    }

    public void jump() {
        if (jumps == 0) {
            return;
        } else if (move == -1) {
            physics.setVelocityY(-400);
            physics.setVelocityX(-200);
            move = 0;
            jumps--;
        } else if (move == 1) {
            physics.setVelocityY(-400);
            physics.setVelocityX(200);
            move = 0;
            jumps--;
        } else  {
            physics.setVelocityY(-400);
            move = 0;
            jumps--;
        }


    }
}

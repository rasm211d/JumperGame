import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.time.LocalTimer;
import com.almasb.fxgl.time.Timer;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;

public class PlayerComponent extends Component {
    private PhysicsComponent physics;
    private int move = 0;
    private int jumps = 1;
    private long start;
    private long end;



    @Override
    public void onAdded() {
        //entity.getTransformComponent().setScaleOrigin(new Point2D(30,30));
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
    public void moveStop() {
        move = 0;
    }

    public void moveRight() {
        move = 1;
    }

    public void jumpStart() {
        setStart(start);
    }

    public void setStart(long start) {
        this.start = System.currentTimeMillis();
    }

    public long getStart() {
        return start;
    }

    public void setEnd(long end) {
        this.end = System.currentTimeMillis();
    }

    public long getEnd() {
        return end;
    }

    public void jumpEnd() {
        setEnd(end);

        if (jumps == 0) {
            return;
        } else if (move == -1) {
            move = 0;
            if ((getEnd()-getStart())/1.2 <= 1500) {
                physics.setVelocityY(-(getEnd()-getStart())/1.2);
                System.out.println((getEnd()-getStart())/1.2);
                physics.setVelocityX(-350);
            } else {
                physics.setVelocityY(-1500);
                physics.setVelocityX(-350);
            }
            jumps--;
        } else if (move == 1) {
            move = 0;
            if ((getEnd()-getStart())/1.2 <= 1500) {
                physics.setVelocityY(-(getEnd()-getStart())/1.2);
                System.out.println((getEnd()-getStart())/1.2);
                physics.setVelocityX(350);
            } else {
                physics.setVelocityY(-1500);
                physics.setVelocityX(350);
            }
            jumps--;
        } else  {
            move = 0;
            if ((getEnd()-getStart())/1.2 <= 1500) {
                physics.setVelocityY(-(getEnd()-getStart())/1.2);
                System.out.println((getEnd()-getStart())/1.2);
            } else {
                physics.setVelocityY(-1500);
            }
            physics.setVelocityY(-(getEnd()-getStart()));
            System.out.println((getEnd()-getStart()));
            jumps--;
        }

    }

    public void bounce() {
        physics.setVelocityX(-physics.getVelocityX());
    }

    public void bounceLoft() {
        physics.setVelocityX(physics.getVelocityX());
        System.out.println(physics.getVelocityX());
    }

}

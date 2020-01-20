import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.time.LocalTimer;
import com.almasb.fxgl.time.Timer;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;

public class PlayerComponent extends Component {
    private PhysicsComponent physics;
    private int move = 0;
    private int isJump = 0;
    private int jumps = 1;
    private long start;
    private long end;
    private AnimationChannel animIdle, animJumpCharge, animJump;
    private AnimatedTexture texture;

    public PlayerComponent() {
        Image image = image("JumperSprite.png");

        animIdle = new AnimationChannel(image, 3, 60, 60, Duration.seconds(1), 0, 0);
        animJumpCharge = new AnimationChannel(image, 3, 60, 60, Duration.seconds(1), 1, 1);
        animJump = new AnimationChannel(image,3,60,60, Duration.seconds(1),2,2);

        texture = new AnimatedTexture(animIdle);
        texture.loop();
    }



    @Override
    public void onAdded() {
        //entity.getTransformComponent().setScaleOrigin(new Point2D(30,30));
        entity.getViewComponent().addChild(texture);
        physics.onGroundProperty().addListener((obs, old, isOnPlatform) -> {
            if (isOnPlatform) {
                texture.loopAnimationChannel(animIdle);
                physics.setVelocityX(0);
                jumps = 1;
            }
        });
    }


    public void moveLeft() {
        getEntity().setScaleX(-1);
        move = -1;
    }
    public void moveStop() {
        move = 0;
        isJump = 0;
    }

    public void moveRight() {
        getEntity().setScaleX(1);
        move = 1;
    }

    public void jumpStart() {
        setStart(start);
        texture.loopAnimationChannel(animJumpCharge);

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

    public long getDifference() {
        return getEnd()-getStart();
    }



    public void jumpEnd() {
        setEnd(end);
        texture.loopAnimationChannel(animJump);

        if (jumps == 0) {
            return;
        } else if (move == -1) {
            move = 0;
            if (getDifference()/1.2 <= 1000) {
                physics.setVelocityY(-getDifference()/1.2);
                physics.setVelocityX(-300);
            } else {
                physics.setVelocityY(-1000);
                physics.setVelocityX(-300);
            }
            jumps--;
        } else if (move == 1) {
            move = 0;
            if (getDifference()/1.2 <= 1000) {
                physics.setVelocityY(-(getEnd()-getStart())/1.2);
                physics.setVelocityX(300);
            } else {
                physics.setVelocityY(-1000);
                physics.setVelocityX(300);
            }
            jumps--;
        } else  {
            move = 0;
            if (getDifference()/1.2 <= 1000) {
                physics.setVelocityY(-getDifference()/1.2);
            } else {
                physics.setVelocityY(-1000);
            }
            //physics.setVelocityY(-(getEnd()-getStart()));
            //System.out.println((getEnd()-getStart()));
            jumps--;
        }

    }
    /** Denne metode returnere manden med samme hastighed som han rammer en væg med, dog i modsat retning */
    public void bounce() {
        /*if (getEntity().getScaleX() == 1) {
            physics.setVelocityX(-physics.getVelocityX());
            getEntity().setScaleX(1);
        } else {
            physics.setVelocityX(-physics.getVelocityX());
            getEntity().setScaleX(-1);
        }
        if (physics.getVelocityX() > 0) {
            getEntity().setScaleX(-1);
            physics.setVelocityX(-physics.getVelocityX());
        } else if (physics.getVelocityX() < 0) {
            getEntity().setScaleX(1);
            physics.setVelocityX(-physics.getVelocityX());
        }*/
        physics.setVelocityX(-physics.getVelocityX());

    }

    public void bounceLoft() {
        physics.setVelocityX(physics.getVelocityX());
        physics.setVelocityY(-(physics.getVelocityY()/2));
        //physics.setVelocityY(-physics.getVelocityY()); Denne returnere manden med samme hastighed nedad som han rammer loftet med
        //System.out.println(physics.getVelocityX());
    }

}

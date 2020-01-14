import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.Viewport;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.time.LocalTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.security.Key;

import static com.almasb.fxgl.dsl.FXGL.*;

public class JumperApp extends GameApplication {
    private Entity player;
    private PlayerComponent playerComponent;


    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(16*70);
        gameSettings.setHeight(1000);
    }


    @Override
    protected void initGame() {
        getGameScene().setBackgroundColor(Color.LIGHTBLUE);
        getGameWorld().addEntityFactory(new JumperFactory());
        setLevelFromMap("level0.tmx");
        getPhysicsWorld().setGravity(0, 1000);

        player = getGameWorld().spawn("player", 70*8, 70*48);

        Viewport view = getGameScene().getViewport();
        view.setBounds(0, 0, 15*70, 50*70);
        view.bindToEntity(player, getAppWidth(), getAppHeight() - 200);

    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(JumperType.PLAYER, JumperType.WALL) {
            @Override
            protected void onCollisionBegin(Entity player, Entity wall) {
                player.getComponent(PlayerComponent.class).bounce();
            }
        });

        getPhysicsWorld().addCollisionHandler(new CollisionHandler(JumperType.PLAYER, JumperType.LOFT) {
            @Override
            protected void onCollisionBegin(Entity player, Entity loft) {
                player.getComponent(PlayerComponent.class).bounceLoft();
            }
        });

        getPhysicsWorld().addCollisionHandler(new CollisionHandler(JumperType.PLAYER, JumperType.DOOR) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door) {
                getDisplay().showMessageBox("Level Complete!", () -> System.out.println("Dialog Closed"));
            }
        });

    }

    @Override
    protected void initInput() {

        getInput().addAction(new UserAction("Jump")  {
            @Override
            protected void onActionBegin() {
                player.getComponent(PlayerComponent.class).jumpStart();
            }

            @Override
            protected void onActionEnd() {
                player.getComponent(PlayerComponent.class).jumpEnd();
            }
        }, KeyCode.SPACE);

        getInput().addAction(new UserAction("Right") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerComponent.class).moveRight();
            }

            @Override
            protected void onActionEnd() {
                player.getComponent(PlayerComponent.class).moveStop();
            }

        }, KeyCode.RIGHT);

        getInput().addAction(new UserAction("Left") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerComponent.class).moveLeft();
            }

            @Override
            protected void onActionEnd() {
                player.getComponent(PlayerComponent.class).moveStop();
            }
        }, KeyCode.LEFT);

    }

    public static void main(String[] args) {
        launch(args) ;
    }

}

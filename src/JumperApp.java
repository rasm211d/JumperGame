import com.almasb.fxgl.app.*;
import com.almasb.fxgl.audio.Sound;
import com.almasb.fxgl.core.collection.PropertyMap;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.entity.level.text.TextLevelLoader;
import com.almasb.fxgl.entity.level.tiled.TMXLevelLoader;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.time.LocalTimer;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.File;
import java.security.Key;

import static com.almasb.fxgl.dsl.FXGL.*;

public class JumperApp extends GameApplication {
    private static final boolean DEVELOPING_NEW_LEVEL = true;
    private static final int MAX_LEVEL = 20;
    private Entity player;
    private PlayerComponent playerComponent;


    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(1120);
        gameSettings.setHeight(1000);
        gameSettings.setTitle("Jumper");
        gameSettings.setVersion("1.0");
        gameSettings.setMenuEnabled(true);
        gameSettings.setSceneFactory(new SceneFactory() {
            @Override
            public FXGLMenu newMainMenu() {
                return new JumperMainMenu();
            }
            @Override
            public FXGLMenu newGameMenu() {
                return new JumperGameMenu();
            }

        });
        gameSettings.setDeveloperMenuEnabled(false);
    }


    @Override
    protected void initGame() {
        /*TMXLevelLoader loader = new TMXLevelLoader();
        Level level = loader.parse();
        getGameWorld().setLevel(level);*/




        getGameScene().setBackgroundColor(Color.LIGHTBLUE);
        getGameWorld().addEntityFactory(new JumperFactory());
        setLevelFromMap("level1.tmx");
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
                play("bounce.wav");
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
                PropertyMap propertyMap = door.getProperties();
                setLevel(propertyMap.getString("nextlevel"));
                //getGameWorld().spawn("player", 70*8, 70*48);

            }
        });

    }
    protected void setLevel(String level) {
        setLevelFromMap(level);
        player = getGameWorld().spawn("player", 70*8, 70*48);

        Viewport view = getGameScene().getViewport();
        view.setBounds(0, 0, 15*70, 50*70);
        view.bindToEntity(player, getAppWidth(), getAppHeight() - 200);

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
                play("jump.wav");
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


    /*private void nextLevel() {
        if (geti("level") == MAX_LEVEL) {
            getDisplay().showMessageBox("You finished the game!");
            return;
        }

        if (!DEVELOPING_NEW_LEVEL) {
            inc("level" , +1);
        }
        setLevel(geti("level"));

    }

    private boolean isRelease() {
        return getSettings().getApplicationMode() == ApplicationMode.RELEASE;
    }

    private void setLevel(int levelNum) {
        if (player != null) {
            player.getComponent(PhysicsComponent.class).overwritePosition(new Point2D(50, 50));
            player.setZ(Integer.MAX_VALUE);

        }

        set("levelTime", 0.0);

        var levelFile = new File("level0.tmx");

        Level level;

        // this supports hot reloading of levels during development
        if (!isRelease() && DEVELOPING_NEW_LEVEL && levelFile.exists()) {
            System.out.println("Loading from development level");

            try {
                level = new TMXLevelLoader().load(levelFile.toURI().toURL(), getGameWorld());
                getGameWorld().setLevel(level);

                System.out.println("Success");

            } catch (Exception e) {
                level = setLevelFromMap("tmx/level" + levelNum  + ".tmx");
            }
        } else {
            level = setLevelFromMap("tmx/level" + levelNum  + ".tmx");
        }

    }

     */

    public static void main(String[] args) {
        launch(args) ;
    }

}

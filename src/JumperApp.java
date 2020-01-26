import com.almasb.fxgl.app.*;
import com.almasb.fxgl.core.collection.PropertyMap;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;


import java.time.Duration;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;

public class JumperApp extends GameApplication {
    private Entity player;
    private Entity text;
    private PlayerComponent playerComponent;
    private double color = 0.3;
    private String level = "level1.tmx";

    public void setLevelString(String level) {
        this.level = level;
    }

    public String getLevelString() {
        return level;
    }

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
        getGameScene().setBackgroundColor(Color.rgb(0, 0, 0, color));
        getGameWorld().addEntityFactory(new JumperFactory());
        setLevelFromMap(getLevelString());
        getPhysicsWorld().setGravity(0, 1000);

        player = getGameWorld().spawn("player", 70*8, 70*48);
        //text = getGameWorld().spawn("text", 687.50, 3197.83);


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
                play("bounce.wav");
                player.getComponent(PlayerComponent.class).bounceLoft();
            }
        });

        getPhysicsWorld().addCollisionHandler(new CollisionHandler(JumperType.PLAYER, JumperType.DOOR) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door) {
                PropertyMap propertyMap = door.getProperties();
                setLevelString(propertyMap.getString("nextlevel"));
                color = color + 0.05;
                if (getLevelString().charAt(5) == '7') {
                    getDisplay().showMessageBox("Game Complete");
                } else {
                    getDisplay().showMessageBox("Level Complete!", () -> System.out.println("Dialog Closed"));
                    setLevel(propertyMap.getString("nextlevel"));

                }


            }
        });
    }

    /*@Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("time", 0.0);
    }
    @Override
    protected void initUI() {
        addVarText(50,50, "time");
    }

    @Override
    protected void onUpdate(double tpf) {
        inc("time", tpf);
    }*/

    protected void setLevel(String level) {
        //set("time", 0.0);
        player.removeFromWorld();
        getGameScene().setBackgroundColor(Color.rgb(0, 0, 0, color));
        level = getLevelString();
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

    public static void main(String[] args) {
        launch(args) ;
    }

}

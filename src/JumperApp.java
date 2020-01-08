import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import javafx.scene.input.KeyCode;

import java.security.Key;

import static com.almasb.fxgl.dsl.FXGL.*;

public class JumperApp extends GameApplication {
    private Entity player;
    private PlayerComponent playerComponent;
    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(70*16);

    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new JumperFactory());
        setLevelFromMap("level0.tmx");

        player = getGameWorld().spawn("player", 70*8, 70*48);

        getGameScene().getViewport().bindToEntity(player,getAppWidth()/2, getAppHeight()/2);
    }

    @Override
    protected void initInput() {

        getInput().addAction(new UserAction("Jump") {
            @Override
            protected void onActionBegin() {
                player.getComponent(PlayerComponent.class).jump();
            }
        }, KeyCode.SPACE);

        getInput().addAction(new UserAction("Right") {
            @Override
            protected void onActionBegin() {
                player.getComponent(PlayerComponent.class).moveRight();
            }
        }, KeyCode.RIGHT);

        getInput().addAction(new UserAction("Left") {
            @Override
            protected void onActionBegin() {
                player.getComponent(PlayerComponent.class).moveLeft();
            }
        }, KeyCode.LEFT);

    }

    public static void main(String[] args) {
        launch(args) ;
    }

}

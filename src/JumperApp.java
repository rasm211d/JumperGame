import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;

import static com.almasb.fxgl.dsl.FXGL.*;

public class JumperApp extends GameApplication {
    private Entity player;
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

    public static void main(String[] args) {
        launch(args);
    }
}

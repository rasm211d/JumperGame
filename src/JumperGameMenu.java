import com.almasb.fxgl.app.FXGLMenu;
import com.almasb.fxgl.app.MenuType;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import static com.almasb.fxgl.dsl.FXGL.*;

public class JumperGameMenu extends FXGLMenu {

    public JumperGameMenu() {
        super(MenuType.GAME_MENU);

        double centerX = getAppWidth() / 2 - 300 / 2;
        double centerY = getAppHeight()/2;

        var mainMenuButton = new JumperButton("Return to main menu", () -> this.fireExitToMainMenu());
        mainMenuButton.setTranslateX(centerX);
        mainMenuButton.setTranslateY(centerY);


        /*String salt = jumperApp.getLevelString();
        var restartLevelButton = new JumperButton("Restart level", () -> jumperApp.setLevel(salt));
        restartLevelButton.setTranslateX(centerX);
        restartLevelButton.setTranslateY(centerY - 120);*/

        var restartButton = new JumperButton("Restart level", () -> this.fireNewGame());
        restartButton.setTranslateX(centerX);
        restartButton.setTranslateY(centerY - 60);

        //var restartLevelButton = new JumperMainMenu.JumperButton("Restart Level", () -> )

        var text = getUIFactory().newText("Game Paused", Color.WHITE, 24);
        text.setUnderline(true);
        text.setTranslateX(centerX + 75);
        text.setTranslateY(centerY - 400);

        getMenuContentRoot().getChildren().addAll(mainMenuButton, restartButton, text);


    }


    @Override
    protected Button createActionButton(StringBinding stringBinding, Runnable runnable) {
        return new Button();
    }

    @Override
    protected Button createActionButton(String s, Runnable runnable) {
        return new Button();
    }

    @Override
    protected Node createBackground(double width, double height) {
        return new Rectangle(width, height, Color.DARKGRAY);
    }

    @Override
    protected Node createProfileView(String s) {
        return new Text();
    }

    @Override
    protected Node createTitleView(String s) {
        return new Text();
    }

    @Override
    protected Node createVersionView(String s) {
        return new Text();
    }
    public static class JumperButton extends StackPane {
        public JumperButton(String name, Runnable action) {
            var bg = new Rectangle(300, 40, Color.BLACK);
            bg.setStroke(Color.WHITE);

            var text = getUIFactory().newText(name, Color.WHITE, 24);
            bg.fillProperty().bind(Bindings.when(hoverProperty()).then(Color.WHITE).otherwise(Color.BLACK));
            text.fillProperty().bind(Bindings.when(hoverProperty()).then(Color.BLACK).otherwise(Color.WHITE));

            setOnMouseClicked(e -> action.run());

            getChildren().addAll(bg, text);
        }
    }

}

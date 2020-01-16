import com.almasb.fxgl.app.FXGLMenu;
import com.almasb.fxgl.app.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.RecursiveAction;

public class JumperMainMenu extends FXGLMenu {
    public JumperMainMenu() {
        super(MenuType.MAIN_MENU);

        var button = new JumperButton("Start New Game", () -> this.fireNewGame());
        button.setTranslateX(FXGL.getAppWidth()/2 - 200/2);
        button.setTranslateY(FXGL.getAppHeight()/2 - 40/2);

        getMenuContentRoot().getChildren().add(button);

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
            var bg = new Rectangle(200, 40, Color.BLACK);
            bg.setStroke(Color.WHITE);

            var text = FXGL.getUIFactory().newText(name, Color.WHITE, 24);
            bg.fillProperty().bind(Bindings.when(hoverProperty()).then(Color.WHITE).otherwise(Color.BLACK));
            text.fillProperty().bind(Bindings.when(hoverProperty()).then(Color.BLACK).otherwise(Color.WHITE));

            setOnMouseClicked(e -> action.run());

            getChildren().addAll(bg, text);
        }
    }
}

import com.almasb.fxgl.app.FXGLMenu;
import com.almasb.fxgl.app.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.RecursiveAction;

import static com.almasb.fxgl.dsl.FXGL.*;

public class JumperMainMenu extends FXGLMenu {
    JumperApp jumperApp = new JumperApp();
    public JumperMainMenu() {
        super(MenuType.MAIN_MENU);

        double centerX = getAppWidth() / 2.0 - 200 / 2.0;
        double centerY = getAppHeight()/2.0;

        var start = new JumperButton("Start Game", () -> this.fireNewGame());

        start.setTranslateX(centerX);
        start.setTranslateY(centerY - 100);

        var exit = new JumperButton("Exit", () -> this.fireExit());
        exit.setTranslateX(centerX);
        exit.setTranslateY(centerY - 40);

        getMenuContentRoot().getChildren().addAll(start, exit);

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

            var text = getUIFactory().newText(name, Color.WHITE, 24);
            bg.fillProperty().bind(Bindings.when(hoverProperty()).then(Color.WHITE).otherwise(Color.BLACK));
            text.fillProperty().bind(Bindings.when(hoverProperty()).then(Color.BLACK).otherwise(Color.WHITE));

            setOnMouseClicked(e -> action.run());

            getChildren().addAll(bg, text);
        }

    }
}

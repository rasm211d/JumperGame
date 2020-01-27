import com.almasb.fxgl.entity.components.IntegerComponent;

public class IndicatorComponent extends IntegerComponent {
    private final int maxJumpTime;

    IndicatorComponent() {
        int jumpTime = (int) new PlayerComponent().getEnd();
        maxJumpTime = jumpTime;

    }

    public int getMaxJumpTime() {
        return maxJumpTime;
    }
}

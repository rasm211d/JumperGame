import com.almasb.fxgl.entity.components.IntegerComponent;

public class IndicatorComponent extends IntegerComponent {
    private int maxJumpTime;
    private int minJumpTime;
    private PlayerComponent playerComponent = new PlayerComponent();

    IndicatorComponent() {
        this.maxJumpTime = (int) playerComponent.getDifference();
        this.minJumpTime = (int) playerComponent.getStart();

    }

    public int getMinJumpTime() {
        return minJumpTime;
    }

    public int getMaxJumpTime() {
        return maxJumpTime;
    }
}

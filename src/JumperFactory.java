import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.components.IrremovableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.geometry.Point2D;


import static com.almasb.fxgl.dsl.FXGL.*;

public class JumperFactory implements EntityFactory {
    PlayerComponent playerComponent = new PlayerComponent();

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        physics.addGroundSensor(new HitBox("GROUND_SENSOR", new Point2D(10, 10) , BoundingShape.box(40, 50)));
        //physics.setFixtureDef(new FixtureDef().friction(0.0f));

        return entityBuilder()
                .type(JumperType.PLAYER)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(60, 60)))
                .with(physics)
                .with(new IndicatorComponent())
                .with(new CollidableComponent(true))
                .with(new IrremovableComponent())
                .with(new PlayerComponent())
                .build();
    }

    @Spawns("platform")
    public Entity newPlatform(SpawnData data) {
        return entityBuilder()
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .build();

    }

    @Spawns("door")
    public Entity newDoor(SpawnData data) {
        return entityBuilder()
                .type(JumperType.DOOR)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();

    }

    @Spawns("wall")
    public Entity newWall(SpawnData data) {
        return entityBuilder()
                .type(JumperType.WALL)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .build();
    }
    @Spawns("loft")
    public Entity newLoft(SpawnData data) {
        return entityBuilder()
                .type(JumperType.LOFT)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("text")
    public Entity newText(SpawnData data) {
        return entityBuilder()
                .type(JumperType.TEXT)
                .from(data)
                .build();
    }
}

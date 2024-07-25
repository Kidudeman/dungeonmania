package dungeonmania.entities.logical;

import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.entities.enemies.Spider;
import dungeonmania.entities.enemies.snake.SnakeNode;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class SwitchDoor extends Logical {
    private boolean open = false;

    public SwitchDoor(Position position) {
        super(position.asLayer(Entity.DOOR_LAYER));
    }

    @Override
    public void onActivated(Game game) {
        this.open = this.runActivationStrategy(game);
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        if (open || entity instanceof Spider || entity instanceof SnakeNode) {
            return true;
        }

        return false;
    }

    public boolean isOpen() {
        return this.open;
    }
}

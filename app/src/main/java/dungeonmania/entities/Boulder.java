package dungeonmania.entities;

import dungeonmania.Game;
import dungeonmania.entities.enemies.Spider;
import dungeonmania.entities.enemies.snake.SnakeNode;
import dungeonmania.map.GameMap;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Boulder extends Entity implements Overlappable {
    public Boulder(Position position) {
        super(position.asLayer(Entity.CHARACTER_LAYER));
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        if (entity instanceof Spider)
            return false;
        if (entity instanceof Player && canPush(map, entity.getFacing()))
            return true;
        if (entity instanceof SnakeNode)
            return true;
        return false;
    }

    @Override
    public void onOverlap(Game game, Entity entity) {
        if (entity instanceof Player) {
            game.moveTo(this, entity.getFacing());
        }
    }

    private boolean canPush(GameMap map, Direction direction) {
        Position newPosition = Position.translateBy(this.getPosition(), direction);
        for (Entity e : map.getEntities(newPosition)) {
            if (!e.canMoveOnto(map, this))
                return false;
        }
        return true;
    }
}

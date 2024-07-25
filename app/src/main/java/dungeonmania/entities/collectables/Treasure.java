package dungeonmania.entities.collectables;

import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Overlappable;
import dungeonmania.entities.Player;
import dungeonmania.entities.enemies.snake.SnakeBody;
import dungeonmania.entities.enemies.snake.SnakeHead;
import dungeonmania.entities.enemies.snake.SnakeNode;
import dungeonmania.entities.inventory.InventoryItem;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Treasure extends Entity implements InventoryItem, Overlappable, SnakeConsumable {
    public Treasure(Position position) {
        super(position);
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    @Override
    public void onOverlap(Game game, Entity entity) {
        if (entity instanceof Player) {
            if (!((Player) entity).pickUp(this)) {
                return;
            }
            game.destroyEntity(this);
        }

        if (entity instanceof SnakeHead) {
            ((SnakeHead) entity).appendNode(game);
            applySnakeBuff((SnakeHead) entity);
            game.destroyEntity(this);
        }
    }

    @Override
    public void applySnakeBuff(SnakeHead snakeHead) {
        double newHealth = snakeHead.getBattleStatistics().getHealth() + snakeHead.getHealthTreasureBuff();

        snakeHead.getBattleStatistics().setHealth(newHealth);

        SnakeNode curr = snakeHead.getRight();

        while (curr != null) {
            ((SnakeBody) curr).getBattleStatistics().setHealth(newHealth);
            curr = curr.getRight();
        }
    }
}

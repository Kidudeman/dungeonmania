package dungeonmania.entities.collectables;

import dungeonmania.entities.EntityInterface;
import dungeonmania.entities.enemies.snake.SnakeHead;

public interface SnakeConsumable extends EntityInterface {
    public void applySnakeBuff(SnakeHead snakeHead);
}

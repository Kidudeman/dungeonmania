package dungeonmania.entities.enemies.movementStrategies;

import dungeonmania.Game;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.util.Position;

public class MercenaryAdjacentToPlayerMovementStrategy extends EnemyMovementStrategy {
    public MercenaryAdjacentToPlayerMovementStrategy(Enemy enemy) {
        super(enemy);
    }

    @Override
    public Position getNextPosition(Game game) {
        return game.getPlayersPreviousDistinctPosition();
    }

}

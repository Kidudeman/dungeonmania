package dungeonmania.entities.enemies.movementStrategies;

import dungeonmania.Game;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.util.Position;

public class DefaultMercenaryMovementStrategy extends EnemyMovementStrategy {
    public DefaultMercenaryMovementStrategy(Enemy enemy) {
        super(enemy);
    }

    @Override
    public Position getNextPosition(Game game) {
        return game.dijkstraPathFind(this.getEnemy().getPosition(), game.getPlayerPosition(), this.getEnemy());
    }
}

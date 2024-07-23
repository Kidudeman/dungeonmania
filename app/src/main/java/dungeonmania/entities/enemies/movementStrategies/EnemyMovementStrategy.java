package dungeonmania.entities.enemies.movementStrategies;

import dungeonmania.Game;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.util.Position;

public abstract class EnemyMovementStrategy {
    private Enemy enemy;

    public EnemyMovementStrategy(Enemy enemy) {
        this.enemy = enemy;
    }

    public abstract Position getNextPosition(Game game);

    public Enemy getEnemy() {
        return this.enemy;
    }
}

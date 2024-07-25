package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.entities.collectables.potions.InvincibilityPotion;
import dungeonmania.entities.enemies.movementStrategies.EnemyAvoidsPlayerMovementStrategy;
import dungeonmania.entities.enemies.movementStrategies.RandomMovementStrategy;
import dungeonmania.util.Position;

public class ZombieToast extends Enemy {
    public static final double DEFAULT_HEALTH = 5.0;
    public static final double DEFAULT_ATTACK = 6.0;

    public ZombieToast(Position position, double health, double attack) {
        super(position, health, attack);
    }

    @Override
    public Position calculateNextPosition(Game game) {
        if (game.getPlayerEffectivePotion() instanceof InvincibilityPotion) {
            return new EnemyAvoidsPlayerMovementStrategy(this).getNextPosition(game);
        }

        return new RandomMovementStrategy(this).getNextPosition(game);
    }
}

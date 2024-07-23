package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Interactable;
import dungeonmania.entities.Player;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.entities.collectables.potions.InvincibilityPotion;
import dungeonmania.entities.collectables.potions.InvisibilityPotion;
import dungeonmania.entities.enemies.movementStrategies.DefaultMercenaryMovementStrategy;
import dungeonmania.entities.enemies.movementStrategies.EnemyAvoidsPlayerMovementStrategy;
import dungeonmania.entities.enemies.movementStrategies.MercenaryAdjacentToPlayerMovementStrategy;
import dungeonmania.entities.enemies.movementStrategies.RandomMovementStrategy;
import dungeonmania.util.Position;

public class Mercenary extends Enemy implements Interactable {
    public static final int DEFAULT_BRIBE_AMOUNT = 1;
    public static final int DEFAULT_BRIBE_RADIUS = 1;
    public static final double DEFAULT_ATTACK = 5.0;
    public static final double DEFAULT_HEALTH = 10.0;

    private int bribeAmount = Mercenary.DEFAULT_BRIBE_AMOUNT;
    private int bribeRadius = Mercenary.DEFAULT_BRIBE_RADIUS;

    private double allyAttack;
    private double allyDefence;
    private boolean allied = false;
    private boolean isAdjacentToPlayer = false;

    public Mercenary(Position position, double health, double attack, int bribeAmount, int bribeRadius,
            double allyAttack, double allyDefence) {
        super(position, health, attack);
        this.bribeAmount = bribeAmount;
        this.bribeRadius = bribeRadius;
        this.allyAttack = allyAttack;
        this.allyDefence = allyDefence;
    }

    public boolean isAllied() {
        return allied;
    }

    @Override
    public void onOverlap(Game map, Entity entity) {
        if (allied)
            return;
        super.onOverlap(map, entity);
    }

    /**
     * check whether the current merc can be bribed
     * @param player
     * @return
     */
    private boolean canBeBribed(Player player) {
        return bribeRadius >= 0 && player.countEntityOfType(Treasure.class) >= bribeAmount;
    }

    /**
     * bribe the merc
     */
    private void bribe(Player player) {
        for (int i = 0; i < bribeAmount; i++) {
            player.use(Treasure.class);
        }

    }

    @Override
    public void interact(Player player, Game game) {
        allied = true;
        bribe(player);
        if (!isAdjacentToPlayer && Position.isAdjacent(player.getPosition(), getPosition()))
            isAdjacentToPlayer = true;
    }

    @Override
    public boolean isInteractable(Player player) {
        return !allied && canBeBribed(player);
    }

    @Override
    public BattleStatistics getBattleStatistics() {
        if (!allied)
            return super.getBattleStatistics();
        return new BattleStatistics(0, allyAttack, allyDefence, 1, 1);
    }

    @Override
    public Position calculateNextPosition(Game game) {
        if (allied) {
            Position nextPos = isAdjacentToPlayer
                    ? new MercenaryAdjacentToPlayerMovementStrategy(this).getNextPosition(game)
                    : new DefaultMercenaryMovementStrategy(this).getNextPosition(game);

            if (Position.isAdjacent(game.getPlayerPosition(), nextPos)) {
                isAdjacentToPlayer |= true;
            }

            return nextPos;
        } else if (game.getPlayerEffectivePotion() instanceof InvisibilityPotion) {
            return new RandomMovementStrategy(this).getNextPosition(game);
        } else if (game.getPlayerEffectivePotion() instanceof InvincibilityPotion) {
            return new EnemyAvoidsPlayerMovementStrategy(this).getNextPosition(game);
        } else {
            return new DefaultMercenaryMovementStrategy(this).getNextPosition(game);
        }
    }
}

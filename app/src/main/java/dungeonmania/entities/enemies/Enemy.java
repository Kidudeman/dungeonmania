package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.battles.Battleable;
import dungeonmania.entities.Destructible;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Overlappable;
import dungeonmania.entities.Player;
import dungeonmania.entities.enemies.snake.SnakeNode;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public abstract class Enemy extends Entity implements Battleable, Overlappable, Destructible {
    private BattleStatistics battleStatistics;

    public Enemy(Position position, double health, double attack) {
        super(position.asLayer(Entity.CHARACTER_LAYER));
        battleStatistics = new BattleStatistics(health, attack, 0, BattleStatistics.DEFAULT_DAMAGE_MAGNIFIER,
                BattleStatistics.DEFAULT_ENEMY_DAMAGE_REDUCER);
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return entity instanceof Player || entity instanceof SnakeNode;
    }

    @Override
    public BattleStatistics getBattleStatistics() {
        return battleStatistics;
    }

    @Override
    public void onOverlap(Game game, Entity entity) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            game.battle(player, this);
        }
    }

    @Override
    public void onDestroy(Game game) {
        game.unsubscribe(getId());
    }

    public int getMovementPriority() {
        return Game.AI_MOVEMENT;
    }

    public void move(Game game) {
        Position nexPosition = calculateNextPosition(game);
        game.moveTo(this, nexPosition);
    }

    public abstract Position calculateNextPosition(Game game);
}

package dungeonmania.entities.enemies;

import java.util.List;

import dungeonmania.Game;
import dungeonmania.entities.Destructible;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Interactable;
import dungeonmania.entities.Player;
import dungeonmania.entities.Wall;
import dungeonmania.entities.enemies.snake.SnakeNode;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class ZombieToastSpawner extends Entity implements Interactable, Destructible {
    public static final int DEFAULT_SPAWN_INTERVAL = 0;

    public ZombieToastSpawner(Position position, int spawnInterval) {
        super(position);
    }

    public void spawn(Game game) {
        List<Position> positions = this.getPosition().getCardinallyAdjacentPositions();
        if (positions.stream().map(p -> game.getEntities(p, Wall.class)).anyMatch(l -> l.isEmpty())) {
            game.getEntityFactory().spawnZombie(game, this);
        }
    }

    @Override
    public void onDestroy(Game game) {
        game.unsubscribe(getId());
    }

    @Override
    public void interact(Player player, Game game) {
        player.useWeapon(game);
    }

    @Override
    public boolean isInteractable(Player player) {
        return Position.isAdjacent(player.getPosition(), getPosition()) && player.hasWeapon();
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return entity instanceof SnakeNode;
    }
}

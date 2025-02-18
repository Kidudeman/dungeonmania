package dungeonmania.entities;

import dungeonmania.map.GameMap;

import java.util.Optional;

import dungeonmania.Game;
import dungeonmania.entities.collectables.Key;
import dungeonmania.entities.collectables.SunStone;
import dungeonmania.entities.enemies.Spider;
import dungeonmania.entities.inventory.Inventory;
import dungeonmania.entities.enemies.snake.SnakeNode;
import dungeonmania.util.Position;

public class Door extends Entity implements Overlappable {
    private boolean open = false;
    private int number;

    public Door(Position position, int number) {
        super(position.asLayer(Entity.DOOR_LAYER));
        this.number = number;
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        if (open || entity instanceof Spider || entity instanceof SnakeNode) {
            return true;
        }
        return (entity instanceof Player
                && (hasKey((Player) entity).isPresent() || hasSunStone(((Player) entity)) >= 1));
    }

    @Override
    public void onOverlap(Game map, Entity entity) {
        if (!(entity instanceof Player)) {
            return;
        }

        Player player = (Player) entity;
        Optional<Key> possibleKey = hasKey(player);

        if (possibleKey.isPresent()) {
            player.removeInventoryItem(possibleKey.get());
            open();
        }
        if (hasSunStone(((Player) entity)) >= 1) {
            open();
        }
    }

    private Optional<Key> hasKey(Player player) {
        return player.getKeys().stream().filter(k -> k != null && k.getnumber() == number).findFirst();
    }

    private int hasSunStone(Player player) {
        Inventory inventory = player.getInventory();
        return inventory.count(SunStone.class);
    }

    public boolean isOpen() {
        return open;
    }

    public void open() {
        open = true;
    }
}

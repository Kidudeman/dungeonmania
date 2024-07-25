package dungeonmania.entities.conductors;

import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public abstract class Conductor extends Entity {
    public Conductor(Position position) {
        super(position);
    }

    public abstract boolean isActivated(Game game);

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }
}

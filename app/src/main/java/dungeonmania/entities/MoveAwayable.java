package dungeonmania.entities;

import dungeonmania.map.GameMap;

public interface MoveAwayable extends EntityInterface {
    void onMovedAway(GameMap map, Entity entity);
}

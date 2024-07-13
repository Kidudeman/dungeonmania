package dungeonmania.entities;

import dungeonmania.map.GameMap;

public interface Overlappable extends EntityInterface {
    void onOverlap(GameMap map, Entity entity);
}

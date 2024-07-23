package dungeonmania.entities;

import dungeonmania.Game;

public interface Overlappable extends EntityInterface {
    void onOverlap(Game map, Entity entity);
}

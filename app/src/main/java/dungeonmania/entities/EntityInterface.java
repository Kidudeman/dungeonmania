
package dungeonmania.entities;

import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public interface EntityInterface {
    public boolean canMoveOnto(GameMap map, Entity entity);

    public Position getPosition();
}

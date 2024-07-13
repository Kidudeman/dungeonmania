package dungeonmania.entities;

import dungeonmania.map.GameMap;

public interface Destructible extends EntityInterface {
    void onDestroy(GameMap gameMap);
}

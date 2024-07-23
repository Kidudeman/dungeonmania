package dungeonmania.entities;

import dungeonmania.Game;

public interface Destructible extends EntityInterface {
    void onDestroy(Game gameMap);
}

package dungeonmania.entities.logical;

import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class LightBulb extends Logical {
    private boolean on = false;

    public LightBulb(Position position) {
        super(position.asLayer(Entity.DOOR_LAYER));
    }

    @Override
    public void onActivated(Game game) {
        this.on = this.runActivationStrategy(game);
    }

    public boolean isOn() {
        return this.on;
    }
}

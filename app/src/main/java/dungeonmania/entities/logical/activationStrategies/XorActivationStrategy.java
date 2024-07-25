package dungeonmania.entities.logical.activationStrategies;

import dungeonmania.Game;
import dungeonmania.entities.logical.Logical;

public class XorActivationStrategy extends ActivationStrategy {
    public XorActivationStrategy(Logical entity) {
        super(entity);
    }

    @Override
    public boolean isActivated(Game game) {
        return this.activeConductors(game).size() == 1;
    }

}

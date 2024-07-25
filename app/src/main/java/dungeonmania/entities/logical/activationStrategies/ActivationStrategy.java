package dungeonmania.entities.logical.activationStrategies;

import java.util.List;

import dungeonmania.Game;
import dungeonmania.entities.conductors.Conductor;
import dungeonmania.entities.logical.Logical;

public abstract class ActivationStrategy {
    private Logical entity;

    public ActivationStrategy(Logical entity) {
        this.entity = entity;
    }

    protected List<Conductor> activeConductors(Game game) {
        return this.entity.getPosition().getCardinallyAdjacentPositions().stream()
                .flatMap(p -> game.getEntities(p, Conductor.class).stream()).filter(c -> c.isActivated(game)).toList();
    }

    public abstract boolean isActivated(Game game);
}

package dungeonmania.entities.conductors;

import java.util.List;
import java.util.HashSet;

import dungeonmania.Game;
import dungeonmania.util.Position;

public class Wire extends Conductor {
    public Wire(Position position) {
        super(position);
    }

    public boolean isActivated(Game game, HashSet<Conductor> visited) {
        List<Position> positions = this.getPosition().getCardinallyAdjacentPositions();
        List<Conductor> conductors = positions.stream().flatMap(p -> game.getEntities(p, Conductor.class).stream())
                .filter(c -> !visited.contains(c)).toList();

        if (conductors.isEmpty()) {
            return false;
        }

        visited.addAll(conductors);

        boolean result = conductors.stream().anyMatch(c -> {
            if (c instanceof Wire) {
                return ((Wire) c).isActivated(game, visited);
            }

            return c.isActivated(game);
        });

        visited.removeAll(conductors);

        return result;
    }

    @Override
    public boolean isActivated(Game game) {
        return isActivated(game, new HashSet<Conductor>());
    }
}

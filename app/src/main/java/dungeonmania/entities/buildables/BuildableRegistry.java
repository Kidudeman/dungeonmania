package dungeonmania.entities.buildables;

import java.util.HashMap;
import java.util.Map;

import dungeonmania.entities.inventory.Inventory;
import dungeonmania.map.GameMap;

public class BuildableRegistry {
    private static Map<String, BuildableCriteria> buildRegistry = new HashMap<>();

    public static void registerBuildable(String entity, BuildableCriteria criteria) {
        buildRegistry.put(entity, criteria);
    }

    public static BuildableCriteria getBuildableCriteria(String entity) {
        return buildRegistry.get(entity);
    }

    public static boolean checkCriteria(String entity, Inventory items, GameMap map) {
        BuildableCriteria criteria = BuildableRegistry.getBuildableCriteria(entity);
        if (criteria == null) {
            return false;
        }
        return criteria.checkCriteria(items, map);
    }

    public static Map<String, BuildableCriteria> getBuildRegistry() {
        return buildRegistry;
    }

}

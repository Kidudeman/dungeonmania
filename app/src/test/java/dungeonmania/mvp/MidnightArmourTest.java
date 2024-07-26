package dungeonmania.mvp;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;

public class MidnightArmourTest {
    @Test
    @Tag("18-1")
    @DisplayName("Test armour can be built")
    public void buildMidnightArmour() throws InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_MidnightArmourTest_buildMidnightArmour", "c_midnightArmour");

        assertEquals(0, TestUtils.getInventory(res, "sword").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());

        // Pick up Sword
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sword").size());

        // Pick up SunStone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Build Midnight Armour
        assertEquals(0, TestUtils.getInventory(res, "midnight_armour").size());
        res = assertDoesNotThrow(() -> dmc.build("midnight_armour"));
        assertEquals(1, TestUtils.getInventory(res, "midnight_armour").size());

        // Materials used in construction disappear from inventory
        assertEquals(0, TestUtils.getInventory(res, "sword").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
    }

    @Test
    @Tag("18-2")
    @DisplayName("Test armour can't be built as zombies are present")
    public void buildMidnightArmourFailsZombies() throws InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_MidnightArmourTest_buildMidnightArmourFailsZombies", "c_midnightArmour");

        assertEquals(0, TestUtils.getInventory(res, "sword").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());

        // Pick up Sword
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sword").size());

        // Pick up SunStone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Build Fails
        assertEquals(0, TestUtils.getInventory(res, "midnight_armour").size());
        assertThrows(InvalidActionException.class, () -> dmc.build("midnight_armour"));
        assertEquals(0, TestUtils.getInventory(res, "midnight_armour").size());

        // Materials remain in inventory
        assertEquals(1, TestUtils.getInventory(res, "sword").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
    }

    @Test
    @Tag("18-3")
    @DisplayName("Test armour can't be built as zombies are present, can after")
    public void buildMidnightArmourAfterZombiesKilled() throws InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_MidnightArmourTest_MidnightArmourAfterZombiesKilled", "c_midnightArmour");

        assertEquals(0, TestUtils.getInventory(res, "sword").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());

        // Pick up Sword
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sword").size());

        // Pick up SunStone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Build Fails
        assertEquals(0, TestUtils.getInventory(res, "midnight_armour").size());
        assertThrows(InvalidActionException.class, () -> dmc.build("midnight_armour"));
        assertEquals(0, TestUtils.getInventory(res, "midnight_armour").size());

        // Materials remain in inventory
        assertEquals(1, TestUtils.getInventory(res, "sword").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        // Build Midnight Armour
        assertEquals(0, TestUtils.getInventory(res, "midnight_armour").size());
        res = assertDoesNotThrow(() -> dmc.build("midnight_armour"));
        assertEquals(1, TestUtils.getInventory(res, "midnight_armour").size());

        // Materials used in construction disappear from inventory
        assertEquals(0, TestUtils.getInventory(res, "sword").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());

    }

    @Test
    @Tag("18-4")
    @DisplayName("Test midnight_armour is on list of buildables that the player can currently build")
    public void onBuildables() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_MidnightArmourTest_buildMidnightArmour", "c_midnightArmour");

        List<String> buildables = new ArrayList<>();
        assertEquals(buildables, res.getBuildables());

        // Gather entities to build sceptre
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        // Midnight armour added to buildables list
        buildables.add("midnight_armour");
        assertEquals(buildables.size(), res.getBuildables().size());
        assertTrue(buildables.containsAll(res.getBuildables()));
        assertTrue(res.getBuildables().containsAll(buildables));

        // Build Midnight armour
        res = assertDoesNotThrow(() -> dmc.build("midnight_armour"));
        assertEquals(1, TestUtils.getInventory(res, "midnight_armour").size());

        // Sceptre disappears from buildables list
        buildables.remove("midnight_armour");
        assertEquals(buildables, res.getBuildables());
    }

    @Test
    @Tag("18-5")
    @DisplayName("Test midnight_armour is not on list of buildables that the player can currently build due to zombie")
    public void onBuildablesFailsZombies() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_MidnightArmourTest_buildMidnightArmourFailsZombies", "c_midnightArmour");

        List<String> buildables = new ArrayList<>();
        assertEquals(buildables, res.getBuildables());

        // Gather entities to build midnight_armour
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        assertEquals(buildables.size(), res.getBuildables().size());
        assertTrue(buildables.containsAll(res.getBuildables()));
        assertTrue(res.getBuildables().containsAll(buildables));

    }

    @Test
    @Tag("18-6")
    @DisplayName("Test midnight_armour is not on list of buildables due to zombie, can after zombie killed")
    public void onBuildablesAfterZombiesKilled() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_MidnightArmourTest_MidnightArmourAfterZombiesKilled", "c_midnightArmour");

        List<String> buildables = new ArrayList<>();
        assertEquals(buildables, res.getBuildables());

        // Gather entities to build midnight_armour
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        assertEquals(buildables.size(), res.getBuildables().size());
        assertTrue(buildables.containsAll(res.getBuildables()));
        assertTrue(res.getBuildables().containsAll(buildables));

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        buildables.add("midnight_armour");
        assertEquals(buildables.size(), res.getBuildables().size());
        assertTrue(buildables.containsAll(res.getBuildables()));
        assertTrue(res.getBuildables().containsAll(buildables));

    }

    @Test
    @Tag("18-7")
    @DisplayName("Test midnight_armour increases attack damage")
    public void testArmourIncreasesAttackDamage() {
        DungeonManiaController dmc = new DungeonManiaController();
        String config = "c_midnightArmourTest_midnightArmourBattle";
        DungeonResponse res = dmc.newGame("d_MidnightArmourTest_midnightArmourBattle", config);

        // Pick up sword and sun_stone
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        assertEquals(1, TestUtils.getInventory(res, "sword").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        assertEquals(0, TestUtils.getInventory(res, "midnight_armour").size());
        res = assertDoesNotThrow(() -> dmc.build("midnight_armour"));
        assertEquals(1, TestUtils.getInventory(res, "midnight_armour").size());

        // Battle the merc
        res = dmc.tick(Direction.RIGHT);
        List<BattleResponse> battles = res.getBattles();
        BattleResponse battle = battles.get(0);

        // This is the attack without the armour
        double playerBaseAttack = Double.parseDouble(TestUtils.getValueFromConfigFile("player_attack", config));
        double armourAttack = Double.parseDouble(TestUtils.getValueFromConfigFile("midnight_armour_attack", config));

        RoundResponse firstRound = battle.getRounds().get(0);

        assertEquals((playerBaseAttack + armourAttack) / 5, -firstRound.getDeltaEnemyHealth(), 0.001);
        assertEquals(1, TestUtils.getInventory(res, "midnight_armour").size());
    }

    @Test
    @Tag("18-8")
    @DisplayName("Test armour reduces enemy attack")

    public void testArmourReducesAttackDamage() throws InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        String config = "c_midnightArmourTest_midnightArmourBattle";
        DungeonResponse res = dmc.newGame("d_MidnightArmourTest_midnightArmourBattle", config);

        // Pick up sword and sun_stone
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        assertEquals(1, TestUtils.getInventory(res, "sword").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        assertEquals(0, TestUtils.getInventory(res, "midnight_armour").size());
        res = assertDoesNotThrow(() -> dmc.build("midnight_armour"));
        assertEquals(1, TestUtils.getInventory(res, "midnight_armour").size());

        res = dmc.tick(Direction.RIGHT);
        BattleResponse battle = res.getBattles().get(0);
        RoundResponse firstRound = battle.getRounds().get(0);

        // Assumption: armour effect calculation to reduce damage makes enemyAttack =
        // enemyAttack - armour effect
        int enemyAttack = Integer.parseInt(TestUtils.getValueFromConfigFile("mercenary_attack", config));
        int armourEffect = Integer.parseInt(TestUtils.getValueFromConfigFile("midnight_armour_defence", config));
        int expectedDamage = (enemyAttack - armourEffect) / 10;
        // Delta health is negative so take negative here
        assertEquals(expectedDamage, -firstRound.getDeltaCharacterHealth(), 0.001);
        assertEquals(1, TestUtils.getInventory(res, "midnight_armour").size());
    }

    @Test
    @Tag("18-9")
    @DisplayName("Test armour durability")
    public void testArmourDurability() throws InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        String config = "c_MidnightArmourTest_midnightArmourBattleDurability";
        DungeonResponse res = dmc.newGame("d_MidnightArmourTest_midnightArmourBattleDurability", config);

        // Pick up sword and sun_stone
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        assertEquals(1, TestUtils.getInventory(res, "sword").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        assertEquals(0, TestUtils.getInventory(res, "midnight_armour").size());
        res = assertDoesNotThrow(() -> dmc.build("midnight_armour"));
        assertEquals(1, TestUtils.getInventory(res, "midnight_armour").size());

        res = dmc.tick(Direction.RIGHT);

        // Battle many enemies (13)
        List<EntityResponse> entities = res.getEntities();
        while (TestUtils.countEntityOfType(entities, "mercenary") != 0) {
            res = dmc.tick(Direction.RIGHT);
            entities = res.getEntities();
        }

        // Check midnight armour remains in inventory throughout all battles
        assertTrue(res.getBattles().size() != 0);
        List<BattleResponse> battles = res.getBattles();
        BattleResponse firstBattle = battles.get(0);
        BattleResponse lastBattle = battles.get(battles.size() - 1);
        assertNotEquals(0, firstBattle.getBattleItems().size());
        assertNotEquals(0, lastBattle.getBattleItems().size());
        assertTrue(firstBattle.getBattleItems().get(0).getType().startsWith("midnight_armour"));
        assertTrue(lastBattle.getBattleItems().get(0).getType().startsWith("midnight_armour"));
        assertEquals(1, TestUtils.getInventory(res, "midnight_armour").size());

    }

}

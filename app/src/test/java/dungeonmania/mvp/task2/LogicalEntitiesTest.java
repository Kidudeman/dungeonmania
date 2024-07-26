package dungeonmania.mvp.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.mvp.TestUtils;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;

import static org.junit.jupiter.api.Assertions.*;

public class LogicalEntitiesTest {
        @Test
        @Tag("17-1")
        @DisplayName("Test OrActivationStrategy")
        public void orActivationStrategy() {
                DungeonManiaController dmc = new DungeonManiaController();
                DungeonResponse res;
                res = dmc.newGame("d_simple_light_bulb", "c_basicGoalsTest_exit");

                assertEquals(0, TestUtils.countType(res, "light_bulb_on"));

                res = dmc.tick(Direction.RIGHT);
                res = dmc.tick(Direction.RIGHT);
                res = dmc.tick(Direction.DOWN);
                res = dmc.tick(Direction.LEFT);

                assertEquals(1, TestUtils.countType(res, "light_bulb_on"));
        }

        @Test
        @Tag("17-2")
        @DisplayName("Test AndActivationStrategy")
        public void andActivationStrategy() {
                DungeonManiaController dmc = new DungeonManiaController();
                DungeonResponse res;
                res = dmc.newGame("d_and_light_bulb", "c_basicGoalsTest_exit");

                assertEquals(0, TestUtils.countType(res, "light_bulb_on"));

                res = dmc.tick(Direction.RIGHT);
                res = dmc.tick(Direction.RIGHT);
                res = dmc.tick(Direction.DOWN);
                res = dmc.tick(Direction.LEFT);

                assertEquals(0, TestUtils.countType(res, "light_bulb_on"));

                res = dmc.tick(Direction.DOWN);
                res = dmc.tick(Direction.DOWN);
                res = dmc.tick(Direction.DOWN);
                res = dmc.tick(Direction.DOWN);
                res = dmc.tick(Direction.DOWN);
                res = dmc.tick(Direction.LEFT);
                res = dmc.tick(Direction.LEFT);
                res = dmc.tick(Direction.LEFT);
                res = dmc.tick(Direction.LEFT);
                res = dmc.tick(Direction.UP);

                assertEquals(1, TestUtils.countType(res, "light_bulb_on"));
        }

        @Test
        @Tag("17-3")
        @DisplayName("Test XorActivationStrategy")
        public void xorActivationStrategy() {
                DungeonManiaController dmc = new DungeonManiaController();
                DungeonResponse res;
                res = dmc.newGame("d_xor_light_bulb", "c_basicGoalsTest_exit");

                assertEquals(0, TestUtils.countType(res, "light_bulb_on"));

                res = dmc.tick(Direction.RIGHT);
                res = dmc.tick(Direction.RIGHT);
                res = dmc.tick(Direction.DOWN);
                res = dmc.tick(Direction.LEFT);

                assertEquals(1, TestUtils.countType(res, "light_bulb_on"));

                res = dmc.tick(Direction.DOWN);
                res = dmc.tick(Direction.DOWN);
                res = dmc.tick(Direction.DOWN);
                res = dmc.tick(Direction.DOWN);
                res = dmc.tick(Direction.DOWN);
                res = dmc.tick(Direction.LEFT);
                res = dmc.tick(Direction.LEFT);
                res = dmc.tick(Direction.LEFT);
                res = dmc.tick(Direction.LEFT);
                res = dmc.tick(Direction.UP);

                assertEquals(0, TestUtils.countType(res, "light_bulb_on"));

                res = dmc.tick(Direction.RIGHT);
                res = dmc.tick(Direction.RIGHT);
                res = dmc.tick(Direction.RIGHT);
                res = dmc.tick(Direction.UP);
                res = dmc.tick(Direction.UP);
                res = dmc.tick(Direction.UP);
                res = dmc.tick(Direction.UP);
                res = dmc.tick(Direction.UP);

                assertEquals(1, TestUtils.countType(res, "light_bulb_on"));
        }

        @Test
        @Tag("17-4")
        @DisplayName("Test CoAndActivationStrategy")
        public void coAndActivationStrategy() {
                DungeonManiaController dmc = new DungeonManiaController();
                DungeonResponse res;
                res = dmc.newGame("d_co_and_light_bulb", "c_basicGoalsTest_exit");

                assertEquals(0, TestUtils.countType(res, "light_bulb_on"));

                res = dmc.tick(Direction.LEFT);
                res = dmc.tick(Direction.DOWN);
                res = dmc.tick(Direction.DOWN);
                res = dmc.tick(Direction.DOWN);
                res = dmc.tick(Direction.DOWN);
                res = dmc.tick(Direction.LEFT);
                res = dmc.tick(Direction.UP);

                assertEquals(1, TestUtils.countType(res, "light_bulb_on"));

                res = dmc.tick(Direction.RIGHT);
                res = dmc.tick(Direction.RIGHT);
                res = dmc.tick(Direction.RIGHT);
                res = dmc.tick(Direction.RIGHT);
                res = dmc.tick(Direction.RIGHT);
                res = dmc.tick(Direction.RIGHT);
                res = dmc.tick(Direction.RIGHT);
                res = dmc.tick(Direction.DOWN);
                res = dmc.tick(Direction.LEFT);

                // first boulder pushed

                res = dmc.tick(Direction.DOWN);
                res = dmc.tick(Direction.LEFT);
                res = dmc.tick(Direction.LEFT);
                res = dmc.tick(Direction.DOWN);
                res = dmc.tick(Direction.DOWN);
                res = dmc.tick(Direction.DOWN);
                res = dmc.tick(Direction.LEFT);
                res = dmc.tick(Direction.UP);

                // second boulder pushed
                assertEquals(1, TestUtils.countType(res, "light_bulb_on"));
        }
}

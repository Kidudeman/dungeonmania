package dungeonmania.mvp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LogicalEntitiesTest {
    @Test
    @Tag("17-1")
    @DisplayName("Test OrActivationStrategy")
    public void orActivationStrategy() {
        assertTrue(true);
    }

    @Test
    @Tag("17-2")
    @DisplayName("Test AndActivationStrategy")
    public void andActivationStrategy() {
        assertTrue(true);
    }

    @Test
    @Tag("17-3")
    @DisplayName("Test XorActivationStrategy")
    public void xorActivationStrategy() {
        assertTrue(true);
    }

    @Test
    @Tag("17-4")
    @DisplayName("Test CoAndActivationStrategy")
    public void coAndActivationStrategy() {
        assertTrue(true);
    }
}

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.*;

public class SnakePathFinderTest {

    @Test
    public void testBasicNoSnakes() {
        List<Snake> snakes = new ArrayList<>();
        Position start = new Position(0, 0);
        Position end = new Position(0, 2);

        boolean result = SnakePathFinder.pathExists(1, 3, snakes, start, end);
        assertTrue(result);
    }

    @Test
    public void testBlockedBySnake() {
        List<Position> body = List.of(
            new Position(0, 1),
            new Position(1, 1),
            new Position(2, 1)
        );
        Snake snake = new Snake(body);

        List<Snake> snakes = List.of(snake);
        Position start = new Position(0, 0);
        Position end = new Position(0, 4);

        boolean result = SnakePathFinder.pathExists(3, 5, snakes, start, end);
        assertFalse(result);
    }

    @Test
    public void testValidSlideThroughSnake() {
        List<Position> body = List.of(
            new Position(0, 1),
            new Position(1, 1),
            new Position(2, 1),
            new Position(2, 2)
        );
        Snake snake = new Snake(body);

        List<Snake> snakes = List.of(snake);
        Position start = new Position(0, 0);
        Position end = new Position(0, 4);

        // Slide-out cell from snake1 is (2, 3)
        boolean result = SnakePathFinder.pathExists(3, 5, snakes, start, end);
        assertTrue(result);
    }


    @Test
    public void testInvalidSlide_HitsSnake() {
        Snake snake1 = new Snake(List.of(
            new Position(0, 1),
            new Position(1, 1),
            new Position(2, 1),
            new Position(2, 2)
        ));

        Snake snake2 = new Snake(List.of(
            new Position(1, 3),
            new Position(2, 3)
        ));

        List<Snake> snakes = List.of(snake1, snake2);
        Position start = new Position(0, 0);
        Position end = new Position(0, 4);

        // Slide-out cell from snake1 is (2, 3) which is occupied by snake2's body
        boolean result = SnakePathFinder.pathExists(3, 5, snakes, start, end);
        assertFalse(result);
    }
}

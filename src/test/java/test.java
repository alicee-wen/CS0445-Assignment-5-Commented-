import org.junit.Test;
import static org.junit.Assert.*;

import java.util.*;

public class test {

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
        // Snake from (1,3) → (1,4) → (1,5), slides out to (1,6)
        Snake snake = new Snake(List.of(
            new Position(1, 3),
            new Position(1, 4),
            new Position(1, 5)
        ));

        List<Snake> snakes = List.of(snake);
        Position start = new Position(1, 1);
        Position end = new Position(2, 6); // Just to make sure it goes past (1,6)

        boolean result = SnakePathFinder.pathExists(3, 7, snakes, start, end);
        assertTrue(result);
    }

    @Test
    public void testInvalidSlide_OutOfBounds() {
        // Grid is only 6 columns, snake tries to slide out to (1,6)
        Snake snake = new Snake(List.of(
            new Position(1, 3),
            new Position(1, 4),
            new Position(1, 5)
        ));

        List<Snake> snakes = List.of(snake);
        Position start = new Position(1, 1);
        Position end = new Position(2, 5);  // reachable only if slide works

        boolean result = SnakePathFinder.pathExists(3, 6, snakes, start, end);
        assertTrue(result);
    }

    @Test
    public void testInvalidSlide_HitsSnakeBody() {
        Snake snake1 = new Snake(List.of(
            new Position(1, 3),
            new Position(1, 4),
            new Position(1, 5)
        ));

        Snake snake2 = new Snake(List.of(
            new Position(0, 6),
            new Position(1, 6),  // ← this body segment blocks slide-out from snake1
            new Position(2, 6)
        ));

        List<Snake> snakes = List.of(snake1, snake2);
        Position start = new Position(1, 1);
        Position end = new Position(2, 6);

        boolean result = SnakePathFinder.pathExists(3, 7, snakes, start, end);
        assertFalse(result);
    }

    @Test
    public void testSlideOutIntoAnotherSnakeHead() {
        Snake snake1 = new Snake(List.of(
            new Position(1, 3),
            new Position(1, 4),
            new Position(1, 5)
        ));

        Snake snake2 = new Snake(List.of(
            new Position(0, 6),
            new Position(1, 6),
            new Position(2, 6)
        ));

        // Slide-out from snake1 ends at (1,6) = head of snake2 → allowed!
        List<Snake> snakes = List.of(snake1, snake2);
        Position start = new Position(1, 1);
        Position end = new Position(2, 5);

        boolean result = SnakePathFinder.pathExists(3, 7, snakes, start, end);
        assertTrue(result);
    }

    @Test
    public void testSnakeWithOnlyHead() {
        Snake snake = new Snake(List.of(
            new Position(1, 2) // single-position snake
        ));

        List<Snake> snakes = List.of(snake);
        Position start = new Position(1, 1);
        Position end = new Position(1, 3); // not reachable, no slide-out

        boolean result = SnakePathFinder.pathExists(3, 5, snakes, start, end);
        assertTrue(result);
    }

    @Test
    public void testStartIsAtSnakeHead() {
        Snake snake = new Snake(List.of(
            new Position(0, 0),
            new Position(0, 1),
            new Position(0, 2)
        ));

        List<Snake> snakes = List.of(snake);
        Position start = new Position(0, 0); // head
        Position end = new Position(0, 3);   // would be reachable via slide-out

        boolean result = SnakePathFinder.pathExists(1, 5, snakes, start, end);
        assertTrue(result);
    }
}

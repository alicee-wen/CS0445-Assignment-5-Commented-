import java.util.List;

import java.util.ArrayList;

public class SnakePathFinder {

    /**
     * Determines whether a path exists from start to end in a grid with embedded
     * snakes.
     * A path cannot go through any snake body cell, but may enter a snake head,
     * in which case it slides through the snake's body and exits from the tail
     * in the same direction as the last body segment.
     *
     * @param numRows Number of rows in the grid
     * @param numCols Number of columns in the grid
     * @param snakes  List of Snake objects (each iterable over its body from head
     *                to tail)
     * @param start   The starting position
     * @param end     The ending position
     * @return true if a valid path exists, false otherwise
     */
    public static boolean pathExists(
            int numRows,
            int numCols,
            List<Snake> snakes,
            Position start,
            Position end) {
        // TODO: Implement pathfinding with backtracking and snake sliding logic

        List<Position> visited = new ArrayList<>();
        visited.add(start);


        // //right
        // Position right = new Position(start.row, start.col+1);
        // Position left = new Position(start.row, start.col-1);
        // Position up = new Position(start.row-1, start.col);
        // Position down = new Position(start.row+1, start.col);
        // if(search(right, start, end)) return true;
        // else if(search(left, start, end)) return true;
        // else if(search(up, start, end)) return true;
        // else if(search(down, start, end)) return true;

        
        

        return search(start, end, visited, numRows, numCols, snakes);
    }

    public static boolean isInsideGrid(Position p, int numRows, int numCols) {
        System.out.println("In isInsideGrid, checking position: " + p);
        return p.row >= 0 && p.row < numRows &&
               p.col >= 0 && p.col < numCols;
    }

    public static boolean isSnakeHead(Position p, List<Snake> snakes) {
        for (Snake snake : snakes) {
            Position head = snake.iterator().next(); // Get the head of the snake
            if (head.equals(p)) {
                return true;
            }
            else continue;
        }
        return false;
    }

    public static boolean isSnakeBody(Position p, List<Snake> snakes) {
        System.out.println("In isSnakeBody, checking position: " + p);
        for (Snake snake : snakes) {
            Position head = snake.iterator().next(); 
            System.out.println("Head of snake at position" + head);// Get the head of the snake
            for (Position snakeCell : snake) {
                if (!snakeCell.equals(head) && snakeCell.equals(p)) {
                    System.out.println("In isSnakeBody, found snake body at: " + p);
                    return true;
                }
            }
        }
        System.out.println("In isSnakeBody, no snake body found for " + p);
        return false;
    }

    public static Position getSlideOut(Position head, Snake s, int numRows, int numCols, List<Snake> snakes) {
        System.out.println("IN SLIDEOUT METHOD, HEAD = " + head);
        List<Position> cells = new ArrayList<>();
        for (Position pos : s) {
            cells.add(pos);
        }

        if(!cells.get(0).equals(head)) {
            return null; // Not a valid snake head
        }
        if(cells.size() < 2) {
            return new Position(head.row, head.col+1);
        }
        int total = cells.size();

        Position tail = cells.get(total - 1);
        Position secondToLast = cells.get(total - 2);

        int rowDiff = tail.row - secondToLast.row;
        int colDiff = tail.col - secondToLast.col;

        Position slideOut = new Position(tail.row + rowDiff, tail.col + colDiff);

        // out of bounds
        if(!isInsideGrid(slideOut, numRows, numCols)) {
            System.out.println("Slide out position is out of bounds: " + slideOut + ", returning null");
            return null; 
        }
    
        //head
        if(isSnakeHead(slideOut, snakes)) {
            System.out.println("Slide out position is a snake head: " + slideOut + ", searching slideout again");
            return getSlideOut(slideOut, s, numRows, numCols, snakes);
        }

        //body
        if(isSnakeBody(slideOut, snakes)){
            System.out.println("Slide out position is a snake body: " + slideOut + ", returning null");
            return null;
        }


        if(isInsideGrid(slideOut, numRows, numCols)){
            System.out.println("Slide out position is valid, in bounds: " + slideOut);
            return slideOut;
        }
        
        System.out.println("reached end of slideout method, returning null");
        return null;
    }

    public static boolean search(Position current, Position end, List<Position> visited, int numRows, int numCols,
            List<Snake> snakes) {
        boolean found = false;
         if (current.equals(end)) {
            System.out.println("Reached the end position at beginning of search method: " + end);
            return true;
         }
        

        int[] rowMoves = { 0, 0, -1, 1 }; // right, left, up, down
        int[] colMoves = { 1, -1, 0, 0 }; // right, left, up, down

        for (int i = 0; i < 4; i++) {
            found = false;
            if (current.equals(end)){
                System.out.println("Reached the end position in midst of search method: " + end);
                return true;
            }
            Position next = new Position(current.row + rowMoves[i], current.col + colMoves[i]);
            System.out.println("----------------------------------------------------------------------------------");
            System.out.println();
            printState(current, next, visited, "Try moving in search method, start/current = " + current);

            if(!visited.contains(next) && isInsideGrid(next, numRows, numCols)) {
                visited.add(next);
                if (isSnakeHead(next, snakes)) {
                    Snake snake = getSnakeAtPosition(next, snakes);
                    printState(current, next, visited, "Entered a snake head, now have to get slideOut");
                    Position slideOut = getSlideOut(next, snake, numRows, numCols, snakes);
                    System.out.println("exited slideout, back in search, slideOut = " + slideOut);
                    if (slideOut != null && !visited.contains(slideOut)) {
                        visited.add(slideOut);
                        if(search(slideOut, end, visited, numRows, numCols, snakes)) return true;
                        visited.remove(slideOut);
                    }
                }
                if (!isSnakeHead(next, snakes) && !isSnakeBody(next, snakes)) {
                    if (!found)printState(current, next, visited, "Normal move");
                        if(search(next, end, visited, numRows, numCols, snakes)) return true;
                }
                visited.remove(next);
        }
        
    }
    return false;
}


    public static Snake getSnakeAtPosition(Position p, List<Snake> snakes) {
        for (Snake snake : snakes) {
            for (Position snakeCell : snake) {
                if (snakeCell.equals(p)) {
                    return snake;
                }
            }
        }
        return null;

    }




    public static void printState(Position current, Position next, List<Position> visited, String action) {
    System.out.println("From: " + current + " | To: " + next + " | Action: " + action);
    System.out.print("Visited path: ");
    for (Position p : visited) {
        System.out.print(p + " ");
    }
    System.out.println("\n--------------------------");
}

}

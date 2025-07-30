# CS 0445 – Assignment #5 -  Pathfinding with Snake Sliding
### 🐍 Objective

Implement a recursive backtracking algorithm to determine whether a path exists between two points in a 2D grid populated with snake obstacles. A path cannot pass through a snake body, but may enter a snake head and "slide" along the body, exiting from the tail in the same direction as the snake's last segment.

> **Note:** This assignment was developed with the help of OpenAI ChatGPT to brainstorm and generate parts of the scaffolding and documentation files to speedup prototyping.


---

### 📁 Folder Structure

```
|-- pom.xml                      
|-- src/
    |-- main/
        |-- java/
            |-- SnakePathFinder.java          # Your implementation (you must complete this)
            |-- Snake.java                    # Provided class representing a snake
            |-- Position.java                 # Provided immutable coordinate class
    |-- test/
        |-- java/
            |-- SnakePathFinderTest.java       # Provided partial JUnit 4 test suite
```

---

## **⚙️ Compilation & Running Tests**

You can use GitHub Codespaces to run, compile, and test this assignment entirely in the cloud — no local setup required.

If you choose to work on your **local machine**, you must have Maven installed. If not:

### **Linux/macOS**

```
sudo apt install maven   # on Ubuntu/Debian
brew install maven       # on macOS

```

### **Windows**

1. Download from: [https://maven.apache.org/download.cgi](https://maven.apache.org/download.cgi)
2. Extract to a folder like `C:\\Program Files\\Apache\\maven`
3. Set environment variable `MAVEN_HOME` to that folder
4. Add `%MAVEN_HOME%\\bin` to your system `PATH`
5. Open a new command prompt and type `mvn -v` to verify installation

### **🔨 Compile the Project**

```
mvn compile

```

### **✅ Run Tests**

```
mvn test

```

---

## **🔍 Debugging Test Cases in VS Code with Test Runner**

To debug JUnit test cases in VS Code, follow these steps:

### **Prerequisites:**

* Install the **Java Extension Pack** in VS Code.
* You may need to install version **0.40.0** of the **Test Runner for Java** extension if debugging options do not appear.

#### **Steps:**

1. Open the test file (e.g., `SnakePathFinderTest.java`) in the editor.
2. Set breakpoints by clicking on the gutter next to the line numbers.
3. Right-click on the gutter next to the line number of the test method name and select **Debug Test**.
4. Use the debug toolbar to step through code, inspect variables, and view call stacks.

This allows you to easily verify internal state, control flow, and ensure correctness of your implementation.

---

### 🧪 Task

You must implement the following method as described in `SnakePathFinder.java`:

* `public static boolean pathExists(int numRows, int numCols, List<Snake> snakes, Position start, Position end)`

---

### 🧠 Implementation Details

* A snake is represented as an iterable sequence of Position objects from head to tail.

* Paths can move freely to empty cells.

* If a move attempts to enter a snake body (but not the head), it is invalid.

* If a move enters a snake head, the path slides along the snake body in order and exits one cell beyond the tail, in the same direction as the final segment of the snake.

* Sliding is aborted if the exit is outside the grid or inside another snake body. Sliding into another snake's head is permissible.

### Snake Slide Examples

#### Valid Slide Example

##### Grid (3x7):

```
. . . . . . .
. A . S B B .
. . . . . Z .
```
A is the starting position and Z is the ending position.

###### Snake:

```
[(1,3), (1,4), (1,5)]  // S = head at (1,3), tail at (1,5)
```

##### Sliding Behavior:

* Path moves from (1,1) to (1,2) to (1,3) → snake head.
* Slides through (1,4), (1,5).
* Last movement = RIGHT → slide-out = (1,6).
* Path continues from (1,6).

#### Invalid Slide Example: Out of Bounds

##### Grid (3x6):

```
. . . . . .
. A . S B B
. . . . . Z
```

##### Sliding Behavior:

* Slide-out = (1,6), but grid only has columns 0–5 → invalid.
* Result: move into (1,3) is rejected.

#### Invalid Slide Example: Slide-Out Hits Snake

##### Grid (3x7):

```
. . . . . . S
. A . S B B C
. . . . . Z C
```
###### Snakes:

```
[(1,3), (1,4), (1,5)]  // S = head at (1,3), tail at (1,5)
[(0,6), (1,6), (2,6)]  // S = head at (0,36), tail at (2,6)
```

* Original slide-out = (1,6), but (1,6) is now a snake body.
* Result: move into (1,3) is rejected.

#### Summary

* Slide-in is only allowed at snake **head**.
* Slide must follow exact snake sequence.
* Slide-out cell must be in bounds and **not** part of any snake's body (sliding into another snake's head is permissible).


#### We recommend creating static helper methods to:

* Implemment the backtracking logic. The method should be recursive. Check the backtracking recipes in the lecture notes. 

* Detect whether a position is empty, a snake head, or snake body.

* Follow a snake from head to tail and return the slide-out cell based on the last segment direction
---


### 🚫 Restrictions or Constraints

* ✅ **You may only modify one file**: `SnakePathFinder.java` This is the only file you are allowed to change. All other files are provided for context and testing.
* You may only use the provided Position and Snake classes.

* You may not use additional instance variables; everything must be handled within the method or local helpers.

* Only orthogonal movement is allowed; that is, no diagonal moves are allowed.

* ⚠️ Violating these restrictions may result in a **zero** for the implementation portion of the assignment.

---

### 📊 Grading Rubric and Guidelines

| Category                                    | Points  |
| ------------------------------------------- | ------- |
| Correct snake slide behavior                | 30      |
| Proper bounds and cell checks.              | 30      |
| Correct use of recursion and backtracking   | 30      |
| Code style, comments, and modularity        | 10      |
| **Total**                                   | **100** |

Additional hidden tests may be used during grading. Partial credit will be awarded for clean logic that adheres to structure even if it doesn’t pass all tests.

### **💡 Grading Guidelines**

* The assignment will be graded using Gradescope’s autograder.
* Test cases include both visible and hidden scenarios to assess correctness, edge handling, and boundary conditions.
* If your autograder score is below 60%, your code will be manually reviewed for partial credit.

  * However, **manual grading can award no more than 60% of the total autograder points**.
* Code style, comments, and modularity is graded manually and includes:

  * Clear and meaningful variable/method names
  * Proper indentation and formatting
  * Use of helper methods to reduce duplication
  * Inline comments explaining non-obvious logic
  * Adherence to Java naming conventions
---

Good luck, and don’t forget to test early and often! 🎯
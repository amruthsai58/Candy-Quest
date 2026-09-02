# 🍬 Candy Quest — Gamified Java DSA Learning Platform
> *"Fruity Blast in 4 Exciting Data Structures"*

Candy Quest is a rich desktop GUI application built in **Java 21** with **JavaFX**, **Maven**, and **SQLite**. It gamifies the study of **150 Data Structures & Algorithms (DSA)** topics using a candy-pack motif ("CandyMan Fruitee Fun Soft Chews"), complete with flavor-themed learning tracks, particle animations, interactive algorithm visualizers, an Australian kangaroo guide mascot ("Candy Roo"), and educational design pattern implementations.

---

## 🍓 1. The Candy Metaphor & 4 Flavor Worlds

| Flavor Track | DSA Domain | Color & Emoji | Description |
|---|---|---|---|
| **Strawberry Track** | Foundations | 🍓 Red (`#E63946`) | Big-O notation, Arrays, Strings, Recursion, Bit Manipulation, Math, Sorting & Searching (36 topics) |
| **Orange Track** | Linear Structures | 🍊 Orange (`#FB8500`) | Linked Lists, Stacks, Queues, Hashing, Deques, DSU, Monotonic structures (36 topics) |
| **Grape Track** | Trees & Graphs | 🍇 Purple (`#7209B7`) | Binary Trees, BSTs, AVL, Tries, Heaps, BFS/DFS, Dijkstra, MST, Network Flow (36 topics) |
| **Watermelon Track** | Advanced Algorithms & Design | 🍉 Green/Pink (`#2EC4B6`) | Dynamic Programming, Greedy, Backtracking, System Design, Concurrency, Bitmask DP (42 topics) |

- **🎁 Free Toy Inside**: Unlocks every 10 completed topics (10, 20, 30 ... 150) granting mascot skins, golden trophies, accessories, and celebratory easter eggs!
- **🦘 Candy Roo Mascot**: An animated Australian kangaroo guide who hops along topic paths, cheers on correct quiz answers, offers gentle hints, and claps with candy sparkles!
- **🍬 Candy Drops & Jars**: Earn candy drop XP particles on answering quiz challenges correctly, filling 3D-styled glass candy jars for each flavor world.

---

## 🏛️ 2. Architectural Design Patterns (System Design Reference)

Every design pattern in Candy Quest is implemented with educational Javadocs:

| Pattern | Class / Package | Purpose in Candy Quest |
|---|---|---|
| **Singleton** | `com.candyquest.pattern.singleton.AppSessionManager` | Manages single user session, active track, audio toggle, and progression dispatcher. |
| **Factory Method** | `com.candyquest.pattern.factory.TopicFactory` | Subclasses (`FoundationsTopicFactory`, `TreeGraphTopicFactory`, etc.) construct track-specific topics with tailored visualizers. |
| **Observer** | `com.candyquest.pattern.observer.ProgressObserver` | UI headers, candy jars, and mascot listen to `ProgressSubject` for real-time XP and milestone updates. |
| **Strategy** | `com.candyquest.pattern.strategy.QuizGradingStrategy` | Interchangeable evaluation strategies for MCQs, Code Output Predictions, and Asymptotic Complexity questions. |
| **State** | `com.candyquest.pattern.state.TopicState` | Enforces lifecycle transitions: `LOCKED` → `IN_PROGRESS` → `COMPLETED` → `MASTERED`. |
| **Decorator** | `com.candyquest.pattern.decorator.BadgeDecorator` | Stacks visual layers (Sparkles, Golden Halo Glow, Toy Ribbons) onto base achievement badges. |
| **Command** | `com.candyquest.pattern.command.UserActionCommand` | Encapsulates study actions (Bookmarks, Quiz Answers, Hint Requests) with full Undo/Redo & history logs. |
| **Builder** | `com.candyquest.pattern.builder.QuizBuilder` | Fluent API to configure and construct randomized or track-specific challenge quizzes. |

---

## 🚀 3. How to Run

### Prerequisites
- **Java 21+** (JDK 21 LTS installed)
- **Maven** (A Windows wrapper `mvnw.cmd` is included in the project root)

### Launching the Application
Run from the root directory:
```bash
# Using standard Maven
mvn clean compile javafx:run

# Using the included Windows wrapper
.\mvnw.cmd clean compile javafx:run
```

### Running Unit Tests
```bash
mvn test
# or
.\mvnw.cmd test
```

---

## 🎨 4. Key UI & Visual Features

1. **Candy Pack Burst Intro**: Interactive torn-pack wrapper animation bursting into 45 flying candy particles.
2. **Board Game Candy Trail**: Interactive node progression map connected by track lines with pulsing active nodes.
3. **Interactive Algorithm Visualizers**:
   - *Array & Sorting Visualizer*: Animated bar columns with step-by-step bubble sort & pointer comparisons.
   - *Linked List Visualizer*: Dynamic node boxes `[Data | Next]` with head insertions and list reversal.
   - *Binary Tree Visualizer*: Interactive BFS level-order traversal tree.
   - *Recursion Visualizer*: Call-stack frame branching tree for divide-and-conquer.
4. **Interactive Quiz Mode**: Multi-format questions (MCQ, Code Trace, Complexity) with soft-chew button squish micro-interactions, audio effects, and particle drops.
5. **Confectionary Progress Dashboard**: 4 animated glass candy jars filling with fruit candies, decorated badge shelf, and command history log.
6. **DSA Code Playground**: Java 21 editor with starter templates and simulated test case runner.
7. **Search & Bookmarks**: Fast filter across all 150 DSA topics with tag and difficulty filters.

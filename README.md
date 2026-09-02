<p align="center">
  <img src="web/assets/logo.png" alt="Candy Quest Logo" width="220" style="border-radius: 50%; box-shadow: 0 0 30px rgba(255, 209, 102, 0.6);" />
</p>

# 🍬 Candy Quest — Solve • Learn • Conquer
> *"Gamified DSA Learning Platform with Real-World Production Systems & The Candy Packet Metaphor"*

[![GitHub Pages](https://img.shields.io/badge/Live%20Demo-GitHub%20Pages-brightgreen?style=for-the-badge&logo=github)](https://amruthsai58.github.io/Candy-Quest/)
[![Java](https://img.shields.io/badge/Java-21%20LTS-orange?style=for-the-badge&logo=openjdk)](https://openjdk.org/)
[![JavaFX](https://img.shields.io/badge/JavaFX-21-purple?style=for-the-badge)](https://openjfx.io/)
[![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)](LICENSE)

---

## 🌐 Live Web Application
👉 **[https://amruthsai58.github.io/Candy-Quest/](https://amruthsai58.github.io/Candy-Quest/)**

---

## 🍓 1. The Candy Packet Metaphor & 4 Flavor Worlds

Candy Quest maps the physical mechanics of opening a confectionery pack ("CandyMan Fruitee Fun Soft Chews") directly to **high-scale real-world production architectures**:

| Flavor Track | DSA Domain | Color Theme | 🏭 Real-World Production System Deployment | 🍫 Candy Packet Metaphor |
|---|---|---|---|---|
| **🍓 Strawberry** | Foundations & Complexity *(36 Topics)* | Bubblegum Pink (`#FF007F`) | High-Frequency Stock Trading Books, Audio Sample Streaming Buffers, Image Pixels | **Solid Segmented Chocolate Slab**: Snapping piece `#i` is instantaneous $O(1)$ direct offset memory. |
| **🍊 Mango** | Linear Structures *(36 Topics)* | Citrus Mango (`#FF9E00`) | Kafka Event Streams, Redis LRU Cache Eviction, Spotify Playlist Traversal | **Wrapped Candy Ribbon**: Individually wrapped chews connected by string twists with $O(1)$ pointer splicing. |
| **🍇 Grape** | Trees & Graphs *(36 Topics)* | Grape Purple (`#8338EC`) | Google Search Autocomplete (Tries), GPS Route Finding (Dijkstra/A*), Social Graphs | **Branching Lollipop Tree**: Sweet buds on right branches and sour on left; predictive tree routes in $O(Length)$. |
| **🍉 Lime** | Advanced & DP *(42 Topics)* | Lime Green (`#38B000`) | Distributed Caches (Memcached), Flight Ticket Pricing Engines, DNA Alignment | **Assorted Recipe Box**: Caching subproblem flavors in a grid to never re-cook identical batches ($O(2^N) \to O(N)$). |

---

## ✨ 2. Key Features

- **👤 Name Login & Profile Selector**: Enter your Explorer Name and select your fruit avatar (🍓 🍊 🍇 🍉) for personalized quest greetings and streak tracking.
- **🛡️ Admin Topic Management Console**: Full administrative power to create new custom DSA topics (with custom tags, complexities, visualizers, real-world notes, and code snippets) or delete existing topics with real-time persistence.
- **🎯 4 Interactive Algorithm Visualizers**:
  - 🍫 **Array / Sorting Bar Visualizer**: Interactive bubble sort step execution.
  - 🍬 **Linked List Node Pointer Ribbon**: Head/tail insertions and pointer traversal.
  - 🍭 **Binary Search Tree Visualizer**: BFS level-order lighting and BST properties.
  - 🎁 **Recursion & DP Call Stack**: Visualizing memoized subproblem call trees.
- **🦘 "Candy Roo" Mascot Guide**: An animated kangaroo companion reacting to study progress, offering hints, and cheering on quiz clears.
- **🎁 Free Toy Inside Rewards**: Unlocks every 10 completed topics (Pixel Roo, Lollipop Wand, Ninja Skin, Royal Crown).
- **💻 Java 21 Code Playground**: In-browser code editor with instant test assertion compilation and feedback.

---

## 🏛️ 3. 8 Software Design Patterns Implemented

| Pattern | Class / Package | Purpose in Candy Quest |
|---|---|---|
| **1. Singleton** | `com.candyquest.pattern.singleton.AppSessionManager` | Thread-safe single user session, active track, and progression dispatcher. |
| **2. Factory Method** | `com.candyquest.pattern.factory.TopicFactory` | Concrete factories (`FoundationsTopicFactory`, `TreeGraphTopicFactory`, etc.) construct track-specific topics with tailored visualizers. |
| **3. Observer** | `com.candyquest.pattern.observer.ProgressObserver` | UI headers, candy jars, and mascot listen to `ProgressSubject` for real-time XP gains. |
| **4. Strategy** | `com.candyquest.pattern.strategy.QuizGradingStrategy` | Interchangeable evaluation strategies for MCQs, Code Output Predictions, and Big-O Complexity questions. |
| **5. State** | `com.candyquest.pattern.state.TopicState` | Enforces lifecycle transitions: `LOCKED` → `IN_PROGRESS` → `COMPLETED` → `MASTERED`. |
| **6. Decorator** | `com.candyquest.pattern.decorator.BadgeDecorator` | Stacks visual layers (Sparkles, Golden Glow, Toy Ribbons) onto achievement badges. |
| **7. Command** | `com.candyquest.pattern.command.UserActionCommand` | Encapsulates study actions (Bookmarks, Quiz Answers, Hint Requests) with full Undo/Redo history. |
| **8. Builder** | `com.candyquest.pattern.builder.QuizBuilder` | Fluent API to configure and construct randomized or track-specific challenge quizzes. |

---

## 🚀 4. How to Run Locally

### Option A: Run the Web App Locally (Node.js)
```bash
# Start local web server on port 8080
node server.js
# Open http://localhost:8080 in your browser
```

### Option B: Run the JavaFX Desktop GUI (Java 21)
```bash
# Using the Windows Maven wrapper
.\mvnw.cmd compile exec:java

# Or standard Maven
mvn compile exec:java
```

### Option C: Run Automated Tests
```bash
.\mvnw.cmd test
```

---

## 📁 5. Repository Structure

```
Candy-Quest/
├── web/                                 # Complete Web Application (GitHub Pages)
│   ├── index.html                       # Main HTML5 Single Page Application
│   ├── style.css                        # Modern Design System (Cosmic Purple + Candy Accents)
│   ├── app.js                           # App Engine, Login, Visualizers & Admin logic
│   ├── topics.js                        # Complete 150 Topics Dataset with Real-World Deployments
│   └── assets/
│       └── logo.png                     # Official Candy Quest Logo
├── src/                                 # Java 21 LTS Source Code (Desktop JavaFX App)
│   ├── main/java/com/candyquest/
│   │   ├── Launcher.java                # Bootstrap Entry Point
│   │   ├── MainApp.java                 # JavaFX Application Shell
│   │   ├── pattern/                     # 8 Software Design Patterns
│   │   ├── repository/                  # SQLite & Topic Data Access
│   │   ├── service/                     # Business Logic Services
│   │   ├── controller/                  # View Controllers
│   │   └── view/                        # Visualizers & Animated Sprite Components
│   └── main/resources/com/candyquest/   # FXML Layouts, Stylesheets & Images
├── .github/workflows/deploy.yml         # GitHub Actions Pages Deployment
├── server.js                            # Local HTTP Web Server
├── pom.xml                              # Maven Build & Dependencies
└── README.md                            # Documentation
```

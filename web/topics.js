// 🍬 Candy Quest - 150 Topics Dataset with Real-World Production Usage & Candy Packet Metaphors

const TRACKS = {
  FOUNDATIONS: {
    id: "FOUNDATIONS",
    name: "Strawberry Foundations",
    domain: "Foundations & Complexity",
    emoji: "🍓",
    primaryColor: "#FF007F",
    accentColor: "#D0006F",
    gradient: "linear-gradient(135deg, #FF007F, #FF5D8F)",
    glowColor: "rgba(255, 0, 127, 0.4)",
    realWorldRole: "Core Runtime Engines, String Indexers & Low-Level Memory Buffers",
    description: "Master foundational building blocks: Arrays, Strings, Recursion, Bit Manipulation, Math, and Asymptotic Complexity."
  },
  LINEAR: {
    id: "LINEAR",
    name: "Mango Linear Structures",
    domain: "Linear Data Structures",
    emoji: "🍊",
    primaryColor: "#FF9E00",
    accentColor: "#D97706",
    gradient: "linear-gradient(135deg, #FF9E00, #FFD166)",
    glowColor: "rgba(255, 158, 0, 0.4)",
    realWorldRole: "Message Brokers (Kafka/RabbitMQ), LRU Caching, Browser History & Task Schedulers",
    description: "Traverse linked lists, stacks, queues, hash maps, heaps, and monotonic patterns with tangy efficiency."
  },
  TREES_GRAPHS: {
    id: "TREES_GRAPHS",
    name: "Grape Trees & Graphs",
    domain: "Hierarchical & Network Algorithms",
    emoji: "🍇",
    primaryColor: "#8338EC",
    accentColor: "#560BAD",
    gradient: "linear-gradient(135deg, #8338EC, #C77DFF)",
    glowColor: "rgba(131, 56, 236, 0.4)",
    realWorldRole: "Google Search Index (Tries), GPS Route Finding (Dijkstra/A*), Social Friend Graphs",
    description: "Climb binary trees, tries, AVL trees, graphs, shortest path algorithms, and network flows."
  },
  ADVANCED: {
    id: "ADVANCED",
    name: "Lime Advanced Algorithms",
    domain: "Dynamic Programming & System Design",
    emoji: "🍉",
    primaryColor: "#38B000",
    accentColor: "#007200",
    gradient: "linear-gradient(135deg, #38B000, #70E000)",
    glowColor: "rgba(56, 176, 0, 0.4)",
    realWorldRole: "High-Scale Distributed Caches, Flight Fare Optimizers, Distributed Locks & AI Pathfinding",
    description: "Unleash Dynamic Programming, Greedy heuristics, Backtracking, System Design, and Concurrency."
  }
};

const SEED_DATA = [
  // 🍓 FOUNDATIONS (36 Topics)
  ["Big-O notation", "Complexity", "Understanding upper asymptotic bounding of algorithms.", 1, "O(1) to O(N!)", "O(1)", "ARRAY", "FOUNDATIONS", "Cloud SLA Guarantees (AWS Lambda scaling limits)", "Examining the total calorie & chew time scaling as the candy pack size grows from snack-size to party-pack."],
  ["Time vs space complexity", "Complexity", "Balancing computational steps vs auxiliary memory consumption.", 1, "Varies", "Varies", "ARRAY", "FOUNDATIONS", "Mobile App Battery & RAM Optimization", "Trading off pre-unwrapping all candies into a bowl (high RAM) vs unwrapping one candy on demand (high CPU)."],
  ["Arrays basics", "Arrays", "Contiguous memory allocation with constant time random access.", 1, "O(1) access", "O(N)", "ARRAY", "FOUNDATIONS", "Audio Sample Streaming Buffers & Image Pixels", "A rigid segmented chocolate slab where breaking off piece #4 takes direct O(1) instant snapping."],
  ["Multi-dimensional arrays", "Arrays", "Matrices and grids in row-major memory order.", 2, "O(R*C)", "O(R*C)", "ARRAY", "FOUNDATIONS", "Game Physics Grids & Spreadsheet Engines", "A 2D chocolate box with Rows and Columns of flavored truffles."],
  ["String basics", "Strings", "Character sequences and ASCII/Unicode encoding representation.", 1, "O(N)", "O(N)", "ARRAY", "FOUNDATIONS", "Text Search Parsers & URL Encoding", "A roll of lettered bubblegum candy tape unrolled character by character."],
  ["String immutability in Java", "Strings", "String pool internals and immutability guarantees in Java.", 2, "O(N)", "O(N)", "ARRAY", "FOUNDATIONS", "Thread-Safe Database Connection Strings & Security Tokens", "Once a hard candy chew is molded in the factory, its shape is immutable; creating variations requires molding a fresh chew."],
  ["StringBuilder", "Strings", "Mutable character sequences for efficient string concatenations.", 2, "O(1) amortized", "O(N)", "ARRAY", "FOUNDATIONS", "JSON Document Builders & SQL Query Constructors", "A flexible candy taffy puller that appends colorful chewy layers without wasting extra wrapper wrappers."],
  ["Two-pointer technique", "Two Pointers", "Opposite or same-direction pointer iteration to avoid O(N^2) loops.", 2, "O(N)", "O(1)", "ARRAY", "FOUNDATIONS", "High-Frequency Stock Match Trading Engines", "Two kids eating from opposite ends of a long candy stick towards the center."],
  ["Sliding window", "Sliding Window", "Expanding and shrinking continuous subarray windows.", 2, "O(N)", "O(K)", "ARRAY", "FOUNDATIONS", "Network Rate Limiting (Token Bucket & Sliding Logs)", "A transparent plastic window sliding across a strip of assorted candy drops."],
  ["Prefix sums", "Prefix Sums", "Precomputing cumulative sums for O(1) range queries.", 2, "O(N) build, O(1) query", "O(N)", "ARRAY", "FOUNDATIONS", "Financial Ledger Balance History & Range Analytics", "Weighing the candy packet progressively so you can find the weight of candies between item 3 and 8 in O(1)."],
  ["Recursion basics", "Recursion", "Base cases, recursive steps, and execution call stack unwinding.", 2, "O(N)", "O(N) stack", "RECURSION", "FOUNDATIONS", "File System Directory Walkers & DOM Parsers", "Opening a Russian Matryoshka candy box that contains smaller identical candy boxes until reaching a gold coin (base case)."],
  ["Recursion tree/stack", "Recursion", "Visualizing recursive branching factors and maximum recursion depth.", 3, "O(2^N)", "O(N) stack", "RECURSION", "FOUNDATIONS", "Divide & Conquer Rendering in 3D Graphics", "Branching candy canes splitting into mini candy canes at each knot."],
  ["Backtracking intro", "Backtracking", "Systematic trial-and-error state exploration with pruning.", 3, "Exponential", "O(Depth)", "RECURSION", "FOUNDATIONS", "Robotics Maze Solving & Circuit Routing", "Exploring a candy maze, leaving breadcrumbs, and taking a step back if a dead end is hit."],
  ["Bit manipulation basics", "Bit Manipulation", "Bitwise AND, OR, XOR, NOT, and bit shifts.", 2, "O(1)", "O(1)", "ARRAY", "FOUNDATIONS", "Hardware Register Masks & Linux Permissions", "Flipping tiny chocolate chip switches on a circuit board to encode flavors in a single byte."],
  ["Bitmasking", "Bit Manipulation", "Using integer bit registers to represent power sets and subsets.", 3, "O(1)", "O(1)", "ARRAY", "FOUNDATIONS", "Game Inventory Flags & Feature Toggle Flags", "An 8-slot chocolate tray where a 1 means flavor is present and 0 means empty."],
  ["Math for DSA (GCD/LCM)", "Math", "Euclidean GCD algorithm and prime factorization.", 2, "O(log(min(a,b)))", "O(1)", "ARRAY", "FOUNDATIONS", "RSA Cryptography & Screen Aspect Ratio Calculators", "Finding the largest equal-sized square chocolate squares that can tile a rectangular bar."],
  ["Sorting overview", "Sorting", "Comparison vs non-comparison sorts, stability, and in-place properties.", 2, "O(N log N)", "O(1)", "ARRAY", "FOUNDATIONS", "Database Index Creation & Search Result Ranking", "Arranging assorted fruit candies by sweetness level on a conveyer belt."],
  ["Bubble sort", "Sorting", "Repeatedly swapping adjacent out-of-order elements.", 1, "O(N^2)", "O(1)", "ARRAY", "FOUNDATIONS", "Teaching Tool & Near-Sorted Sensor Signals", "Heavy chocolate balls sinking to the bottom while light jelly drops bubble up to the top."],
  ["Selection sort", "Sorting", "Finding the minimum element and placing it into sorted prefix.", 1, "O(N^2)", "O(1)", "ARRAY", "FOUNDATIONS", "Flash Memory Wear-Reduction (Minimizing Writes)", "Scanning the entire candy tray to pick the single smallest candy and moving it to the front."],
  ["Insertion sort", "Sorting", "Inserting elements into their correct position in a growing sorted array.", 2, "O(N^2) worst, O(N) best", "O(1)", "ARRAY", "FOUNDATIONS", "Online Real-Time Streaming Sensor Feeds", "Holding a hand of playing cards (or candy vouchers) and inserting each new voucher into place."],
  ["Merge sort", "Sorting", "Divide-and-conquer sorting with guaranteed O(N log N) performance.", 3, "O(N log N)", "O(N)", "ARRAY", "FOUNDATIONS", "External Hard Drive Sorting (Big Data MapReduce)", "Splitting a pile of candies in half, sorting both halves, and zipping them together smoothly."],
  ["Quick sort", "Sorting", "Partitioning around a pivot with fast cache performance.", 3, "O(N log N) avg", "O(log N)", "ARRAY", "FOUNDATIONS", "V8 JavaScript Engine & Standard Library Sorting", "Choosing a pivot candy flavor and sorting smaller candies to the left and larger to the right."],
  ["Counting sort", "Sorting", "Non-comparison sorting over a finite bounded integer key range.", 2, "O(N + K)", "O(K)", "ARRAY", "FOUNDATIONS", "Age Demographics Sorting & RGB Color Histograms", "Sorting candy drops into 5 color bowls by counting quantities and pouring them in order."],
  ["Radix sort", "Sorting", "Digit-by-digit sorting using stable counting sort subroutines.", 3, "O(d * (N + b))", "O(N + b)", "ARRAY", "FOUNDATIONS", "Card Number Sorting & Suffix Array Generation", "Sorting candies by expiration date: first by day, then month, then year."],
  ["Searching overview", "Searching", "Lookup paradigms across ordered vs unordered data structures.", 1, "O(N) to O(log N)", "O(1)", "ARRAY", "FOUNDATIONS", "Database Query Optimizers", "Finding your favorite strawberry chew in a mixed candy jar."],
  ["Linear search", "Searching", "Sequential scanning through each element in an array.", 1, "O(N)", "O(1)", "ARRAY", "FOUNDATIONS", "Small Cache Lookups & Unsorted Logs", "Checking every single candy wrapper one by one until finding the golden ticket."],
  ["Binary search", "Searching", "Logarithmic halving search over sorted indexed sequences.", 2, "O(log N)", "O(1)", "ARRAY", "FOUNDATIONS", "Git Bisect Bug Locator & Dictionary Lookups", "Guessing a number in a sorted candy weight quiz by cutting the remaining options in half each guess."],
  ["Binary search variants", "Searching", "Lower bound, upper bound, and peak element search.", 3, "O(log N)", "O(1)", "ARRAY", "FOUNDATIONS", "Stock Price Range Queries & Capacity Schedulers", "Finding the exact boundary where candies switch from sour to sweet in a graded continuum."],
  ["Kadane's algorithm", "Dynamic Programming", "Maximum subarray sum in single-pass linear time.", 2, "O(N)", "O(1)", "ARRAY", "FOUNDATIONS", "Maximum Profit Stock Window & Genomic Sequence Scoring", "Finding the tastiest continuous stretch of candy bites that maximizes happiness."],
  ["Dutch national flag", "Two Pointers", "3-way in-place partitioning algorithm by Edsger Dijkstra.", 3, "O(N)", "O(1)", "ARRAY", "FOUNDATIONS", "QuickSort 3-Way Partitioning (Duplicate Keys Handling)", "Sorting a mix of Red, White, and Blue candies into 3 distinct sections with a single pass."],
  ["Matrix rotation", "Arrays", "In-place 90-degree matrix transposition and row reversal.", 3, "O(N^2)", "O(1)", "ARRAY", "FOUNDATIONS", "Image Processing Rotate Tools & Video Feeds", "Rotating a square chocolate display case 90 degrees without taking the chocolates out."],
  ["Matrix spiral traversal", "Arrays", "Boundary-walking traversal across 2D rectangular matrices.", 2, "O(R*C)", "O(1)", "ARRAY", "FOUNDATIONS", "Spiral 2D Camera Sensors & PCB Drill Paths", "Peeling a chocolate roll spiraling from outer edges towards the center core."],
  ["Subarray problems", "Arrays", "Continuous slices, prefix difference hashing, and sliding bounds.", 3, "O(N)", "O(N)", "ARRAY", "FOUNDATIONS", "Network Bandwidth Peak Detection", "Finding continuous segments of candies that add up to an exact target calorie count."],
  ["Subsequence vs substring", "Strings", "Continuous substrings vs non-contiguous ordered subsequences.", 2, "O(N)", "O(1)", "ARRAY", "FOUNDATIONS", "DNA Sequence Matching & Autocomplete Typo Matching", "Comparing a solid chunk of chocolate bar (substring) vs picking 3 scattered chips in order (subsequence)."],
  ["Java generics for DSA", "Java Essentials", "Type parameterization, type erasure, and bounded wildcards.", 2, "Compile-time", "O(1)", "ARRAY", "FOUNDATIONS", "Enterprise Reusable Libraries & Type Safety", "Labeling a candy box as Box<Chocolate> so peanut candies cannot accidentally enter at compile-time."],
  ["Java Collections Framework overview", "Java Essentials", "List, Set, Queue, and Map hierarchy and trade-offs.", 2, "Varies", "Varies", "ARRAY", "FOUNDATIONS", "High-Performance Backend Server Development", "Selecting the perfect container: Jars (Sets), Dispensers (Queues), or Shelves (Maps)."],

  // 🍊 LINEAR STRUCTURES (36 Topics)
  ["Singly linked list", "Linked List", "Dynamic linear nodes connected via single next pointers.", 2, "O(1) head insert, O(N) search", "O(N)", "LINKED_LIST", "LINEAR", "Blockchain Blocks & Undo Action Chains", "A string of individually wrapped fruit chews tied together by wrapper twists."],
  ["Doubly linked list", "Linked List", "Bidirectional nodes with previous and next references.", 2, "O(1) insert/delete at node", "O(N)", "LINKED_LIST", "LINEAR", "Web Browser Back/Forward Navigation & Music Playlists", "A candy necklace with twin cords allowing you to slide beads forwards or backwards."],
  ["Circular linked list", "Linked List", "Tail node connects back to head forming an endless ring.", 2, "O(N) traversal", "O(N)", "LINKED_LIST", "LINEAR", "Operating System Round-Robin CPU Task Scheduling", "A circular candy carousel where the last treat leads right back to the first."],
  ["LL reversal", "Linked List", "Iterative and recursive pointer reversal of singly linked list.", 2, "O(N)", "O(1)", "LINKED_LIST", "LINEAR", "Transaction Reversal Engines in Banking", "Flipping all the wrapper twist directions so the candy train pulls in reverse."],
  ["Fast-slow pointers (cycle detection)", "Two Pointers", "Floyd's cycle-finding tortoise and hare algorithm.", 3, "O(N)", "O(1)", "LINKED_LIST", "LINEAR", "Deadlock Loop Detection in Distributed Systems", "Two runners tasting candies around a circular track until the faster runner laps the slower one."],
  ["Merge two sorted lists", "Linked List", "Splice and stitch two sorted linked lists in linear time.", 2, "O(N + M)", "O(1)", "LINKED_LIST", "LINEAR", "Merging Sorted Database Shards", "Zipping together two sorted candy ribbons into one master ribbon without extra bowls."],
  ["LRU cache design", "System Design Basics", "Doubly linked list + HashMap for O(1) get and put eviction.", 4, "O(1) get/put", "O(Capacity)", "LINKED_LIST", "LINEAR", "Redis Cache In-Memory Eviction & OS Page Replacement", "Keeping popular candies at the front of the shelf while stale, untouched ones fall off the back."],
  ["Stack basics", "Stack", "LIFO (Last-In First-Out) data structure operations.", 1, "O(1) push/pop/peek", "O(N)", "ARRAY", "LINEAR", "Ctrl+Z Undo Stack & Browser Call Stack", "A vertical Pez candy dispenser where the last candy pushed into the top is popped first."],
  ["Stack using arrays/LL", "Stack", "Implementing bounded array and dynamic node-based stacks.", 2, "O(1)", "O(N)", "ARRAY", "LINEAR", "Embedded Microcontroller Call Stacks", "Building a stack inside a fixed tube vs an expandable chain of candy links."],
  ["Monotonic stack", "Stack", "Maintaining strictly increasing or decreasing elements on stack.", 3, "O(N)", "O(N)", "ARRAY", "LINEAR", "Stock Span Analysis & Histogram Max Rectangle", "Stacking chocolates where every new piece must be smaller than the one underneath."],
  ["Next greater element", "Stack", "Linear time next greater element using monotonic decreasing stack.", 3, "O(N)", "O(N)", "ARRAY", "LINEAR", "Weather Temperature Spike Warnings", "Finding the next taller candy tower on the right for each building on the shelf."],
  ["Balanced parentheses", "Stack", "Matching nested bracket syntax using stack validation.", 2, "O(N)", "O(N)", "ARRAY", "LINEAR", "Compilers, JSON Parsers & HTML Document Validators", "Matching opening candy wrapper seals with closing seals in properly nested fashion."],
  ["Infix/postfix/prefix conversion", "Stack", "Shunting-yard algorithm for arithmetic expression parsing.", 3, "O(N)", "O(N)", "ARRAY", "LINEAR", "Spreadsheet Formula Engines (Excel) & Scientific Calculators", "Transforming complicated candy recipe orders into linear assembly lines."],
  ["Queue basics", "Queue", "FIFO (First-In First-Out) buffer with enqueue and dequeue.", 1, "O(1) offer/poll", "O(N)", "ARRAY", "LINEAR", "Print Queue & Web Server Request Handling", "A candy factory conveyor belt where the first batch in is the first batch packed."],
  ["Circular queue", "Queue", "Ring buffer using modulo arithmetic on fixed arrays.", 2, "O(1)", "O(Capacity)", "ARRAY", "LINEAR", "Audio Streaming Hardware Ring Buffers", "A rotating lazy susan for candies that reuses empty slots seamlessly."],
  ["Deque", "Queue", "Double-ended queue supporting insertion and removal at both ends.", 2, "O(1)", "O(N)", "ARRAY", "LINEAR", "Undo/Redo with Size Limits & Sliding Window Maximums", "A candy chute open at both ends allowing snacks to be loaded or grabbed from front or back."],
  ["Priority queue", "Heap", "Retrieving elements based on priority rather than arrival order.", 3, "O(log N) insert", "O(N)", "ARRAY", "LINEAR", "Emergency Room Triage & Dijkstra's Algorithm", "A VIP candy line where rare golden truffles always jump ahead of plain candies."],
  ["Stack-Queue interconversion", "Design", "Implementing Queue using 2 Stacks and Stack using 2 Queues.", 3, "O(1) amortized", "O(N)", "ARRAY", "LINEAR", "Fault-Tolerant Storage Adaptor Layers", "Pouring candies from one Pez dispenser into another to reverse the order into FIFO."],
  ["HashMap internals", "Hashing", "Hash table buckets, hash distribution, and bucket node chaining.", 3, "O(1) avg get/put", "O(N)", "ARRAY", "LINEAR", "Database Indexing & Distributed In-Memory Caches", "Dividing a warehouse into numbered bins based on flavor hash codes for instant O(1) retrieval."],
  ["HashSet", "Hashing", "Unique element set backed by HashMap dummy value mapping.", 2, "O(1) avg", "O(N)", "ARRAY", "LINEAR", "Duplicate User Filtering & Unique Visitor Counting", "A candy sampler tray where duplicate flavors are instantly rejected."],
  ["Collision handling (chaining/open addressing)", "Hashing", "Separate chaining vs linear probing and double hashing.", 3, "O(1) avg", "O(N)", "ARRAY", "LINEAR", "High-Performance Hash Tables (Java, C++ unordered_map)", "When two candies hash to the same bin, either chaining them in a mini-pouch or finding the next open slot."],
  ["Load factor & rehashing", "Hashing", "Resizing threshold (0.75) and doubling bucket capacity.", 3, "O(N) rehash", "O(Capacity)", "ARRAY", "LINEAR", "Elastic Cloud Storage Scaling", "When the candy box gets 75% full, transferring everything into a box twice as large."],
  ["TreeMap/TreeSet", "Trees", "Red-Black tree backed sorted maps with guaranteed O(log N) lookups.", 3, "O(log N)", "O(N)", "ARRAY", "LINEAR", "Sorted Leaderboards & Database Range Scans", "A self-organizing tiered candy pyramid that always stays sorted by sweetness."],
  ["Two-sum pattern", "Hashing", "Complement lookup in HashMap to find pair sums in O(N).", 2, "O(N)", "O(N)", "ARRAY", "LINEAR", "Matching Buy/Sell Orders on Crypto Exchanges", "Looking for a candy pair whose combined price hits exact budget in single pass."],
  ["Frequency counting patterns", "Hashing", "Character and frequency array patterns for anagrams and top-K.", 2, "O(N)", "O(K)", "ARRAY", "LINEAR", "Spell Checkers & Anagram Search Engines", "Counting the exact tally of each fruit chew color in a mixed packet."],
  ["String hashing", "Hashing", "Polynomial rolling hash functions for fast string matching.", 3, "O(N)", "O(1)", "ARRAY", "LINEAR", "Rabin-Karp Substring Matching & Plagiarism Detectors", "Converting a word into a unique candy flavor barcode number."],
  ["Sliding window + hashmap combo", "Sliding Window", "Longest substring without repeating characters pattern.", 3, "O(N)", "O(min(N, Alphabet))", "ARRAY", "LINEAR", "Network Packet De-Duplication", "Grabbing the longest continuous candy tape slice with all distinct fruit flavors."],
  ["Union-Find (Disjoint Set)", "Graph", "Disjoint set union data structure for connected components.", 3, "O(alpha(N)) ~ O(1)", "O(N)", "ARRAY", "LINEAR", "Kruskal's MST & Social Network Group Merging", "Grouping gummy candies into connected flavor families and finding group leaders."],
  ["Union by rank/path compression", "Graph", "Optimizing DSU tree height to near-constant inverse Ackermann.", 3, "O(alpha(N))", "O(N)", "ARRAY", "LINEAR", "Dynamic Connectivity in Large Scale Networks", "Flattening the gummy candy family tree so everyone links directly to the grand chief."],
  ["LinkedHashMap", "Hashing", "Preserving insertion or access order with doubly linked hash buckets.", 3, "O(1)", "O(N)", "LINKED_LIST", "LINEAR", "JSON Field Ordering & Recent Cache Stores", "A hash table where candies remember the exact order they were unwrapped."],
  ["Java equals()/hashCode() contract", "Java Essentials", "Correctly overriding equals and hashCode for Hash collections.", 2, "O(1)", "O(1)", "ARRAY", "LINEAR", "Enterprise Domain Entity Deduplication", "Ensuring identical chocolate recipes always generate matching barcode hashes."],
  ["Custom comparators", "Java Essentials", "Implementing Comparable and Comparator with lambda expressions.", 2, "O(1)", "O(1)", "ARRAY", "LINEAR", "Multi-Column Sorting (e.g. Price then Rating)", "Custom sorting rules: sort candies by sweetness first, then by size descending."],
  ["Iterator pattern in Java Collections", "Design Patterns", "Fail-fast iterators, ConcurrentModificationException, and Iterable.", 2, "O(1) per step", "O(1)", "ARRAY", "LINEAR", "Safe Multi-Threaded Collection Traversal", "A candy dispensing claw that safely hands out one sweet at a time without disturbing the pack."],
  ["ArrayDeque usage", "Queue", "Resizing circular array implementation outperforming Stack and LinkedList.", 2, "O(1) amortized", "O(N)", "ARRAY", "LINEAR", "Fastest In-Memory Queues in High-Performance Java", "A high-speed twin-door candy loader that outperforms old metal pipes."],
  ["Immutable collections", "Java Essentials", "List.of(), Set.of(), Map.of(), and unmodifiable wrappers.", 2, "O(1)", "O(N)", "ARRAY", "LINEAR", "Thread-Safe Functional Architecture", "Sealing a candy variety pack with tamper-proof vacuum wrap so no pieces can be added or taken."],
  ["Concurrent collections overview", "Concurrency", "ConcurrentHashMap, CopyOnWriteArrayList, and BlockingQueue basics.", 3, "O(1) lock-free read", "O(N)", "ARRAY", "LINEAR", "High-Throughput Multi-Core Web Servers", "Multiple people grabbing candies from different bins at the same time without collisions."],

  // 🍇 TREES & GRAPHS (36 Topics)
  ["Binary tree basics", "Tree", "Hierarchical structure where each node has at most two children.", 2, "O(N) nodes", "O(Height)", "TREE", "TREES_GRAPHS", "DOM Trees in Web Browsers & JSON Hierarchies", "A candy branch that splits into at most two baby branches at each bud."],
  ["Tree traversals (in/pre/post-order)", "Tree", "Depth-first tree traversal orders and properties.", 2, "O(N)", "O(Height)", "TREE", "TREES_GRAPHS", "Expression Tree Evaluation & Serialization", "Systematically visiting every candy on a giant Christmas tree in 3 distinct routes."],
  ["Level-order traversal (BFS on tree)", "Tree", "Breadth-first traversal layer by layer using a Queue.", 2, "O(N)", "O(Width)", "TREE", "TREES_GRAPHS", "Shortest Path Search & Peer-to-Peer Discovery", "Picking all candies on level 1, then all on level 2, layer by layer downward."],
  ["Binary Search Tree", "Tree", "BST invariant: left < root < right for efficient lookups.", 2, "O(Height)", "O(Height)", "TREE", "TREES_GRAPHS", "Database Index Lookup Trees", "A candy tree where sour candies hang on left branches and sweet candies hang on right."],
  ["BST insert/delete/search", "Tree", "Maintaining BST invariants through 3-case node deletions.", 3, "O(log N) avg", "O(Height)", "TREE", "TREES_GRAPHS", "Dynamic Sorted In-Memory Dictionaries", "Plucking a candy from the tree and grafting a child candy to preserve sweetness order."],
  ["Balanced BST intro (AVL)", "Tree", "Self-balancing tree using balance factors and LL/RR/LR/RL rotations.", 4, "O(log N) strict", "O(N)", "TREE", "TREES_GRAPHS", "Low-Latency High-Frequency Trading Engines", "A tree that auto-rotates branches whenever one side gets heavier than the other."],
  ["Red-Black tree overview", "Tree", "Self-balancing binary search tree with node color constraints.", 4, "O(log N)", "O(N)", "TREE", "TREES_GRAPHS", "Linux Process Virtual Memory Mapping (vm_area_struct)", "Coloring candy buds Red and Black to balance growth automatically."],
  ["Tree height/diameter", "Tree", "Calculating maximum distance between any two nodes in a tree.", 3, "O(N)", "O(Height)", "TREE", "TREES_GRAPHS", "Network Cable Span Planning", "Finding the longest possible path between any two dangling lollipops."],
  ["Lowest common ancestor", "Tree", "Finding closest shared ancestor node in BST and general Binary Trees.", 3, "O(N)", "O(Height)", "TREE", "TREES_GRAPHS", "Git Merge-Base Commit Finders & Family Tree Analysis", "Tracing back two candy drops to find where their branches first met."],
  ["Binary tree to DLL", "Tree", "In-place in-order transformation of binary tree to doubly linked list.", 3, "O(N)", "O(Height)", "TREE", "TREES_GRAPHS", "Flattening Hierarchical Tree Data into Linear Memory", "Untangling a bushy candy tree into a single straight garland necklace."],
  ["Segment tree", "Advanced Tree", "Tree structure for answering range queries and updates in logarithmic time.", 4, "O(log N) query", "O(4N)", "TREE", "TREES_GRAPHS", "Competitive Gaming Leaderboard Range Stats", "A master candy dashboard that summarizes calorie sums across ranges in O(log N)."],
  ["Fenwick tree (BIT)", "Advanced Tree", "Binary Indexed Tree for efficient prefix sum updates in O(log N).", 4, "O(log N)", "O(N)", "ARRAY", "TREES_GRAPHS", "Cumulative Frequency Analyzers in Telecommunications", "Compact binary indexed candy jars for lightning fast running totals."],
  ["Trie (prefix tree)", "Trie", "N-ary tree for lightning fast string dictionary prefix lookups.", 3, "O(Word Length)", "O(Alphabet * Length)", "TREE", "TREES_GRAPHS", "Google Search Auto-Complete & Spell Check Engines", "A tree where each letter is a colored candy leading to complete words."],
  ["Trie applications (autocomplete)", "Trie", "Prefix searching, wildcards, and autocomplete suggestions.", 3, "O(Prefix + Matches)", "O(Alphabet * N)", "TREE", "TREES_GRAPHS", "Mobile Keyboard Predictive Typing", "Typing 'choc' and following the trie path to reveal 'chocolate' and 'chocobar'."],
  ["Heap basics", "Heap", "Complete binary tree satisfying min/max heap parent-child order.", 2, "O(1) peek", "O(N)", "TREE", "TREES_GRAPHS", "Priority Task Schedulers", "A pyramid of candies where the biggest sweet is always perched at the top."],
  ["Min-heap/max-heap", "Heap", "Priority-based parent invariants and heap array mapping.", 3, "O(log N) push/pop", "O(N)", "TREE", "TREES_GRAPHS", "Bandwidth Throttling Schedulers", "Extracting the highest priority treat in O(log N) while preserving the pyramid."],
  ["Heapify", "Heap", "Bottom-up O(N) linear time heap construction algorithm.", 3, "O(N) build", "O(1)", "TREE", "TREES_GRAPHS", "Fast Priority Heap Construction", "Turning a chaotic pile of candies into a structured heap in O(N) linear time."],
  ["Heap sort", "Sorting", "In-place O(N log N) sorting using binary heap structure.", 3, "O(N log N)", "O(1)", "TREE", "TREES_GRAPHS", "Space-Constrained Embedded Systems Sorting", "Repeatedly popping the top candy from the heap into the sorted tray."],
  ["K-th largest/smallest element", "Heap", "Finding K-th extreme element using min/max heaps of size K.", 3, "O(N log K)", "O(K)", "TREE", "TREES_GRAPHS", "Trending Twitter Hashtags Top-K Analytics", "Keeping a small bowl of the top 3 best candies out of 10,000."],
  ["Graph representation (adjacency list/matrix)", "Graph", "Representing vertex connections via lists vs matrices.", 2, "O(V + E) vs O(V^2)", "O(V + E)", "TREE", "TREES_GRAPHS", "Social Media Friend Networks & Flight Route Maps", "Mapping candy shops (nodes) and delivery paths (edges) on a road network."],
  ["Graph traversal BFS", "Graph", "Breadth-first search finding shortest unweighted paths.", 3, "O(V + E)", "O(V)", "TREE", "TREES_GRAPHS", "LinkedIn 1st/2nd/3rd Degree Connections Search", "Spreading sugar ripples outwards from your candy store to find nearby friends."],
  ["Graph traversal DFS", "Graph", "Depth-first search exploring paths deeply with recursion or stack.", 3, "O(V + E)", "O(V)", "TREE", "TREES_GRAPHS", "Dependency Resolvers (npm, Maven) & Maze Explorers", "Following one long candy trail all the way to its end before backtracking."],
  ["Topological sort", "Graph", "Linear ordering of vertices in a Directed Acyclic Graph (Kahn / DFS).", 3, "O(V + E)", "O(V)", "TREE", "TREES_GRAPHS", "Build Systems (Webpack, Gradle) & Course Prerequisites", "Finding the exact cooking step order to make chocolate truffles with dependent recipes."],
  ["Cycle detection (directed/undirected)", "Graph", "Detecting cycles using 3-color DFS and Union-Find.", 3, "O(V + E)", "O(V)", "TREE", "TREES_GRAPHS", "Deadlock Detection & Circular Dependency Blockers", "Checking if candy delivery pipelines loop endlessly back into themselves."],
  ["Dijkstra's algorithm", "Graph", "Single-source shortest path algorithm with non-negative weights.", 4, "O((V + E) log V)", "O(V)", "TREE", "TREES_GRAPHS", "Google Maps Navigation & Network Packet Routing (OSPF)", "Finding the quickest delivery route across candy towns with traffic weights."],
  ["Bellman-Ford algorithm", "Graph", "Shortest path algorithm supporting negative edge weights and negative cycle detection.", 4, "O(V * E)", "O(V)", "TREE", "TREES_GRAPHS", "Currency Arbitrage Detection in Forex Markets", "Calculating shortest paths even when some candy shop roads give discounts (negative cost)."],
  ["Floyd-Warshall algorithm", "Graph", "All-pairs shortest path dynamic programming algorithm.", 4, "O(V^3)", "O(V^2)", "TREE", "TREES_GRAPHS", "Global Transit Matrix Pre-computation", "Creating a master distance chart between every single candy factory in the country."],
  ["Minimum spanning tree overview", "Graph", "Connecting all vertices with minimum total edge weight.", 3, "O(E log V)", "O(V)", "TREE", "TREES_GRAPHS", "Telecommunications Cable Laying & Water Pipe Networks", "Connecting all candy stores with the minimum total length of licorice wire."],
  ["Prim's algorithm", "Graph", "Greedy vertex-growing MST algorithm using priority queues.", 4, "O(E log V)", "O(V)", "TREE", "TREES_GRAPHS", "Power Grid Network Generation", "Growing the candy network from a central hub by always picking the closest unvisited shop."],
  ["Kruskal's algorithm", "Graph", "Greedy edge-adding MST algorithm using Union-Find.", 4, "O(E log E)", "O(V)", "TREE", "TREES_GRAPHS", "LAN Network Design & Circuit Board Tracing", "Sorting all licorice connections by cost and adding them without creating loops."],
  ["Bipartite graph check", "Graph", "2-coloring graph verification using BFS or DFS.", 3, "O(V + E)", "O(V)", "TREE", "TREES_GRAPHS", "Job-Applicant Matching & Dating App Matchmakers", "Coloring candy jars Red and Blue so no two linked jars share the same color."],
  ["Connected components", "Graph", "Finding isolated subgraphs using flood-fill DFS or DSU.", 2, "O(V + E)", "O(V)", "TREE", "TREES_GRAPHS", "Image Segmentation & Community Detection", "Counting how many isolated island clusters of candy stores exist."],
  ["Strongly connected components (Kosaraju/Tarjan)", "Graph", "Decomposing directed graphs into mutually reachable subgraphs.", 5, "O(V + E)", "O(V)", "TREE", "TREES_GRAPHS", "Web Page Connectivity Clusters & Compiler Optimizations", "Finding clusters of candy stores where you can drive from any store to any other."],
  ["Graph coloring", "Graph", "Vertex coloring optimization and chromatic numbers.", 4, "NP-Complete (Heuristics)", "O(V)", "TREE", "TREES_GRAPHS", "Register Allocation in Compilers & Radio Frequency Assignment", "Assigning wrapper colors to neighboring sweets so none touch the same shade."],
  ["Network flow basics", "Graph", "Ford-Fulkerson and Edmonds-Karp maximum flow through flow networks.", 5, "O(V * E^2)", "O(V + E)", "TREE", "TREES_GRAPHS", "Supply Chain Logistics & Airline Flight Scheduling", "Maximizing the gallons of liquid chocolate pumped through a network of factory pipes."],
  ["Java tree/graph implementation idioms", "Java Essentials", "Clean object-oriented node models, record patterns, and adjacency lists.", 2, "O(1)", "O(1)", "TREE", "TREES_GRAPHS", "Production Backend Code Architecture", "Clean modern Java 21 Records and Map structures for graph representations."],

  // 🍉 ADVANCED ALGORITHMS (42 Topics)
  ["Dynamic programming intro", "Dynamic Programming", "Optimal substructure and overlapping subproblems.", 3, "O(N)", "O(N)", "RECURSION", "ADVANCED", "DNA Sequencing & Robotics Trajectory Planning", "Writing down previous candy recipe answers in a notebook so you never recalculate them."],
  ["Memoization vs tabulation", "Dynamic Programming", "Top-down cached recursion vs bottom-up iterative table filling.", 3, "O(N)", "O(N)", "RECURSION", "ADVANCED", "Financial Cash Flow Modeling", "Top-down (remembering flavors as asked) vs Bottom-up (filling the candy table row by row)."],
  ["0/1 Knapsack", "Dynamic Programming", "Maximizing value without exceeding weight capacity with single item use.", 4, "O(N * W)", "O(W)", "RECURSION", "ADVANCED", "Server Resource Packing & Cargo Loading", "Maximizing the tastiness of chocolates in your backpack without exceeding the weight limit."],
  ["Unbounded knapsack", "Dynamic Programming", "Knapsack variant where items can be selected infinitely.", 4, "O(N * W)", "O(W)", "RECURSION", "ADVANCED", "Coin Change & Stock Portfolio Optimization", "Filling your candy bag when the store has unlimited refills of every chocolate type."],
  ["Longest common subsequence", "Dynamic Programming", "Finding longest sequence appearing in both strings in order.", 4, "O(N * M)", "O(min(N, M))", "RECURSION", "ADVANCED", "Git Diff File Comparison Tool & DNA Alignment", "Finding the longest shared recipe steps between two chocolate cake versions."],
  ["Longest increasing subsequence", "Dynamic Programming", "Patience sorting with binary search in O(N log N).", 4, "O(N log N)", "O(N)", "ARRAY", "ADVANCED", "Box Stacking & Trend Detection in Time Series", "Arranging chocolates on a display shelf so each one is taller and tastier than before."],
  ["Edit distance", "Dynamic Programming", "Minimum insert/delete/replace operations to convert string A to B.", 4, "O(N * M)", "O(min(N, M))", "RECURSION", "ADVANCED", "Autocorrect Spell Checkers & Speech-to-Text Correction", "Minimum candy wrapper letter swaps to turn 'chocholate' into 'chocolate'."],
  ["Matrix chain multiplication", "Dynamic Programming", "Finding optimal parenthesization of matrix product series.", 5, "O(N^3)", "O(N^2)", "RECURSION", "ADVANCED", "3D Graphics Pipeline Multiplication Optimization", "Finding the fastest order to multiply and blend chocolate layers together."],
  ["DP on trees", "Dynamic Programming", "Subtree dynamic programming (e.g. tree diameter, house robber on tree).", 4, "O(N)", "O(Height)", "TREE", "ADVANCED", "Organizational Hierarchy Salary Optimization", "Maximizing sweetness gathered across a hierarchical candy tree without picking adjacent nodes."],
  ["DP on grids", "Dynamic Programming", "Unique paths, minimum path sum, and cherry pickup on 2D grids.", 3, "O(R * C)", "O(C)", "RECURSION", "ADVANCED", "Autonomous Robot Navigation & Game Level Path Planning", "Walking across a chocolate chessboard collecting the maximum sugar drops."],
  ["Coin change problem", "Dynamic Programming", "Fewest coins required to make up an exact total amount.", 3, "O(N * Amount)", "O(Amount)", "RECURSION", "ADVANCED", "ATM Cash Dispensing & Financial Ledger Settlement", "Finding the fewest candy coins needed to pay exact shop price."],
  ["Partition problems", "Dynamic Programming", "Partitioning array into equal sum subsets (subset sum problem).", 4, "O(N * Sum)", "O(Sum)", "RECURSION", "ADVANCED", "Distributed Job Load Balancing Across Servers", "Splitting a pile of candies between two siblings so both get exact equal sugar grams."],
  ["Greedy algorithms intro", "Greedy", "Making locally optimal choices at each step hoping for global optimum.", 3, "O(N log N)", "O(1)", "ARRAY", "ADVANCED", "Interval Scheduling & Audio Compression", "Grabbing the most delicious available chocolate at every single step."],
  ["Activity selection", "Greedy", "Selecting maximum number of non-overlapping interval tasks.", 3, "O(N log N)", "O(1)", "ARRAY", "ADVANCED", "Meeting Room Schedulers & Satellite Task Allocation", "Attending the maximum number of candy tasting sessions without schedule clashes."],
  ["Huffman coding", "Greedy", "Optimal prefix-free variable length data compression encoding.", 4, "O(N log N)", "O(N)", "TREE", "ADVANCED", "ZIP File Compression & JPEG Image Compression", "Giving the most common candy flavor the shortest code tag to shrink the box size."],
  ["Fractional knapsack", "Greedy", "Continuous value-density sorting for fractional item knapsack.", 3, "O(N log N)", "O(1)", "ARRAY", "ADVANCED", "Liquid Commodity Trading & Bulk Material Packing", "Scooping bulk liquid chocolate with highest taste-per-gram density into a jar."],
  ["Backtracking — N-Queens", "Backtracking", "Placing N non-attacking queens on an N*N chessboard.", 4, "O(N!)", "O(N)", "RECURSION", "ADVANCED", "VLSI Chip Design & Satellite Constellation Placement", "Placing N chocolate queen pieces on a board so no two can attack each other."],
  ["Backtracking — Sudoku solver", "Backtracking", "Constraint satisfaction board solver with row/col/box pruning.", 4, "O(9^(Empty))", "O(1)", "RECURSION", "ADVANCED", "Puzzle Solvers & AI Constraint Satisfaction", "Filling an 81-slot chocolate sampler box following strict row, column, and box constraints."],
  ["Backtracking — permutations/combinations", "Backtracking", "Generating power sets, permutations, and subsets without duplicates.", 3, "O(N * N!)", "O(N)", "RECURSION", "ADVANCED", "Password Combination Checkers & Feature Testing", "Generating all possible flavor gift box assortments without repeats."],
  ["Divide and conquer paradigm", "Algorithmic Paradigms", "Dividing into subproblems, conquering recursively, and combining.", 3, "O(N log N)", "O(log N)", "RECURSION", "ADVANCED", "Distributed MapReduce & Fast Fourier Transform (FFT)", "Breaking a giant chocolate bar into mini squares, processing each, and assembling."],
  ["Master theorem", "Complexity", "Asymptotic formula for divide-and-conquer recurrence relations.", 3, "O(1)", "O(1)", "ARRAY", "ADVANCED", "Algorithm Performance Benchmarking", "A mathematical recipe formula to instantly calculate how fast divide-and-conquer scales."],
  ["Advanced binary search (search in rotated array)", "Binary Search", "Pivoted binary search with modified boundary tests.", 3, "O(log N)", "O(1)", "ARRAY", "ADVANCED", "Circular Buffer Lookups in Operating Systems", "Finding a target chocolate in a conveyor belt that was rotated mid-shift."],
  ["Sliding window maximum", "Queue", "Maintaining max element in sliding window using monotonic deque.", 4, "O(N)", "O(K)", "ARRAY", "ADVANCED", "Real-Time Stock Market Highs & Network Telemetry", "Finding the sweetest candy in a moving window of size K in O(1) per step."],
  ["Monotonic queue", "Queue", "Deque maintaining elements in monotonic order for O(1) range queries.", 4, "O(1) amortized", "O(K)", "ARRAY", "ADVANCED", "Audio Signal Peak Volume Detection", "A queue of candies where new pieces push out older, smaller candies."],
  ["String matching (KMP)", "Strings", "Knuth-Morris-Pratt string matching using Longest Prefix Suffix (LPS).", 4, "O(N + M)", "O(M)", "ARRAY", "ADVANCED", "Genome DNA Search & Antivirus Virus Signature Scanning", "Searching for a secret candy recipe string without ever backtracking through text."],
  ["Rabin-Karp algorithm", "Strings", "Rolling hash based substring search with false positive validation.", 4, "O(N + M) avg", "O(1)", "ARRAY", "ADVANCED", "Plagiarism Detection Systems (Turnitin)", "Using a rolling chocolate barcode hash to find matching text in massive documents."],
  ["Z-algorithm", "Strings", "Linear time string matching using Z-array prefix comparisons.", 4, "O(N + M)", "O(N + M)", "ARRAY", "ADVANCED", "Bioinformatics Sequence Aligners", "Computing the longest prefix match for every candy wrapper position in linear time."],
  ["Suffix array intro", "Strings", "Sorted array of all string suffixes for fast pattern matching.", 5, "O(N log N)", "O(N)", "ARRAY", "ADVANCED", "Burrows-Wheeler Transform in bzip2 Compression", "Sorting all tail endings of a candy label to enable binary search for any sub-pattern."],
  ["A* search algorithm", "Graph", "Heuristic graph pathfinding combining Dijkstra with Euclidean distance.", 4, "O(E)", "O(V)", "TREE", "ADVANCED", "Game NPC AI Navigation & Self-Driving Car Pathfinding", "Finding the fastest route to the candy castle using GPS straight-line distance heuristics."],
  ["Design patterns — Singleton", "Design Patterns", "Single instance guarantee with global access point in Java.", 2, "O(1)", "O(1)", "ARRAY", "ADVANCED", "Database Connection Pools & App Configuration Managers", "Ensuring only one master chocolate recipe vault exists in the entire factory."],
  ["Design patterns — Factory", "Design Patterns", "Encapsulating object creation behind parameterized interfaces.", 2, "O(1)", "O(1)", "ARRAY", "ADVANCED", "Cross-Platform UI Toolkits & Payment Gateways", "A candy molding machine that creates Strawberry, Orange, or Grape candies on request."],
  ["Design patterns — Observer", "Design Patterns", "Publish-subscribe event broadcasting pattern in JavaFX.", 3, "O(Observers)", "O(Observers)", "ARRAY", "ADVANCED", "Event-Driven Microservices (Kafka) & UI Event Listeners", "Broadcasting a chime whenever fresh candies come out of the oven so all eaters react."],
  ["Design patterns — Strategy", "Design Patterns", "Interchangeable algorithm families encapsulated in classes.", 3, "O(1)", "O(1)", "ARRAY", "ADVANCED", "Payment Processors (Stripe/PayPal/ApplePay) & Compression", "Switching between MCQ grading, Code Trace grading, and Speed grading seamlessly."],
  ["Design patterns — Builder", "Design Patterns", "Fluent step-by-step object construction API.", 2, "O(1)", "O(1)", "ARRAY", "ADVANCED", "HTTP Request Builders & Complex Document Generators", "Custom assembling a personalized candy gift box step-by-step with a fluent API."],
  ["System design basics — scalability", "System Design", "Horizontal vs vertical scaling, load balancers, and stateless services.", 3, "Theoretical", "Theoretical", "ARRAY", "ADVANCED", "Handling Black Friday Traffic (Netflix/Amazon)", "Scaling up candy production: buying bigger machines (vertical) vs opening 10 parallel factory branches (horizontal)."],
  ["System design basics — caching", "System Design", "Write-through, write-back, CDN, and distributed Redis caches.", 3, "O(1) cache hit", "O(Storage)", "ARRAY", "ADVANCED", "Cloudflare CDN & Distributed Memcached", "Keeping a small candy bowl right next to the desk so you don't walk to the basement warehouse."],
  ["LFU cache design", "System Design", "Least Frequently Used cache eviction using frequency lists + maps.", 5, "O(1) get/put", "O(Capacity)", "LINKED_LIST", "ADVANCED", "Web Proxy Cache Replacement Policies", "Evicting the candy flavor that was picked the fewest times across all history."],
  ["Concurrency basics for DSA (thread-safe structures)", "Concurrency", "Synchronized blocks, ReentrantLock, and volatile memory visibility.", 4, "O(1) lock ops", "O(1)", "ARRAY", "ADVANCED", "Multi-Threaded Trading Platforms & Game GameLoop Engines", "Locking the chocolate dispenser door while one customer scoops so nobody spills."],
  ["Bitmask DP (Travelling Salesperson)", "Dynamic Programming", "Solving NP-hard problems over small N<=20 using bitmask states.", 5, "O(2^N * N^2)", "O(2^N * N)", "RECURSION", "ADVANCED", "Logistics Delivery Routing (FedEx/UPS)", "Visiting all N candy stores with minimum driving gas using a bit register to track visited stores."],
  ["Digit DP", "Dynamic Programming", "Counting numbers satisfying properties across bounded digit ranges.", 5, "O(Digits * States)", "O(Digits * States)", "RECURSION", "ADVANCED", "Barcode Validator & Financial Range Enumeration", "Counting how many candy serial numbers have no repeating digits in range [1..10^18]."],
  ["Heavy-Light Decomposition", "Advanced Tree", "Decomposing trees into vertex-disjoint paths for range queries.", 5, "O(log^2 N)", "O(N)", "TREE", "ADVANCED", "Large Scale Network Traffic Monitoring", "Decomposing a massive candy tree into heavy highway branches for O(log^2 N) path queries."],
  ["Centroid Decomposition", "Advanced Tree", "Divide-and-conquer on trees by finding centroid nodes.", 5, "O(N log N)", "O(N)", "TREE", "ADVANCED", "Hierarchical Tree Clustering in Big Data", "Finding the center-of-gravity candy node that splits any tree into balanced halves."]
];

const ALL_TOPICS = SEED_DATA.map((item, index) => {
  const [name, tag, summary, difficulty, timeComp, spaceComp, visType, trackKey, realWorldUsage, candyMetaphor] = item;
  const id = `topic_${trackKey.toLowerCase()}_${index + 1}`;
  return {
    id,
    sequence: index + 1,
    name,
    tag,
    summary,
    difficulty,
    timeComplexity: timeComp,
    spaceComplexity: spaceComp,
    visualizerType: visType,
    track: trackKey,
    realWorldUsage,
    candyMetaphor,
    explanation: `### 🍬 Concept Deep-Dive: ${name}

${summary}

---

#### 🏭 Real-World Production Usage & Engineering Context:
> **Industry Deployment:** **${realWorldUsage}**  
> In production architectures (e.g. Google, Netflix, Amazon, Uber), this technique is vital to minimize server latency, prevent computational bottlenecks, and maintain predictable high-throughput SLAs.

---

#### 🍫 The Candy / Chocolate Packet Metaphor:
> **${candyMetaphor}**  
> By visualizing how physical candy pieces interact in a pack, you build intuitive muscle memory for pointer movements, boundary contracts, and cache locality!

---

#### 🎯 Key Principles & Complexity Metrics:
- **Track & Category:** \`${TRACKS[trackKey].name}\` (\`${tag}\`)
- **Asymptotic Time Complexity:** \`${timeComp}\`
- **Auxiliary Space Complexity:** \`${spaceComp}\``,

    javaCode: `/**
 * 🍬 Candy Quest Java 21 Implementation
 * Topic: ${name} (${tag})
 * Real-World Deployment: ${realWorldUsage}
 */
public class ${name.replace(/[^a-zA-Z0-9]/g, "")}ProductionDemo {
    public static void main(String[] args) {
        System.out.println("🍬 Candy Quest: Executing ${name}");
        int[] candyPresents = {12, 45, 19, 88, 33};
        processProductionData(candyPresents);
    }

    public static void processProductionData(int[] data) {
        // Optimized ${tag} execution
        System.out.println("Processing " + data.length + " production units with ${timeComp} time!");
    }
}`,
    quiz: [
      {
        type: "MCQ",
        question: `How is ${name} typically deployed in high-scale real-world production systems?`,
        options: [
          `In ${realWorldUsage} to guarantee optimal step efficiency`,
          "To consume infinite thread stack memory without bounds",
          "As a decorative dummy wrapper that adds zero performance value",
          "Only for single-character ASCII strings"
        ],
        correct: 0,
        explanation: `${name} is standardly leveraged for ${realWorldUsage}.`,
        xp: 25
      },
      {
        type: "COMPLEXITY",
        question: `What is the asymptotic time complexity for ${name}?`,
        options: ["O(1)", timeComp, "O(N!)", "O(2^N)"],
        correct: 1,
        explanation: `The strict asymptotic worst/average bound for ${name} is ${timeComp}.`,
        xp: 25
      },
      {
        type: "CODE_TRACE",
        question: `What is the result when executing this ${name} algorithmic snippet?`,
        code: `int[] candies = {5, 15, 25};\nint sum = 0;\nfor (int c : candies) {\n    if (c > 10) sum += c;\n}\nSystem.out.print(sum);`,
        options: ["40", "45", "25", "0"],
        correct: 0,
        explanation: "15 + 25 = 40 (5 is skipped since 5 is not greater than 10).",
        xp: 30
      }
    ]
  };
});

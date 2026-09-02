package com.candyquest.repository;

import com.candyquest.model.QuizQuestion;
import com.candyquest.model.QuizQuestionType;
import com.candyquest.model.Topic;
import com.candyquest.model.Track;
import com.candyquest.pattern.factory.TopicFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Central repository for all 150 DSA topics in Candy Quest.
 */
public class TopicRepository {
    private final Map<String, Topic> topicsById = new LinkedHashMap<>();
    private final Map<Track, List<Topic>> topicsByTrack = new EnumMap<>(Track.class);

    public TopicRepository() {
        for (Track track : Track.values()) {
            topicsByTrack.put(track, new ArrayList<>());
        }
        loadTopics();
    }

    private void loadTopics() {
        // Attempt to load from JSON seed file in classpath
        boolean loadedFromJson = false;
        try (InputStream is = getClass().getResourceAsStream("/com/candyquest/data/topics_seed.json")) {
            if (is != null) {
                try (Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
                    Gson gson = new Gson();
                    List<Topic> list = gson.fromJson(reader, new TypeToken<List<Topic>>() {}.getType());
                    if (list != null && !list.isEmpty()) {
                        for (Topic topic : list) {
                            addTopic(topic);
                        }
                        loadedFromJson = true;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Could not load topics from JSON: " + e.getMessage());
        }

        if (!loadedFromJson || topicsById.size() < 150) {
            seedAll150Topics();
        }
    }

    private void addTopic(Topic topic) {
        topicsById.put(topic.getId(), topic);
        topicsByTrack.get(topic.getTrack()).add(topic);
    }

    public List<Topic> getAllTopics() {
        return new ArrayList<>(topicsById.values());
    }

    public List<Topic> getTopicsForTrack(Track track) {
        return topicsByTrack.getOrDefault(track, Collections.emptyList());
    }

    public Topic getTopicById(String id) {
        return topicsById.get(id);
    }

    public Topic getNextTopic(String currentTopicId) {
        Topic current = getTopicById(currentTopicId);
        if (current == null) return null;
        List<Topic> trackTopics = getTopicsForTrack(current.getTrack());
        int index = trackTopics.indexOf(current);
        if (index >= 0 && index < trackTopics.size() - 1) {
            return trackTopics.get(index + 1);
        }
        return null;
    }

    public List<Topic> searchTopics(String query, Track trackFilter, Integer difficultyFilter) {
        String lower = (query == null) ? "" : query.toLowerCase().trim();
        return topicsById.values().stream()
            .filter(t -> trackFilter == null || t.getTrack() == trackFilter)
            .filter(t -> difficultyFilter == null || t.getDifficulty() == difficultyFilter)
            .filter(t -> lower.isEmpty() || 
                         t.getName().toLowerCase().contains(lower) || 
                         (t.getTag() != null && t.getTag().toLowerCase().contains(lower)) ||
                         (t.getSummary() != null && t.getSummary().toLowerCase().contains(lower)))
            .collect(Collectors.toList());
    }

    /**
     * Seeds all 150 required topics across the 4 tracks using the TopicFactory pattern.
     */
    private void seedAll150Topics() {
        topicsById.clear();
        for (Track track : Track.values()) {
            topicsByTrack.get(track).clear();
        }

        int globalSeq = 1;

        // 🍓 TRACK 1: FOUNDATIONS (36 Topics)
        String[][] foundationsSeed = {
            {"Big-O notation", "Complexity", "Understanding upper asymptotic bounding of algorithms.", "1", "O(1) to O(N!)", "O(1)", "ARRAY"},
            {"Time vs space complexity", "Complexity", "Balancing computational steps vs auxiliary memory consumption.", "1", "Varies", "Varies", "ARRAY"},
            {"Arrays basics", "Arrays", "Contiguous memory allocation with constant time random access.", "1", "O(1) access", "O(N)", "ARRAY"},
            {"Multi-dimensional arrays", "Arrays", "Matrices and grids in row-major memory order.", "2", "O(R*C)", "O(R*C)", "ARRAY"},
            {"String basics", "Strings", "Character sequences and ASCII/Unicode encoding representation.", "1", "O(N)", "O(N)", "ARRAY"},
            {"String immutability in Java", "Strings", "String pool internals and immutability guarantees in Java.", "2", "O(N)", "O(N)", "ARRAY"},
            {"StringBuilder", "Strings", "Mutable character sequences for efficient string concatenations.", "2", "O(1) amortized append", "O(N)", "ARRAY"},
            {"Two-pointer technique", "Two Pointers", "Opposite or same-direction pointer iteration to avoid O(N^2) loops.", "2", "O(N)", "O(1)", "ARRAY"},
            {"Sliding window", "Sliding Window", "Expanding and shrinking continuous subarray windows.", "2", "O(N)", "O(K)", "ARRAY"},
            {"Prefix sums", "Prefix Sums", "Precomputing cumulative sums for O(1) range queries.", "2", "O(N) build, O(1) query", "O(N)", "ARRAY"},
            {"Recursion basics", "Recursion", "Base cases, recursive steps, and execution call stack unwinding.", "2", "O(N)", "O(N) stack", "RECURSION"},
            {"Recursion tree/stack", "Recursion", "Visualizing recursive branching factors and maximum recursion depth.", "3", "O(2^N)", "O(N) stack", "RECURSION"},
            {"Backtracking intro", "Backtracking", "Systematic trial-and-error state exploration with pruning.", "3", "Exponential", "O(Depth)", "RECURSION"},
            {"Bit manipulation basics", "Bit Manipulation", "Bitwise AND, OR, XOR, NOT, and bit shifts.", "2", "O(1)", "O(1)", "ARRAY"},
            {"Bitmasking", "Bit Manipulation", "Using integer bit registers to represent power sets and subsets.", "3", "O(1)", "O(1)", "ARRAY"},
            {"Math for DSA (GCD/LCM)", "Math", "Euclidean GCD algorithm and prime factorization.", "2", "O(log(min(a,b)))", "O(1)", "ARRAY"},
            {"Sorting overview", "Sorting", "Comparison vs non-comparison sorts, stability, and in-place properties.", "2", "O(N log N)", "O(1)", "ARRAY"},
            {"Bubble sort", "Sorting", "Repeatedly swapping adjacent out-of-order elements.", "1", "O(N^2) worst/avg, O(N) best", "O(1)", "ARRAY"},
            {"Selection sort", "Sorting", "Finding the minimum element and placing it into sorted prefix.", "1", "O(N^2)", "O(1)", "ARRAY"},
            {"Insertion sort", "Sorting", "Inserting elements into their correct position in a growing sorted array.", "2", "O(N^2) worst, O(N) best", "O(1)", "ARRAY"},
            {"Merge sort", "Sorting", "Divide-and-conquer sorting with guaranteed O(N log N) performance.", "3", "O(N log N)", "O(N)", "ARRAY"},
            {"Quick sort", "Sorting", "Partitioning around a pivot with fast cache performance.", "3", "O(N log N) avg, O(N^2) worst", "O(log N)", "ARRAY"},
            {"Counting sort", "Sorting", "Non-comparison sorting over a finite bounded integer key range.", "2", "O(N + K)", "O(K)", "ARRAY"},
            {"Radix sort", "Sorting", "Digit-by-digit sorting using stable counting sort subroutines.", "3", "O(d * (N + b))", "O(N + b)", "ARRAY"},
            {"Searching overview", "Searching", "Lookup paradigms across ordered vs unordered data structures.", "1", "O(N) to O(log N)", "O(1)", "ARRAY"},
            {"Linear search", "Searching", "Sequential scanning through each element in an array.", "1", "O(N)", "O(1)", "ARRAY"},
            {"Binary search", "Searching", "Logarithmic halving search over sorted indexed sequences.", "2", "O(log N)", "O(1)", "ARRAY"},
            {"Binary search variants", "Searching", "Lower bound, upper bound, and peak element search.", "3", "O(log N)", "O(1)", "ARRAY"},
            {"Kadane's algorithm", "Dynamic Programming", "Maximum subarray sum in single-pass linear time.", "2", "O(N)", "O(1)", "ARRAY"},
            {"Dutch national flag", "Two Pointers", "3-way in-place partitioning algorithm by Edsger Dijkstra.", "3", "O(N)", "O(1)", "ARRAY"},
            {"Matrix rotation", "Arrays", "In-place 90-degree matrix transposition and row reversal.", "3", "O(N^2)", "O(1)", "ARRAY"},
            {"Matrix spiral traversal", "Arrays", "Boundary-walking traversal across 2D rectangular matrices.", "2", "O(R*C)", "O(1)", "ARRAY"},
            {"Subarray problems", "Arrays", "Continuous slices, prefix difference hashing, and sliding bounds.", "3", "O(N)", "O(N)", "ARRAY"},
            {"Subsequence vs substring", "Strings", "Continuous substrings vs non-contiguous ordered subsequences.", "2", "O(N)", "O(1)", "ARRAY"},
            {"Java generics for DSA", "Java Essentials", "Type parameterization, type erasure, and bounded wildcards.", "2", "Compile-time", "O(1)", "ARRAY"},
            {"Java Collections Framework overview", "Java Essentials", "List, Set, Queue, and Map hierarchy and trade-offs.", "2", "Varies", "Varies", "ARRAY"}
        };

        for (String[] row : foundationsSeed) {
            Topic t = createTopicFromRow(row, Track.FOUNDATIONS, globalSeq++);
            addTopic(t);
        }

        // 🍊 TRACK 2: LINEAR STRUCTURES (36 Topics)
        String[][] linearSeed = {
            {"Singly linked list", "Linked List", "Dynamic linear nodes connected via single next pointers.", "2", "O(1) head insert, O(N) search", "O(N)", "LINKED_LIST"},
            {"Doubly linked list", "Linked List", "Bidirectional nodes with previous and next references.", "2", "O(1) insert/delete at node", "O(N)", "LINKED_LIST"},
            {"Circular linked list", "Linked List", "Tail node connects back to head forming an endless ring.", "2", "O(N) traversal", "O(N)", "LINKED_LIST"},
            {"LL reversal", "Linked List", "Iterative and recursive pointer reversal of singly linked list.", "2", "O(N)", "O(1)", "LINKED_LIST"},
            {"Fast-slow pointers (cycle detection)", "Two Pointers", "Floyd's cycle-finding tortoise and hare algorithm.", "3", "O(N)", "O(1)", "LINKED_LIST"},
            {"Merge two sorted lists", "Linked List", "Splice and stitch two sorted linked lists in linear time.", "2", "O(N + M)", "O(1)", "LINKED_LIST"},
            {"LRU cache design", "System Design Basics", "Doubly linked list + HashMap for O(1) get and put eviction.", "4", "O(1) get/put", "O(Capacity)", "LINKED_LIST"},
            {"Stack basics", "Stack", "LIFO (Last-In First-Out) data structure operations.", "1", "O(1) push/pop/peek", "O(N)", "ARRAY"},
            {"Stack using arrays/LL", "Stack", "Implementing bounded array and dynamic node-based stacks.", "2", "O(1)", "O(N)", "ARRAY"},
            {"Monotonic stack", "Stack", "Maintaining strictly increasing or decreasing elements on stack.", "3", "O(N)", "O(N)", "ARRAY"},
            {"Next greater element", "Stack", "Linear time next greater element using monotonic decreasing stack.", "3", "O(N)", "O(N)", "ARRAY"},
            {"Balanced parentheses", "Stack", "Matching nested bracket syntax using stack validation.", "2", "O(N)", "O(N)", "ARRAY"},
            {"Infix/postfix/prefix conversion", "Stack", "Shunting-yard algorithm for arithmetic expression parsing.", "3", "O(N)", "O(N)", "ARRAY"},
            {"Queue basics", "Queue", "FIFO (First-In First-Out) buffer with enqueue and dequeue.", "1", "O(1) offer/poll", "O(N)", "ARRAY"},
            {"Circular queue", "Queue", "Ring buffer using modulo arithmetic on fixed arrays.", "2", "O(1)", "O(Capacity)", "ARRAY"},
            {"Deque", "Queue", "Double-ended queue supporting insertion and removal at both ends.", "2", "O(1)", "O(N)", "ARRAY"},
            {"Priority queue", "Heap", "Retrieving elements based on priority rather than arrival order.", "3", "O(log N) insert/remove, O(1) peek", "O(N)", "ARRAY"},
            {"Stack-Queue interconversion", "Design", "Implementing Queue using 2 Stacks and Stack using 2 Queues.", "3", "O(1) amortized", "O(N)", "ARRAY"},
            {"HashMap internals", "Hashing", "Hash table buckets, hash distribution, and bucket node chaining.", "3", "O(1) avg get/put", "O(N)", "ARRAY"},
            {"HashSet", "Hashing", "Unique element set backed by HashMap dummy value mapping.", "2", "O(1) avg add/contains", "O(N)", "ARRAY"},
            {"Collision handling (chaining/open addressing)", "Hashing", "Separate chaining vs linear probing and double hashing.", "3", "O(1) avg, O(N) worst", "O(N)", "ARRAY"},
            {"Load factor & rehashing", "Hashing", "Resizing threshold (0.75) and doubling bucket capacity.", "3", "O(N) during rehash", "O(Capacity)", "ARRAY"},
            {"TreeMap/TreeSet", "Trees", "Red-Black tree backed sorted maps with guaranteed O(log N) lookups.", "3", "O(log N)", "O(N)", "ARRAY"},
            {"Two-sum pattern", "Hashing", "Complement lookup in HashMap to find pair sums in O(N).", "2", "O(N)", "O(N)", "ARRAY"},
            {"Frequency counting patterns", "Hashing", "Character and frequency array patterns for anagrams and top-K.", "2", "O(N)", "O(K)", "ARRAY"},
            {"String hashing", "Hashing", "Polynomial rolling hash functions for fast string matching.", "3", "O(N)", "O(1)", "ARRAY"},
            {"Sliding window + hashmap combo", "Sliding Window", "Longest substring without repeating characters pattern.", "3", "O(N)", "O(min(N, Alphabet))", "ARRAY"},
            {"Union-Find (Disjoint Set)", "Graph", "Disjoint set union data structure for connected components.", "3", "O(alpha(N)) ~ O(1)", "O(N)", "ARRAY"},
            {"Union by rank/path compression", "Graph", "Optimizing DSU tree height to near-constant inverse Ackermann.", "3", "O(alpha(N))", "O(N)", "ARRAY"},
            {"LinkedHashMap", "Hashing", "Preserving insertion or access order with doubly linked hash buckets.", "3", "O(1)", "O(N)", "LINKED_LIST"},
            {"Java equals()/hashCode() contract", "Java Essentials", "Correctly overriding equals and hashCode for Hash collections.", "2", "O(1)", "O(1)", "ARRAY"},
            {"Custom comparators", "Java Essentials", "Implementing Comparable and Comparator with lambda expressions.", "2", "O(1)", "O(1)", "ARRAY"},
            {"Iterator pattern in Java Collections", "Design Patterns", "Fail-fast iterators, ConcurrentModificationException, and Iterable.", "2", "O(1) per step", "O(1)", "ARRAY"},
            {"ArrayDeque usage", "Queue", "Resizing circular array implementation outperforming Stack and LinkedList.", "2", "O(1) amortized", "O(N)", "ARRAY"},
            {"Immutable collections", "Java Essentials", "List.of(), Set.of(), Map.of(), and unmodifiable wrappers.", "2", "O(1)", "O(N)", "ARRAY"},
            {"Concurrent collections overview", "Concurrency", "ConcurrentHashMap, CopyOnWriteArrayList, and BlockingQueue basics.", "3", "O(1) lock-free read", "O(N)", "ARRAY"}
        };

        for (String[] row : linearSeed) {
            Topic t = createTopicFromRow(row, Track.LINEAR, globalSeq++);
            addTopic(t);
        }

        // 🍇 TRACK 3: TREES & GRAPHS (36 Topics)
        String[][] treeGraphSeed = {
            {"Binary tree basics", "Tree", "Hierarchical structure where each node has at most two children.", "2", "O(N) nodes", "O(Height)", "TREE"},
            {"Tree traversals (in/pre/post-order)", "Tree", "Depth-first tree traversal orders and properties.", "2", "O(N)", "O(Height)", "TREE"},
            {"Level-order traversal (BFS on tree)", "Tree", "Breadth-first traversal layer by layer using a Queue.", "2", "O(N)", "O(Width)", "TREE"},
            {"Binary Search Tree", "Tree", "BST invariant: left < root < right for efficient lookups.", "2", "O(Height)", "O(Height)", "TREE"},
            {"BST insert/delete/search", "Tree", "Maintaining BST invariants through 3-case node deletions.", "3", "O(log N) avg, O(N) worst", "O(Height)", "TREE"},
            {"Balanced BST intro (AVL)", "Tree", "Self-balancing tree using balance factors and LL/RR/LR/RL rotations.", "4", "O(log N) strict", "O(N)", "TREE"},
            {"Red-Black tree overview", "Tree", "Self-balancing binary search tree with node color constraints.", "4", "O(log N)", "O(N)", "TREE"},
            {"Tree height/diameter", "Tree", "Calculating maximum distance between any two nodes in a tree.", "3", "O(N)", "O(Height)", "TREE"},
            {"Lowest common ancestor", "Tree", "Finding closest shared ancestor node in BST and general Binary Trees.", "3", "O(N)", "O(Height)", "TREE"},
            {"Binary tree to DLL", "Tree", "In-place in-order transformation of binary tree to doubly linked list.", "3", "O(N)", "O(Height)", "TREE"},
            {"Segment tree", "Advanced Tree", "Tree structure for answering range queries and updates in logarithmic time.", "4", "O(log N) query/update", "O(4N)", "TREE"},
            {"Fenwick tree (BIT)", "Advanced Tree", "Binary Indexed Tree for efficient prefix sum updates in O(log N).", "4", "O(log N)", "O(N)", "ARRAY"},
            {"Trie (prefix tree)", "Trie", "N-ary tree for lightning fast string dictionary prefix lookups.", "3", "O(Word Length)", "O(Alphabet * Length)", "TREE"},
            {"Trie applications (autocomplete)", "Trie", "Prefix searching, wildcards, and autocomplete suggestions.", "3", "O(Prefix + Matches)", "O(Alphabet * N)", "TREE"},
            {"Heap basics", "Heap", "Complete binary tree satisfying min/max heap parent-child order.", "2", "O(1) peek", "O(N)", "TREE"},
            {"Min-heap/max-heap", "Heap", "Priority-based parent invariants and heap array mapping.", "3", "O(log N) push/pop", "O(N)", "TREE"},
            {"Heapify", "Heap", "Bottom-up O(N) linear time heap construction algorithm.", "3", "O(N) build", "O(1)", "TREE"},
            {"Heap sort", "Sorting", "In-place O(N log N) sorting using binary heap structure.", "3", "O(N log N)", "O(1)", "TREE"},
            {"K-th largest/smallest element", "Heap", "Finding K-th extreme element using min/max heaps of size K.", "3", "O(N log K)", "O(K)", "TREE"},
            {"Graph representation (adjacency list/matrix)", "Graph", "Representing vertex connections via lists vs matrices.", "2", "O(V + E) vs O(V^2)", "O(V + E)", "TREE"},
            {"Graph traversal BFS", "Graph", "Breadth-first search finding shortest unweighted paths.", "3", "O(V + E)", "O(V)", "TREE"},
            {"Graph traversal DFS", "Graph", "Depth-first search exploring paths deeply with recursion or stack.", "3", "O(V + E)", "O(V)", "TREE"},
            {"Topological sort", "Graph", "Linear ordering of vertices in a Directed Acyclic Graph (Kahn / DFS).", "3", "O(V + E)", "O(V)", "TREE"},
            {"Cycle detection (directed/undirected)", "Graph", "Detecting cycles using 3-color DFS and Union-Find.", "3", "O(V + E)", "O(V)", "TREE"},
            {"Dijkstra's algorithm", "Graph", "Single-source shortest path algorithm with non-negative weights.", "4", "O((V + E) log V)", "O(V)", "TREE"},
            {"Bellman-Ford algorithm", "Graph", "Shortest path algorithm supporting negative edge weights and negative cycle detection.", "4", "O(V * E)", "O(V)", "TREE"},
            {"Floyd-Warshall algorithm", "Graph", "All-pairs shortest path dynamic programming algorithm.", "4", "O(V^3)", "O(V^2)", "TREE"},
            {"Minimum spanning tree overview", "Graph", "Connecting all vertices with minimum total edge weight.", "3", "O(E log V)", "O(V)", "TREE"},
            {"Prim's algorithm", "Graph", "Greedy vertex-growing MST algorithm using priority queues.", "4", "O(E log V)", "O(V)", "TREE"},
            {"Kruskal's algorithm", "Graph", "Greedy edge-adding MST algorithm using Union-Find.", "4", "O(E log E)", "O(V)", "TREE"},
            {"Bipartite graph check", "Graph", "2-coloring graph verification using BFS or DFS.", "3", "O(V + E)", "O(V)", "TREE"},
            {"Connected components", "Graph", "Finding isolated subgraphs using flood-fill DFS or DSU.", "2", "O(V + E)", "O(V)", "TREE"},
            {"Strongly connected components (Kosaraju/Tarjan)", "Graph", "Decomposing directed graphs into mutually reachable subgraphs.", "5", "O(V + E)", "O(V)", "TREE"},
            {"Graph coloring", "Graph", "Vertex coloring optimization and chromatic numbers.", "4", "NP-Complete (Heuristics)", "O(V)", "TREE"},
            {"Network flow basics", "Graph", "Ford-Fulkerson and Edmonds-Karp maximum flow through flow networks.", "5", "O(V * E^2)", "O(V + E)", "TREE"},
            {"Java tree/graph implementation idioms", "Java Essentials", "Clean object-oriented node models, record patterns, and adjacency lists.", "2", "O(1)", "O(1)", "TREE"}
        };

        for (String[] row : treeGraphSeed) {
            Topic t = createTopicFromRow(row, Track.TREES_GRAPHS, globalSeq++);
            addTopic(t);
        }

        // 🍉 TRACK 4: ADVANCED ALGORITHMS & DESIGN (42 Topics)
        String[][] advancedSeed = {
            {"Dynamic programming intro", "Dynamic Programming", "Optimal substructure and overlapping subproblems.", "3", "O(N)", "O(N)", "RECURSION"},
            {"Memoization vs tabulation", "Dynamic Programming", "Top-down cached recursion vs bottom-up iterative table filling.", "3", "O(N)", "O(N)", "RECURSION"},
            {"0/1 Knapsack", "Dynamic Programming", "Maximizing value without exceeding weight capacity with single item use.", "4", "O(N * W)", "O(W)", "RECURSION"},
            {"Unbounded knapsack", "Dynamic Programming", "Knapsack variant where items can be selected infinitely.", "4", "O(N * W)", "O(W)", "RECURSION"},
            {"Longest common subsequence", "Dynamic Programming", "Finding longest sequence appearing in both strings in order.", "4", "O(N * M)", "O(min(N, M))", "RECURSION"},
            {"Longest increasing subsequence", "Dynamic Programming", "Patience sorting with binary search in O(N log N).", "4", "O(N log N)", "O(N)", "ARRAY"},
            {"Edit distance", "Dynamic Programming", "Minimum insert/delete/replace operations to convert string A to B.", "4", "O(N * M)", "O(min(N, M))", "RECURSION"},
            {"Matrix chain multiplication", "Dynamic Programming", "Finding optimal parenthesization of matrix product series.", "5", "O(N^3)", "O(N^2)", "RECURSION"},
            {"DP on trees", "Dynamic Programming", "Subtree dynamic programming (e.g. tree diameter, house robber on tree).", "4", "O(N)", "O(Height)", "TREE"},
            {"DP on grids", "Dynamic Programming", "Unique paths, minimum path sum, and cherry pickup on 2D grids.", "3", "O(R * C)", "O(C)", "RECURSION"},
            {"Coin change problem", "Dynamic Programming", "Fewest coins required to make up an exact total amount.", "3", "O(N * Amount)", "O(Amount)", "RECURSION"},
            {"Partition problems", "Dynamic Programming", "Partitioning array into equal sum subsets (subset sum problem).", "4", "O(N * Sum)", "O(Sum)", "RECURSION"},
            {"Greedy algorithms intro", "Greedy", "Making locally optimal choices at each step hoping for global optimum.", "3", "O(N log N)", "O(1)", "ARRAY"},
            {"Activity selection", "Greedy", "Selecting maximum number of non-overlapping interval tasks.", "3", "O(N log N)", "O(1)", "ARRAY"},
            {"Huffman coding", "Greedy", "Optimal prefix-free variable length data compression encoding.", "4", "O(N log N)", "O(N)", "TREE"},
            {"Fractional knapsack", "Greedy", "Continuous value-density sorting for fractional item knapsack.", "3", "O(N log N)", "O(1)", "ARRAY"},
            {"Backtracking — N-Queens", "Backtracking", "Placing N non-attacking queens on an N*N chessboard.", "4", "O(N!)", "O(N)", "RECURSION"},
            {"Backtracking — Sudoku solver", "Backtracking", "Constraint satisfaction board solver with row/col/box pruning.", "4", "O(9^(Empty Cells))", "O(1)", "RECURSION"},
            {"Backtracking — permutations/combinations", "Backtracking", "Generating power sets, permutations, and subsets without duplicates.", "3", "O(N * N!)", "O(N)", "RECURSION"},
            {"Divide and conquer paradigm", "Algorithmic Paradigms", "Dividing into subproblems, conquering recursively, and combining.", "3", "O(N log N)", "O(log N)", "RECURSION"},
            {"Master theorem", "Complexity", "Asymptotic formula for divide-and-conquer recurrence relations.", "3", "O(1) evaluation", "O(1)", "ARRAY"},
            {"Advanced binary search (search in rotated array)", "Binary Search", "Pivoted binary search with modified boundary tests.", "3", "O(log N)", "O(1)", "ARRAY"},
            {"Sliding window maximum", "Queue", "Maintaining max element in sliding window using monotonic deque.", "4", "O(N)", "O(K)", "ARRAY"},
            {"Monotonic queue", "Queue", "Deque maintaining elements in monotonic order for O(1) range queries.", "4", "O(1) amortized", "O(K)", "ARRAY"},
            {"String matching (KMP)", "Strings", "Knuth-Morris-Pratt string matching using Longest Prefix Suffix (LPS).", "4", "O(N + M)", "O(M)", "ARRAY"},
            {"Rabin-Karp algorithm", "Strings", "Rolling hash based substring search with false positive validation.", "4", "O(N + M) avg", "O(1)", "ARRAY"},
            {"Z-algorithm", "Strings", "Linear time string matching using Z-array prefix comparisons.", "4", "O(N + M)", "O(N + M)", "ARRAY"},
            {"Suffix array intro", "Strings", "Sorted array of all string suffixes for fast pattern matching.", "5", "O(N log N)", "O(N)", "ARRAY"},
            {"A* search algorithm", "Graph", "Heuristic graph pathfinding combining Dijkstra with Euclidean distance.", "4", "O(E)", "O(V)", "TREE"},
            {"Design patterns — Singleton", "Design Patterns", "Single instance guarantee with global access point in Java.", "2", "O(1)", "O(1)", "ARRAY"},
            {"Design patterns — Factory", "Design Patterns", "Encapsulating object creation behind parameterized interfaces.", "2", "O(1)", "O(1)", "ARRAY"},
            {"Design patterns — Observer", "Design Patterns", "Publish-subscribe event broadcasting pattern in JavaFX.", "3", "O(Observers)", "O(Observers)", "ARRAY"},
            {"Design patterns — Strategy", "Design Patterns", "Interchangeable algorithm families encapsulated in classes.", "3", "O(1)", "O(1)", "ARRAY"},
            {"Design patterns — Builder", "Design Patterns", "Fluent step-by-step object construction API.", "2", "O(1)", "O(1)", "ARRAY"},
            {"System design basics — scalability", "System Design", "Horizontal vs vertical scaling, load balancers, and stateless services.", "3", "Theoretical", "Theoretical", "ARRAY"},
            {"System design basics — caching", "System Design", "Write-through, write-back, CDN, and distributed Redis caches.", "3", "O(1) cache hit", "O(Storage)", "ARRAY"},
            {"LFU cache design", "System Design", "Least Frequently Used cache eviction using frequency lists + maps.", "5", "O(1) get/put", "O(Capacity)", "LINKED_LIST"},
            {"Concurrency basics for DSA (thread-safe structures)", "Concurrency", "Synchronized blocks, ReentrantLock, and volatile memory visibility.", "4", "O(1) lock ops", "O(1)", "ARRAY"},
            {"Bitmask DP (Travelling Salesperson)", "Dynamic Programming", "Solving NP-hard problems over small N<=20 using bitmask states.", "5", "O(2^N * N^2)", "O(2^N * N)", "RECURSION"},
            {"Digit DP", "Dynamic Programming", "Counting numbers satisfying properties across bounded digit ranges.", "5", "O(Digits * States)", "O(Digits * States)", "RECURSION"},
            {"Heavy-Light Decomposition", "Advanced Tree", "Decomposing trees into vertex-disjoint paths for range queries.", "5", "O(log^2 N)", "O(N)", "TREE"},
            {"Centroid Decomposition", "Advanced Tree", "Divide-and-conquer on trees by finding centroid nodes.", "5", "O(N log N)", "O(N)", "TREE"}
        };

        for (String[] row : advancedSeed) {
            Topic t = createTopicFromRow(row, Track.ADVANCED, globalSeq++);
            addTopic(t);
        }
    }

    private Topic createTopicFromRow(String[] row, Track track, int seq) {
        String name = row[0];
        String tag = row[1];
        String summary = row[2];
        int diff = Integer.parseInt(row[3]);
        String timeComp = row[4];
        String spaceComp = row[5];
        String id = "topic_" + track.name().toLowerCase() + "_" + seq;

        String explanation = generateExplanation(name, tag, summary, timeComp, spaceComp);
        String codeExample = generateCodeExample(name, tag);
        List<QuizQuestion> quiz = generateQuizQuestions(id, name, timeComp, spaceComp, tag);

        TopicFactory factory = TopicFactory.getFactory(track);
        Topic topic = factory.createTopic(id, seq, name, diff, tag, summary, explanation, 
                                         codeExample, timeComp, spaceComp, quiz);
        return topic;
    }

    private String generateExplanation(String name, String tag, String summary, String timeComp, String spaceComp) {
        return """
            ### 🍬 Concept Deep-Dive: %s
            
            %s
            
            #### 🎯 Key Principles:
            - **Domain & Classification:** `%s`
            - **Asymptotic Time Complexity:** `%s`
            - **Auxiliary Space Complexity:** `%s`
            
            #### 💡 Fruity Intuition:
            Imagine a pack of assorted fruit candies. To master **%s**, observe how each step transforms the state predictably. 
            Whether traversing, partitioning, or memoizing previous results, optimizing operations ensures you use minimum candy moves (time) and fit everything inside your candy jar (memory).
            
            #### 🚀 Real-World Applications:
            - High-frequency trade matching engines & database indexes.
            - Autocomplete search queries and predictive text algorithms.
            - Pathfinding in maps and network packet routing.
            """.formatted(name, summary, tag, timeComp, spaceComp, name);
    }

    private String generateCodeExample(String name, String tag) {
        return """
            public class %sDemo {
                // Java implementation for %s
                public static void main(String[] args) {
                    System.out.println("🍬 Candy Quest: Executing %s");
                    int[] candyScores = {12, 45, 19, 88, 33};
                    processCandy(candyScores);
                }

                public static void processCandy(int[] data) {
                    // Core algorithm logic for %s
                    int total = 0;
                    for (int chew : data) {
                        total += chew;
                    }
                    System.out.println("Processed total candy score: " + total);
                }
            }
            """.formatted(
                name.replaceAll("[^a-zA-Z0-9]", ""),
                name,
                name,
                tag
            );
    }

    private List<QuizQuestion> generateQuizQuestions(String topicId, String name, String timeComp, String spaceComp, String tag) {
        List<QuizQuestion> questions = new ArrayList<>();

        // Question 1: MCQ Concept
        questions.add(new QuizQuestion(
            topicId + "_q1",
            QuizQuestionType.MCQ,
            "What is the primary operational advantage of " + name + " in Java DSA?",
            null,
            List.of(
                "Guarantees optimal step scaling for " + tag + " workflows",
                "Consumes infinite stack memory without garbage collection",
                "Requires zero memory overhead regardless of input size",
                "Only works on primitive types without generic wrappers"
            ),
            0,
            name + " is engineered to optimize problem resolution under the " + tag + " paradigm.",
            20
        ));

        // Question 2: Complexity Question
        questions.add(new QuizQuestion(
            topicId + "_q2",
            QuizQuestionType.COMPLEXITY,
            "What is the asymptotic time complexity for " + name + "?",
            null,
            List.of(
                "O(1)",
                timeComp,
                "O(N!)",
                "O(2^N)"
            ),
            1,
            "The standard evaluated time complexity for " + name + " is " + timeComp + ".",
            25
        ));

        // Question 3: Code Trace Question
        questions.add(new QuizQuestion(
            topicId + "_q3",
            QuizQuestionType.CODE_TRACE,
            "What will be the output of running this " + name + " snippet?",
            """
            int[] chews = {5, 15, 25};
            int drops = 0;
            for (int c : chews) {
                if (c > 10) drops += c;
            }
            System.out.print(drops);
            """,
            List.of(
                "40",
                "45",
                "25",
                "0"
            ),
            0,
            "15 + 25 = 40 (since 5 is not greater than 10).",
            30
        ));

        return questions;
    }
}

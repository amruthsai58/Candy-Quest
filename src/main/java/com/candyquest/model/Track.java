package com.candyquest.model;

/**
 * Represents the 4 Flavor Tracks of Candy Quest, mapping directly to DSA domains.
 * The 4 tracks mirror the 4 classic fruit chew candy flavors.
 */
public enum Track {
    FOUNDATIONS(
        "Strawberry Track",
        "Foundations",
        "🍓",
        "#E63946",
        "#FFF0F3",
        "#C9184A",
        "Master foundational building blocks: Arrays, Strings, Recursion, Bit Manipulation, and Complexity Analysis."
    ),
    LINEAR(
        "Orange Track",
        "Linear Structures",
        "🍊",
        "#FB8500",
        "#FFF8ED",
        "#D46B08",
        "Traverse linked lists, stacks, queues, hash maps, heaps, and monotonic patterns with tangy efficiency."
    ),
    TREES_GRAPHS(
        "Grape Track",
        "Trees & Graphs",
        "🍇",
        "#7209B7",
        "#F6EEFC",
        "#560BAD",
        "Climb binary trees, tries, AVL trees, graphs, shortest path algorithms, and network flows."
    ),
    ADVANCED(
        "Watermelon Track",
        "Advanced Algorithms & Design",
        "🍉",
        "#2EC4B6",
        "#E6FCF8",
        "#0F9F90",
        "Unleash Dynamic Programming, Greedy heuristics, Backtracking, System Design, and Concurrency."
    );

    private final String flavorName;
    private final String domainTitle;
    private final String emoji;
    private final String primaryColor;
    private final String lightBackgroundColor;
    private final String darkAccentColor;
    private final String description;

    Track(String flavorName, String domainTitle, String emoji, String primaryColor, 
          String lightBackgroundColor, String darkAccentColor, String description) {
        this.flavorName = flavorName;
        this.domainTitle = domainTitle;
        this.emoji = emoji;
        this.primaryColor = primaryColor;
        this.lightBackgroundColor = lightBackgroundColor;
        this.darkAccentColor = darkAccentColor;
        this.description = description;
    }

    public String getFlavorName() {
        return flavorName;
    }

    public String getDomainTitle() {
        return domainTitle;
    }

    public String getDisplayName() {
        return emoji + " " + flavorName + " (" + domainTitle + ")";
    }

    public String getEmoji() {
        return emoji;
    }

    public String getPrimaryColor() {
        return primaryColor;
    }

    public String getLightBackgroundColor() {
        return lightBackgroundColor;
    }

    public String getDarkAccentColor() {
        return darkAccentColor;
    }

    public String getDescription() {
        return description;
    }
}

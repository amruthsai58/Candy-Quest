package com.candyquest.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Domain entity representing a Data Structures & Algorithms topic.
 */
public class Topic {
    private String id;
    private int sequenceNumber;
    private String name;
    private Track track;
    private int difficulty; // 1 to 5
    private String tag;     // e.g. "Sorting", "Tree", "Graph", "DP"
    private String summary;
    private String explanation;
    private String javaCodeExample;
    private String timeComplexity;
    private String spaceComplexity;
    private String visualizerType; // e.g., "ARRAY", "LINKED_LIST", "TREE", "RECURSION", "NONE"
    private List<QuizQuestion> quizQuestions;
    private boolean bookmarked;

    public Topic() {
        this.quizQuestions = new ArrayList<>();
        this.difficulty = 1;
        this.visualizerType = "NONE";
    }

    public Topic(String id, int sequenceNumber, String name, Track track, int difficulty, 
                 String tag, String summary, String explanation, String javaCodeExample, 
                 String timeComplexity, String spaceComplexity, String visualizerType, 
                 List<QuizQuestion> quizQuestions) {
        this.id = id;
        this.sequenceNumber = sequenceNumber;
        this.name = name;
        this.track = track;
        this.difficulty = difficulty;
        this.tag = tag;
        this.summary = summary;
        this.explanation = explanation;
        this.javaCodeExample = javaCodeExample;
        this.timeComplexity = timeComplexity;
        this.spaceComplexity = spaceComplexity;
        this.visualizerType = visualizerType;
        this.quizQuestions = quizQuestions != null ? quizQuestions : new ArrayList<>();
        this.bookmarked = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getJavaCodeExample() {
        return javaCodeExample;
    }

    public void setJavaCodeExample(String javaCodeExample) {
        this.javaCodeExample = javaCodeExample;
    }

    public String getTimeComplexity() {
        return timeComplexity;
    }

    public void setTimeComplexity(String timeComplexity) {
        this.timeComplexity = timeComplexity;
    }

    public String getSpaceComplexity() {
        return spaceComplexity;
    }

    public void setSpaceComplexity(String spaceComplexity) {
        this.spaceComplexity = spaceComplexity;
    }

    public String getVisualizerType() {
        return visualizerType;
    }

    public void setVisualizerType(String visualizerType) {
        this.visualizerType = visualizerType;
    }

    public List<QuizQuestion> getQuizQuestions() {
        return quizQuestions;
    }

    public void setQuizQuestions(List<QuizQuestion> quizQuestions) {
        this.quizQuestions = quizQuestions;
    }

    public boolean isBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    public String getDifficultyStars() {
        return "★".repeat(Math.max(1, Math.min(5, difficulty))) + 
               "☆".repeat(Math.max(0, 5 - Math.max(1, Math.min(5, difficulty))));
    }
}

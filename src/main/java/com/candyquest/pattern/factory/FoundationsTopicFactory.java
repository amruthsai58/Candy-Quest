package com.candyquest.pattern.factory;

import com.candyquest.model.QuizQuestion;
import com.candyquest.model.Topic;
import com.candyquest.model.Track;

import java.util.List;

/**
 * Concrete Factory for Track 1: Foundations (Strawberry).
 * Configures default visualizer to ARRAY or RECURSION based on topic tag.
 */
public class FoundationsTopicFactory extends TopicFactory {
    @Override
    public Topic createTopic(String id, int sequenceNumber, String name, int difficulty, 
                             String tag, String summary, String explanation, 
                             String javaCodeExample, String timeComplexity, 
                             String spaceComplexity, List<QuizQuestion> quizQuestions) {
        String visualizerType = "ARRAY";
        if (tag != null && (tag.equalsIgnoreCase("Recursion") || tag.equalsIgnoreCase("Backtracking"))) {
            visualizerType = "RECURSION";
        }
        return new Topic(id, sequenceNumber, name, Track.FOUNDATIONS, Math.max(1, Math.min(5, difficulty)),
                         tag, summary, explanation, javaCodeExample, timeComplexity, spaceComplexity,
                         visualizerType, quizQuestions);
    }
}

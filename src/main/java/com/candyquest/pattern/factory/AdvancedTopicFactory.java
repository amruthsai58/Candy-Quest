package com.candyquest.pattern.factory;

import com.candyquest.model.QuizQuestion;
import com.candyquest.model.Topic;
import com.candyquest.model.Track;

import java.util.List;

/**
 * Concrete Factory for Track 4: Advanced Algorithms (Watermelon).
 * Configures default visualizer to RECURSION or ARRAY based on DP/Greedy paradigm.
 */
public class AdvancedTopicFactory extends TopicFactory {
    @Override
    public Topic createTopic(String id, int sequenceNumber, String name, int difficulty, 
                             String tag, String summary, String explanation, 
                             String javaCodeExample, String timeComplexity, 
                             String spaceComplexity, List<QuizQuestion> quizQuestions) {
        String visualizerType = "RECURSION";
        if (tag != null && (tag.equalsIgnoreCase("Greedy") || tag.equalsIgnoreCase("Design Patterns") || tag.equalsIgnoreCase("System Design"))) {
            visualizerType = "ARRAY";
        }
        return new Topic(id, sequenceNumber, name, Track.ADVANCED, Math.max(1, Math.min(5, difficulty)),
                         tag, summary, explanation, javaCodeExample, timeComplexity, spaceComplexity,
                         visualizerType, quizQuestions);
    }
}

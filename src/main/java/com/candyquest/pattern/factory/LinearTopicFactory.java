package com.candyquest.pattern.factory;

import com.candyquest.model.QuizQuestion;
import com.candyquest.model.Topic;
import com.candyquest.model.Track;

import java.util.List;

/**
 * Concrete Factory for Track 2: Linear Structures (Orange).
 * Configures default visualizer to LINKED_LIST or ARRAY.
 */
public class LinearTopicFactory extends TopicFactory {
    @Override
    public Topic createTopic(String id, int sequenceNumber, String name, int difficulty, 
                             String tag, String summary, String explanation, 
                             String javaCodeExample, String timeComplexity, 
                             String spaceComplexity, List<QuizQuestion> quizQuestions) {
        String visualizerType = "LINKED_LIST";
        if (tag != null && (tag.equalsIgnoreCase("Stack") || tag.equalsIgnoreCase("Queue") || tag.equalsIgnoreCase("Hashing"))) {
            visualizerType = "ARRAY";
        }
        return new Topic(id, sequenceNumber, name, Track.LINEAR, Math.max(1, Math.min(5, difficulty)),
                         tag, summary, explanation, javaCodeExample, timeComplexity, spaceComplexity,
                         visualizerType, quizQuestions);
    }
}

package com.candyquest.pattern.factory;

import com.candyquest.model.QuizQuestion;
import com.candyquest.model.Topic;
import com.candyquest.model.Track;

import java.util.List;

/**
 * Concrete Factory for Track 3: Trees & Graphs (Grape).
 * Configures default visualizer to TREE.
 */
public class TreeGraphTopicFactory extends TopicFactory {
    @Override
    public Topic createTopic(String id, int sequenceNumber, String name, int difficulty, 
                             String tag, String summary, String explanation, 
                             String javaCodeExample, String timeComplexity, 
                             String spaceComplexity, List<QuizQuestion> quizQuestions) {
        String visualizerType = "TREE";
        return new Topic(id, sequenceNumber, name, Track.TREES_GRAPHS, Math.max(1, Math.min(5, difficulty)),
                         tag, summary, explanation, javaCodeExample, timeComplexity, spaceComplexity,
                         visualizerType, quizQuestions);
    }
}

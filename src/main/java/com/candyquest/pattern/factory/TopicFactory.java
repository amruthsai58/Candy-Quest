package com.candyquest.pattern.factory;

import com.candyquest.model.QuizQuestion;
import com.candyquest.model.Topic;
import com.candyquest.model.Track;

import java.util.List;

/**
 * <h1>Design Pattern: Factory Method (Abstract Creator)</h1>
 * <p>
 * <b>Why chosen:</b> Each DSA track has specialized visualizer requirements, default difficulty ranges,
 * and track-specific color metadata. The Factory Method pattern allows subclasses to instantiate
 * and configure {@link Topic} instances according to the flavor/domain conventions without client code
 * having to know the exact initialization rules.
 * </p>
 */
public abstract class TopicFactory {

    /**
     * Factory Method for constructing a specialized Topic.
     */
    public abstract Topic createTopic(String id, int sequenceNumber, String name, int difficulty,
                                      String tag, String summary, String explanation, 
                                      String javaCodeExample, String timeComplexity, 
                                      String spaceComplexity, List<QuizQuestion> quizQuestions);

    /**
     * Helper factory selector to fetch the appropriate factory instance by Track.
     */
    public static TopicFactory getFactory(Track track) {
        return switch (track) {
            case FOUNDATIONS -> new FoundationsTopicFactory();
            case LINEAR -> new LinearTopicFactory();
            case TREES_GRAPHS -> new TreeGraphTopicFactory();
            case ADVANCED -> new AdvancedTopicFactory();
        };
    }
}

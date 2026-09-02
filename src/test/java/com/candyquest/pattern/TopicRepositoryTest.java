package com.candyquest.pattern;

import com.candyquest.model.Topic;
import com.candyquest.model.Track;
import com.candyquest.repository.TopicRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TopicRepositoryTest {

    @Test
    void testAll150TopicsLoadedAcrossTracks() {
        TopicRepository repo = new TopicRepository();
        List<Topic> allTopics = repo.getAllTopics();

        assertEquals(150, allTopics.size(), "Repository must contain exactly 150 topics");

        List<Topic> track1 = repo.getTopicsForTrack(Track.FOUNDATIONS);
        List<Topic> track2 = repo.getTopicsForTrack(Track.LINEAR);
        List<Topic> track3 = repo.getTopicsForTrack(Track.TREES_GRAPHS);
        List<Topic> track4 = repo.getTopicsForTrack(Track.ADVANCED);

        assertEquals(36, track1.size(), "Foundations track must have 36 topics");
        assertEquals(36, track2.size(), "Linear track must have 36 topics");
        assertEquals(36, track3.size(), "Trees & Graphs track must have 36 topics");
        assertEquals(42, track4.size(), "Advanced track must have 42 topics");

        // Verify each topic has valid non-empty fields and quiz questions
        for (Topic t : allTopics) {
            assertNotNull(t.getId());
            assertNotNull(t.getName());
            assertNotNull(t.getTrack());
            assertTrue(t.getDifficulty() >= 1 && t.getDifficulty() <= 5);
            assertNotNull(t.getTimeComplexity());
            assertNotNull(t.getSpaceComplexity());
            assertFalse(t.getQuizQuestions().isEmpty(), "Topic " + t.getName() + " must have quiz questions");
        }
    }
}

package com.candyquest.service;

import com.candyquest.model.Topic;
import com.candyquest.model.Track;
import com.candyquest.pattern.command.BookmarkToggleCommand;
import com.candyquest.pattern.command.CommandHistory;
import com.candyquest.pattern.command.TopicOpenedCommand;
import com.candyquest.pattern.singleton.AppSessionManager;
import com.candyquest.repository.TopicRepository;
import com.candyquest.repository.UserProgressRepository;

import java.util.List;

/**
 * Service for accessing topics, searching, and managing bookmarks with command execution.
 */
public class TopicService {
    private final TopicRepository topicRepository;
    private final UserProgressRepository progressRepository;
    private final CommandHistory commandHistory;

    public TopicService(TopicRepository topicRepository, 
                        UserProgressRepository progressRepository,
                        CommandHistory commandHistory) {
        this.topicRepository = topicRepository;
        this.progressRepository = progressRepository;
        this.commandHistory = commandHistory;
    }

    public List<Topic> getAllTopics() {
        return topicRepository.getAllTopics();
    }

    public List<Topic> getTopicsForTrack(Track track) {
        return topicRepository.getTopicsForTrack(track);
    }

    public Topic getTopicById(String id) {
        return topicRepository.getTopicById(id);
    }

    public Topic getNextTopic(String currentTopicId) {
        return topicRepository.getNextTopic(currentTopicId);
    }

    public List<Topic> search(String query, Track trackFilter, Integer difficultyFilter) {
        return topicRepository.searchTopics(query, trackFilter, difficultyFilter);
    }

    public void openTopic(Topic topic) {
        if (topic != null) {
            commandHistory.executeCommand(new TopicOpenedCommand(topic));
        }
    }

    public void toggleBookmark(Topic topic) {
        if (topic != null) {
            BookmarkToggleCommand cmd = new BookmarkToggleCommand(topic);
            commandHistory.executeCommand(cmd);
            String userId = AppSessionManager.getInstance().getCurrentUser().getId();
            progressRepository.saveBookmark(userId, topic.getId(), topic.isBookmarked());
        }
    }

    public CommandHistory getCommandHistory() {
        return commandHistory;
    }
}

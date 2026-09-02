package com.candyquest.controller;

import com.candyquest.config.AppConfig;
import com.candyquest.model.UserProfile;
import com.candyquest.pattern.observer.ProgressEvent;
import com.candyquest.pattern.observer.ProgressObserver;
import com.candyquest.pattern.singleton.AppSessionManager;
import com.candyquest.service.*;
import com.candyquest.view.component.ParticleEffectPane;
import com.candyquest.view.util.ViewNavigator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.*;
import javafx.util.Duration;

/**
 * Controller for the Main Application Shell (Header, Navigation, Global Particle FX).
 */
public class MainLayoutController implements ProgressObserver {

    @FXML private BorderPane rootPane;
    @FXML private HBox headerBar;
    @FXML private Label appTitleLabel;
    @FXML private Label taglineLabel;
    @FXML private Label userLevelLabel;
    @FXML private ProgressBar xpProgressBar;
    @FXML private Label xpTextLabel;
    @FXML private Label streakLabel;
    @FXML private Button homeNavButton;
    @FXML private Button searchNavButton;
    @FXML private Button dashboardNavButton;
    @FXML private Button playgroundNavButton;
    @FXML private StackPane contentContainer;
    @FXML private ParticleEffectPane globalParticlePane;

    private final TopicService topicService;
    private final ProgressService progressService;
    private final QuizService quizService;
    private final RewardService rewardService;
    private final MascotService mascotService;

    public MainLayoutController(TopicService topicService, ProgressService progressService,
                                QuizService quizService, RewardService rewardService,
                                MascotService mascotService) {
        this.topicService = topicService;
        this.progressService = progressService;
        this.quizService = quizService;
        this.rewardService = rewardService;
        this.mascotService = mascotService;
    }

    @FXML
    public void initialize() {
        ViewNavigator.setContentContainer(contentContainer);
        AppSessionManager.getInstance().getProgressSubject().registerObserver(this);

        updateHeaderStats();
        setupNavigationButtons();

        // Navigate to Home View initially
        showHomeView();
    }

    private void updateHeaderStats() {
        UserProfile user = AppSessionManager.getInstance().getCurrentUser();
        userLevelLabel.setText("Lvl " + user.getLevel());
        
        int currentLevelXp = user.getXpInCurrentLevel();
        double progressRatio = currentLevelXp / 100.0;
        xpProgressBar.setProgress(progressRatio);
        xpTextLabel.setText(user.getTotalXp() + " XP (" + currentLevelXp + "/100)");

        streakLabel.setText("🔥 " + user.getStreakDays() + "d");
    }

    private void setupNavigationButtons() {
        homeNavButton.setOnAction(e -> showHomeView());
        searchNavButton.setOnAction(e -> showSearchView());
        dashboardNavButton.setOnAction(e -> showDashboardView());
        playgroundNavButton.setOnAction(e -> showPlaygroundView());
    }

    public void showHomeView() {
        HomeController home = new HomeController(topicService, progressService, mascotService, this);
        ViewNavigator.navigateTo(home.getView());
    }

    public void showTrackMapView(com.candyquest.model.Track track) {
        AppSessionManager.getInstance().setActiveTrack(track);
        TrackMapController trackMap = new TrackMapController(track, topicService, progressService, mascotService, this);
        ViewNavigator.navigateTo(trackMap.getView());
    }

    public void showTopicDetailView(com.candyquest.model.Topic topic) {
        TopicDetailController detail = new TopicDetailController(topic, topicService, progressService, quizService, mascotService, this);
        ViewNavigator.navigateTo(detail.getView());
    }

    public void showQuizView(com.candyquest.model.Topic topic) {
        QuizController quiz = new QuizController(topic, topicService, quizService, progressService, mascotService, this);
        ViewNavigator.navigateTo(quiz.getView());
    }

    public void showDashboardView() {
        DashboardController dashboard = new DashboardController(topicService, progressService, rewardService, this);
        ViewNavigator.navigateTo(dashboard.getView());
    }

    public void showSearchView() {
        SearchController search = new SearchController(topicService, progressService, this);
        ViewNavigator.navigateTo(search.getView());
    }

    public void showPlaygroundView() {
        CodePlaygroundController playground = new CodePlaygroundController(this);
        ViewNavigator.navigateTo(playground.getView());
    }

    public void showRewardModal(com.candyquest.model.ToyReward toy) {
        RewardModalController modal = new RewardModalController(toy, this);
        ViewNavigator.navigateTo(modal.getView());
    }

    public ParticleEffectPane getGlobalParticlePane() {
        return globalParticlePane;
    }

    @Override
    public void onProgressUpdated(ProgressEvent event) {
        Platform.runLater(() -> {
            updateHeaderStats();
            if (event.getType() == ProgressEvent.EventType.TOPIC_COMPLETED || 
                event.getType() == ProgressEvent.EventType.XP_GAINED) {
                if (globalParticlePane != null) {
                    globalParticlePane.spawnFallingCandyRain(15);
                }
            } else if (event.getType() == ProgressEvent.EventType.TOY_UNLOCKED) {
                if (globalParticlePane != null) {
                    globalParticlePane.spawnCandyBurst(450, 300, 50);
                }
                showRewardModal(event.getToyReward());
            }
        });
    }
}

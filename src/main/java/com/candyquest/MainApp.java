package com.candyquest;

import com.candyquest.config.AppConfig;
import com.candyquest.controller.MainLayoutController;
import com.candyquest.pattern.command.CommandHistory;
import com.candyquest.pattern.singleton.AppSessionManager;
import com.candyquest.repository.BadgeRepository;
import com.candyquest.repository.TopicRepository;
import com.candyquest.repository.ToyRewardRepository;
import com.candyquest.repository.UserProgressRepository;
import com.candyquest.service.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.InputStream;

/**
 * Main Application Entry Point for Candy Quest JavaFX Desktop GUI.
 */
public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // 1. Initialize Repositories
            TopicRepository topicRepository = new TopicRepository();
            UserProgressRepository progressRepository = new UserProgressRepository();
            BadgeRepository badgeRepository = new BadgeRepository();
            ToyRewardRepository toyRewardRepository = new ToyRewardRepository();

            // 2. Initialize Session
            AppSessionManager session = AppSessionManager.getInstance();
            session.setCurrentUser(progressRepository.loadUserProfile("user_default"));
            session.setCurrentProgress(progressRepository.loadUserProgress("user_default"));

            // 3. Initialize Services
            CommandHistory commandHistory = new CommandHistory();
            TopicService topicService = new TopicService(topicRepository, progressRepository, commandHistory);
            ProgressService progressService = new ProgressService(topicRepository, progressRepository, badgeRepository, toyRewardRepository);
            QuizService quizService = new QuizService(commandHistory);
            RewardService rewardService = new RewardService(badgeRepository, toyRewardRepository);
            MascotService mascotService = new MascotService(commandHistory);

            // 4. Load Main Layout FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/candyquest/fxml/MainLayout.fxml"));
            MainLayoutController mainController = new MainLayoutController(
                topicService, progressService, quizService, rewardService, mascotService
            );
            loader.setController(mainController);

            Parent root = loader.load();
            Scene scene = new Scene(root, 1150, 780);

            primaryStage.setTitle(AppConfig.APP_NAME + " — " + AppConfig.APP_TAGLINE);
            primaryStage.setMinWidth(960);
            primaryStage.setMinHeight(640);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

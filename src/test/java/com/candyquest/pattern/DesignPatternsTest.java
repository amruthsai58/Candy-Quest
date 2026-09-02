package com.candyquest.pattern;

import com.candyquest.model.*;
import com.candyquest.pattern.builder.QuizBuilder;
import com.candyquest.pattern.command.*;
import com.candyquest.pattern.decorator.*;
import com.candyquest.pattern.factory.TopicFactory;
import com.candyquest.pattern.observer.*;
import com.candyquest.pattern.singleton.AppSessionManager;
import com.candyquest.pattern.state.*;
import com.candyquest.pattern.strategy.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

public class DesignPatternsTest {

    @BeforeEach
    void setUp() {
        AppSessionManager.getInstance().reset();
    }

    // 1. Singleton Pattern Test
    @Test
    void testSingletonInstanceIdentity() {
        AppSessionManager instance1 = AppSessionManager.getInstance();
        AppSessionManager instance2 = AppSessionManager.getInstance();
        assertSame(instance1, instance2, "AppSessionManager must always return the identical singleton instance");
    }

    // 2. Factory Method Pattern Test
    @Test
    void testTopicFactoryCreation() {
        TopicFactory strawberryFactory = TopicFactory.getFactory(Track.FOUNDATIONS);
        Topic foundationsTopic = strawberryFactory.createTopic(
            "test_1", 1, "Array Basics", 1, "Arrays", "Summary", "Exp", "Code", "O(1)", "O(N)", List.of()
        );
        assertEquals(Track.FOUNDATIONS, foundationsTopic.getTrack());
        assertEquals("ARRAY", foundationsTopic.getVisualizerType());

        TopicFactory grapeFactory = TopicFactory.getFactory(Track.TREES_GRAPHS);
        Topic treeTopic = grapeFactory.createTopic(
            "test_2", 2, "BST Search", 2, "Tree", "Summary", "Exp", "Code", "O(log N)", "O(Height)", List.of()
        );
        assertEquals(Track.TREES_GRAPHS, treeTopic.getTrack());
        assertEquals("TREE", treeTopic.getVisualizerType());
    }

    // 3. Observer Pattern Test
    @Test
    void testObserverNotification() {
        ProgressSubject subject = new ProgressSubject();
        AtomicBoolean notified = new AtomicBoolean(false);

        ProgressObserver observer = event -> {
            if (event.getType() == ProgressEvent.EventType.XP_GAINED && event.getXpAdded() == 50) {
                notified.set(true);
            }
        };

        subject.registerObserver(observer);
        subject.notifyObservers(new ProgressEvent.Builder(ProgressEvent.EventType.XP_GAINED).xpAdded(50).build());

        assertTrue(notified.get(), "Observer should be notified when XP is gained");
    }

    // 4. Strategy Pattern Test
    @Test
    void testQuizGradingStrategies() {
        QuizQuestion mcq = new QuizQuestion("q1", QuizQuestionType.MCQ, "What is O(1)?", null, List.of("Constant", "Linear"), 0, "Exp", 20);
        QuizGradingStrategy mcqStrategy = GradingStrategyFactory.getStrategy(QuizQuestionType.MCQ);
        QuizGradingStrategy.GradingResult mcqResult = mcqStrategy.grade(mcq, 0, 5);
        assertTrue(mcqResult.isCorrect());
        assertTrue(mcqResult.xpEarned() >= 20);

        QuizQuestion codeTrace = new QuizQuestion("q2", QuizQuestionType.CODE_TRACE, "Trace output", "int a = 5;", List.of("5", "10"), 0, "Exp", 20);
        QuizGradingStrategy codeStrategy = GradingStrategyFactory.getStrategy(QuizQuestionType.CODE_TRACE);
        QuizGradingStrategy.GradingResult codeResult = codeStrategy.grade(codeTrace, 0, 10);
        assertTrue(codeResult.isCorrect());
        assertEquals(30, codeResult.xpEarned()); // 1.5x base for code trace
    }

    // 5. State Pattern Test
    @Test
    void testTopicStateTransitions() {
        TopicStateContext context = new TopicStateContext("test_topic", false, false, 0);
        assertInstanceOf(LockedState.class, context.getCurrentState());
        assertFalse(context.getCurrentState().canOpen());

        // Unlock topic
        context.unlock();
        assertInstanceOf(InProgressState.class, context.getCurrentState());
        assertTrue(context.getCurrentState().canOpen());

        // Pass with 70% -> Moves to Completed
        int xp = context.onQuizCompleted(70);
        assertTrue(xp > 0);
        assertInstanceOf(CompletedState.class, context.getCurrentState());

        // Retake with 100% -> Moves to Mastered
        context.onQuizCompleted(100);
        assertInstanceOf(MasteredState.class, context.getCurrentState());
    }

    // 6. Decorator Pattern Test
    @Test
    void testBadgeDecoratorStacking() {
        Badge baseBadge = new Badge("badge_test", "Master", "Completed tracks", "🏆", null, 10);
        baseBadge.setUnlocked(true);

        BadgeComponent comp = new BaseBadgeComponent(baseBadge);
        assertEquals("Master", comp.getDisplayName());
        assertEquals(1, comp.getPrestigeLevel());

        // Stack Sparkle Decorator
        comp = new SparkleBadgeDecorator(comp);
        assertTrue(comp.getDisplayName().contains("✨"));
        assertEquals(2, comp.getPrestigeLevel());

        // Stack Golden Glow Decorator
        comp = new GlowBadgeDecorator(comp);
        assertTrue(comp.getDisplayName().contains("🌟"));
        assertEquals(4, comp.getPrestigeLevel());

        // Stack Toy Ribbon Decorator
        comp = new ToyUnlockedBadgeDecorator(comp, "Pixel Roo");
        assertTrue(comp.getDisplayName().contains("Pixel Roo"));
        assertEquals(7, comp.getPrestigeLevel());
    }

    // 7. Command Pattern Test
    @Test
    void testCommandHistoryExecutionAndUndo() {
        CommandHistory history = new CommandHistory();
        Topic topic = new Topic("top_1", 1, "Binary Search", Track.FOUNDATIONS, 2, "Search", "Sum", "Exp", "Code", "O(log N)", "O(1)", "ARRAY", List.of());

        assertFalse(topic.isBookmarked());
        BookmarkToggleCommand bookmarkCmd = new BookmarkToggleCommand(topic);

        history.executeCommand(bookmarkCmd);
        assertTrue(topic.isBookmarked(), "Executing command must toggle bookmark to true");
        assertTrue(history.canUndo());

        history.undo();
        assertFalse(topic.isBookmarked(), "Undoing command must revert bookmark to false");
        assertTrue(history.canRedo());

        history.redo();
        assertTrue(topic.isBookmarked(), "Redoing command must re-apply bookmark");
    }

    // 8. Builder Pattern Test
    @Test
    void testQuizBuilderFluentAssembly() {
        QuizQuestion q1 = new QuizQuestion("q1", QuizQuestionType.MCQ, "Question 1", null, List.of("A", "B"), 0, "Exp", 15);
        QuizQuestion q2 = new QuizQuestion("q2", QuizQuestionType.COMPLEXITY, "Question 2", null, List.of("O(1)", "O(N)"), 0, "Exp", 25);

        QuizBuilder.QuizSession session = new QuizBuilder()
            .withTitle("Strawberry Speed Exam")
            .forTrack(Track.FOUNDATIONS)
            .withDifficulty(2)
            .withTimeLimitSeconds(60)
            .addQuestion(q1)
            .addQuestion(q2)
            .shuffle(false)
            .build();

        assertEquals("Strawberry Speed Exam", session.title());
        assertEquals(Track.FOUNDATIONS, session.track());
        assertEquals(2, session.questions().size());
        assertEquals(40, session.getTotalPossibleXp());
    }
}

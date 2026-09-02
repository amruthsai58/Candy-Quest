package com.candyquest.service;

import com.candyquest.model.Topic;
import com.candyquest.pattern.command.CommandHistory;
import com.candyquest.pattern.command.HintRequestedCommand;

import java.util.List;
import java.util.Random;

/**
 * Service managing "Candy Roo" Kangaroo Mascot interactions, speech bubbles,
 * contextual hints, and celebration animations.
 */
public class MascotService {
    private final CommandHistory commandHistory;
    private final Random random = new Random();

    public enum MascotMood {
        IDLE_BOUNCE,
        CHEERING_CLAP,
        CONFUSED_SHRUG,
        POINTING_HINT,
        WAVING_HELLO
    }

    public MascotService(CommandHistory commandHistory) {
        this.commandHistory = commandHistory;
    }

    public String getWelcomeGreeting(String username) {
        List<String> greetings = List.of(
            "G'day " + username + "! Ready for a fruity blast of DSA puzzles?",
            "Hop in, " + username + "! Which candy track are we conquering today?",
            "Welcome back! Keep your streak alive and grab that Free Toy Inside!"
        );
        return greetings.get(random.nextInt(greetings.size()));
    }

    public String getCelebrationMessage(int xpEarned) {
        List<String> shouts = List.of(
            "Boom! That was sweet! +" + xpEarned + " XP in the jar!",
            "Crikey! Look at those candy drops fly! +" + xpEarned + " XP!",
            "Flawless hop! You're crushing these algorithms! +" + xpEarned + " XP!"
        );
        return shouts.get(random.nextInt(shouts.size()));
    }

    public String getEncouragingMessage() {
        List<String> encouragements = List.of(
            "No worries! Even the best roos stumble before leaping!",
            "Take another bite of the logic! You'll get it next try!",
            "Check the time & space complexity hints and try again!"
        );
        return encouragements.get(random.nextInt(encouragements.size()));
    }

    public String requestTopicHint(Topic topic) {
        if (topic == null) return "Focus on the base cases and data invariants!";
        String hint = "💡 Roo's Chew Hint: Look at the " + topic.getTag() + " rules. " +
                      "Remember that optimal time for this is " + topic.getTimeComplexity() + "!";
        
        commandHistory.executeCommand(new HintRequestedCommand(topic.getId(), hint));
        return hint;
    }
}

package com.candyquest.pattern.command;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * <h1>Design Pattern: Command (Invoker / History Manager)</h1>
 * <p>
 * Maintains history stacks for executing, undoing, and inspecting recent user actions.
 * </p>
 */
public class CommandHistory {
    private final Deque<UserActionCommand> undoStack = new ArrayDeque<>();
    private final Deque<UserActionCommand> redoStack = new ArrayDeque<>();
    private final List<UserActionCommand> fullActionLog = new ArrayList<>();
    private static final int MAX_HISTORY_SIZE = 100;

    public void executeCommand(UserActionCommand command) {
        if (command == null) return;
        command.execute();
        undoStack.push(command);
        redoStack.clear();
        fullActionLog.add(command);
        if (undoStack.size() > MAX_HISTORY_SIZE) {
            undoStack.removeLast();
        }
    }

    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    public boolean canRedo() {
        return !redoStack.isEmpty();
    }

    public UserActionCommand undo() {
        if (!undoStack.isEmpty()) {
            UserActionCommand command = undoStack.pop();
            command.undo();
            redoStack.push(command);
            return command;
        }
        return null;
    }

    public UserActionCommand redo() {
        if (!redoStack.isEmpty()) {
            UserActionCommand command = redoStack.pop();
            command.execute();
            undoStack.push(command);
            return command;
        }
        return null;
    }

    public List<UserActionCommand> getFullActionLog() {
        return new ArrayList<>(fullActionLog);
    }
}

package com.candyquest.repository;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Manages SQLite database connection lifecycle and schema migrations.
 */
public class DatabaseManager {
    private static final String DB_DIR = System.getProperty("user.home") + File.separator + ".candyquest";
    private static final String DB_URL = "jdbc:sqlite:" + DB_DIR + File.separator + "candyquest.db";
    private static Connection connection;

    public static synchronized Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                File dir = new File(DB_DIR);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                connection = DriverManager.getConnection(DB_URL);
                initSchema(connection);
            }
        } catch (SQLException e) {
            System.err.println("DatabaseManager: SQLite file connection failed, falling back to in-memory DB: " + e.getMessage());
            try {
                if (connection == null || connection.isClosed()) {
                    connection = DriverManager.getConnection("jdbc:sqlite::memory:");
                    initSchema(connection);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return connection;
    }

    private static void initSchema(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            // User Profile table
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS user_profiles (
                    id TEXT PRIMARY KEY,
                    username TEXT NOT NULL,
                    avatar_skin TEXT DEFAULT 'classic_roo',
                    total_xp INTEGER DEFAULT 0,
                    streak_days INTEGER DEFAULT 1,
                    last_active TEXT
                );
            """);

            // Topic Progress table
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS topic_progress (
                    user_id TEXT,
                    topic_id TEXT,
                    completed INTEGER DEFAULT 0,
                    best_score INTEGER DEFAULT 0,
                    bookmarked INTEGER DEFAULT 0,
                    updated_at TEXT,
                    PRIMARY KEY (user_id, topic_id)
                );
            """);

            // Badges table
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS user_badges (
                    user_id TEXT,
                    badge_id TEXT,
                    unlocked INTEGER DEFAULT 0,
                    unlocked_at TEXT,
                    PRIMARY KEY (user_id, badge_id)
                );
            """);

            // Claimed Toys table
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS claimed_toys (
                    user_id TEXT,
                    toy_id TEXT,
                    claimed_at TEXT,
                    PRIMARY KEY (user_id, toy_id)
                );
            """);
        }
    }
}

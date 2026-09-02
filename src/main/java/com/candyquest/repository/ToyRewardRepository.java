package com.candyquest.repository;

import com.candyquest.model.ToyReward;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Repository for the "Free Toy Inside" reward catalog unlocked every 10 topics.
 */
public class ToyRewardRepository {
    private final Map<String, ToyReward> toyCatalog = new ConcurrentHashMap<>();

    public ToyRewardRepository() {
        initToyCatalog();
    }

    private void initToyCatalog() {
        add(new ToyReward("toy_10", 10, "Retro Pixel Roo", "Mascot Skin", 
            "Unlock the 8-bit retro gaming sprite for Candy Roo!", "👾", "PIXEL_BOUNCE"));
        add(new ToyReward("toy_20", 20, "Golden Lollipop Wand", "Accessory", 
            "A shimmering candy wand that makes Roo cast code sparkles!", "🍭", "SPARKLE_BURST"));
        add(new ToyReward("toy_30", 30, "Ninja Roo Headband", "Mascot Skin", 
            "Roo dons a ninja headband for swift O(1) executions!", "🥷", "NINJA_SLASH"));
        add(new ToyReward("toy_40", 40, "Gummy Bear Minion", "Pet Follower", 
            "A mini bouncy green gummy bear that cheers you on!", "🧸", "GUMMY_WIGGLE"));
        add(new ToyReward("toy_50", 50, "Wizard Candy Hat", "Mascot Skin", 
            "Enchants your graph & tree traversals with purple magic!", "🧙", "MAGIC_AURA"));
        add(new ToyReward("toy_60", 60, "Disco Candy Ball", "Easter Egg Toy", 
            "Transforms the track map into a retro disco dance party!", "🪩", "DISCO_FLASH"));
        add(new ToyReward("toy_70", 70, "Cyberpunk Roo", "Mascot Skin", 
            "Neon visor and futuristic candy glow for high-tech recursion!", "🕶️", "NEON_PULSE"));
        add(new ToyReward("toy_80", 80, "Bubblegum Jetpack", "Accessory", 
            "Roo flies between topic nodes with pink bubble propulsion!", "🚀", "JETPACK_FLY"));
        add(new ToyReward("toy_90", 90, "Golden Chew Trophy", "Physical Toy", 
            "A 24-karat edible golden trophy for your trophy shelf!", "🏆", "GOLD_SHINE"));
        add(new ToyReward("toy_100", 100, "King Roo Royal Crown", "Mascot Skin", 
            "Crown fit for a Centurion who conquered 100 DSA topics!", "👑", "CROWN_FLOAT"));
        add(new ToyReward("toy_110", 110, "Sour Blast Fireworks", "Screen FX", 
            "Explodes sour crystal fireworks on correct answers!", "🎆", "FIREWORK_BURST"));
        add(new ToyReward("toy_120", 120, "Astronaut Roo Suit", "Mascot Skin", 
            "Spacewalk across complex dynamic programming grids!", "👨‍🚀", "SPACE_FLOAT"));
        add(new ToyReward("toy_130", 130, "Candy Dragon Companion", "Pet Follower", 
            "A friendly mini candy dragon breathing peppermint sparks!", "🐉", "DRAGON_ROAR"));
        add(new ToyReward("toy_140", 140, "Infinity Gum Gauntlet", "Accessory", 
            "Harness all 4 candy track powers into one snap!", "🥊", "INFINITY_SNAP"));
        add(new ToyReward("toy_150", 150, "Grand Confectioner Halo", "Ultimate Reward", 
            "The supreme ascended halo for mastering all 150 DSA topics!", "🌌", "COSMIC_GLOW"));
    }

    private void add(ToyReward toy) {
        toyCatalog.put(toy.getId(), toy);
    }

    public List<ToyReward> getAllToys() {
        return new ArrayList<>(toyCatalog.values());
    }

    public ToyReward getToyById(String id) {
        return toyCatalog.get(id);
    }

    public ToyReward getToyForMilestone(int completedCount) {
        for (ToyReward toy : toyCatalog.values()) {
            if (toy.getUnlockThreshold() == completedCount) {
                return toy;
            }
        }
        return null;
    }
}

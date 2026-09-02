package com.candyquest.service;

import com.candyquest.pattern.singleton.AppSessionManager;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

/**
 * Service providing synthesized retro-modern candy sound effects (candy drop, sweet pop, level up, buzzer).
 */
public class SoundService {
    private static final SoundService INSTANCE = new SoundService();

    public static SoundService getInstance() {
        return INSTANCE;
    }

    private SoundService() {}

    public void playCandyDrop() {
        if (!AppSessionManager.getInstance().isSoundEnabled()) return;
        playTone(784, 80, 0.4); // G5 note sweet chime
    }

    public void playCorrectSweetPop() {
        if (!AppSessionManager.getInstance().isSoundEnabled()) return;
        new Thread(() -> {
            playTone(523, 60, 0.3); // C5
            playTone(659, 60, 0.4); // E5
            playTone(784, 120, 0.5); // G5
        }).start();
    }

    public void playIncorrectThud() {
        if (!AppSessionManager.getInstance().isSoundEnabled()) return;
        new Thread(() -> {
            playTone(220, 100, 0.3); // A3 low thud
            playTone(180, 120, 0.2);
        }).start();
    }

    public void playLevelUpFanfare() {
        if (!AppSessionManager.getInstance().isSoundEnabled()) return;
        new Thread(() -> {
            int[] notes = {523, 659, 784, 1046}; // C5, E5, G5, C6
            for (int note : notes) {
                playTone(note, 90, 0.5);
                try { Thread.sleep(30); } catch (InterruptedException ignored) {}
            }
        }).start();
    }

    private void playTone(int hz, int msecs, double vol) {
        try {
            float sampleRate = 8000f;
            byte[] buf = new byte[(int) (sampleRate * msecs / 1000)];
            for (int i = 0; i < buf.length; i++) {
                double angle = i / (sampleRate / hz) * 2.0 * Math.PI;
                buf[i] = (byte) (Math.sin(angle) * 127.0 * vol);
            }
            AudioFormat af = new AudioFormat(sampleRate, 8, 1, true, false);
            SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
            sdl.open(af);
            sdl.start();
            sdl.write(buf, 0, buf.length);
            sdl.drain();
            sdl.close();
        } catch (Exception ignored) {
            // Audio hardware might be absent in CI or headless environments
        }
    }
}

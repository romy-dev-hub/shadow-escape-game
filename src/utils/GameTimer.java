package utils;

public class GameTimer {
    private long startTime;
    private boolean running;

    public GameTimer() {
        reset();
    }

    public void start() {
        if (!running) {
            startTime = System.currentTimeMillis();
            running = true;
        }
    }

    public void stop() {
        running = false;
    }

    public void reset() {
        startTime = System.currentTimeMillis();
        running = false;
    }

    public String getElapsedTime() {
        long elapsed = running ? System.currentTimeMillis() - startTime : 0;
        int seconds = (int) (elapsed / 1000) % 60;
        int minutes = (int) (elapsed / (1000 * 60)) % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}

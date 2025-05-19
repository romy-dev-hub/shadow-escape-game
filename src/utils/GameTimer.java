package utils;

public class GameTimer {
    private long startTime;
    private long elapsedTime;
    private boolean running;

    public GameTimer() {
        startTime = 0;
        elapsedTime = 0;
        running = false;
    }

    public void start() {
        if (!running) {
            startTime = System.currentTimeMillis();
            running = true;
        }
    }

    public void stop() {
        if (running) {
            elapsedTime += System.currentTimeMillis() - startTime;
            running = false;
        }
    }

    public String getElapsedTime() {
        long currentElapsed = running ? elapsedTime + (System.currentTimeMillis() - startTime) : elapsedTime;
        long seconds = currentElapsed / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
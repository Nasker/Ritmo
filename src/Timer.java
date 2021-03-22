import processing.core.PApplet;

public class Timer {

    int lastTimeCheck;
    int interval;
    PApplet applet;

    Timer(PApplet applet, int interval) {
        this.applet = applet;
        this.interval = interval;
    }

    void setup() {
        lastTimeCheck = this.applet.millis();
    }

    boolean checkInterval() {
        if (this.applet.millis() > this.lastTimeCheck + this.interval ) {
            this.lastTimeCheck = this.applet.millis();
            return true;
        }
        return false;
    }
}

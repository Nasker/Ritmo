import processing.core.PApplet;

public class Circle {
    int dst2Center;
    int center;
    float posX;
    float posY;
    int[] ccolor;
    boolean circleOver;
    int type;
    int noteNumber;
    PApplet applet;

    Circle(PApplet applet, float posX, float posY, int[] ccolor, int type) {
        this.applet = applet;
        this.posX = posX;
        this.posY = posY;
        this.ccolor = ccolor;
        this.circleOver = false;
        this.type = type;
    }

    void display() {
        this.applet.fill(ccolor[0], ccolor[1], ccolor[2]);
        this.applet.stroke(0);
        this.applet.ellipse(posX, posY, 50, 50);
    }

    void move(float x, float y) {
        this.posX = x;
        this.posY = y;
        this.applet.ellipse(posX, posY, 50, 50);
    }
}

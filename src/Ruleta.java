import processing.core.PApplet;

public class Ruleta {

    float ang = 0;
    float x0, y0, x1, y1;
    PApplet applet;

    Ruleta(PApplet applet) {
        this.applet = applet;
    }

    void draw(float ang) {
        this.ang = ang;
        x0 = (float) (this.applet.width/2.0);
        y0 = (float) (this.applet.height/2.0);
        x1 = (float) ((PApplet.cos(PApplet.radians(this.ang)) * this.applet.height/2) + this.applet.width/2.0);
        y1 = (float) ((PApplet.sin(PApplet.radians(this.ang)) * this.applet.height/2) + this.applet.height/2.0);


        if (this.ang >= 360) this.ang = 0;

        this.applet.stroke(200);
        this.applet.fill(200);
        this.applet.circle((float) (this.applet.width/2.0), (float) (this.applet.height/2.0), this.applet.width - 250);

        this.applet.stroke(255);
        this.applet.strokeWeight(5);
        this.applet.fill(237, 46, 36);
        this.applet.circle((float) (this.applet.width/2.0), (float) (this.applet.height/2.0), 50);

        this.applet.line(x0, y0, x1, y1);
    }
}

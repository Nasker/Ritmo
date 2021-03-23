import processing.core.PApplet;
import processing.core.PVector;

public class Ruleta {
    float ang = 0;
    PVector center = new PVector();
    PVector radius = new PVector();
    PApplet applet;

    Ruleta(PApplet applet) {
        this.applet = applet;
    }

    void draw(float ang) {
        this.ang = ang;
        center.x = (float) (this.applet.width/2.0);
        center.y = (float) (this.applet.height/2.0);
        radius.x = (float) ((PApplet.cos(PApplet.radians(this.ang)) * this.applet.height/2) + this.applet.width/2.0);
        radius.y = (float) ((PApplet.sin(PApplet.radians(this.ang)) * this.applet.height/2) + this.applet.height/2.0);


        if (this.ang >= 360) this.ang = 0;

        this.applet.stroke(200,100);
        this.applet.fill(200,200);
        this.applet.circle((float) (this.applet.width/2.0), (float) (this.applet.height/2.0), this.applet.width - 250);

        this.applet.stroke(255);
        this.applet.strokeWeight(2);
        this.applet.fill(100, 46, 36);
        this.applet.circle((float) (this.applet.width/2.0), (float) (this.applet.height/2.0), 20);
        for(int i = 0; i < 10; i++) {
            this.applet.stroke(255,255-i*40);
            this.applet.line(center.x, center.y,
                    (float) ((PApplet.cos(PApplet.radians(this.ang-i)) * this.applet.height/2) + this.applet.width/2.0),
                    (float) ((PApplet.sin(PApplet.radians(this.ang-i)) * this.applet.height/2) + this.applet.height/2.0)
            );
        }
        this.applet.line(center.x, center.y, radius.x, radius.y);
    }
}

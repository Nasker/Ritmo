import processing.core.PApplet;

public class Element {

    boolean collisionDetected = false;
    float x, y, r;
    int[] ccolor;
    int noteNumber;
    PApplet applet;

    Element(PApplet applet, float x, float y, float r, int[] ccolor, int noteNumber) {
        this.applet = applet;
        this.x = x;
        this.y = y;
        this.r = r;
        this.ccolor = ccolor;
        this.noteNumber = noteNumber;
    }

    void draw() {
        this.applet.noStroke();
        this.applet.fill(ccolor[0], ccolor[1], ccolor[2]);
        this.applet.circle(this.x, this.y, this.r/10);
        int alpha;
        if (this.collisionDetected){
            alpha = 255;
        }
        else{
            alpha = 100;
        }
        this.applet.fill(ccolor[0], ccolor[1], ccolor[2],alpha);
        this.applet.circle(this.x, this.y, this.r);
    }

    boolean isOver(float x, float y){
        return pointCircle(this.x, this.y, x, y, this.r);
    }

    boolean lineCollision(float x0, float y0, float x1, float y1) {

        boolean lineCollisioned = lineCircle(
                x0,
                y0,
                x1,
                y1,
                this.x,
                this.y,
                this.r
        );

        if (lineCollisioned && !this.collisionDetected) {
            this.collisionDetected = true;
            return true;
        }

        if (!lineCollisioned) {
            this.collisionDetected = false;
        }

        return false;
    }
    // LINE/CIRCLE
    boolean lineCircle(float x1, float y1, float x2, float y2, float cx, float cy, float r) {

        // is either end INSIDE the circle?
        // if so, return true immediately
        boolean inside1 = pointCircle(x1, y1, cx, cy, r);
        boolean inside2 = pointCircle(x2, y2, cx, cy, r);
        if (inside1 || inside2) return true;

        // get length of the line
        float distX = x1 - x2;
        float distY = y1 - y2;
        float len = PApplet.sqrt( (distX*distX) + (distY*distY) );

        // get dot product of the line and circle
        float dot = ( ((cx-x1)*(x2-x1)) + ((cy-y1)*(y2-y1)) ) / PApplet.pow(len, 2);

        // find the closest point on the line
        float closestX = x1 + (dot * (x2-x1));
        float closestY = y1 + (dot * (y2-y1));

        // is this point actually on the line segment?
        // if so keep going, but if not, return false
        boolean onSegment = linePoint(x1, y1, x2, y2, closestX, closestY);
        if (!onSegment) return false;

        // optionally, draw a circle at the closest
        // point on the line
        //fill(255,0,0);
        //noStroke();
        //ellipse(closestX, closestY, 20, 20);

        // get distance to closest point
        distX = closestX - cx;
        distY = closestY - cy;
        float distance = PApplet.sqrt( (distX*distX) + (distY*distY) );

        return distance <= r;
    }

    // POINT/CIRCLE
    boolean pointCircle(float px, float py, float cx, float cy, float r) {

        // get distance between the point and circle's center
        // using the Pythagorean Theorem
        float distX = px - cx;
        float distY = py - cy;
        float distance = PApplet.sqrt( (distX*distX) + (distY*distY) );

        // if the distance is less than the circle's
        // radius the point is inside!
        return distance <= r;
    }


    // LINE/POINT
    boolean linePoint(float x1, float y1, float x2, float y2, float px, float py) {

        // get distance from the point to the two ends of the line
        float d1 = PApplet.dist(px, py, x1, y1);
        float d2 = PApplet.dist(px, py, x2, y2);

        // get the length of the line
        float lineLen = PApplet.dist(x1, y1, x2, y2);

        // since floats are so minutely accurate, add
        // a little buffer zone that will give collision
        float buffer = (float) 0.1;    // higher # = less accurate

        // if the two distances are equal to the line's
        // length, the point is on the line!
        // note we use the buffer here to give a range,
        // rather than one #
        return d1 + d2 >= lineLen - buffer && d1 + d2 <= lineLen + buffer;
    }
}
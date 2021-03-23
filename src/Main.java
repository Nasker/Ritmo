import processing.core.PApplet;
import themidibus.*;

import java.util.ArrayList;

public class Main extends PApplet {
    MidiBus myBus;
    Roulette roulette;
    ArrayList<Element> elements;
    Timer timeInterval;
    float ang = 0;
    int bpm = 10;
    int offsetBottom = 10;
    int offsetSide = 60;

    //Circles
    ArrayList<Circle> barCircles = new ArrayList<>();
    ArrayList<Circle> addedCircles = new ArrayList<>();
    int cx, moveX;
    int cy, moveY;
    float easing = (float) 0.05;
    Circle aux;
    boolean toPutCircle = false;
    int lastCircle;

    float giro = 0;

    public void settings(){
        size(800, 1000);
        smooth(10);
    }
    public void setup(){
        myBus = new MidiBus(this, "loopMIDI Port", "loopMIDI Port");
        elements = new ArrayList<>();
        roulette = new Roulette(this);
        timeInterval = new Timer(this,10); //10ms
        timeInterval.setup();

    }

    public void draw() {
        background(100);
        calculateAngle();
        roulette.draw(ang);
        for (Element e : elements) {
            e.draw();
            if (e.lineCollision(roulette.center.x, roulette.center.y, roulette.radius.x, roulette.radius.y)) {
                //COLLISION!!!!
                myBus.sendNoteOn(0, e.noteNumber, 100); // Send a Midi noteOn
                myBus.sendNoteOff(0, e.noteNumber, 0); // Send a Midi noteOn
            }
        }

        drawElements();
    }

    public void calculateAngle() {
        if (timeInterval.checkInterval()) {
            //Time interval 10ms
            ang += (float) bpm;
            if (ang > 360) ang = 0;
        }
    }

    public static void main(String[] args) {
        PApplet.main("Main");
    }

    public void keyPressed() {
        if (key == CODED) {
            if (keyCode == UP) {
                bpm ++;
            } else if (keyCode == DOWN) {
                bpm --;
            }
            println(bpm);
        }
        if (key == 'c')
            elements.clear();
    }

    public void mousePressed() {
        mouseAction();
    }

    public void mouseClicked() { moveElement(); }

    public void addNewElement(float posX, float posY, int[] ccolor, int type) {
        Element newElement = new Element(this, posX, posY, 50, ccolor, getNoteByType(type));
        elements.add(newElement);
    }

    public int getNoteByType(int type) {
        return 36+type;
    }

    public void drawElements() {
        drawBar();
        drawObjects();

        float targetX = mouseX;
        float dx = targetX - moveX;
        moveX += dx * easing;

        float targetY = mouseY;
        float dy = targetY - moveY;
        moveY += dy * easing;
        if (toPutCircle) {
            drawBar();
            drawObjects();
            addedCircles.get(lastCircle).move(moveX, moveY);
            addedCircles.get(lastCircle).display();
        }

        fill(0, 0, 0);
        pushMatrix();
        translate((float) (width/2.0), (float) ((height - (70))/2.0));
        rotate(giro);
        popMatrix();
        rect(0, 0, 280, 20);

        giro = (float) (giro + 0.01);
    }


    public void drawBar() {
        fill(150);
        stroke(0);
        rect((float) (offsetSide/2.0), height - (70 + offsetBottom), width-offsetSide, 70);
    }


    public void drawObjects() {
        //RED
        cx = width/10;
        cy = height - (35 + offsetBottom);
        int[] ccolor = new int[]{255, 0, 0};
        barCircles.add(new Circle(this, cx, cy, ccolor, 0));
        barCircles.get(barCircles.size()-1).display();
        //GREEN
        cx = 2 * width/10;
        cy = height - (35 + offsetBottom);
        ccolor = new int[]{0, 255, 0};
        barCircles.add(new Circle(this, cx, cy, ccolor, 1));
        barCircles.get(barCircles.size()-1).display();
        //BLUE
        cx = 3 * width/10;
        cy = height - (35 + offsetBottom);
        ccolor = new int[]{0, 0, 255};
        barCircles.add(new Circle(this, cx, cy, ccolor, 2));
        barCircles.get(barCircles.size()-1).display();
        cx = 4 * width/10;
        cy = height - (35 + offsetBottom);
        ccolor = new int[]{255, 0, 255};
        barCircles.add(new Circle(this, cx, cy, ccolor, 3));
        barCircles.get(barCircles.size()-1).display();
        cx = 5 * width/10;
        cy = height - (35 + offsetBottom);
        ccolor = new int[]{0, 255, 255};
        barCircles.add(new Circle(this, cx, cy, ccolor, 4));
        barCircles.get(barCircles.size()-1).display();
        cx = 6 * width/10;
        cy = height - (35 + offsetBottom);
        ccolor = new int[]{255, 255, 0};
        barCircles.add(new Circle(this, cx, cy, ccolor, 5));
        barCircles.get(barCircles.size()-1).display();
        //BLUE
        cx = 7 * width/10;
        cy = height - (35 + offsetBottom);
        ccolor = new int[]{255, 255, 255};
        barCircles.add(new Circle(this, cx, cy, ccolor, 6));
        barCircles.get(barCircles.size()-1).display();
        cx = 8 * width/10;
        cy = height - (35 + offsetBottom);
        ccolor = new int[]{255, 127, 127};
        barCircles.add(new Circle(this, cx, cy, ccolor, 7));
        barCircles.get(barCircles.size()-1).display();
        cx = 9 * width/10;
        cy = height - (35 + offsetBottom);
        ccolor = new int[]{127, 255, 127};
        barCircles.add(new Circle(this, cx, cy, ccolor, 8));
        barCircles.get(barCircles.size()-1).display();
    }

    public void mouseAction() {

        if (toPutCircle)
        {
            addedCircles.get(lastCircle).posX = mouseX;
            addedCircles.get(lastCircle).posY = mouseY;
            addNewElement(mouseX, mouseY, addedCircles.get(lastCircle).ccolor, addedCircles.get(lastCircle).type);

            toPutCircle = false;
        } else {

            for (int i = 0; i < barCircles.size(); i++) {
                if (mouseX>=(barCircles.get(i).posX - 25) && mouseY <= (barCircles.get(i).posY+25) && mouseX<=(barCircles.get(i).posX + 25) && mouseY >= (barCircles.get(i).posY-25))
                {
                    aux = new Circle(this, mouseX, mouseY, barCircles.get(i).ccolor, i);
                    addedCircles.add(aux);
                    addedCircles.get(addedCircles.size()-1).display();
                    toPutCircle = true;
                    lastCircle = addedCircles.size()-1;
                    break;
                }
            }
        }
    }

    public void moveElement() {
        elements.removeIf(element -> element.isOver(mouseX, mouseY) && mouseButton == RIGHT);
    }
}

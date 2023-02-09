package ui;

import java.awt.*;
import java.awt.event.MouseEvent;

public class ListTickBox {
    private int element = 0;
    private TickBox[] tickBoxes;
    private int xPos, yPos;
    private int xOffsetCenter;
    private boolean isRow;

    public ListTickBox(int x, int y, int num, int sizeB, int distance, boolean isRow){
        this.xPos = x;
        this.yPos = y;
        tickBoxes = new TickBox[num];
        this.isRow = isRow;
        this.xOffsetCenter = (sizeB+distance)*num/2-distance;
        for(int i=0;i<tickBoxes.length; i++){
            if(isRow)
                tickBoxes[i] = new TickBox(x-xOffsetCenter, y+i*(sizeB+distance), sizeB);
            else
                tickBoxes[i] = new TickBox(x-xOffsetCenter+i*(sizeB+distance), y, sizeB);
        }
    }
    public void mousePressed(MouseEvent e) {
        int i=0;
        for(TickBox mb: tickBoxes){
            if(mb.getBound().contains(e.getX(), e.getY())){
                resetAllTickBox();
                mb.setTick(true);
                element = i;
            }
            i++;
        }
    }
    public void Tick(int element){
        if(element>=0 && element < tickBoxes.length){
            this.element = element;
            resetAllTickBox();
            tickBoxes[element].setTick(true);
        }
    }
    private void resetAllTickBox(){
        for(TickBox mb: tickBoxes){
            mb.setTick(false);
        }
    }
    public void draw(Graphics g){
        for(TickBox mb: tickBoxes){
            mb.draw(g);
        }
    }

    public int getElement() {
        return element;
    }
}

package ui;

import java.awt.*;
import java.awt.event.MouseEvent;

public class LoadSkill {
    private LoadBox[] loadBoxes;
    private int xOffsetCenter;
    private boolean isRow = false;
    private int size, distance;


    public LoadSkill(int x, int y, int num, int sizeB, int distance, boolean isRow){
        loadBoxes = new LoadBox[num];
        this.xOffsetCenter = (sizeB+distance)*num/2-distance;
        this.isRow = isRow;
        this.size = sizeB;
        this.distance =distance;
        for(int i=0;i<loadBoxes.length; i++){
            if(isRow)
                loadBoxes[i] = new LoadBox(x-xOffsetCenter, y+i*(sizeB+distance), sizeB);
            else
                loadBoxes[i] = new LoadBox(x-xOffsetCenter+i*(sizeB+distance), y, sizeB);
        }
    }
    public void setTime(int index, long value){
        loadBoxes[index].setRecoverTime(value);
    }
    public void setPos(int x, int y){
        for(int i=0;i<loadBoxes.length; i++){
            if(isRow)
                loadBoxes[i].setPos(x-xOffsetCenter, y+i*(size+distance));
            else
                loadBoxes[i].setPos(x-xOffsetCenter+i*(size+distance), y);
        }
    }
    public LoadBox getLoadBox(int index){return loadBoxes[index];}
    public void draw(Graphics g){
        for(LoadBox mb: loadBoxes){
            mb.draw(g);
        }
    }
    public void update(){
        for(LoadBox mb: loadBoxes){
            mb.update();
        }
    }

}

package edu.uchicago.cs.java.finalproject.game.model;

import java.awt.*;

/**
 * Created by kld on 2014/11/28.
 */
public class DebrisPieces extends Asteroid{

    private int nXCoords[];
    private int nYCoords[];

    public DebrisPieces(Asteroid astExploded) {
        super(astExploded);
    }

    public void expire(){
        if (getExpire() == 0)
            CommandCenter.movDebris.remove(this);
        else
            setExpire(getExpire() - 1);
    }
    public void draw(Graphics g) {
        nXCoords = new int[dDegrees.length];
        nYCoords = new int[dDegrees.length];

        for (int nC = 0; nC < dDegrees.length; nC++) {
            nXCoords[nC] = (int) (getCenter().x + getRadius()
                    * dLengths[nC]
                    * Math.sin(Math.toRadians(getOrientation()) + dDegrees[nC]));
            nYCoords[nC] = (int) (getCenter().y - getRadius()
                    * dLengths[nC]
                    * Math.cos(Math.toRadians(getOrientation()) + dDegrees[nC]));
        }
        g.setColor(new Color(getColor().getRed()/10*getExpire(),getColor().getGreen()/10*getExpire(),getColor().getBlue()/10*getExpire()));
        g.fillPolygon(nXCoords, nYCoords, dDegrees.length);
    }
}
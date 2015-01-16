package edu.uchicago.cs.java.finalproject.game.model;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by kld on 2014/12/4.
 */
public class Shield extends NewShipFloater{

    public Shield(){
        ArrayList<Point> pntCs = new ArrayList<Point>();

//        pntCs.add(new Point(0, 10));
        pntCs.add(new Point(5, 6));
        pntCs.add(new Point(5, -4));
        pntCs.add(new Point(4, -6));

        pntCs.add(new Point(1, -10));
        pntCs.add(new Point(-1, -10));

        pntCs.add(new Point(-4, -6));
        pntCs.add(new Point(-5, -4));
        pntCs.add(new Point(-5, 6));

        assignPolarPoints(pntCs);
        setColor(Color.green);
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.setColor(getColor());
        g.fillPolygon(getXcoords(), getYcoords(), dDegrees.length);
        //now draw a white border
        g.setColor(Color.white);
        g.drawPolygon(getXcoords(), getYcoords(), dDegrees.length);
    }
}

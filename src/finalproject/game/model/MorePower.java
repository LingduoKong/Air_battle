package edu.uchicago.cs.java.finalproject.game.model;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by kld on 2014/11/23.
 */
public class MorePower extends NewShipFloater{

    public MorePower(){

        ArrayList<Point> pntCs = new ArrayList<Point>();

        pntCs.add(new Point(0, 10));

        pntCs.add(new Point(2, 6));
        pntCs.add(new Point(2, 0));
        pntCs.add(new Point(5, -1));
        pntCs.add(new Point(5, -3));
        pntCs.add(new Point(2, -3));
        pntCs.add(new Point(2, -3));

        pntCs.add(new Point(-2, -3));
        pntCs.add(new Point(-2, -3));
        pntCs.add(new Point(-5, -3));
        pntCs.add(new Point(-5, -1));
        pntCs.add(new Point(-2, 0));
        pntCs.add(new Point(-2, 6));
        assignPolarPoints(pntCs);
        setColor(Color.red);
        setExpire(500);
    }
}

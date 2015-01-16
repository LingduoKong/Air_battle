package edu.uchicago.cs.java.finalproject.game.model;

import java.awt.*;

/**
 * Created by kld on 2014/11/22.
 */
public class TwoLineBullet extends Bullet {

    private final double Fire_Power = 55;

    private int[] nXCoords1;
    private int[] nYCoords1;
    private int[] nXCoords2;
    private int[] nYCoords2;

    private int x_add;
    private int y_add;

    public TwoLineBullet(Falcon fal) {

        super(fal);

        x_add = (int)((fal.getRadius()-10)
                * Math.sin(Math.toRadians(getOrientation())));
        y_add = (int)((fal.getRadius()-10)
                * Math.cos(Math.toRadians(getOrientation())));

        setDeltaX( fal.getDeltaX() +
                Math.cos( Math.toRadians( fal.getOrientation() ) ) * Fire_Power );
        setDeltaY( fal.getDeltaY() +
                Math.sin( Math.toRadians( fal.getOrientation() ) ) * Fire_Power );
        setCenter( fal.getCenter() );

        setColor(Color.red);

        setRadius(30);
    }

    public void draw(Graphics g) {

        //in the left wing
        nXCoords1 = new int[dDegrees.length];
        nYCoords1 = new int[dDegrees.length];

        //right wing
        nXCoords2 = new int[dDegrees.length];
        nYCoords2 = new int[dDegrees.length];
        //need this as well

        for (int nC = 0; nC < dDegrees.length; nC++) {

            nXCoords1[nC] =    (int) (getCenter().x + getRadius()/2
                    * dLengths[nC]
                    * Math.sin(Math.toRadians(getOrientation()) + dDegrees[nC]))
                    + x_add;
            nYCoords1[nC] =    (int) (getCenter().y - getRadius()/2
                    * dLengths[nC]
                    * Math.cos(Math.toRadians(getOrientation()) + dDegrees[nC]))
                    - y_add;
            nXCoords2[nC] =    (int) (getCenter().x + getRadius()/2
                    * dLengths[nC]
                    * Math.sin(Math.toRadians(getOrientation()) + dDegrees[nC]))
                    - x_add;
            nYCoords2[nC] =    (int) (getCenter().y - getRadius()/2
                    * dLengths[nC]
                    * Math.cos(Math.toRadians(getOrientation()) + dDegrees[nC]))
                    + y_add;

            //need this line of code to create the points which we will need for debris
        }
        g.setColor(getColor());
        g.fillPolygon(nXCoords1, nYCoords1, dDegrees.length);
        g.fillPolygon(nXCoords2, nYCoords2, dDegrees.length);
    }
}

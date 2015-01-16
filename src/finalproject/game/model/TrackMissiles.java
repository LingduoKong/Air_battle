package edu.uchicago.cs.java.finalproject.game.model;

import java.awt.*;

/**
 * Created by kld on 2014/11/28.
 */
public class TrackMissiles extends Cruise {
    private int FIRE_POWER = 20;
    private Movable movable;
    private Falcon falcon;
    public TrackMissiles(Falcon fal, Movable movable) {
        super(fal);
        this.falcon = fal;
        this.movable = movable;
        setOrientation(fal.getOrientation());
        setExpire(50);
    }

    @Override
    public void move() {

        Point pnt = getCenter();
        Point destination = movable.getCenter();

        setDeltaX(Math.cos(Math.toRadians(getOrientation())) * FIRE_POWER);
        setDeltaY(Math.sin(Math.toRadians(getOrientation())) * FIRE_POWER);

        double dX = pnt.x + getDeltaX();
        double dY = pnt.y + getDeltaY();

        if (pnt.x < destination.x && pnt.y > destination.y) {
            double orientation =
                    Math.toDegrees(Math.atan((pnt.y - destination.y) / (destination.x - pnt.x)));
            setOrientation(360 - (int) orientation);
        } else if (pnt.x < destination.x && pnt.y < destination.y) {
            double orientation =
                    Math.toDegrees(Math.atan((pnt.y - destination.y) / (pnt.x - destination.x)));
            setOrientation((int) orientation);
        } else if (pnt.x > destination.x && pnt.y < destination.y) {
            double orientation =
                    Math.toDegrees(Math.atan((destination.y - pnt.y) / (pnt.x - destination.x)));
            setOrientation(180 - (int) orientation);
        } else if (pnt.x != destination.x) {
            double orientation =
                    Math.toDegrees(Math.atan((pnt.y - destination.y) / (pnt.x - destination.x)));
            setOrientation(180 + (int) orientation);
        }
        setCenter(new Point((int) dX, (int) dY));

    }

    @Override
    public void draw(Graphics g) {
        super.drawAlt(g);
    }
}

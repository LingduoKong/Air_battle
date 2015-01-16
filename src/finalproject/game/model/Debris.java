package edu.uchicago.cs.java.finalproject.game.model;

import edu.uchicago.cs.java.finalproject.controller.Game;

import java.awt.*;

/**
 * Created by kld on 2014/11/22.
 */
public class Debris extends Asteroid{

    public Debris(Asteroid astExploded) {
        super(astExploded);
        setCenter(astExploded.getCenter());
        Asteroid asteroid;
        int sides = Game.R.nextInt(10) + 15;

        for (int i = 0; i < sides; i++) {
            asteroid = new DebrisPieces(astExploded);
            asteroid.setDeltaX(astExploded.getRadius()*
                    Math.cos(Math.toRadians(360/sides*i)));
            asteroid.setDeltaY(astExploded.getRadius() *
                    Math.sin(Math.toRadians(360/sides * i)));
            asteroid.setExpire(10);
            asteroid.setColor(new Color(Game.R.nextInt(256),Game.R.nextInt(256),Game.R.nextInt(256)));
            asteroid.setRadius(astExploded.getRadius()/5);
            CommandCenter.movDebris.add(asteroid);
        }
    }

    //override the expire method - once an object expires, then remove it from the arrayList.
    public void expire(){
        if (getExpire() == 0)
            CommandCenter.movDebris.remove(this);
        else
            setExpire(getExpire() - 1);
    }

}

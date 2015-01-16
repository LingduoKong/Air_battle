package edu.uchicago.cs.java.finalproject.game.model;

import edu.uchicago.cs.java.finalproject.controller.Game;

import java.awt.*;
import java.util.ArrayList;


public class Falcon extends Sprite {

	// ==============================================================
	// FIELDS 
	// ==============================================================
	
	private final double THRUST = .65;

	final int DEGREE_STEP = 7;

	private boolean bFlame1 = false;
	private boolean bFlame2 = false;
    private boolean bThrusting = false;

    private boolean bShield = false;
    private int ShieldTime;
    private int nShield;
    private boolean bProtected; //for fade in and out
	
	private boolean bTurningRight = false;
	private boolean bTurningLeft = false;

    private boolean bStop = false;
    private boolean bFlameBack = false;

    private static boolean MorePower;

    private final double[] FLAME = { 23 * Math.PI / 24 + Math.PI / 2,
			Math.PI + Math.PI / 2, 25 * Math.PI / 24 + Math.PI / 2 };

	private int[] nXFlames1 = new int[FLAME.length];
	private int[] nYFlames1 = new int[FLAME.length];
    private int[] nXFlames2 = new int[FLAME.length];
	private int[] nYFlames2 = new int[FLAME.length];

	private Point[] pntFlames = new Point[FLAME.length];

	
	// ==============================================================
	// CONSTRUCTOR 
	// ==============================================================
	
	public Falcon() {
		super();

		ArrayList<Point> pntCs = new ArrayList<Point>();
		
		// top of ship
		pntCs.add(new Point(0, 18)); 
		
		//right points
		pntCs.add(new Point(3, 3)); 
		pntCs.add(new Point(12, 0)); 
		pntCs.add(new Point(13, -2)); 
		pntCs.add(new Point(13, -4)); 
		pntCs.add(new Point(11, -2)); 
		pntCs.add(new Point(4, -3)); 
		pntCs.add(new Point(2, -10)); 
		pntCs.add(new Point(4, -12)); 
		pntCs.add(new Point(2, -13)); 

		//left points
		pntCs.add(new Point(-2, -13)); 
		pntCs.add(new Point(-4, -12));
		pntCs.add(new Point(-2, -10)); 
		pntCs.add(new Point(-4, -3)); 
		pntCs.add(new Point(-11, -2));
		pntCs.add(new Point(-13, -4));
		pntCs.add(new Point(-13, -2)); 
		pntCs.add(new Point(-12, 0)); 
		pntCs.add(new Point(-3, 3)); 
		

		assignPolarPoints(pntCs);

		setColor(Color.white);
		
		//put falcon in the middle.
		setCenter(new Point(Game.DIM.width / 2, Game.DIM.height / 2));
		
		//with random orientation
		setOrientation(Game.R.nextInt(360));
		
		//this is the size of the falcon
		setRadius(35);

		//these are falcon specific
		setProtected(true);
		setFadeValue(0);
        setShieldTime(255);
	}
	
	
	// ==============================================================
	// METHODS 
	// ==============================================================

	public void move() {
		super.move();
		if (bThrusting) {
			bFlame1 = true;
			bFlame2 = true;
			double dAdjustX = Math.cos(Math.toRadians(getOrientation()))
					* THRUST;
			double dAdjustY = Math.sin(Math.toRadians(getOrientation()))
					* THRUST;
			setDeltaX(getDeltaX() + dAdjustX);
			setDeltaY(getDeltaY() + dAdjustY);
		}
        if (bStop) {
            bFlameBack = true;
            double dAdjustX = Math.cos(Math.toRadians(getOrientation()))
                    * THRUST;
            double dAdjustY = Math.sin(Math.toRadians(getOrientation()))
                    * THRUST;
            setDeltaX(getDeltaX() - dAdjustX);
            setDeltaY(getDeltaY() - dAdjustY);
        }

		if (bTurningLeft) {

			if (getOrientation() <= 0 && bTurningLeft) {
				setOrientation(360);
			}
			setOrientation(getOrientation() - DEGREE_STEP);
		} 
		if (bTurningRight) {
			if (getOrientation() >= 360 && bTurningRight) {
				setOrientation(0);
			}
			setOrientation(getOrientation() + DEGREE_STEP);
		}

	} //end move

	public void rotateLeft() {
        bFlame2 = true;
		bTurningLeft = true;
	}

	public void rotateRight() {
        bFlame1 = true;
		bTurningRight = true;
	}

	public void stopRotating() {
        bFlame1 = false;
        bFlame2 = false;
		bTurningRight = false;
		bTurningLeft = false;
	}

	public void thrustOn() {
		bThrusting = true;
	}

	public void thrustOff() {
		bThrusting = false;
		bFlame1 = false;
		bFlame2 = false;
	}

    public void backOn(){
        bStop = true;
    }

    public void backOff(){
        bStop = false;
        bFlameBack = false;
    }

	private int adjustColor(int nCol, int nAdj) {
		if (nCol - nAdj <= 0) {
			return 0;
		} else {
			return nCol - nAdj;
		}
	}

	public void draw(Graphics g) {

		//does the fading at the beginning or after hyperspace
		Color colShip;
		if (getFadeValue() == 255) {
			colShip = Color.white;
		} else {
			colShip = new Color(adjustColor(getFadeValue(), 200), adjustColor(
					getFadeValue(), 175), getFadeValue());
		}

		//shield on
		if (bShield && getShieldTime() > 0) {
			g.setColor(new Color(getShieldTime(), 0 ,0));
			g.drawOval(getCenter().x - getRadius()-5,
					getCenter().y - getRadius()-5, getRadius() * 2 + 10,
					getRadius() * 2 + 10);
            g.setColor(new Color(0, getShieldTime() ,0));
			g.drawOval(getCenter().x - getRadius()-7,
					getCenter().y - getRadius()-7, getRadius() * 2 + 14,
					getRadius() * 2 + 14);
            g.setColor(new Color(0, 0 ,getShieldTime()));
			g.drawOval(getCenter().x - getRadius()-3,
					getCenter().y - getRadius()-3, getRadius() * 2 + 6,
					getRadius() * 2 + 6);
            setShieldTime(getShieldTime()-1);
		} //end if shield

		//thrusting
		if (bFlame1) {
			//the flame
			for (int nC = 0; nC < FLAME.length; nC++) {
				if (nC % 2 != 0) //odd
				{
					pntFlames[nC] = new Point((int) (getCenter().x + 2
							* getRadius()
							* Math.sin(Math.toRadians(getOrientation())
									+ FLAME[nC])), (int) (getCenter().y - 2
							* getRadius()
							* Math.cos(Math.toRadians(getOrientation())
									+ FLAME[nC])));
				}
                else //even
				{
					pntFlames[nC] = new Point((int) (getCenter().x + getRadius()
							* 1.1
							* Math.sin(Math.toRadians(getOrientation())
									+ FLAME[nC])),
							(int) (getCenter().y - getRadius()
							* 1.1
							* Math.cos(Math.toRadians(getOrientation())
									+ FLAME[nC])));

				} //end even/odd else

			} //end for loop


			for (int nC = 0; nC < FLAME.length; nC++) {
                int x_add = (int)(getRadius()
                        * Math.sin(Math.toRadians(getOrientation())));
                int y_add = (int)(getRadius()
                        * Math.cos(Math.toRadians(getOrientation())));

				nXFlames1[nC] = pntFlames[nC].x + x_add;
				nYFlames1[nC] = pntFlames[nC].y - y_add;

			} //end assign flame points

			g.setColor( Color.yellow );
			g.fillPolygon(nXFlames1, nYFlames1, FLAME.length);

		} //end if flame

        if (bFlame2) {
			//the flame
			for (int nC = 0; nC < FLAME.length; nC++) {
				if (nC % 2 != 0) //odd
				{
					pntFlames[nC] = new Point((int) (getCenter().x + 2
							* getRadius()
							* Math.sin(Math.toRadians(getOrientation())
									+ FLAME[nC])), (int) (getCenter().y - 2
							* getRadius()
							* Math.cos(Math.toRadians(getOrientation())
									+ FLAME[nC])));
				}
                else //even
				{
					pntFlames[nC] = new Point((int) (getCenter().x + getRadius()
							* 1.1
							* Math.sin(Math.toRadians(getOrientation())
									+ FLAME[nC])),
							(int) (getCenter().y - getRadius()
							* 1.1
							* Math.cos(Math.toRadians(getOrientation())
									+ FLAME[nC])));

				} //end even/odd else

			} //end for loop


			for (int nC = 0; nC < FLAME.length; nC++) {
                int x_add = (int)(getRadius()
                        * Math.sin(Math.toRadians(getOrientation())));
                int y_add = (int)(getRadius()
                        * Math.cos(Math.toRadians(getOrientation())));

                nXFlames2[nC] = pntFlames[nC].x - x_add;
				nYFlames2[nC] = pntFlames[nC].y + y_add;

			} //end assign flame points

			g.setColor( Color.yellow );
            g.fillPolygon(nXFlames2, nYFlames2, FLAME.length);

		} //end if flame

        //back
        if (bFlameBack) {
            //the flame
            for (int nC = 0; nC < FLAME.length; nC++) {
                if (nC % 2 != 0) //odd
                {
                    pntFlames[nC] = new Point((int) (getCenter().x - 2
                            * getRadius()
                            * Math.sin(Math.toRadians(getOrientation())
                                + FLAME[nC])), (int) (getCenter().y + 2
                            * getRadius()
                            * Math.cos(Math.toRadians(getOrientation())
                                + FLAME[nC])));

                }
                else //even
                {
                    pntFlames[nC] = new Point((int) (getCenter().x - getRadius()
                            * 1.1
                            * Math.sin(Math.toRadians(getOrientation())
                            + FLAME[nC])),
                            (int) (getCenter().y + getRadius()
                            * 1.1
                            * Math.cos(Math.toRadians(getOrientation())
                            + FLAME[nC])));

                } //end even/odd else

            } //end for loop


            for (int nC = 0; nC < FLAME.length; nC++) {
                int x_add = (int)(getRadius()
                        * Math.sin(Math.toRadians(getOrientation())));
                int y_add = (int)(getRadius()
                        * Math.cos(Math.toRadians(getOrientation())));
                nXFlames1[nC] = pntFlames[nC].x + x_add;
                nYFlames1[nC] = pntFlames[nC].y - y_add;
                nXFlames2[nC] = pntFlames[nC].x - x_add;
                nYFlames2[nC] = pntFlames[nC].y + y_add;

            } //end assign flame points

            g.setColor( Color.yellow );
            g.fillPolygon(nXFlames1, nYFlames1, FLAME.length);
            g.fillPolygon(nXFlames2, nYFlames2, FLAME.length);

        } //end if flame

		drawShipWithColor(g, colShip);

	} //end draw()


	public void drawShipWithColor(Graphics g, Color col) {
        super.draw(g);
        if(bShield){
            g.setColor(Color.green);
            g.fillPolygon(getXcoords(), getYcoords(), dDegrees.length);
        }
        else {
            g.setColor(col);
            g.drawPolygon(getXcoords(), getYcoords(), dDegrees.length);
        }
	}

	public void fadeInOut() {
		if (getProtected()) {
			setFadeValue(getFadeValue() + 3);
		}
		if (getFadeValue() == 255) {
			setProtected(false);
		}
	}
	
	public void setProtected(boolean bParam) {
		if (bParam) {
			setFadeValue(0);
		}
		bProtected = bParam;
	}

    public void setbShield(boolean bShield) {
        this.bShield = bShield;
	}

    public boolean getbShield() {
        return bShield;
	}

    public void setShieldTime(int ShieldTime) {
        this.ShieldTime = ShieldTime;
        if(this.ShieldTime==0){
            setbShield(false);
            setShieldTime(255);
        }
	}

    public int getShieldTime() {
        return ShieldTime;
	}

	public void setProtected(boolean bParam, int n) {
		if (bParam && n % 3 == 0) {
			setFadeValue(n);
		} else if (bParam) {
			setFadeValue(0);
		}
		bProtected = bParam;
	}	

	public boolean getProtected() {return bProtected;}
	public void setShield(int n) {nShield = n;}
	public int getShield() {return nShield;}

    public void setMorePower(boolean b){
        MorePower = b;
    }

    public boolean getMorePower(){
        return MorePower;
    }
	
} //end class

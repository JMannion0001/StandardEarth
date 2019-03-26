/*
 * Copyright (C) 2019 eib15ns
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package Earth;

/**
 * Models a gravitational field for an oblate celestial body. Only 2nd order for eci.
 * @author Nathaniel
 */
public class Gravity{
    
    //Instance Variables
    private final double j2 = Constants.JEFFERY_2; 
    private final double mass = Constants.EARTH_MASS; //Mass of body
    private final double radiusEq = Constants.RADIUS_EQUATOR; //Equatorial Radius
    private double accX;
    private double accY;
    private double accZ;
    
    /**
     * Calculates the acceleration due to gravity for any ECI co-ordinates in the J2000 system.
     * @param eciX Distance in x axis (m)
     * @param eciY Distance in y axis (m)
     * @param eciZ Distance in z axis (m)
     */
    public Gravity(double eciX, double eciY, double eciZ){
        this.gravityECI(eciX, eciY, eciZ);
    }
    
    /**
     * Calculates the P values used in the radial component of the gravitational
     * force.
     * @param phi Angle in radians from the axis passing through the northern 
     * most pole.
     * @return Array containing the three P values {P2,P3,P4}
     */
    private double[] calcPValues(double phi) {
        double P2 = 0.5*(3*Math.cos(phi)*Math.cos(phi) - 1);
        double P3 = 0.5*(5*Math.cos(phi)*Math.cos(phi)*Math.cos(phi) - 3*Math.cos(phi));
        double P4 = 0.125*(35*Math.cos(phi)*Math.cos(phi)*Math.cos(phi)*Math.cos(phi) - 30*Math.cos(phi)*Math.cos(phi) + 3);
        double[] PArray = {P2,P3,P4};
        return(PArray);
}
    
    /**
     * Computes the gravitational acceleration vector at a specified ECI location using the JGM2 gravitational ellipsoid only. Higher-order gravity terms (the "gravity anomaly") are ignored. Only the pure ellipsoid is used.
     * @param positionECI Position co-ordinates in ECI system
     * @return The acceleration vector of gravity in ECI system
     */
    private void gravityECI(double positionECIX, double positionECIY, double positionECIZ){
        
        double r = Math.sqrt(Math.pow(positionECIX, 2)+Math.pow(positionECIY, 2)+Math.pow(positionECIZ, 2));
        
        double sub1 = 1.5 * this.j2 * Math.pow((this.radiusEq / r), 2);
        double sub2 = 5 * Math.pow((positionECIZ/r), 2);
        double sub3 = -1 * (Constants.GRAVITATIONAL_CONSTANT*this.mass) / Math.pow(r, 3);
        double sub4 = sub3 * (1 - sub1 * (sub2 - 1));
        
        double gx = positionECIX * sub4;
        double gy = positionECIY * sub4;
        double gz = positionECIZ * sub3 * (1 - sub1 * (sub2 - 3));
        
        this.accX = gx;
        this.accY = gy;
        this.accZ = gz;
        
    }
    
    // GET FUNCTIONS

    /**
     * Returns the acceleration along the x-axis(as defined by J2000 ECI)
     * @return The acceleration along x (m/s^2)
     */
    public double getAccX(){return(this.accX);}
    /**
     * Returns the acceleration along the y-axis(as defined by J2000 ECI)
     * @return The acceleration along y (m/s^2)
     */
    public double getAccY(){return(this.accY);}
    /**
     * Returns the acceleration along the z-axis(as defined by J2000 ECI)
     * @return The acceleration along z (m/s^2)
     */
    public double getAccZ(){return(this.accZ);}

}

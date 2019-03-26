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
 *
 * @author eib15ns
 */
class Constants {
    public static final double GRAVITATIONAL_CONSTANT = 6.67408*Math.pow(10, -11);
    public static final double RADIUS_POLE = 6356766; //Geopotential radius at which g=9.81, also the radius at the pole in metres
    public static final double M0 = 0.0289644; // Molar Constant
    public static final double G0 = 9.80665; //Acceleration due to gravity at sea level
    public static final double R = 8.31432; // Gas constant
    public static final double GAMMA = 1.4; //Ratio of specific heats for air
    public static final double EARTH_MASS = 5.9722*Math.pow(10,24);
    public static final double RADIUS_EQUATOR = 6378135; // radius of earth at the equator
    public static final double JEFFERY_2 = 1.08263*Math.pow(10, -3);
}

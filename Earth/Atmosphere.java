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
 * Calculates the atmospheric values at given geometric heights based on the 1976 standard
 * atmospheric model.
 * The standard atmosphere, as well as the original equations and interpolation values can be found at:
 * https://ntrs.nasa.gov/search.jsp?R=19770009539
 * @author eib15ns
 */
public class Atmosphere {
    
    //Instance Variables
    private double height; // km
    private double temperature; // K
    private double pressure; // Pa
    private double speedSound; // m/s
    private double density; // kd /m3
    
    
    Atmosphere(){
        layer(0.0);
        this.speedSound = Math.pow((Constants.GAMMA*Constants.R*this.temperature)/Constants.M0,0.5);
        this.density = this.pressure/(this.temperature*Constants.R);
    }
    /**
     * Uses the provided height to calculate the temperature of the surrounding
     * atmosphere based on the 1976 Standard Atmosphere Model
     * @param height The geometric height must be in kilometers
     */
    public Atmosphere(double height){
        if (height > 1000.0) throw new RuntimeException("The Standard Atmosphere 1976 is only defined up to a geodetic altitude of 1000km. \n Your value for height exceeds this value.");
        if (height < 0.0) throw new RuntimeException("The Standard Atmosphere 1976 is not defined below a geodetic altitude of 0km. \n Your value for height falls below this value.");
        layer(height);
        this.speedSound = calcSpeedSound(this.temperature);
        this.density = this.pressure/(this.temperature*Constants.R);
    }
    
    /**
     * Sets the height of an instance of Atmosphere and updates the values of the class
     * @param height Geometric height in km
     */
    public void setHeight(double height){
        layer(height);
        this.speedSound = calcSpeedSound(this.temperature);
        this.density = this.pressure/(this.temperature*Constants.R);
    }
    
    /**
     * Calculates the geopotential height at a given geometric height
     * @param height Geometric height above sea level
     * @return Geopotential height above sea level
     */
    private double getGeoPotHeight(double height){
        double geoPotHeight = (Constants.RADIUS_POLE/1000.0*height)/(Constants.RADIUS_POLE/1000.0+height);
        return(geoPotHeight);
    }
    
    /**
     * Calculates the speed of sound as a function of temperature
     * @param temperature The temperature (K)
     * @return The speed of sound (m/s)
     */
    private double calcSpeedSound(double temperature){
        double speedOfSound = Math.pow((Constants.GAMMA*Constants.R*temperature)/Constants.M0,0.5);
        return(speedOfSound);
    }
    
    
    // LAYER FUNCTIONS
    
    /**
     * Assigns class variables to instance
     * @param height Geodetic altitude above Earth in km
     */
    private void layer(double height){
        
        double geoPot = this.getGeoPotHeight(height); // Calculates geopotential height from geometric height
        // Determines which layer the position is in and passes it as a function
        if (height <= 80){
            layer80(geoPot);
        }
        else if (height <= 86){
            layer86(geoPot);
        }
        else if (height <= 91){
            layer91(height);
        }
        else if (height <= 110){
            layer110(height);
        }
        else if (height <= 120){
            layer120(height);
        }
        else{
            layer1000(height);
        }
        
    }
    
    private void layer80(double geoPotHeight){
        double lapse; //lapse rate in specific layer
        double tempBase; //temp at base of specific layer (K)
        double pressureBase; //pressure at the base of the layer (Pa)
        double heightBase; //geopotential height at base of specific layer
        
        if (geoPotHeight < 11){
            lapse = -6.5;
            tempBase = 288.15;
            pressureBase = 101325;
            heightBase = 0;
            
            this.temperature = tempBase + lapse*(geoPotHeight - heightBase);
            this.pressure = pressure(geoPotHeight,tempBase,lapse,pressureBase,heightBase);
        }
        else if (geoPotHeight < 20){
            lapse = 0;
            tempBase = 216.65;
            pressureBase = 22632.1;
            heightBase = 11;
            
            this.temperature = tempBase + lapse*(geoPotHeight - heightBase);
            this.pressure = pressure(geoPotHeight,tempBase,lapse,pressureBase,heightBase);
        }
        else if (geoPotHeight < 32){
            lapse = 1;
            tempBase = 216.65;
            pressureBase = 5474.89;
            heightBase = 20;
            
            this.temperature = tempBase + lapse*(geoPotHeight - heightBase);
            this.pressure = pressure(geoPotHeight,tempBase,lapse,pressureBase,heightBase);
        }
        else if (geoPotHeight < 47){
            lapse = 2.8;
            tempBase = 228.65;
            pressureBase = 868.019;
            heightBase = 32;
            
            this.temperature = tempBase + lapse*(geoPotHeight - heightBase);
            this.pressure = pressure(geoPotHeight,tempBase,lapse,pressureBase,heightBase);
        }
        else if (geoPotHeight < 51){
            lapse = 0;
            tempBase = 270.65;
            pressureBase = 110.906;
            heightBase = 47;
            
            this.temperature = tempBase + lapse*(geoPotHeight - heightBase);
            this.pressure = pressure(geoPotHeight,tempBase,lapse,pressureBase,heightBase);
        }
        else if (geoPotHeight < 71){
            lapse = -2.8;
            tempBase = 270.65;
            pressureBase = 66.9389;
            heightBase = 51;
            
            this.temperature = tempBase + lapse*(geoPotHeight - heightBase);
            this.pressure = pressure(geoPotHeight,tempBase,lapse,pressureBase,heightBase);
        }
        
        else{
            lapse = -2; //lapse rate in specific layer
            tempBase = 214.65; //temp at base of specific layer
            pressureBase = 3.95642; //pressure at base of specific layer
            heightBase = 71;//geopotential height at base of specific layer
            
            this.temperature = (tempBase + lapse*(geoPotHeight - heightBase));
            this.pressure = pressure(geoPotHeight,tempBase,lapse,pressureBase,heightBase);
        }

    }
    
    private void layer86(double geoPotHeight){
        double lapse = -2; //lapse rate in specific layer
        double tempBase = 214.65; //temp at base of specific layer
        double pressureBase = 3.95642; //pressure at base of specific layer
        double heightBase = 71;//geopotential height at base of specific layer
        double molWeightRatio; //Conversion factor for transition regime from 86 - 91 kilometers
        
        if (geoPotHeight < 79005.7){
            molWeightRatio = 1;
        }
        else if (geoPotHeight < 79493.3){
            molWeightRatio = 0.999996;
        }
        else if (geoPotHeight < 79980.8){
            molWeightRatio = 0.999989;
        }
        else if (geoPotHeight < 80468.2){
            molWeightRatio = 0.999971;
        }
        else if (geoPotHeight < 80955.7){
            molWeightRatio = 0.999941;
        }
        else if (geoPotHeight < 81443.0){
            molWeightRatio = 0.999909;
        }
        else if (geoPotHeight < 81930.2){
            molWeightRatio = 0.999870;
        }
        else if (geoPotHeight < 82417.3){
            molWeightRatio = 0.999829;
        }
        else if (geoPotHeight < 82904.4){
            molWeightRatio = 0.999786;
        }
        else if (geoPotHeight < 83391.4){
            molWeightRatio = 0.999741;
        }
        else if (geoPotHeight < 83878.4){
            molWeightRatio = 0.999694;
        }
        else if (geoPotHeight < 84365.2){
            molWeightRatio = 0.999641;
        }
        else{
            molWeightRatio = 0.999579;
        }
        
        this.temperature = molWeightRatio*(tempBase + lapse*(geoPotHeight - heightBase));
        this.pressure = pressure(geoPotHeight,tempBase,lapse,pressureBase,heightBase);
    }
    
    private void layer91(double height){
        double ratio; // Ratio for linear interpolation
        double layer1 = 86; // Geometric altitude
        double basePres1 = 0.37338;
        double layer2 = 91; // Geometric altitude
        double basePres2 = 0.15381;
        
        this.temperature = 186.8673; // Temperature in layer (K)
        ratio = (height - layer1)/(layer2-layer1);
        this.pressure = basePres1 + ratio * (basePres2 - basePres1);        

    }
    
    private void layer110(double height){
        double Tc = 263.1905; // Constant defined by Standard 1976
        double A = -76.3232; // Constant defined by Standard 1976
        double a = -19.9429; // Constant defined by Standard 1976
        double ratio; // Ratio for linear interpolation of pressures
        double layer1 = 91;
        double basePres1 = 0.15381;
        double layer2 = 96;
        double basePres2 = 0.063765;
        double layer3 = 102;
        double basePres3 = 0.023144;
        double layer4 = 110;
        double basePres4 = 0.0071042;
        
        if (height < layer2) {
            ratio = (height - layer1)/(layer2-layer1);
            this.pressure = basePres1 + ratio * (basePres2 - basePres1);
        }
        else if (height < layer3) {
            ratio = (height - layer2)/(layer3-layer2);
            this.pressure = basePres2 + ratio * (basePres3 - basePres2);
        }
        else {
            ratio = (height - layer3)/(layer4-layer3);
            this.pressure = basePres3 + ratio * (basePres4 - basePres3);
        }
        
        this.temperature = Tc + A * Math.pow(1-Math.pow((height-91)/a, 2), 0.5);

    }
        
    private void layer120(double height){
        double ratio; // Ratio for linear interpolation
        double lapse = 12; //lapse rate in specific layer
        double tempBase = 240.0; //temp at base of specific layer
        double heightBase = 110;//geometric height at base of specific layer
        double layer1 = 110;
        double basePres1 = 0.0071042;
        double layer2 = 120;
        double basePres2 = 0.0025382;
        
        this.temperature = tempBase + lapse*(height - heightBase);

        ratio = (height - layer1)/(layer2-layer1);
        this.pressure = basePres1 + ratio * (basePres2 - basePres1);
    }
    
    private void layer1000(double height){
        double ratio;
        double lambda = 0.01875; // Constant defined by Standard 1976
        double tempBase = 360; //temp at base of specific layer
        double heightBase = 120;//geometric height at base of specific layer
        double Tinf = 1000;
        double layer1 = 120;
        double basePres1 = 0.0025382;
        double layer2 = 200;
        double basePres2 = 0.000084736;
        double layer3 = 300;
        double basePres3 = 0.0000087704;
        double layer4 = 400;
        double basePres4 = 0.0000014518;
        double layer5 = 500;
        double basePres5 = 0.00000030236;
        double layer6 = 600;
        double basePres6 = 0.000000082130;
        double layer7 = 700;
        double basePres7 = 0.000000031908;
        double layer8 = 800;
        double basePres8 = 0.000000017036;
        double layer9 = 900;
        double basePres9 = 0.000000010873;
        double layer10 = 1000;
        double basePres10 = 0.0000000075138;
        
        double epsilon = (height - heightBase)*(Constants.RADIUS_POLE/1000.0+heightBase)/(Constants.RADIUS_POLE/1000.0+height);
        this.temperature = Tinf - (Tinf - tempBase) * Math.pow(Math.E, -1*lambda*epsilon);
        
        if (height < layer2) {
            ratio = (height - layer1)/(layer2-layer1);
            this.pressure = basePres1 + ratio * (basePres2 - basePres1);
        }
        else if (height < layer3) {
            ratio = (height - layer2)/(layer3-layer2);
            this.pressure = basePres2 + ratio * (basePres3 - basePres2);
        }
        else if (height < layer4) {
            ratio = (height - layer3)/(layer4-layer3);
            this.pressure = basePres3 + ratio * (basePres4 - basePres3);
        }
        else if (height < layer5) {
            ratio = (height - layer4)/(layer5-layer4);
            this.pressure = basePres4 + ratio * (basePres5 - basePres4);
        }
        else if (height < layer6) {
            ratio = (height - layer5)/(layer6-layer5);
            this.pressure = basePres5 + ratio * (basePres6 - basePres5);
        }
        else if (height < layer7) {
            ratio = (height - layer6)/(layer7-layer6);
            this.pressure = basePres6 + ratio * (basePres7 - basePres6);
        }
        else if (height < layer8) {
            ratio = (height - layer7)/(layer8-layer7);
            this.pressure = basePres7 + ratio * (basePres8 - basePres7);
        }
        else if (height < layer9) {
            ratio = (height - layer8)/(layer9-layer8);
            this.pressure = basePres8 + ratio * (basePres9 - basePres8);
        }
        else if (height < layer10) {
            ratio = (height - layer9)/(layer3-layer9);
            this.pressure = basePres9 + ratio * (basePres10 - basePres9);
        }
        
    }
    
    // PRESSURE EQUATIONS
    
    /**
     * Calculates the pressure at an altitude using the layer properties
     * @param geoHeight Geopotential Height
     * @param tempBase Temperature at the base of the layer
     * @param lapseRate Lapse rate of the layer
     * @param pressureBase Pressure at the base of the layer
     * @param heightBase Altitude at the base of the layer
     * @return Pressure at the specified altitude
     */
    private double pressure(double geoHeight,double tempBase,
            double lapseRate,double pressureBase,double heightBase){
        
        double pres; // Placeholder variable for the calculated pressure
        
        // Different equations for lapseRate = 0 and lapseRate != 0
        if (lapseRate == 0){
            double temporary = (-1*Constants.G0*Constants.M0*((geoHeight-heightBase)*1000.0))/(Constants.R*tempBase);
            pres = pressureBase * Math.pow(Math.E,temporary);
        } 
        
        else {
            double exponent = ((Constants.G0*Constants.M0)/(Constants.R*(lapseRate/1000.0)));
            double value = tempBase / (tempBase + lapseRate*(geoHeight-heightBase));
            pres = pressureBase * Math.pow(value,exponent);            
        }
        
        return(pres);
    }
            
    
    // GET FUNCTIONS
    
    /**
     * Get the height for this instance
     * @return The altitude above the Earth's Atmosphere (km)
     */
    public double getHeight(){return(this.height);}
    /**
     * Get the temperature for this instance
     * @return The temperature calculated for this instance (K)
     */
    public double getTemperature(){return(this.temperature);}
    /**
     * Get the pressure for this instance
     * @return The pressure calculated for this instance (Pa)
     */
    public double getPressure(){return(this.pressure);}
    /**
     * Get the density for this instance
     * @return The density calculated for this instance (kg/m^3)
     */
    public double getDensity(){return(this.height);}
    /**
     * Get the speed of sound for this instance
     * @return The speed of sound calculated for this instance (m/s)
     */
    public double getSpeedSound(){return(this.speedSound);}
    
    
    
    
    @Override
    public String toString() {
        String result = "At Height(km): "+height+"\n"
                +"Density(kg/m^3: "+density+"\n"
                +"Pressure(Pa): "+pressure+"\n"
                +"Temperature(K): "+temperature+"\n"
                +"Speed of Sound(m/s): "+speedSound+"\n";
        return(result);
    }
    

}

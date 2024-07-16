package com.mladin.compton.calculations;

import com.mladin.compton.ComptonEffectApplication;

public class ComptonEffectCalculations {
    protected ComptonEffectApplication comptonEffectApplication;
    protected double initialEnergy;
    protected double photonAngle;
    protected double plankConstant = 6.626 * Math.pow(10, -34);
    protected double comptonConstant = 2.42 * Math.pow(10, -12);
    protected double lightSpeedConstant = 3.00 * Math.pow(10, 8);
    protected double evToJs = 1.602177 * Math.pow(10, -19);
    protected double electronAngle;

    protected double frequencyInitial;
    protected double waveLengthInitial;
    protected double waveLengthFinal;
    protected double impulseInitial;
    protected double impulsePhoton;
    protected double impulseElectron;

    protected double photonX;
    protected double photonY;

    protected double electronX;
    protected double electronY;
    public ComptonEffectCalculations(ComptonEffectApplication comptonEffectApplication, double initialEnergy, double photonAngle) {
        this.initialEnergy = initialEnergy * evToJs * Math.pow(10, 6);
        this.photonAngle = photonAngle;
        this.comptonEffectApplication = comptonEffectApplication;

        calculateParameters();
    }

    public void calculateParameters() {
        this.frequencyInitial = initialEnergy / plankConstant;
        this.waveLengthInitial = lightSpeedConstant / frequencyInitial;
        this.waveLengthFinal = 2 * comptonConstant * Math.pow(Math.sin(Math.toRadians(photonAngle / 2)), 2) + waveLengthInitial;
        this.impulseInitial = plankConstant / waveLengthInitial;
        this.impulsePhoton = plankConstant / waveLengthFinal;
        this.impulseElectron = Math.sqrt(Math.pow(impulsePhoton, 2) + Math.pow(impulseInitial, 2) - 2 * impulsePhoton * impulseInitial * Math.cos(Math.toRadians(photonAngle)));
        this.electronAngle =  Math.toDegrees(Math.acos(Math.pow(impulseElectron, 2) + Math.pow(impulseInitial, 2) - Math.pow(impulsePhoton, 2) / (2 * impulseElectron * impulseInitial)));

        double length = 200;
        double tangentPhotonAngle = Math.tan(Math.toRadians(photonAngle));

        this.photonX = length / Math.sqrt(1 + Math.pow(tangentPhotonAngle, 2));
        this.photonY = photonX * tangentPhotonAngle;

        this.photonX += 374;
        this.photonY = 278 - photonY;

        double tangentElectronAngle = Math.tan(Math.toRadians(electronAngle));

        this.electronX = -1 * length / Math.sqrt(1 + Math.pow(tangentElectronAngle, 2));
        this.electronY = electronX * tangentElectronAngle;

        this.electronX += 374;
        this.electronY += 278;
    }

    public void setInitialEnergy(double initialEnergy) {
        this.initialEnergy = initialEnergy * evToJs * Math.pow(10, 6);
    }

    public void setSpreadAngle(double photonAngle) {
        this.photonAngle = photonAngle;
    }

    public double getInitialEnergy() {
        return this.initialEnergy;
    }

    public double getPhotonAngle() {
        return this.photonAngle;
    }

    public double getElectronAngle() {
        return this.electronAngle;
    }

    public double getPhotonX() {
        return this.photonX;
    }

    public double getPhotonY() {
        return this.photonY;
    }

    public double getElectronX() {
        return this.electronX;
    }

    public double getElectronY() {
        return this.electronY;
    }

    public double getPlankConstant() {
        return plankConstant;
    }

    public double getComptonConstant() {
        return comptonConstant;
    }

    public double getLightSpeedConstant() {
        return lightSpeedConstant;
    }

    public double getEvToJs() {
        return evToJs;
    }

    public double getFrequencyInitial() {
        return frequencyInitial;
    }

    public double getWaveLengthInitial() {
        return waveLengthInitial;
    }

    public double getWaveLengthFinal() {
        return waveLengthFinal;
    }

    public double getImpulseInitial() {
        return impulseInitial;
    }

    public double getImpulsePhoton() {
        return impulsePhoton;
    }

    public double getImpulseElectron() {
        return impulseElectron;
    }
}

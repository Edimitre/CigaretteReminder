package com.edikorce.cigarettereminder.entities;

public class PacketCigarettes {

    int nrOfCigars;

    int value;

    public PacketCigarettes(int nrOfCigars, int value) {
        this.nrOfCigars = nrOfCigars;
        this.value = value;
    }

    public int getNrOfCigars() {
        return nrOfCigars;
    }

    public void setNrOfCigars(int nrOfCigars) {
        this.nrOfCigars = nrOfCigars;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

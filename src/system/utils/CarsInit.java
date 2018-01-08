package system.utils;

import system.enums.ClientType;
import system.gestion_de_concurrence.SeGarer;
import system.models.Parking;
import ui.GraphicCar;

import java.util.LinkedList;
import java.util.Random;

public class CarsInit {
    private final Parking parking;
    private int nbrVoituresAbo = 20, nbrVoituresSpcl = 50, nbrVoituresNrml = 17;
    private LinkedList<GraphicCar> listVoitures;
    private int nbrVoitures;


    private LinkedList<GraphicCar> listVoituresSpcl, listVoituresAbo, listVoituresNormal;

    public CarsInit(int nbrVoituresAbo, int nbrVoituresNrml, int nbrVoituresSpcl, Parking parking) {
        listVoitures = new LinkedList<>();
        this.nbrVoituresAbo = nbrVoituresAbo;
        this.nbrVoituresSpcl = nbrVoituresSpcl;
        this.nbrVoituresNrml = nbrVoituresNrml;
        nbrVoitures = nbrVoituresAbo + nbrVoituresNrml + nbrVoituresSpcl;
        this.parking = parking;
        initList();
    }


    private void initSpclClientCar() {
        listVoituresSpcl = new LinkedList<>();
        for (int i = 0; i < nbrVoituresSpcl; i++) {
            listVoituresSpcl.add(new GraphicCar(ClientType.HANDICAP));
        }
    }

    private void initAboClientCar() {
        listVoituresAbo = new LinkedList<>();
        for (int i = 0; i < nbrVoituresAbo; i++) {
            listVoituresAbo.add(new GraphicCar(ClientType.ABONNE));
        }
    }

    private void initNormalClientCar() {
        listVoituresNormal = new LinkedList<>();
        for (int i = 0; i < nbrVoituresNrml; i++) {
            listVoituresNormal.add(new GraphicCar(ClientType.NORMAL));
        }
    }

    private void addSpclClientCar() {
        listVoitures.add(listVoituresSpcl.peekFirst());
    }

    private void addAboClientCar() {
        listVoitures.add(listVoituresAbo.peekFirst());
    }

    private void addNormalClientCar() {
        listVoitures.add(listVoituresNormal.peekFirst());
    }

    private void initList() {
        initSpclClientCar();
        initAboClientCar();
        initNormalClientCar();
        for (int i = 0; i <= nbrVoitures; i++) {
            int x = new Random().nextInt(2) + 1;
            switch (x) {
                case 1: {
                    addNormalClientCar();
                }
                case 2: {
                    addSpclClientCar();
                }
                case 3: {
                    addAboClientCar();
                }
            }
        }
    }

    public LinkedList<SeGarer> getSeGarrers() {
        LinkedList<SeGarer> liste = new LinkedList<>();
        int i = 1;
        for (GraphicCar car : listVoitures) {
            liste.add(new SeGarer(parking, car, "seGarer : " + i));
            i++;
        }
        return liste;
    }
}

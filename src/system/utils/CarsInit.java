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
        listVoituresSpcl = new LinkedList<>();
        listVoituresAbo = new LinkedList<>();
        listVoituresNormal = new LinkedList<>();
        synchronized (this) {
            initSpclClientCar();
            initAboClientCar();
            initNormalClientCar();
        }
        initList();
    }


    private void initSpclClientCar() {

        for (int i = 0; i < nbrVoituresSpcl; i++) {
            GraphicCar car = new GraphicCar(ClientType.HANDICAP);
            car.setMatricule("Handi#" + i);
            listVoituresSpcl.add(car);
        }
    }

    private void initAboClientCar() {
        for (int i = 0; i < nbrVoituresAbo; i++) {
            GraphicCar car = new GraphicCar(ClientType.ABONNE);
            car.setMatricule("Abo#" + i);
            listVoituresAbo.add(car);
        }
    }

    private void initNormalClientCar() {
        for (int i = 0; i < nbrVoituresNrml; i++) {
            GraphicCar car = new GraphicCar(ClientType.NORMAL);
            car.setMatricule("Norm#" + i);
            listVoituresNormal.add(car);
        }
    }

    private void addSpclClientCar() {
        if (!listVoituresSpcl.isEmpty()) {
            listVoitures.add(listVoituresSpcl.get(0));
            listVoituresSpcl.remove(0);
        }
    }

    private void addAboClientCar() {
        if (!listVoituresAbo.isEmpty()) {
            listVoitures.add(listVoituresAbo.get(0));
            listVoituresAbo.remove(0);
        }
    }

    private void addNormalClientCar() {
        if (!listVoituresNormal.isEmpty()) {
            listVoitures.add(listVoituresNormal.get(0));
            listVoituresNormal.remove(0);
        }
    }

    private void initList() {
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

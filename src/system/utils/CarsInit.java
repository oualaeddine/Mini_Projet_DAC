/******************************************************************************
 *                                                                            *
 *  (C) Copyright ${year} Berrehal Ouala Eddine & Benghezal Ines.             *
 *  G2 L3 GL.                                                                 *
 *  Mini projet Module DAC.                                                   *
 *  Simulation d'un parking                                                   *
 *  avec gestion des concurrences en utilisant des sémaphores                 *
 *                                                                            *
 ******************************************************************************/
package system.utils;

import system.enums.ClientType;
import system.gestion_de_concurrence.SeGarer;
import system.models.Parking;
import ui.GraphicCar;

import java.util.LinkedList;
import java.util.Random;
/**Cette classe est destinée à l'initialisation des listes de voitures*/
public class CarsInit {
    private final Parking parking;
    private int nbrVoituresAbo = 20, nbrVoituresSpcl = 50, nbrVoituresNrml = 17;
    private final LinkedList<GraphicCar> listVoitures;
    private final int nbrVoitures;

    private final LinkedList<GraphicCar> listVoituresSpclAbo;
    private final LinkedList<GraphicCar> listVoituresSpcl;
    private final LinkedList<GraphicCar> listVoituresAboNormal;
    private final LinkedList<GraphicCar> listVoituresNormal;
    private int nbrVoituresAboHndi;

    public CarsInit(int nbrVoituresAbo, int nbrVoituresNrml, int nbrVoituresSpcl, int nbrVoituresSpclAbo, Parking parking) {
        listVoitures = new LinkedList<>();
        this.nbrVoituresAbo = nbrVoituresAbo;
        this.nbrVoituresSpcl = nbrVoituresSpcl;
        this.nbrVoituresAboHndi = nbrVoituresSpclAbo;
        this.nbrVoituresNrml = nbrVoituresNrml;
        nbrVoitures = nbrVoituresAbo + nbrVoituresNrml + nbrVoituresSpcl;
        this.parking = parking;
        listVoituresSpclAbo = new LinkedList<>();
        listVoituresSpcl = new LinkedList<>();
        listVoituresAboNormal = new LinkedList<>();
        listVoituresNormal = new LinkedList<>();
        synchronized (this) {
            initSpclClientCar();
            initAboClientCar();
            initNormalClientCar();
            initSpclAboCar();
        }
        initList();
    }

    private void initSpclAboCar(){
        for(int i=0;i< nbrVoituresAboHndi;i++){
            GraphicCar car = new GraphicCar(ClientType.HANDICAPABONNE);
            car.setMatricule("Handi Abo#"+i);
            listVoituresSpclAbo.add(car);
        }
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
            GraphicCar car = new GraphicCar(ClientType.NORMALABONNE);
            car.setMatricule("Abo#" + i);
            listVoituresAboNormal.add(car);
        }
    }


    private void initNormalClientCar() {
        for (int i = 0; i < nbrVoituresNrml; i++) {
            GraphicCar car = new GraphicCar(ClientType.NORMAL);
            car.setMatricule("Norm#" + i);
            listVoituresNormal.add(car);
        }
    }
    private  void addSpclAboClientCar(){
        if(!listVoituresSpclAbo.isEmpty()){
            listVoitures.add(listVoituresSpclAbo.get(0));
            listVoituresSpclAbo.remove(0);
        }
    }
    private void addSpclClientCar() {
        if (!listVoituresSpcl.isEmpty()) {
            listVoitures.add(listVoituresSpcl.get(0));
            listVoituresSpcl.remove(0);
        }
    }

    private void addAboClientCar() {
        if (!listVoituresAboNormal.isEmpty()) {
            listVoitures.add(listVoituresAboNormal.get(0));
            listVoituresAboNormal.remove(0);
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
            int x = new Random().nextInt(3) + 1;
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
                case 4:{
                    addSpclAboClientCar();
                }
            }
        }
    }

    public LinkedList<SeGarer> getSeGarrers() {
        LinkedList<SeGarer> liste = new LinkedList<>();
        int i = 1;
        for (GraphicCar car : listVoitures) {
            liste.add(new SeGarer(parking, car, "#" + i));
            i++;
        }
        return liste;
    }
}

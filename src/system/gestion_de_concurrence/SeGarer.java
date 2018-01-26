/******************************************************************************
 *                                                                            *
 *  (C) Copyright ${year} Berrehal Ouala Eddine & Benghezal Ines.             *
 *  G2 L3 GL.                                                                 *
 *  Mini projet Module DAC.                                                   *
 *  Simulation d'un parking                                                   *
 *  avec gestion des concurrences en utilisant des sémaphores                 *
 *                                                                            *
 ******************************************************************************/

package system.gestion_de_concurrence;

import system.enums.ClientType;
import system.models.Parking;
import ui.GraphicCar;
import ui.MainWindow;

import java.util.Random;

import static system.Params.NBR_SECONDES_PARKING_MAX;
import static system.Params.NBR_SECONDES_PARKING_MIN;

public class SeGarer extends Thread {
    private final Parking parking;

    private GraphicCar getVoiture() {
        return voiture;
    }

    private final GraphicCar voiture;

    public SeGarer(Parking parking, GraphicCar voiture, String s) {
        this.parking = parking;
        this.voiture = voiture;
        setName(s);
        setPriority(voiture.getPriorityInt());
    }

    private void trouver() {
        synchronized (this) {
            try {
                this.wait(700);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(this.toString() + " is entering!");
            MainWindow.nbrVoituresEntrees++;
            parking.prendrePlace(this.voiture);
        }
    }

    private synchronized void ekhroj() {

        parking.sortir(this.voiture);
        System.out.println(this.toString() + " is exiting!");

    }

    private synchronized void assena() {
        try {
            long waiting = new Random().nextInt(NBR_SECONDES_PARKING_MAX * 1000) + NBR_SECONDES_PARKING_MIN * 1000;
            System.out.println(this.toString() + " is in the park for : " + waiting + " ms!");
            this.wait(waiting);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        if (this.getVoiture().getClient().getType() != ClientType.HANDICAP)
            MainWindow.getVideNormal().P(this);
        else
            MainWindow.getVideHandi().P(this);

        MainWindow.getEntree().P(this);
        //Trouver une place
        trouver();

        MainWindow.getEntree().V(this);
        //yroh ygari w yebqa ltem pendant X
        assena();

        MainWindow.getSortie().P(this);
        //Sortir et libérer la place
        ekhroj();
        MainWindow.getSortie().V(this);

        if (this.getVoiture().getClient().getType() == ClientType.HANDICAP)
            MainWindow.getVideHandi().V(this);
        else
            MainWindow.getVideNormal().V(this);
    }

    @Override
    public String toString() {
        return getName() +
                ", voiture : " + voiture.getMatricule();
    }
}

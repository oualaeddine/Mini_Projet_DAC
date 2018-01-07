package system;

import system.models.Parking;
import ui.GraphicCar;
import ui.MainWindow;

import java.util.Random;

public class SeGarer extends Thread {
    private Parking parking;
    private GraphicCar voiture;

    public SeGarer(Parking parking, GraphicCar voiture) {
        this.parking = parking;
        this.voiture = voiture;
    }

    public void trouver() {
//        synchronized (this) {

            parking.prendrePlace(this.voiture);
//                this.wait(500);

//        }
    }
    public void ekhroj(){
        parking.sortir(this.voiture);
    }


    public synchronized void assena() {
        try {
            long waiting = new Random().nextInt(5000) + 5000;
            System.out.println("Waiting");
            this.wait(waiting);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        MainWindow.getEntree().P();
        MainWindow.getVide().P();
        //Trouver une place
        trouver();
        MainWindow.getEntree().V();
        //yroh ygari w yebqa ltem pendant X
        assena();

        MainWindow.getSortie().P();
        //Sortir et libérer la place
        ekhroj();
//        parking.sortir(this.voiture);
        MainWindow.getSortie().V();
        MainWindow.getVide().V();
    }
}

package system.gestion_de_concurrence;

import system.enums.ClientType;
import system.models.Parking;
import ui.GraphicCar;
import ui.MainWindow;

import java.util.Random;

public class SeGarer extends Thread implements Comparable {
    private Parking parking;

    public Parking getParking() {
        return parking;
    }

    public GraphicCar getVoiture() {
        return voiture;
    }

    private GraphicCar voiture;

    public SeGarer(Parking parking, GraphicCar voiture) {
        this.parking = parking;
        this.voiture = voiture;
    }

    public void trouver() {
        synchronized (this) {
            parking.prendrePlace(this.voiture);
            try {
                this.wait(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void ekhroj() {
        parking.sortir(this.voiture);
    }


    public synchronized void assena() {
        try {

            long waiting = new Random().nextInt(5000) + 9000;
//            long waiting = 2000;

            System.out.println("Waiting");
            this.wait(waiting);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        MainWindow.getEntree().P();
        if (this.getVoiture().getClient().getType()==ClientType.HANDICAP)
            MainWindow.getVideHandi().P();
        else
            MainWindow.getVideNormal().P();
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
        if (this.getVoiture().getClient().getType() == ClientType.HANDICAP)
            MainWindow.getVideHandi().V();
        else
            MainWindow.getVideNormal().V();
    }

    @Override
    public int compareTo(Object o) {
        SeGarer other = (SeGarer) o;
        if (this.voiture.getClient().getType() == other.getVoiture().getClient().getType())
            return 0;
        if (this.voiture.getClient().getType() == ClientType.HANDICAP &&
                other.getVoiture().getClient().getType() != ClientType.HANDICAP)
            return -1;
        if (this.voiture.getClient().getType() != ClientType.HANDICAP &&
                other.getVoiture().getClient().getType() == ClientType.HANDICAP)
            return 1;
        if (this.voiture.getClient().getType() == ClientType.ABONNE &&
                other.getVoiture().getClient().getType() == ClientType.NORMAL)
            return -1;
        if (this.voiture.getClient().getType() == ClientType.NORMAL &&
                other.getVoiture().getClient().getType() != ClientType.NORMAL)
            return 1;
        else return 0;
    }
}

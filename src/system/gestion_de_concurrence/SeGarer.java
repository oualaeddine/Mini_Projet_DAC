package system.gestion_de_concurrence;

import system.enums.ClientType;
import system.models.Parking;
import ui.GraphicCar;
import ui.MainWindow;

import java.util.Random;

public class SeGarer extends Thread implements Comparable {
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

            long waiting = new Random().nextInt(5000) + 25000;
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

    @Override
    public String toString() {
        return getName() +
                ", voiture : " + voiture.getMatricule();
    }
}

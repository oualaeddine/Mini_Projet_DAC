package system;

import sun.applet.Main;
import system.models.Parking;
import ui.GraphicCar;
import ui.MainWindow;

public class SeGarer extends Thread{
    private Parking parking;
    private GraphicCar voiture;
    public SeGarer(Parking parking,GraphicCar voiture){
        this.parking=parking;
        this.voiture=voiture;
    }

    public void trouver(){
        parking.prendrePlace(this.voiture);
    }


    public synchronized void assena(){
        try {
            long waiting=(long) Math.random()*300;
            System.out.println("Waiting");
            this.wait(waiting);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run(){
        MainWindow.getEntree().P();
        MainWindow.getVide().P();
        //Trouver une place
        trouver();
        MainWindow.getEntree().V();
        //yroh ygari w yebqa ltem pendant X
        assena();
        MainWindow.getSortie().P();
        //Sortir et libérer la place
        MainWindow.getSortie().V();
        MainWindow.getVide().V();

    }
}

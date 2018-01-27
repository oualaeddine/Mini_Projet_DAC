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
    /**
     * La création du thread se fait en lui attribuant:
     * Le parking dans lequel la voiture va se garer
     * La voiture qui va effectuer le processus
     * Un nom (pour des raisons de distinctions entre les voitures sur la console)*/

    public SeGarer(Parking parking, GraphicCar voiture, String s) {
        this.parking = parking;
        this.voiture = voiture;
        setName(s);
        setPriority(voiture.getPriorityInt());
    }
    /**
     * Cette méthode demande à la voiture (à laquelle on a donné le droit de so garer)
     * de trouver la place libre la plus proche et d'aller de placer dessus.
     * Cette méthode n'est appélée que lorsque l'on est sur qu'il y a au moins une place vide*/
    private void seGare() {
        synchronized (this) {
            try {
                this.wait(700); /**Le but de ce wait est de ralentir la procédure pour qu'elle ne soit
                                            pas trop rapide lors de la simulation*/
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(this.toString() + " is entering!");
            MainWindow.nbrVoituresEntrees++;
            parking.prendrePlace(this.voiture);
        }
    }
    /**
     * Cette méthode fait sortir la voiture du parking
     * */
    private synchronized void sors() {
        parking.sortir(this.voiture);
        System.out.println(this.toString() + " is exiting!");
    }

    /**
     * Cette méthode définit aléatoirement le temps d'attente des voitures
     * selon les valeurs max/min définies dans la classe Params.java
     */
    private synchronized void attends() {
        try {
            long waiting = new Random().nextInt(NBR_SECONDES_PARKING_MAX * 1000) + NBR_SECONDES_PARKING_MIN * 1000;
            System.out.println(this.toString() + " is in the park for : " + waiting + " ms!");
            this.wait(waiting);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    /**
     * Cette méthode va définir le processus qu'executent les voitures (tout types confondus)
     * Le choix des sémaphores appelés s'effectue selon le type des voiture s*/
    @Override
    public void run() {
        if (this.getVoiture().getClient().getType() != ClientType.HANDICAP)
            /**Selon le type de client, demander le 1er accès au parking en vérifiant s'il y a
             * une place libre*/
            MainWindow.getVideNormal().P(this);
        else
            MainWindow.getVideHandi().P(this);

        /**Demander le 2ème accès au parking en vérifiant que l'entrée est libre*/
        MainWindow.getEntree().P(this);
        //Trouver une place après avoir reçu le signal qu'une place est libre et se garer
        seGare();

        /**Envoyer un signal que l'entrée est désormais libre*/
        MainWindow.getEntree().V(this);
        //Attendre pendant X temps
        attends();

        /**Demander l'accès à la sortie pour que 2 véhicules ne sortent pas en même temps*/
        MainWindow.getSortie().P(this);
        //Sortir et libérer la place
        sors();
        /**Envoyer un signal pour dire que la sortie est désormais libre*/
        MainWindow.getSortie().V(this);

        if (this.getVoiture().getClient().getType() == ClientType.HANDICAP)
            /**Selon le type de client, signaler qu'une place s'est libérée*/
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

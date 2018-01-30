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

import ui.MainWindow;

/**
 * Nous avons implementé notre propre semaphore sans étendre la classe
 * java.util.concurrent.Semaphore
 * afin d'appliquer les notions qu'on a appris dans le module DAC
 */

@SuppressWarnings("ALL")
public class Semaphore {
    private int n;
    private String name;

    public Semaphore(int max, String s) {
        this.n = max;
        this.name = s;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * cette méthode peut être remplacée par la méthode acquire()
     * si on étend la classe java.util.concurrent.Semaphore
     */
    synchronized void P(SeGarer sg) {
        n--;
        log(sg, "aquiring");
        if (n < 0) {
            try {
                log(sg, "waiting!");
                MainWindow.updateMainViewSema(name, n);
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Cette méthode peut être remplacée par la méthode release()
     * si on étend la classe java.util.concurrent.Semaphore
     */
    synchronized void V(SeGarer sg) {
        n++;
        log(sg, "releasing");
        MainWindow.updateMainViewSema(name, n);
        notify();
    }

    private void log(SeGarer sg, String action) {
        System.out.println("SEMAPHORE : " + name + " = " + n + " || " + sg + " is " + action);
    }
}

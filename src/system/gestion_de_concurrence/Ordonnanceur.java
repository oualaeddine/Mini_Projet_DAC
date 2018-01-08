package system.gestion_de_concurrence;

import java.util.Collections;
import java.util.LinkedList;

public class Ordonnanceur {

    private static LinkedList<SeGarer> seGarers = new LinkedList<>();

    public Ordonnanceur(LinkedList<SeGarer> list) {
        seGarers = list;
    }

    private static LinkedList<SeGarer> getFileDAttente() {
        LinkedList<SeGarer> seGarerLinkedList = new LinkedList<>();
        /*for (SeGarer seGarer : seGarers) {
            if (seGarer.getVoiture().getClient().getType() == ClientType.HANDICAP) {
                seGarerLinkedList.add(seGarers.pop());
            }
        }
        for (SeGarer seGarer : seGarers) {
            if (seGarer.getVoiture().getClient().getType() == ClientType.ABONNE) {
                seGarerLinkedList.add(seGarers.pop());
            }
        }
        seGarers.clear();
*/
        Collections.sort(seGarers);
        seGarerLinkedList.addAll(seGarers);
        seGarers.clear();
        return seGarerLinkedList;
    }

    public static void notifyLiDalethom() {
        for (SeGarer seGarer : getFileDAttente()) {
            seGarer.notify();
        }
    }

    public void startAll() {
        for (SeGarer seGarer : getFileDAttente()) {
            seGarer.start();
        }
    }

    public static void addVoiture(SeGarer voiture) {
        seGarers.add(voiture);
    }

}

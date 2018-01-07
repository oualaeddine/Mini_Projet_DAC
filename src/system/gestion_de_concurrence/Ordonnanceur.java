package system.gestion_de_concurrence;

import java.util.Collections;
import java.util.LinkedList;

public class Ordonnanceur {

    private LinkedList<SeGarer> seGarers;

    public Ordonnanceur(LinkedList<SeGarer> list) {
        this.seGarers = list;
    }

    private synchronized LinkedList<SeGarer> getFileDAttente() {
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

    public void notifyLiDalethom() {
        for (SeGarer seGarer : getFileDAttente()) {
            seGarer.notify();
        }
    }

    public void startAll() {
        for (SeGarer seGarer : getFileDAttente()) {
            seGarer.start();
        }
    }
}

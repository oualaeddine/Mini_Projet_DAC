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
        /*for (SeGarer seGarer : getFileDAttente()) {
            seGarer.notify();
        }
        LinkedList<SeGarer> fileDAttente = getFileDAttente();
        for (int i = 0, fileDAttenteSize = fileDAttente.size(); i < fileDAttenteSize; i++) {
            SeGarer sg = fileDAttente.get(i);
            sg.notify();
            System.out.println(sg.toString());
            return;
        }*/
    }

    public void startAll() {
        for (SeGarer seGarer : seGarers) {
            // int i = new Random().nextInt(seGarers.size());
            seGarer.start();
        }
    }

    public static void addVoiture(SeGarer voiture) {
        seGarers.add(voiture);
    }

}

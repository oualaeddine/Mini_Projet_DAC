package system;

import system.enums.ClientType;
import ui.GraphicCar;

import java.util.LinkedList;

public class Ordonnanceur {

    private LinkedList<SeGarer> seGarers;
    public Ordonnanceur(LinkedList<SeGarer> list){
        this.seGarers=list;
    }

    public LinkedList<SeGarer> getFileDAttente() {
        LinkedList<SeGarer> seGarerLinkedList = new LinkedList<>();
        for (SeGarer seGarer:seGarers
             ) {
            if (seGarer.getVoiture().getClient().getType()== ClientType.HANDICAP)
                seGarerLinkedList.add(seGarer);
        }
        for (SeGarer seGarer:seGarers
                ) {
            if (seGarer.getVoiture().getClient().getType()==ClientType.ABONNE)
                seGarerLinkedList.add(seGarer);
        }
        for (SeGarer seGarer:seGarers
             ) {
            seGarerLinkedList.add(seGarer);
        }
        return seGarerLinkedList;
    }

    public void notifyLiDalethom(){
        for (SeGarer seGarer:seGarers
             ) {
            seGarer.notify();
        }
    }
}

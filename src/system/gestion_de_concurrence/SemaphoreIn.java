package system.gestion_de_concurrence;

public class SemaphoreIn extends Semaphore {
    private int n;
    private String name;

    public SemaphoreIn(int max, String s) {
        this.n = max;
        this.name = s;
    }

    @Override
    public void P(SeGarer sg) {
        n--;
        if (n < 0) {
            synchronized (this) {
                try {
                    //      Ordonnanceur.addVoiture(sg);
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    @Override
    synchronized void V(SeGarer sg) {
        n++;
        // Ordonnanceur.notifyLiDalethom();
        this.notify();
    }
}

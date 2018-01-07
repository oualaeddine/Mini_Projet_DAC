package system.gestion_de_concurrence;

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

    public synchronized void P() {
        n--;
        if (n < 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void V() {
        n++;
        notify();
    }
}

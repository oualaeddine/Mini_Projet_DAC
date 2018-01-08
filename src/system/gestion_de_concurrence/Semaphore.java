package system.gestion_de_concurrence;

public class Semaphore {
    private int n;
    private String name;

    public Semaphore(int max, String s) {
        this.n = max;
        this.name = s;
    }

    public Semaphore() {
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

    synchronized void P(SeGarer sg) {
        n--;
        log(sg, "requiring");
        if (n < 0) {
            try {
                log(sg, "waiting!");
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    synchronized void V(SeGarer sg) {
        n++;
        log(sg, "releasing");
        notify();
    }

    void log(SeGarer sg, String action) {
        System.out.println("SEMA : " + name + " = " + n + " || " + sg + " is " + action);
    }
}

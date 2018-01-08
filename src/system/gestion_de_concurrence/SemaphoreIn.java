package system.gestion_de_concurrence;

public class SemaphoreIn extends Semaphore {
    private int n;
    private String name;
    public SemaphoreIn(int max, String s) {
        this.n=max;
        this.name=s;
    }
    @Override
    public void P(){
        n--;
        if (n < 0) {
            
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

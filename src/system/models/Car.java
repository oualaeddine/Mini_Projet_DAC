package system.models;

public class Car {
    private int matricule;
    private Client client;

    public Car() {
    }

    public int getMatricule() {
        return matricule;
    }

    public void setMatricule(int matricule) {
        this.matricule = matricule;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Car{" +
                "matricule=" + matricule +
                ", client=" + client +
                '}';
    }
}

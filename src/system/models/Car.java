package system.models;

public class Car {
    private String matricule;
    private Client client;

    Car() {
        client = new Client();
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
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

package system.models;

public class Voiture {
    private int matricule;
    private Client client;

    public Voiture(int matricule, Client client) {
        this.matricule = matricule;
        this.client = client;
    }
}

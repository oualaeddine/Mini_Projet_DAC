package system.models;

import system.enums.ClientType;

public class Client {
    private ClientType type;
    private int id;

    public Client() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ClientType getType() {
        return type;
    }

    public void setType(ClientType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Client{" +
                "type=" + type +
                ", id=" + id +
                '}';
    }
}

/******************************************************************************
 *                                                                            *
 *  (C) Copyright ${year} Berrehal Ouala Eddine & Benghezal Ines.             *
 *  G2 L3 GL.                                                                 *
 *  Mini projet Module DAC.                                                   *
 *  Simulation d'un parking                                                   *
 *  avec gestion des concurrences en utilisant des sémaphores                 *
 *                                                                            *
 ******************************************************************************/

package system.models;

import system.enums.ClientType;

@SuppressWarnings("unused")
public class Client {
    private ClientType type;
    private int id;

    Client() {
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

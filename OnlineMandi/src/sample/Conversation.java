package sample;

import java.sql.Timestamp;

public class Conversation {
    private String client;
    private String nameOfClient;
    private String convo;
    private int seen;

    public Conversation(String client, String convo, int seen,String nameOfClient) {
        this.client = client;
        this.nameOfClient = nameOfClient;
        this.convo = convo;
        this.seen = seen;
    }

    public String getNameOfClient() {
        return nameOfClient;
    }

    public void setNameOfClient(String nameOfClient) {
        this.nameOfClient = nameOfClient;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getConvo() {
        return convo;
    }

    public void setConvo(String convo) {
        this.convo = convo;
    }

    public int getSeen() {
        return seen;
    }

    public void setSeen(int seen) {
        this.seen = seen;
    }
}

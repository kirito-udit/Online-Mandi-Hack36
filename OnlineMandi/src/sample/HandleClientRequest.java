package sample;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import java.io.*;
import java.net.Socket;

public class HandleClientRequest implements Runnable {
    private String phoneNumber;
    private String fullName;
    private Image image;
    private Socket socket;
    ObjectInputStream ois;    //Input Stream of client socket
    ObjectOutputStream oos;   //Output Stream of client socket

    public HandleClientRequest(String fullName,Image image,String phoneNumber,Socket socket,ObjectInputStream ois,ObjectOutputStream oos) {
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
        this.socket = socket;
        this.image = image;
        this.ois = ois;
        this.oos = oos;
    }

    @Override
    public void run() {
        System.out.println("Client name: "+fullName);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ObjectInputStream getOis() {
        return ois;
    }

    public void setOis(ObjectInputStream ois) {
        this.ois = ois;
    }

    public ObjectOutputStream getOos() {
        return oos;
    }

    public void setOos(ObjectOutputStream oos) {
        this.oos = oos;
    }
}

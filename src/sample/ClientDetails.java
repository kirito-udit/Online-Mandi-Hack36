package sample;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientDetails {
    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public ObjectOutputStream getOos() {
        return oos;
    }

    public void setOos(ObjectOutputStream oos) {
        this.oos = oos;
    }

    private String phoneNo;
    private ObjectOutputStream oos;

    public ClientDetails(String phoneNo, ObjectOutputStream oos) {
        this.phoneNo = phoneNo;
        this.oos = oos;
    }
}

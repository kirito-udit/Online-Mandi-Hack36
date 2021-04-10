package sample;

import javafx.scene.image.Image;

import java.io.Serializable;

public class FullNameProfilePic implements Serializable {
    private String fullName;
    private Image image;
    public FullNameProfilePic(String fullName, Image image){
        this.fullName=fullName;
        this.image=image;
    }

    public String getFullName() {
        return fullName;
    }

    public Image getImage() {
        return image;
    }
}

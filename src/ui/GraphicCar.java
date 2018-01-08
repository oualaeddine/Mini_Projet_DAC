package ui;

import system.enums.ClientType;
import system.enums.Direction;
import system.models.MovingCar;
import system.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;


public class GraphicCar extends MovingCar {
    private String[] vehicules = {
            "/images/buses.png",
            "/images/by1.png",
            "/images/by2.png",
            "/images/by3.png",
            "/images/by4.png",
            "/images/car1.png",
            "/images/car2.png",
            "/images/car3.png",
            "/images/car4.png",
            "/images/other1.png",
            "/images/other2.png",
            "/images/other3.png",
            "/images/other5.png",
            "/images/truck1.png",
            "/images/truck2.png",
            "/images/truck3.png",
            "/images/truck4.png",
    };

    private String image;
    private JLabel label;

    GraphicCar(ClientType clientType) {
        super();
        this.getClient().setType(clientType);
        switch (clientType) {
            case NORMAL:
                image = vehicules[6];
                setupIconSize(1, 1);
                break;
            case HANDICAP:
                image = vehicules[5];
                setupIconSize(1, 1);
                break;
            case ABONNE:
                image = vehicules[7];
        }
    }
/*
    GraphicCar() {
        image = getRandomVehicle();
        setupIconSize(1, 1);
    }

    private String getRandomVehicle() {
        int i = new Random().nextInt(vehicules.length);
        //   System.out.println(vehicules[i]);
        return vehicules[i];
    }*/

    public void setupIconSize(int width, int height) {
        label = new JLabel();
        label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ImageIcon yourImage = new ImageIcon(getClass().getResource(image/*vehicules[6]*/));
        ImageIcon scaledImg = new ImageIcon(yourImage.getImage().getScaledInstance(width, height, Image.SCALE_FAST));
        label.setIcon(ImageUtils.rotateImg(scaledImg, Direction.E)); // NOI18N
        label.setIconTextGap(1);
    }

    public JLabel getLabel() {
        return label;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "GraphicCar{" +
                ", image=" + image +
                ", label=" + label +
                "} " + super.toString();
    }
}

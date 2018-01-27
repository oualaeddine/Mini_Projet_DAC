/******************************************************************************
 *                                                                            *
 *  (C) Copyright ${year} Berrehal Ouala Eddine & Benghezal Ines.             *
 *  G2 L3 GL.                                                                 *
 *  Mini projet Module DAC.                                                   *
 *  Simulation d'un parking                                                   *
 *  avec gestion des concurrences en utilisant des sémaphores                 *
 *                                                                            *
 ******************************************************************************/
package ui;

import system.enums.ClientType;
import system.enums.Direction;
import system.models.MovingCar;
import system.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;

/**Voiture pouvant se mouvoir et possédant une icone propre à son type
 * C'est aussi ici que les priorités des voitures sont attribués selon leurs types*/
public class GraphicCar extends MovingCar {
    @SuppressWarnings("FieldCanBeLocal")
    private final String[] vehicules = {
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

    public GraphicCar(ClientType clientType) {
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
                image = vehicules[13];
                setupIconSize(1, 1);
                break;
        }
    }

    public void setupIconSize(int width, int height) {
        label = new JLabel();
        label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ImageIcon yourImage = new ImageIcon(getClass().getResource(image/*vehicules[6]*/));
        ImageIcon scaledImg = new ImageIcon(yourImage.getImage().getScaledInstance(width, height, Image.SCALE_FAST));
        label.setIcon(ImageUtils.rotateImg(scaledImg, Direction.E)); // NOI18N
        label.setIconTextGap(1);
    }

    public void setupIconSize(int width, int height, Direction dir) {
        label = new JLabel();
        label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ImageIcon yourImage = new ImageIcon(getClass().getResource(image/*vehicules[6]*/));
        ImageIcon scaledImg = new ImageIcon(yourImage.getImage().getScaledInstance(width, height, Image.SCALE_FAST));
        label.setIcon(ImageUtils.rotateImg(scaledImg, dir)); // NOI18N
        label.setIconTextGap(1);
    }

    public JLabel getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "GraphicCar{" +
                ", image=" + image +
                ", label=" + label +
                "} " + super.toString();
    }

    public int getPriorityInt() {
        switch (getClient().getType()) {
            case NORMAL:
                return Thread.MIN_PRIORITY;
            case HANDICAP:
                return Thread.MAX_PRIORITY;
            case ABONNE:
                return Thread.NORM_PRIORITY;
        }
        return 0;
    }
}

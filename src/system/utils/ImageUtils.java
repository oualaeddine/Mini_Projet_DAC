package system.utils;

import system.enums.Direction;

import javax.swing.*;

public class ImageUtils {


    public static RotatedIcon rotateImg(ImageIcon imageIcon, Direction direction) {
        double rotation;
        switch (direction) {
            case N:
                rotation = -90;
                break;
            case S:
                rotation = 90;
                break;
            case O:
                rotation = -180;
                break;
            case E:
                rotation = 0;
                break;
            case NE:
                rotation = -45;
                break;
            case NO:
                rotation = 45;
                break;
            case SE:
                rotation = -135;
                break;
            case SO:
                rotation = 135;
                break;
            default:
                rotation = 0;
                break;
        }
        return new RotatedIcon(imageIcon, rotation);
    }

}

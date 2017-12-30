package system.utils;

import system.enums.Direction;

import javax.swing.*;

public class ImageUtils {


    public static RotatedIcon rotateImg(ImageIcon imageIcon, Direction direction) {
        return new RotatedIcon(imageIcon, 45.0);
    }

}

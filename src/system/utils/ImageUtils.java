package system.utils;

import javax.swing.*;

public class ImageUtils {


    public static RotatedIcon rotateImg(ImageIcon imageIcon, double direction) {
        return new RotatedIcon(imageIcon, direction);
    }

}

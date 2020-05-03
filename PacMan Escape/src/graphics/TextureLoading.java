package graphics;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TextureLoading {
    public static BufferedImage pacman0, pacman1, ghost;

    public static void loadImages() {
        try {
            pacman0 = ImageIO.read(new File("resources/pacman0.png"));
            pacman1 = ImageIO.read(new File("resources/pacman1.png"));
            ghost = ImageIO.read(new File("resources/ghost.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

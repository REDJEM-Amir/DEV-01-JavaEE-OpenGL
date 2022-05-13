package main.rendering;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL11.*;

public class Texture {
    public static final Texture ENV = new Texture("env.png");
    public static final Texture MOBS = new Texture("mobs.png");
    public static final Texture WEAPONS = new Texture("weapons.png");
    public static final Texture MISC = new Texture("misc.png");

    public static final Texture ITEMS = new Texture("items.png");
    public static final Texture SILOUHETTE = new Texture("sil.png");
    public static final Texture POTATO = new Texture("potato.png");
    public static final Texture BG = new Texture("menu.jpg");

    public int width, height;
    public int id;

    public Texture(String path) {
        int[] pixels = null;
        try {
            BufferedImage image = ImageIO.read(Texture.class.getResource("/tex/" + path));
            width = image.getWidth();
            height = image.getHeight();
            pixels = new int[width * height];
            image.getRGB(0, 0, width, height, pixels, 0, width);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int[] data = new int[pixels.length];
        for (int i = 0; i < data.length; i++) {
            int a = (pixels[i] & 0xff000000) >> 24;
            int r = (pixels[i] & 0xff0000) >> 16;
            int g = (pixels[i] & 0xff00) >> 8;
            int b = (pixels[i] & 0xff);

            data[i] = a << 24 | b << 16 | g << 8 | r;
        }

        int id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, id);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

        this.id = id;
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public static void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }
}

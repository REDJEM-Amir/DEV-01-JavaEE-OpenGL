package main.rendering;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import main.Input;

public class Gui {
	
	private static Texture font = new Texture("font.png");
	private static String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
								+ "0123456789.;,:=+-*/\\()!?@ "; 

	public static void drawString(String text, int x, int y, int size) {
		text = text.toUpperCase();
		
		font.bind();
		glColor4f(1, 1, 1, 1);
		glBegin(GL_QUADS);
		for (int i = 0; i < text.length(); i++) {
			drawChar(text.charAt(i), x + i * (int)(size * (7.0f / 8.0f)), y, size);
		}
		glEnd();
		Texture.unbind();
	}
	
	public static void drawString(String text, int x, int y, int size, boolean center) {
		text = text.toUpperCase();
		if (center) x -= (text.length() * (int)(size * (7.0f / 8.0f))) /2;
		font.bind();
		glColor4f(1, 1, 1, 1);
		glBegin(GL_QUADS);
		for (int i = 0; i < text.length(); i++) {
			drawChar(text.charAt(i), x + i * (int)(size * (7.0f / 8.0f)), y, size);
		}
		glEnd();
		Texture.unbind();
	}
	
	public static void drawString(String text, int x, int y, int size, boolean center, boolean colored) {
		text = text.toUpperCase();
		if (center) x -= (text.length() * (int)(size * (7.0f / 8.0f))) /2;
		font.bind();
		if (!colored) glColor4f(1, 1, 1, 1);
		glBegin(GL_QUADS);
		for (int i = 0; i < text.length(); i++) {
			drawChar(text.charAt(i), x + i * (int)(size * (7.0f / 8.0f)), y, size);
		}
		glEnd();
		Texture.unbind();
	}
	
	public static void drawChar(char character, int x, int y, int size) {
		int i = chars.indexOf(character);
		int xo = i % 26;
		int yo = i / 26;
		
		glTexCoord2f((0 + xo) / 26.0f, (0 + yo) / 6.0f); glVertex2f(x, y);
		glTexCoord2f((1 + xo) / 26.0f, (0 + yo) / 6.0f); glVertex2f(x + size, y);
		glTexCoord2f((1 + xo) / 26.0f, (1 + yo) / 6.0f); glVertex2f(x + size, y + size);
		glTexCoord2f((0 + xo) / 26.0f, (1 + yo) / 6.0f); glVertex2f(x, y + size);
	}
	
	public static void drawQuad(float x, float y, float w, float h) {
		glBegin(GL_QUADS);
			glVertex2f(x, y);
			glVertex2f(x + w, y);
			glVertex2f(x + w, y + h);
			glVertex2f(x, y + h);
		glEnd();
	}
	
	public static void drawTexturedQuad(int x, int y, int w, int h, float[][] texCoords) {
		glBegin(GL_QUADS);
			glTexCoord2f(texCoords[0][0], texCoords[0][1]); glVertex2f(x, y);
			glTexCoord2f(texCoords[1][0], texCoords[1][1]); glVertex2f(x + w, y);
			glTexCoord2f(texCoords[2][0], texCoords[2][1]); glVertex2f(x + w, y + h);
			glTexCoord2f(texCoords[3][0], texCoords[3][1]); glVertex2f(x, y + h);
		glEnd();
	}
	
	public static void color(float r, float g, float b, float a) {
		glColor4f(r, g, b, a);
	}
	
	public static boolean button(String text, int x, int y, int w) {
		float mx = Mouse.getX();
		float my = Display.getHeight() - Mouse.getY();
		
		Gui.color(0.5f, 0.5f, 0.5f, 1);
		if (mx > x - w/2 && my > y - 16 && mx < x + w/2 && my < y + 16) {
			Gui.color(1, 0, 0, 1);

			if (Input.r.getMouseDown(0)) {
				return true;
			}
		}
		
		x -= w/2;
		y -= 16;
		Gui.drawQuad(x, y, w, 32);
		Gui.color(1, 1, 1, 1);
		Gui.drawString(text, x + w / 2 - (text.length() * (int)(24 * (7.0f / 8.0f)) / 2), y + 4, 24);
		
		return false;
	}
	
	public static boolean button(String text, int x, int y, int w, boolean centered) {
		float mx = Mouse.getX();
		float my = Display.getHeight() - Mouse.getY();
		
		Gui.color(0.5f, 0.5f, 0.5f, 1);
		if (mx > x - w/2 && my > y - 16 && mx < x + w/2 && my < y + 16) {
			Gui.color(1, 0, 0, 1);
			
			if (Input.r.getMouseDown(0)) {
				return true;
			}
		}
		
		x -= w/2;
		y -= 16;
		Gui.drawQuad(x, y, w, 32);
		Gui.color(1, 1, 1, 1);
		Gui.drawString(text, x + w / 2 - (text.length() * (int)(24 * (7.0f / 8.0f)) / 2), y + 4, 24);
		
		return false;
	}
}

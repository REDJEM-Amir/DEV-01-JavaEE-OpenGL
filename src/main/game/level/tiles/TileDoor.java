package main.game.level.tiles;

import org.lwjgl.opengl.Display;

import main.game.Game;
import main.game.entities.Player;
import main.game.level.Level;
import main.rendering.Gui;
import main.rendering.Renderer;

public class TileDoor extends Tile {

	float angle = 0;
	public int key;
	boolean locked = false;

	int doorTex = 0;

	public TileDoor(float x, float y, int key, Level level) {
		super(x, y);
		this.key = (key & 0xff0000) >> 16;

		if (this.key != 0) {
			locked = true;
		}
		tex = 0;
		solid = true;

		if (level.level == 2) {
			tex = 2;
			doorTex = 2;
		}
	}

	public void update() {
		Player e = Game.getGame().getPlayer();
		if (e.getKey(key) != -1) {
			locked = false;
		}
		if (!locked) {
			float px = Game.getGame().getPlayer().x;
			float py = Game.getGame().getPlayer().y;
			float xx = (x + 0.5f) - px;
			float yy = (y + 0.5f) - py;
			float dist = (float)Math.sqrt(xx * xx + yy * yy);

			if (dist < 1.5f) {
				angle += 40;
				solid = false;
			}else {

				solid = true;
			}
		}

		angle *= 0.7f;
	}

	public void render(Level level) {

		if (level.isWall(x + 1, y, 0) && level.isWall(x - 1, y, 0)) {
			Renderer.renderDoor(x, y, angle, false, key, doorTex);
		}else {
			Renderer.renderDoor(x, y, angle, true, key, doorTex);
		}
	}

	public void renderGUI() {
		if (locked) {
			float px = Game.getGame().getPlayer().x;
			float py = Game.getGame().getPlayer().y;
			float xx = (x + 0.5f) - px;
			float yy = (y + 0.5f) - py;
			float dist = (float)Math.sqrt(xx * xx + yy * yy);

			if (dist < 1.5f) {
				Gui.drawString("La porte est verrouillee", Display.getWidth() / 2, Display.getHeight() / 4, 32, true);
			}
		}
	}

	public static boolean doorColor(int color) {
		if (color == 0xff000000) return true;
		if (color == 0xff100000) return true;
		if (color == 0xff200000) return true;
		if (color == 0xff300000) return true;
		if (color == 0xff400000) return true;
		if (color == 0xff500000) return true;
		if (color == 0xff600000) return true;
		if (color == 0xff700000) return true;
		if (color == 0xff800000) return true;
		if (color == 0xff900000) return true;

		return false;
	}
}

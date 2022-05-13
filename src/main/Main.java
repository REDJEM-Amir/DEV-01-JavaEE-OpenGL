package main;

import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.GLU;

import main.audio.Audio;
import main.audio.AudioManager;
import main.game.Game;
import main.rendering.Gui;

@SuppressWarnings("All")
public class Main {

	private static Main main;
	public static final String NAME = "Rapido Game";
	private AudioManager audioManager;

	public static boolean showFPS = true;

	public Main() {
		main = this;

		audioManager = new AudioManager();
		audioManager.initAudio();
	}

	public void update() {
		GameState.update();
	}

	public void render() {

		GameState.render();
	}

	public void renderGUI() {
		GameState.renderGUI();
		if (showFPS) {
			Gui.drawString(FPS + " FPS", 5, 5, 16);
		}
	}

	// ---
	boolean running = false;
	public static int FPS;

	public void start() {
		running = true;
		loop();
	}

	public void loop() {

		long before = System.nanoTime();
		double ns = 1000000000.0 / 60.0;

		int frames = 0;
		long timer = System.currentTimeMillis();

		while (running) {
			if (Display.isCloseRequested())
				break;

			if (Display.wasResized()) {
				glViewport(0, 0, Display.getWidth(), Display.getHeight());
			}

			long now = System.nanoTime();
			if (now - before > ns) {
				Input.u.update();
				update();
				before += ns;
			} else {
				Input.r.update();
				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

				glMatrixMode(GL_PROJECTION);
				glLoadIdentity();
				GLU.gluPerspective(70.0f, (float) (Display.getWidth()) / (float) Display.getHeight(), 0.1f, 1000.0f);
				glMatrixMode(GL_MODELVIEW);
				glLoadIdentity();

				glEnable(GL_DEPTH_TEST);

				if (Game.getGame() != null) {
					glRotatef(40, 1, 0, 0);
					glTranslatef(-Game.getGame().getPlayer().x, -2, -Game.getGame().getPlayer().y - 3);
				}

				render();

				glMatrixMode(GL_PROJECTION);
				glLoadIdentity();
				glOrtho(0, Display.getWidth(), Display.getHeight(), 0, -1, 1);
				glMatrixMode(GL_MODELVIEW);
				glLoadIdentity();

				glDisable(GL_DEPTH_TEST);
				glBindTexture(GL_TEXTURE_2D, 0);

				renderGUI();
				frames++;
				Display.update();
			}
			if (System.currentTimeMillis() - timer > 1000) {
				FPS = frames;
				frames = 0;

				timer += 1000;
			}

		}
		exit();
	}

	public static void exit() {
		Main.getMain().audioManager.cleanAudio();
		Game.getGame().exit();
		AL.destroy();
		Display.destroy();
		System.exit(0);
	}

	public static Main getMain() {
		return main;
	}

	public AudioManager getAudioManager() {
		return audioManager;
	}

	public Audio getAudio() {
		return audioManager.getAudio();
	}

	public void playSound(int id) {
		audioManager.getAudio().play(id);
	}

	public static void main(String[] args) {
		try {
			Display.setFullscreen(true);
			Display.setTitle(NAME + " InDev 1");
			Display.setResizable(true);
			Display.create();
			AL.create();

			glEnable(GL_TEXTURE_2D);

			glEnable(GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

			glEnable(GL_ALPHA_TEST);
			glAlphaFunc(GL_GREATER, 0);

			glEnable(GL_FOG);

			float[] fogFloatColor = new float[] { 0, 0, 0, 1 };
			FloatBuffer fogColor = (FloatBuffer) BufferUtils.createFloatBuffer(4).put(fogFloatColor).flip();

			glFog(GL_FOG_COLOR, fogColor);
			glFogf(GL_FOG_MODE, GL_LINEAR);
			glFogf(GL_FOG_START, 0);

		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		new Main().start();
	}
}

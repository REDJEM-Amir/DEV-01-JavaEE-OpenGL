package main;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
@SuppressWarnings("All")
public class Input {

	public static Input r = new Input();
	public static Input u = new Input();

	private static List<Integer> keys = new ArrayList<Integer>();
	private static List<Integer> keysDown = new ArrayList<Integer>();

	private static List<Integer> buttons = new ArrayList<Integer>();
	private static List<Integer> buttonsDown = new ArrayList<Integer>();

	public void update() {
		keysDown.clear();

		for (int i = 0; i < 256; i++) {
			if (getKey(i) && !keys.contains(i)) {
				keysDown.add(i);
			}
		}

		keys.clear();

		for (int i = 0; i < 256; i++) {
			if (getKey(i)) {
				keys.add(i);
			}
		}

		buttonsDown.clear();

		for (int i = 0; i < 5; i++) {
			if (getMouse(i) && !buttons.contains(i)) {
				buttonsDown.add(i);
			}
		}

		buttons.clear();

		for (int i = 0; i < 5; i++) {
			if (getMouse(i)) {
				buttons.add(i);
			}
		}
	}

	public boolean getKeyDown(int key) {
		return keysDown.contains(key);
	}

	public boolean getKey(int key) {
		return Keyboard.isKeyDown(key);
	}

	public boolean getMouseDown(int m) {
		return buttonsDown.contains(m);
	}

	public boolean getMouse(int m) {
		return Mouse.isButtonDown(m);
	}
}

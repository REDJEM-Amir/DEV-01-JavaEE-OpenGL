package main;

import main.game.Game;
import main.menu.Menu;
@SuppressWarnings("All")
public class GameState {

	static boolean changingState = false;

	public enum State {
		IN_GAME, IN_MENU
	}

	private static State state = State.IN_MENU;

	public static void setState(State state) {
		GameState.state = state;
		changingState = true;
	}

	private static Game game;
	private static Menu menu;

	public static void update() {
		if (changingState) changingState = false;
		if (state == State.IN_GAME) {
			if (game == null) {
				game = new Game();
			}

			game.update();
		}

		if (changingState) return;

		if (state == State.IN_MENU) {
			if (menu == null) {
				menu = new Menu();
			}

			menu.update();
		}
	}

	public static void render() {
		if (state == State.IN_GAME) {
			if (game == null) {
				game = new Game();
			}

			game.render();
		}

		if (changingState) return;

		if (state == State.IN_MENU) {
			if (menu == null) {
				menu = new Menu();
			}

			menu.render();
		}
	}

	public static void renderGUI() {
		if (state == State.IN_GAME) {
			if (game == null) {
				game = new Game();
			}

			game.renderGUI();
		}

		if (changingState) return;

		if (state == State.IN_MENU) {
			if (menu == null) {
				menu = new Menu();
			}

			menu.renderGUI();
		}
	}
}

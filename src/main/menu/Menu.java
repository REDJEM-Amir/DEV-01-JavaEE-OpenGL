package main.menu;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.Display;

import main.GameState;
import main.GameState.State;
import main.Main;
import main.ViewState;
import main.game.Game;
import main.rendering.Gui;
import main.rendering.Texture;

@SuppressWarnings("All")
public class Menu implements ViewState {
    public static boolean instruction = false;
    public static boolean option = false;
    public static boolean about = false;

    public Menu() {

    }

    public void update() {

    }

    public void render() {

    }

    public void renderGUI() {
        Texture.BG.bind();
        glBegin(GL_QUADS);
        glTexCoord2f(0, 0);
        glVertex2f(0, 0);
        glTexCoord2f(1, 0);
        glVertex2f(Display.getWidth(), 0);
        glTexCoord2f(1, 1);
        glVertex2f(Display.getWidth(), Display.getHeight());
        glTexCoord2f(0, 1);
        glVertex2f(0, Display.getHeight());
        glEnd();
        Texture.unbind();

        if (instruction) {
            renderInstrcution();
        } else if (option) {
            renderOption();
        } else if (about) {
            renderAbout();
        } else {
            renderMenu();
        }
    }

    public void renderMenu() {

        Gui.drawString(Main.NAME, Display.getWidth() / 2, Display.getHeight() / 2 - 250, 48, true);
        if (Gui.button("JOUER", Display.getWidth() / 2, Display.getHeight() / 2, 300)) {
            if (Game.paused) {
                GameState.setState(State.IN_GAME);
            } else {
                instruction = true;
                about = false;
                option = false;
            }
        }
        if (Gui.button("OPTIONS", Display.getWidth() / 2, Display.getHeight() / 2 + 44, 300)) {
            option = true;
            instruction = false;
            about = false;
        }
        if (Gui.button("A PROPOS", Display.getWidth() / 2, Display.getHeight() / 2 + 44 * 2, 300)) {
            option = false;
            instruction = false;
            about = true;
        }
        if (Gui.button("QUITTER LE JEU", Display.getWidth() / 2, Display.getHeight() / 2 + 44 * 3, 300)) {
            Main.exit();
        }
    }

    public void renderInstrcution() {
        Gui.drawString("-ZQSD- ou -WASD- ou Fleches pour se deplacer", Display.getWidth() / 2, Display.getHeight() / 2 - 300, 24, true);
        Gui.drawString("-ESPACE- pour attaquer ET -E- pour ouvrir l inventaire", Display.getWidth() / 2, Display.getHeight() / 2 - 300 + 64, 24, true);
        Gui.drawString("-G- lancer une grenade a patate", Display.getWidth() / 2, Display.getHeight() / 2 - 300 + 64 * 2, 24, true);

        Gui.drawString("De quoi parle le jeu ?", Display.getWidth() / 2, Display.getHeight() / 2 - 300 + 64 * 4, 24, true);
        Gui.drawString("Vous devez recolter le plus de pommes de terre possible !", Display.getWidth() / 2, Display.getHeight() / 2 - 300 + 64 * 5, 16, true);
        Gui.color(0, 1, 1, 1);
        Gui.drawString("REMARQUES: fabriquez une arme des que vous le pouvez, vous allez mourir ;)", Display.getWidth() / 2, Display.getHeight() / 2 - 300 + 52 * 7, 14, true, true);
        Gui.drawString("Lancer une pomme de terre peut vous sauver la vie, mais vous en avez besoin", Display.getWidth() / 2, Display.getHeight() / 2 - 300 + 56 * 7, 14, true, true);
        Gui.drawString("Faites attention lorsque vous tes dans des tunnels etroits car les choses peuvent vous blesser ;)", Display.getWidth() / 2, Display.getHeight() / 2 - 300 + 60 * 7, 14, true, true);
        Gui.color(1, 0, 0, 1);
        Gui.drawString("Avertissement", Display.getWidth() / 2, Display.getHeight() / 2 - 300 + 64 * 8, 32, true, true);
        Gui.drawString("Le jeu peut contenir des bugs, il a etait fais en 5 semaines ;)", Display.getWidth() / 2, Display.getHeight() / 2 - 300 + 63 * 9, 16, true, true);
        Gui.color(1, 1, 1, 1);
        if (Game.paused) {
            if (Gui.button("Reprendre", Display.getWidth() - 150, Display.getHeight() - 50, 200)) {
                instruction = false;
                GameState.setState(State.IN_GAME);
            }
            if (Gui.button("Menu", 150, Display.getHeight() - 50, 200)) {
                instruction = false;
                about = false;
            }
        } else {
            if (Gui.button("Demarrer", Display.getWidth() - 150, Display.getHeight() - 50, 200)) {
                instruction = false;
                GameState.setState(State.IN_GAME);
            }
            if (Gui.button("Retour", 150, Display.getHeight() - 50, 200)) {
                instruction = false;
                about = false;
            }
        }
    }

    public void renderOption() {
        if (Gui.button("Debogage FPS : " + Main.showFPS, Display.getWidth() / 2, Display.getHeight() / 2 - 200, 550, true)) {
            Main.showFPS = !Main.showFPS;
        }

        Gui.drawString("Choisissez la difficulte : " + (Game.difficulty == 3 ? "DURE ! :)" : (Game.difficulty == 2 ? "MOYEN" : (Game.difficulty == 1 ? "FACILE" : ""))), Display.getWidth() / 2, Display.getHeight() / 2 - 50, 32, true);

        if (Gui.button("FACILE", Display.getWidth() / 2 - 200, Display.getHeight() / 2 + 50, 150, true)) {
            Game.difficulty = 1;
        }
        if (Gui.button("MOYEN", Display.getWidth() / 2, Display.getHeight() / 2 + 50, 150, true)) {
            Game.difficulty = 2;
        }
        if (Gui.button("DURE", Display.getWidth() / 2 + 200, Display.getHeight() / 2 + 50, 150, true)) {
            Game.difficulty = 3;
        }

        if (Game.paused) {
            if (Gui.button("Reprendre", 150, Display.getHeight() - 50, 200)) {
                option = false;
                GameState.setState(State.IN_GAME);
            }
        } else {
            if (Gui.button("Retour", 150, Display.getHeight() - 50, 200)) {
                option = false;
            }
        }
    }

    public void renderAbout() {
        Gui.drawString("-" + Main.NAME + " est un jeu ", Display.getWidth() / 2, Display.getHeight() / 2 - 300 + 64, 32, true);
        Gui.drawString("Realise pour UE Genie Logiciel", Display.getWidth() / 2, Display.getHeight() / 2 - 300 + 64 * 2, 32, true);
        Gui.drawString("Jeu realise par REDJEM - SAYAH - BHAR", Display.getWidth() / 2, Display.getHeight() / 2 - 300 + 64 * 5, 32, true);
        Gui.drawString("Equipe @Dev4You", Display.getWidth() / 2, Display.getHeight() / 2 - 300 + 64 * 6, 32, true);
        if (Gui.button("Retour", 150, Display.getHeight() - 50, 200)) {
            about = false;
        }
    }
}

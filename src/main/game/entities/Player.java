package main.game.entities;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import main.Main;
import main.audio.Audio;
import main.game.Game;
import main.game.entities.weapons.Weapon;
import main.game.level.tiles.items.ItemGrenade;
import main.game.level.tiles.items.ItemPotion;
import main.rendering.Gui;
import main.rendering.Renderer;
import main.rendering.Texture;

public class Player extends Entity {

    public boolean attack = false;
    public boolean attacking = false;

    public List<Integer> keys;
    public List<ItemPotion> potions;
    public List<ItemGrenade> potatoes;

    PlayerInventory inv;

    public Player(float x, float y) {
        super(x, y);
        keys = new ArrayList<Integer>();
        potions = new ArrayList<ItemPotion>();
        potatoes = new ArrayList<ItemGrenade>();

        tex = 1;
        life = 10;

        inv = new PlayerInventory();
    }

    float xa, ya;
    int xd, yd;
    float smoothing = 0.8f;
    float speed = 0.1f;
    int dir = 0;
    int power;

    int attackCounter = 0;
    float attackForce = 0;

    int animTimer = 0;

    boolean throwP = false;
    int throwTimer = 0;

    boolean canUpdate = false;

    public void update() {
        if (Game.getGame().levelChange) {
            canUpdate = false;
        }
        if (!canUpdate) {
            if (Keyboard.isKeyDown(Keyboard.KEY_Z) || Keyboard.isKeyDown(Keyboard.KEY_Q) || Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_D) ||
                    Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP) || Keyboard.isKeyDown(Keyboard.KEY_LEFT) ||
                    Keyboard.isKeyDown(Keyboard.KEY_RIGHT) || Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
            } else {
                Game.getGame().levelChange = false;
                canUpdate = true;
            }
        }

        if (!canUpdate) {
            animTimer = 0;
            xa = 0;
            ya = 0;
            return;
        }

        animTimer++;
        if (attack) attack = false;
        boolean moving = false;
        if (!PlayerInventory.showInv) {
            if (Keyboard.isKeyDown(Keyboard.KEY_Z) || Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP)) {
                ya -= speed * (1 - smoothing);
                tex = 2;
                dir = 0;
                yd = -1;
                xd = 0;
                moving = true;
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
                ya += speed * (1 - smoothing);
                tex = 0;
                dir = 1;
                yd = 1;
                xd = 0;
                moving = true;
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_Q) || Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
                xa -= speed * (1 - smoothing);
                tex = 6;
                dir = 2;
                xd = -1;
                yd = 0;
                moving = true;
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
                xa += speed * (1 - smoothing);
                tex = 4;
                dir = 3;
                xd = 1;
                yd = 0;
                moving = true;
            }

            if (Keyboard.isKeyDown(Keyboard.KEY_G)) {
                if (!throwP) {
                    throwP = true;
                    if (potatoes.size() > 0) {
                        Game.getGame().getLevel().add(new main.game.entities.ItemGrenade(x, y, 0.3f, xd, yd));
                        potatoes.remove(0);
                    }
                } else {
                    throwTimer++;
                    if (throwTimer > 20) {
                        throwP = false;
                        throwTimer = 0;
                    }
                }
            }

            xa += fx;
            ya += fy;

            fx *= 0.01f;
            fy *= 0.01f;

            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
                if (attackForce < 10) {
                    attackForce += 0.25f;
                }
            } else {
                if (attackForce != 0) {
                    power = (int) attackForce;
                    attack = true;
                    attacking = true;
                }
            }
        }

        if (attacking) {
            attackCounter++;
            if (attackCounter > 10) {
                attacking = false;
                attackForce = 0;
            }
            if (dir == 0) {
                tex = 3;
            }
            if (dir == 1) {
                tex = 1;
            }
            if (dir == 2) {
                tex = 7;
            }
            if (dir == 3) {
                tex = 5;
            }
        } else {
            if (dir == 0) {
                if (moving) {
                    if (animTimer % 30 > 15) {
                        tex = 8 + 2;
                    } else {
                        tex = 8 + 3;
                    }
                } else {
                    tex = 2;
                }
            }
            if (dir == 1) {
                if (moving) {
                    if (animTimer % 30 > 15) {
                        tex = 8 + 0;
                    } else {
                        tex = 8 + 1;
                    }
                } else {
                    tex = 0;
                }
            }
            if (dir == 2) {
                if (moving) {
                    if (animTimer % 30 > 15) {
                        tex = 8 + 5;
                    } else {
                        tex = 6;
                    }
                } else {
                    tex = 6;
                }
            }
            if (dir == 3) {
                if (moving) {
                    if (animTimer % 30 > 15) {
                        tex = 8 + 4;
                    } else {
                        tex = 4;
                    }
                } else {
                    tex = 4;
                }
            }
            attackCounter = 0;
        }

        if (!Game.getGame().changingLevel) {
            move(xa, ya);
        } else {
            xa = 0;
            ya = 0;
        }

        xa *= smoothing;
        ya *= smoothing;

        inv.update();

        if (inv.getWeapon() != null) {
            inv.getWeapon().update();

            if (inv.getWeapon().attack) {
                inv.getWeapon().attack = false;
                Entity e = getNearBy(2);
                if (e != null) {
                    if (!e.dead) {
                        e.giveDammage(getAttackDamage(), this);
                        attack = false;
                        inv.getWeapon().life--;
                        inv.getWeapon().attack = false;
                    }
                }
            }
        }
    }

    public int getAttackDamage() {
        return (int) (inv.getWeapon().power + (power * 1.0 / 4.0));
    }

    public Player set(float x, float y) {
        this.x = x + 0.5f;
        this.y = y + 0.5f;

        return this;
    }

    public void render() {
        inv.render(x, y, dir, attack);
        glBegin(GL_QUADS);
        Renderer.mobData(x, y, tex);
        glEnd();
    }

    public void renderGUI() {
        Texture.unbind();

        inv.renderInv();
        inv.renderBottom();
        Texture.MISC.bind();


        float[][] heartsCoords = getCoords(1, 4, 4);
        for (int i = 0; i < 10; i++) {
            Gui.drawTexturedQuad(Display.getWidth() / 2 - (10 * 16) + i * 16 - 20, Display.getHeight() - 44 - 30, 16, 16, heartsCoords);
        }
        heartsCoords = getCoords(0, 4, 4);
        for (int i = 0; i < life; i++) {
            Gui.drawTexturedQuad(Display.getWidth() / 2 - (10 * 16) + i * 16 - 20, Display.getHeight() - 44 - 30, 16, 16, heartsCoords);
        }

        float[][] powerCoords = getCoords(3, 4, 4);
        for (int i = 0; i < 10; i++) {
            Gui.drawTexturedQuad(Display.getWidth() / 2 + 40 + i * 16 - 20, Display.getHeight() - 44 - 30, 16, 16, powerCoords);
        }
        powerCoords = getCoords(2, 4, 4);
        for (int i = 0; i < attackForce; i++) {
            Gui.drawTexturedQuad(Display.getWidth() / 2 + 40 + i * 16 - 20, Display.getHeight() - 44 - 30, 16, 16, powerCoords);
        }

        float[][] potatoCoords = getCoords(7, 4, 4);
        for (int i = 0; i < potatoes.size(); i++) {
            Gui.drawTexturedQuad((Display.getWidth() / 2 - 16) + (i * 16) - (potatoes.size() * 16) / 2 + 8, Display.getHeight() - 44 - 20 - 20 - 30, 32, 32, potatoCoords);
        }

    }

    public void add(Weapon weapon) {
        Main.getMain().playSound(Audio.WEAPON_PICKUP);
        inv.add(weapon);
    }

    public void addKey(int code) {
        keys.add(code);
    }

    public int getKey(int code) {
        for (int i = 0; i < keys.size(); i++) {
            if (keys.get(i) == code) return keys.get(i);
        }

        return -1;
    }

    public void addPotion(ItemPotion potion) {
        inv.add(potion);
    }

    public void addPotato(ItemGrenade potato) {
        potatoes.add(potato);
    }

    public List<ItemGrenade> getPotatoes() {
        return potatoes;
    }

    public PlayerInventory getInventory() {
        return inv;
    }

    public float[][] getCoords(int tex, int w, int h) {
        float[][] r = new float[4][2];

        int xo = tex % w;
        int yo = tex / w;

        r[0][0] = (0 + xo) / (float) w;
        r[0][1] = (0 + yo) / (float) h;
        r[1][0] = (1 + xo) / (float) w;
        r[1][1] = (0 + yo) / (float) h;
        r[2][0] = (1 + xo) / (float) w;
        r[2][1] = (1 + yo) / (float) h;
        r[3][0] = (0 + xo) / (float) w;
        r[3][1] = (1 + yo) / (float) h;

        return r;
    }
}

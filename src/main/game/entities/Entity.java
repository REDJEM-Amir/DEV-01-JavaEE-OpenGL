package main.game.entities;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector4f;

import main.Main;
import main.audio.Audio;
import main.game.Game;
import main.game.entities.particles.ParticleSystem;
import main.game.level.Level;
import main.rendering.Renderer;

public abstract class Entity {
    public float x, y;
    public float fx, fy;
    public int tex = 0;
    public int life = 2;
    public boolean dead = false;
    public boolean removed = false;

    public Entity(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void move(float xa, float ya) {
        int xStep = (int) Math.abs(xa * 100);
        for (int i = 0; i < xStep; i++) {
            if (!isColliding(xa / xStep, 0)) {
                x += xa / xStep;
            }
        }

        int yStep = (int) Math.abs(ya * 100);
        for (int i = 0; i < yStep; i++) {
            if (!isColliding(0, ya / yStep)) {
                y += ya / yStep;
            }
        }
    }

    public boolean isColliding(float xa, float ya) {
        Level level = Game.getGame().getLevel();

        float size = 0.3f;

        int x0 = (int) Math.floor(x + xa - size);
        int x1 = (int) Math.floor(x + xa + size);
        int y0 = (int) Math.floor(y + ya - size * 3);
        int y1 = (int) Math.floor(y + ya);

        if (level.getTile(x0, y0).solid) return true;
        if (level.getTile(x1, y0).solid) return true;
        if (level.getTile(x1, y1).solid) return true;
        if (level.getTile(x0, y1).solid) return true;

        return false;
    }

    public void giveDammage(int dammage, Entity e) {
        if (removed) return;
        if (life - dammage <= 0) {
            if (!dead) {
                Game.getGame().getLevel().add(new ParticleSystem(x, -0.5f, y, 50, new Vector4f(0.8f, 0, 0, 1)));
                if (this instanceof Player) {
                    Main.getMain().playSound(Audio.DIE);
                }
            }
            dead = true;

        }
        if (dammage < 0) return;
        life -= dammage;
        if (!(this instanceof Player))
            Main.getMain().playSound(Audio.HIT);

        Main.getMain().playSound(Audio.HIRT);
        float xd = x - e.x;
        float yd = y - e.y;
        float dist = (float) Math.sqrt(xd * xd + yd * yd);
        addForce(xd / dist, yd / dist, 0.3f);
    }

    public void addForce(float xd, float yd, float force) {
        fx = xd * force;
        fy = yd * force;
    }

    public void addLife(int add) {
        if (!dead) {
            if (life + add > 10) {
                life = 10;
                return;
            }

            life += add;
        }
    }

    public Entity getNearBy(float radius) {
        List<Entity> entities = Game.getGame().getLevel().getEntities();

        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            if (e.equals(this)) continue;
            if (e instanceof ParticleSystem) continue;

            float ex = e.x + 0.5f;
            float ey = e.y + 0.5f;
            float xx = (x + 0.5f) - ex;
            float yy = (y + 0.5f) - ey;
            float dist = (float) Math.sqrt(xx * xx + yy * yy);

            if (dist <= radius)
                return e;
        }

        return null;
    }

    public List<Entity> getNearByEntities(float radius, float xOffs, float yOffs) {
        List<Entity> entities = Game.getGame().getLevel().getEntities();
        List<Entity> r = new ArrayList<Entity>();
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            if (e.equals(this)) continue;
            if (e instanceof ParticleSystem) continue;
            float ex = e.x + xOffs;
            float ey = e.y + yOffs;
            float xx = (x + xOffs) - ex;
            float yy = (y + yOffs) - ey;
            float dist = (float) Math.sqrt(xx * xx + yy * yy);

            if (dist <= radius)
                r.add(e);
        }
        return r;
    }

    public void render() {
        glBegin(GL_QUADS);
        Renderer.mobData(x, y, tex);
        glEnd();
    }

    public abstract void update();

    public abstract void renderGUI();
}

package main.rendering;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {
	
	public static final float TEXTURE_WIDTH = 8.0f;
	public static final float TEXTURE_HEIGHT = 8.0f;
	
	public static void floorData(float x, float z, int tex) {
		int xo = tex % (int)TEXTURE_WIDTH;
		int yo = 0;
		
		glTexCoord2f((0 + xo) / TEXTURE_WIDTH, (0 + yo) / TEXTURE_HEIGHT); glVertex3f(x, -1, z);
		glTexCoord2f((1 + xo) / TEXTURE_WIDTH, (0 + yo) / TEXTURE_HEIGHT); glVertex3f(x + 1, -1, z);
		glTexCoord2f((1 + xo) / TEXTURE_WIDTH, (1 + yo) / TEXTURE_HEIGHT); glVertex3f(x + 1, -1, z + 1);
		glTexCoord2f((0 + xo) / TEXTURE_WIDTH, (1 + yo) / TEXTURE_HEIGHT); glVertex3f(x, -1, z + 1);
	}
	
	public static void ceilData(float x, float h, float z, int tex) {
		int xo = tex % (int)TEXTURE_WIDTH;
		int yo = tex / (int)TEXTURE_WIDTH;
		
		glTexCoord2f((0 + xo) / TEXTURE_WIDTH, (0 + yo) / TEXTURE_HEIGHT); glVertex3f(x, -1 + h, z);
		glTexCoord2f((1 + xo) / TEXTURE_WIDTH, (0 + yo) / TEXTURE_HEIGHT); glVertex3f(x + 1, -1 + h, z);
		glTexCoord2f((1 + xo) / TEXTURE_WIDTH, (1 + yo) / TEXTURE_HEIGHT); glVertex3f(x + 1, -1 + h, z + 1);
		glTexCoord2f((0 + xo) / TEXTURE_WIDTH, (1 + yo) / TEXTURE_HEIGHT); glVertex3f(x, -1 + h, z + 1);
	}
	
	public static void quadData(float x, float z, int tex) {
		int xo = tex % (int)TEXTURE_WIDTH;
		int yo = 2;
		
		glTexCoord2f((0 + xo) / TEXTURE_WIDTH, (1 + yo) / TEXTURE_HEIGHT); glVertex3f(x - 0.5f, -1, z);
		glTexCoord2f((1 + xo) / TEXTURE_WIDTH, (1 + yo) / TEXTURE_HEIGHT); glVertex3f(x + 0.5f, -1, z);
		glTexCoord2f((1 + xo) / TEXTURE_WIDTH, (0 + yo) / TEXTURE_HEIGHT); glVertex3f(x + 0.5f, 0 - 0.2f, z + (float) Math.cos(40));
		glTexCoord2f((0 + xo) / TEXTURE_WIDTH, (0 + yo) / TEXTURE_HEIGHT); glVertex3f(x - 0.5f, 0 - 0.2f, z + (float) Math.cos(40));
	}
	
	public static void quadData(float x, float y, float z, float size) {
		glVertex3f(x - size, y - size, z);
		glVertex3f(x + size, y - size, z);
		glVertex3f(x + size, y + size - 0.2f * size, z + (float) Math.cos(40) * size);
		glVertex3f(x - size, y + size - 0.2f * size, z + (float) Math.cos(40) * size);
	}
	
	public static void renderItem(float x, float h, float z, int tex) {
		int xo = tex % (int)TEXTURE_WIDTH;
		int yo = tex / (int)TEXTURE_WIDTH;
		glBegin(GL_QUADS);
			glTexCoord2f((0 + xo) / TEXTURE_WIDTH, (1 + yo) / TEXTURE_HEIGHT); glVertex3f(x, -1 + h, z);
			glTexCoord2f((1 + xo) / TEXTURE_WIDTH, (1 + yo) / TEXTURE_HEIGHT); glVertex3f(x + 1, -1 + h, z);
			glTexCoord2f((1 + xo) / TEXTURE_WIDTH, (0 + yo) / TEXTURE_HEIGHT); glVertex3f(x + 1, 0 - 0.2f + h, z + (float) Math.cos(40));
			glTexCoord2f((0 + xo) / TEXTURE_WIDTH, (0 + yo) / TEXTURE_HEIGHT); glVertex3f(x, 0 - 0.2f + h, z + (float) Math.cos(40));
		glEnd();
	}
	
	public static void weaponData(float x, float h, float z, int tex, boolean flip, float size, int slot) {
		int xo = tex % (int)TEXTURE_WIDTH;
		int yo = tex / (int)TEXTURE_WIDTH;
		
		float rot = -((float)slot / 6.0f) * 180;
		
		if (flip) rot = -rot;
		
		glPushMatrix();
		glTranslatef(x - 0.3f, h, z);
		glRotatef(-rot - 90, 0, 0, 1);
		glTranslatef(-x + 0.3f, -h, -z);		
		glBegin(GL_QUADS);
		if (flip) {
			glTexCoord2f((1 + xo) / TEXTURE_WIDTH, (1 + yo) / TEXTURE_HEIGHT); glVertex3f(x - size, - size + h, z);
			glTexCoord2f((0 + xo) / TEXTURE_WIDTH, (1 + yo) / TEXTURE_HEIGHT); glVertex3f(x + size, - size + h, z);
			glTexCoord2f((0 + xo) / TEXTURE_WIDTH, (0 + yo) / TEXTURE_HEIGHT); glVertex3f(x + size, + size + h, z);
			glTexCoord2f((1 + xo) / TEXTURE_WIDTH, (0 + yo) / TEXTURE_HEIGHT); glVertex3f(x - size, + size + h, z);			
		}else {
			glTexCoord2f((0 + xo) / TEXTURE_WIDTH, (1 + yo) / TEXTURE_HEIGHT); glVertex3f(x - size, - size + h, z);
			glTexCoord2f((1 + xo) / TEXTURE_WIDTH, (1 + yo) / TEXTURE_HEIGHT); glVertex3f(x + size, - size + h, z);
			glTexCoord2f((1 + xo) / TEXTURE_WIDTH, (0 + yo) / TEXTURE_HEIGHT); glVertex3f(x + size, + size + h, z);
			glTexCoord2f((0 + xo) / TEXTURE_WIDTH, (0 + yo) / TEXTURE_HEIGHT); glVertex3f(x - size, + size + h, z);
		}
		glEnd();
		glPopMatrix();
	}
	
	public static void weaponData(float x, float h, float z, int tex, boolean flip, float size) {
		int xo = tex % (int)TEXTURE_WIDTH;
		int yo = tex / (int)TEXTURE_WIDTH;
		
		glPushMatrix();	
		glBegin(GL_QUADS);
		if (flip) {
			glTexCoord2f((1 + xo) / TEXTURE_WIDTH, (1 + yo) / TEXTURE_HEIGHT); glVertex3f(x - size, - size + h, z);
			glTexCoord2f((0 + xo) / TEXTURE_WIDTH, (1 + yo) / TEXTURE_HEIGHT); glVertex3f(x + size, - size + h, z);
			glTexCoord2f((0 + xo) / TEXTURE_WIDTH, (0 + yo) / TEXTURE_HEIGHT); glVertex3f(x + size, + size + h, z);
			glTexCoord2f((1 + xo) / TEXTURE_WIDTH, (0 + yo) / TEXTURE_HEIGHT); glVertex3f(x - size, + size + h, z);			
		}else {
			glTexCoord2f((0 + xo) / TEXTURE_WIDTH, (1 + yo) / TEXTURE_HEIGHT); glVertex3f(x - size, - size + h, z);
			glTexCoord2f((1 + xo) / TEXTURE_WIDTH, (1 + yo) / TEXTURE_HEIGHT); glVertex3f(x + size, - size + h, z);
			glTexCoord2f((1 + xo) / TEXTURE_WIDTH, (0 + yo) / TEXTURE_HEIGHT); glVertex3f(x + size, + size + h, z);
			glTexCoord2f((0 + xo) / TEXTURE_WIDTH, (0 + yo) / TEXTURE_HEIGHT); glVertex3f(x - size, + size + h, z);
		}
		glEnd();
		glPopMatrix();
	}
	
	public static void wallData(float x0, float z0, float x1, float z1, int tex) {
		int xo = tex % (int)TEXTURE_WIDTH;
		int yo = tex / (int)TEXTURE_WIDTH;
		
		glTexCoord2f((0 + xo) / TEXTURE_WIDTH, (1 + yo) / TEXTURE_HEIGHT); glVertex3f(x0, -1, z0);
		glTexCoord2f((1 + xo) / TEXTURE_WIDTH, (1 + yo) / TEXTURE_HEIGHT); glVertex3f(x1, -1, z1);
		glTexCoord2f((1 + xo) / TEXTURE_WIDTH, (0 + yo) / TEXTURE_HEIGHT); glVertex3f(x1, 0, z1);
		glTexCoord2f((0 + xo) / TEXTURE_WIDTH, (0 + yo) / TEXTURE_HEIGHT); glVertex3f(x0, 0, z0);
	}
	
	public static void mobData(float x, float z, int tex) {
		int xo = tex % (int)TEXTURE_WIDTH;
		int yo = tex / (int)TEXTURE_WIDTH;
		
		glTexCoord2f((0 + xo) / TEXTURE_WIDTH, (1 + yo) / TEXTURE_HEIGHT); glVertex3f(x - 0.5f, -1, z);
		glTexCoord2f((1 + xo) / TEXTURE_WIDTH, (1 + yo) / TEXTURE_HEIGHT); glVertex3f(x + 0.5f, -1, z);
		glTexCoord2f((1 + xo) / TEXTURE_WIDTH, (0 + yo) / TEXTURE_HEIGHT); glVertex3f(x + 0.5f, 0 - 0.2f, z + (float) Math.cos(40));
		glTexCoord2f((0 + xo) / TEXTURE_WIDTH, (0 + yo) / TEXTURE_HEIGHT); glVertex3f(x - 0.5f, 0 - 0.2f, z + (float) Math.cos(40));
	}
	
	public static void bigMobData(float x, float z, int tex) {
		int xo = tex % (int)(TEXTURE_WIDTH / 2.0f);
		int yo = tex / (int)(TEXTURE_WIDTH / 2.0f);
		
		glTexCoord2f((0 + xo) / TEXTURE_WIDTH * 2, (1 + yo) / TEXTURE_HEIGHT); glVertex3f(x - 1f, -1, z);
		glTexCoord2f((1 + xo) / TEXTURE_WIDTH * 2, (1 + yo) / TEXTURE_HEIGHT); glVertex3f(x + 1f, -1, z);
		glTexCoord2f((1 + xo) / TEXTURE_WIDTH * 2, (0 + yo) / TEXTURE_HEIGHT); glVertex3f(x + 1f, 0 - 0.2f, z + (float) Math.cos(40));
		glTexCoord2f((0 + xo) / TEXTURE_WIDTH * 2, (0 + yo) / TEXTURE_HEIGHT); glVertex3f(x - 1f, 0 - 0.2f, z + (float) Math.cos(40));
	}
	
	public static void grassData(float x, float z, int tex) {
		int xo = tex % (int)TEXTURE_WIDTH;
		int yo = 3;
		
		float rot = (float) Math.random() * (float) (Math.PI * 2);
		float xRot = (float) Math.cos(rot) * 0.5f;
		float yRot = (float) Math.sin(rot) * 0.5f;
		float xRand = (float) Math.random();		
		float yRand = (float) Math.random();		

		glTexCoord2f((0 + xo) / TEXTURE_WIDTH, (1 + yo) / TEXTURE_HEIGHT); glVertex3f(x - xRot + xRand, -1, z - yRot + yRand);
		glTexCoord2f((1 + xo) / TEXTURE_WIDTH, (1 + yo) / TEXTURE_HEIGHT); glVertex3f(x + xRot + xRand, -1, z  + yRot + yRand);
		glTexCoord2f((1 + xo) / TEXTURE_WIDTH, (0 + yo) / TEXTURE_HEIGHT); glVertex3f(x + xRot + xRand, 0, z  + yRot + yRand);
		glTexCoord2f((0 + xo) / TEXTURE_WIDTH, (0 + yo) / TEXTURE_HEIGHT); glVertex3f(x - xRot + xRand, 0, z - yRot + yRand);
	}
	
	public static void flowerData(float x, float z, int tex) {
		int xo = tex % (int)TEXTURE_WIDTH;
		int yo = 3;
		
		float rot = 0.3f;
		
		glTexCoord2f((0 + xo) / TEXTURE_WIDTH, (1 + yo) / TEXTURE_HEIGHT); glVertex3f(x - rot, -1, z - rot);
		glTexCoord2f((1 + xo) / TEXTURE_WIDTH, (1 + yo) / TEXTURE_HEIGHT); glVertex3f(x + rot, -1, z + rot);
		glTexCoord2f((1 + xo) / TEXTURE_WIDTH, (0 + yo) / TEXTURE_HEIGHT); glVertex3f(x + rot, 0, z + rot);
		glTexCoord2f((0 + xo) / TEXTURE_WIDTH, (0 + yo) / TEXTURE_HEIGHT); glVertex3f(x - rot, 0, z - rot);
		
		glTexCoord2f((0 + xo) / TEXTURE_WIDTH, (1 + yo) / TEXTURE_HEIGHT); glVertex3f(x - rot, -1, z + rot);
		glTexCoord2f((1 + xo) / TEXTURE_WIDTH, (1 + yo) / TEXTURE_HEIGHT); glVertex3f(x + rot, -1, z - rot);
		glTexCoord2f((1 + xo) / TEXTURE_WIDTH, (0 + yo) / TEXTURE_HEIGHT); glVertex3f(x + rot, 0, z - rot);
		glTexCoord2f((0 + xo) / TEXTURE_WIDTH, (0 + yo) / TEXTURE_HEIGHT); glVertex3f(x - rot, 0, z + rot);
	}
	
	public static void renderDoor(float x, float z, float angle, boolean dir, int key, int tex) {
		glColor4f(1, 1, 1, 1);
		int xo = tex;
		if (key > 0) {
			xo = tex + 1;
		}
		int yo = 4;
		glPushMatrix();
		if (!dir) {
			z += 0.5f;
			x += (0.05f * (angle / 90));
		}else {
			angle += 90;
			x += 0.5f;
			z += 1;
		}
		glTranslatef(x, 0, z);
		glRotatef(angle, 0, 1, 0);
		glTranslatef(-x, 0, -z);
		
		glBegin(GL_QUADS);
			glTexCoord2f((0 + xo) / TEXTURE_WIDTH, (0 + yo) / TEXTURE_HEIGHT); glVertex3f(x, -1, z - 0.04f);
			glTexCoord2f((1 + xo) / TEXTURE_WIDTH, (0 + yo) / TEXTURE_HEIGHT); glVertex3f(x + 1, -1, z - 0.04f);
			glTexCoord2f((1 + xo) / TEXTURE_WIDTH, (1 + yo) / TEXTURE_HEIGHT); glVertex3f(x + 1, 0, z - 0.04f);
			glTexCoord2f((0 + xo) / TEXTURE_WIDTH, (1 + yo) / TEXTURE_HEIGHT); glVertex3f(x, 0, z - 0.04f);
			
			glTexCoord2f((0 + xo) / TEXTURE_WIDTH, (0 + yo) / TEXTURE_HEIGHT); glVertex3f(x, -1, z + 0.04f);
			glTexCoord2f((1 + xo) / TEXTURE_WIDTH, (0 + yo) / TEXTURE_HEIGHT); glVertex3f(x + 1, -1, z + 0.04f);
			glTexCoord2f((1 + xo) / TEXTURE_WIDTH, (1 + yo) / TEXTURE_HEIGHT); glVertex3f(x + 1, 0, z + 0.04f);
			glTexCoord2f((0 + xo) / TEXTURE_WIDTH, (1 + yo) / TEXTURE_HEIGHT); glVertex3f(x, 0, z + 0.04f);
			
			glTexCoord2f((0 + xo) / TEXTURE_WIDTH, (0 + yo) / TEXTURE_HEIGHT); glVertex3f(x + 1, -1, z + 0.04f);
			glTexCoord2f((0 + xo) / TEXTURE_WIDTH, (0 + yo) / TEXTURE_HEIGHT); glVertex3f(x + 1, -1, z - 0.04f);
			glTexCoord2f((0 + xo) / TEXTURE_WIDTH, (0 + yo) / TEXTURE_HEIGHT); glVertex3f(x + 1, 0, z - 0.04f);
			glTexCoord2f((0 + xo) / TEXTURE_WIDTH, (0 + yo) / TEXTURE_HEIGHT); glVertex3f(x + 1, 0, z + 0.04f);
			
			glTexCoord2f((0 + xo) / TEXTURE_WIDTH, (0 + yo) / TEXTURE_HEIGHT); glVertex3f(x, -1, z + 0.04f);
			glTexCoord2f((0 + xo) / TEXTURE_WIDTH, (0 + yo) / TEXTURE_HEIGHT); glVertex3f(x, -1, z - 0.04f);
			glTexCoord2f((0 + xo) / TEXTURE_WIDTH, (0 + yo) / TEXTURE_HEIGHT); glVertex3f(x, 0, z - 0.04f);
			glTexCoord2f((0 + xo) / TEXTURE_WIDTH, (0 + yo) / TEXTURE_HEIGHT); glVertex3f(x, 0, z + 0.04f);
			
			glTexCoord2f((0 + xo) / TEXTURE_WIDTH, (0 + yo) / TEXTURE_HEIGHT); glVertex3f(x, 0, z + 0.04f);
			glTexCoord2f((0 + xo) / TEXTURE_WIDTH, (0 + yo) / TEXTURE_HEIGHT); glVertex3f(x + 1, 0, z + 0.04f);
			glTexCoord2f((0 + xo) / TEXTURE_WIDTH, (0 + yo) / TEXTURE_HEIGHT); glVertex3f(x + 1, 0, z - 0.04f);
			glTexCoord2f((0 + xo) / TEXTURE_WIDTH, (0 + yo) / TEXTURE_HEIGHT); glVertex3f(x, 0, z - 0.04f);
		glEnd();
		glPopMatrix();
	}
	
	public static void begin() {
		glBegin(GL_QUADS);
	}
	
	public static void end() {
		glEnd();
	}
}

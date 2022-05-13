package main.game.entities.weapons;

import main.rendering.Gui;

public abstract class Weapon {
	
	int life = 5;
	
	public int tex;
	float[][] texCoords = new float[4][2];
	int textureSize = 8;
	int power;
	
	public Weapon(int tex, int power) {
		this.tex = tex;
		this.power = power;
		
		int xo = tex % textureSize;
		int yo = tex / textureSize;
		texCoords[0][0] = (0 + xo) / (float) textureSize; texCoords[0][1] = (0 + yo) / (float) textureSize;
		texCoords[1][0] = (1 + xo) / (float) textureSize; texCoords[1][1] = (0 + yo) / (float) textureSize;
		texCoords[2][0] = (1 + xo) / (float) textureSize; texCoords[2][1] = (1 + yo) / (float) textureSize;
		texCoords[3][0] = (0 + xo) / (float) textureSize; texCoords[3][1] = (1 + yo) / (float) textureSize;
	}
	
	public abstract void update();
	
	public void renderGUI(int x, int y, int size) {
		Gui.drawTexturedQuad(x, y, size, size, texCoords);
	}
}

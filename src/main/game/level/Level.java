package main.game.level;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import main.game.Game;
import main.game.entities.Entity;
import main.game.entities.Player;
import main.game.entities.mobs.EnemyBoss;
import main.game.entities.mobs.EnemyDoubleHead;
import main.game.entities.mobs.MillipedeEnemy;
import main.game.entities.mobs.EnemyWeird;
import main.game.entities.mobs.EnemyWorm;
import main.game.level.tiles.TileBlock;
import main.game.level.tiles.TileBrickBlock;
import main.game.level.tiles.TileDirt;
import main.game.level.tiles.TileDoor;
import main.game.level.tiles.TileEndDoor;
import main.game.level.tiles.TileGrass;
import main.game.level.tiles.TileRock;
import main.game.level.tiles.TileStoneBrickBlock;
import main.game.level.tiles.TileStone;
import main.game.level.tiles.Tile;
import main.game.level.tiles.UpTile;
import main.game.level.tiles.items.Item;
import main.game.level.tiles.items.ItemKey;
import main.game.level.tiles.items.ItemGrenade;
import main.game.level.tiles.items.ItemPotion;
import main.game.level.tiles.items.weapons.ChikenItem;
import main.game.level.tiles.items.weapons.PingPongItem;
import main.game.level.tiles.items.weapons.RawSteakItem;
import main.game.level.tiles.items.weapons.SteakItem;
import main.game.level.tiles.items.weapons.WaterBottleItem;
import main.rendering.Renderer;
import main.rendering.Texture;

public class Level extends EntityManager {
	
	int list;
	
	public Tile[][] tiles;
	public List<Item> items = new ArrayList<Item>();
	public List<TileDoor> doors = new ArrayList<TileDoor>();
	public int width, height;
	public int[] spawn = new int[2];
	public int[] endPoint = new int[2];
	public int level = 0;
	public float fog;
	
	public Level(String level, Player player) {
		int[] pixels = null;
		int w = 0, h = 0;
		this.level = Integer.parseInt(level.substring(3));
		try {
			BufferedImage image = ImageIO.read(Level.class.getResource("/lvl/" + level + ".png"));
			w = width = image.getWidth();
			h = height = image.getHeight();
			pixels = new int[w * h];
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		tiles = new Tile[w][h];
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				int i = x + y * w;
				//Spawn
				if (pixels[i] == 0xffFFFF00) {tiles[x][y] = tiles[x - 1][y]; spawn[0] = x; spawn[1] = y; add(player.set(x, y)); }
				
				
			}	
		}	
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				int i = x + y * w;

				//Tiles
				if (pixels[i] == 0xff6B9941) tiles[x][y] = new TileGrass(x, y);
				else if (pixels[i] == 0xff808080) tiles[x][y] = new TileRock(x, y);
				else if (pixels[i] == 0xffAF4733) tiles[x][y] = new TileBrickBlock(x, y);
				
				else if (pixels[i] == 0xff404040) tiles[x][y] = new TileStoneBrickBlock(x, y);
				else if (pixels[i] == 0xffADADAD) tiles[x][y] = new TileStone(x, y);
				
				else if (TileDoor.doorColor(pixels[i])) {tiles[x][y] = new TileDoor(x, y, pixels[i], this); doors.add(new TileDoor(x, y, pixels[i], this)); }
				else if (levelDoor(pixels[i])) {tiles[x][y] = new TileEndDoor(x, y, pixels[i]); endPoint[0] = x; endPoint[1] = y; }
				
				//Items
				else if (pixels[i] == 0xffE10000) {items.add( new RawSteakItem(x, y)); tiles[x][y] = tiles[x - 1][y];}
				else if (pixels[i] == 0xffFF8C8C) {items.add( new SteakItem(x, y)); tiles[x][y] = tiles[x - 1][y];}
				else if (pixels[i] == 0xffFF8C00) {items.add( new ChikenItem(x, y)); tiles[x][y] = tiles[x - 1][y];}
				else if (pixels[i] == 0xff930000) {items.add( new PingPongItem(x, y)); tiles[x][y] = tiles[x - 1][y];}
				else if (pixels[i] == 0xff00AEFF) {items.add( new WaterBottleItem(x, y)); tiles[x][y] = tiles[x - 1][y];}
				else if (pixels[i] == 0xff00FFFF) {items.add( new ItemPotion(x, y)); tiles[x][y] = tiles[x - 1][y];}
				else if (ItemKey.keyColor(pixels[i])) {items.add( new ItemKey(x, y, pixels[i])); tiles[x][y] = tiles[x - 1][y];}
				else if (pixels[i] == 0xffFFD800) {items.add( new ItemGrenade(x, y)); tiles[x][y] = tiles[x - 1][y];}
				
				
				//Spawns
				else if (pixels[i] == 0xffFFAA7F) {spawnMob(2, new EnemyWorm(x, y)); tiles[x][y] = tiles[x - 1][y];}
				else if (pixels[i] == 0xffC2B091) {spawnMob(2, new MillipedeEnemy(x, y)); tiles[x][y] = tiles[x - 1][y];}
				else if (pixels[i] == 0xff624138) {spawnMob(2, new EnemyWeird(x, y)); tiles[x][y] = tiles[x - 1][y];}
				else if (pixels[i] == 0xff7A5246) {spawnMob(2, new EnemyDoubleHead(x, y)); tiles[x][y] = tiles[x - 1][y];}
				else if (pixels[i] == 0xff543830) {spawnMob(2, new EnemyBoss(x, y)); tiles[x][y] = tiles[x - 1][y];}
				
				//Others
				else if (pixels[i] == 0xffFFFF00) tiles[x][y] = tiles[x - 1][y];
				else tiles[x][y] = new TileDirt(x, y);
			}	
		}
		
		compile();
		
		Game.getGame().changingLevel = false;
	}
	
	public void spawnMob(int num, Entity mob) {
		for (int i = 0; i < num; i++) {
			add(mob);			
		}
	}
	
	private void compile() {
		list = glGenLists(1);
		
		glNewList(list, GL_COMPILE);
		glBegin(GL_QUADS);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Tile tile = getTile(x, y);

				Renderer.floorData(x, y, tile.tex);
				
				if (tile instanceof UpTile) {
					Renderer.quadData(x + 0.5f, y + 0.5f, ((UpTile) tile).uptex);
				}
				
				Tile right = getTile(x + 1, y);
				Tile down = getTile(x, y + 1);
				
				if (!tile.block) {
					if (right.block) {
						Renderer.wallData(x + 1, y, x + 1, y + 1, ((TileBlock)right).wallTex);
					}
					if (down.block) {
						Renderer.wallData(x + 1, y + 1, x, y + 1, ((TileBlock)down).wallTex);
					}
				}else {
					Renderer.ceilData(x, 1, y, tile.tex);
					if (!right.block) {
						Renderer.wallData(x + 1, y + 1, x + 1, y, ((TileBlock)tile).wallTex);
					}
					if (!down.block) {
						Renderer.wallData(x, y + 1, x + 1, y + 1, ((TileBlock)tile).wallTex);
					}
				}
				
				if (tile.vegetation) {
					 Renderer.grassData(x, y, 0);
					 Renderer.grassData(x, y, 0);
					 Renderer.grassData(x, y, 0);
					 if (Math.random() > 0.95f) {
						 Renderer.flowerData(x, y, 1);
					 }
				}
			}	
		}
		glEnd();
		glEndList();
	}
	
	public void update() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Tile tile = getTile(x, y);
				tile.update();
			}	
		}
		for (int i = 0; i < items.size(); i++) {
			Item item = items.get(i);
			if (item.removed) items.remove(i);
			item.update();
		}
		for (int i = 0; i < doors.size(); i++) {
			TileDoor door = doors.get(i);
			door.update();
		}
		updateEntities(this);
		
		if (level == 5) {
			if (Game.getGame().getPlayer().y < 9) {
				Game.finished = true;
			}
		}
		
	}
	
	public void loadNextLevel() {
		
	}
	
	public void render() {
		Texture.ENV.bind();

		glCallList(list);
		
		for (int i = 0; i < doors.size(); i++) {
			TileDoor door = doors.get(i);
			door.render(this);
		}
		
		Texture.WEAPONS.bind();
		for (int i = 0; i < items.size(); i++) {
			Item item = items.get(i);
			item.render();
		}
		Texture.unbind();
		
		renderEntities();
	}
	
	public void renderGUI() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Tile tile = getTile(x, y);
				tile.renderGUI();
			}	
		}
		renderGUIEntities();
	}
	
	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height)
			return new TileGrass(x, y);
		
		return tiles[x][y];
	}
	
	public boolean isWall(float x, float y, float size) {
		int x0 = (int) Math.floor(x - size);
		int x1 = (int) Math.floor(x + size);
		int y0 = (int) Math.floor(y - size);
		int y1 = (int) Math.floor(y + size);
		
		if (getTile(x0, y0).block) return true;
		if (getTile(x1, y0).block) return true;
		if (getTile(x1, y1).block) return true;
		if (getTile(x0, y1).block) return true;
		
		return false;
	}
	
	public boolean isColliding(float x, float y, float size) {
		int x0 = (int) Math.floor(x - size);
		int x1 = (int) Math.floor(x + size);
		int y0 = (int) Math.floor(y - size);
		int y1 = (int) Math.floor(y + size);
		
		if (getTile(x0, y0).solid) return true;
		if (getTile(x1, y0).solid) return true;
		if (getTile(x1, y1).solid) return true;
		if (getTile(x0, y1).solid) return true;
		
		return false;
	}
	
	public Player getPlayer() {
		return (Player) entities.get(0);
	}
	
	public boolean levelDoor(int color) {
		if (color == 0xff10ff00) return true;
		if (color == 0xff20ff00) return true;
		if (color == 0xff30ff00) return true;
		if (color == 0xff40ff00) return true;
		if (color == 0xff50ff00) return true;
		
		return false;
	}
}

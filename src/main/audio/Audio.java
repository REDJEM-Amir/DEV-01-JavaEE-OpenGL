package main.audio;

import java.util.ArrayList;
import java.util.List;

public class Audio {
	public static final int ADD_LIFE = 0x01;
	public static final int HIT = 0x02;
	public static final int WALK = 0x03;
	public static final int WEAPON_PICKUP = 0x04;
	public static final int KEY_PICKUP = 0x05;
	public static final int LEVEL_CHANGE = 0x06;
	public static final int POTATO_PICKUP = 0x07;
	public static final int HIRT = 0x08;
	public static final int EXPLOSION = 0x09;
	public static final int DIE = 0x10;
	
	List<AudioClip> audios = new ArrayList<AudioClip>();
	
	public Audio() {}
	
	public void add(AudioClip clip) {
		audios.add(clip);
	}
	
	public void play(int id) {
		getClip(id).play();
	}
	
	public AudioClip getClip(int id) {
		for (int i = 0; i < audios.size(); i++) {
			if (audios.get(i).id == id) {
				return audios.get(i);
			}
		}
		
		return null;
	}
	
	public void cleanAll() {
		for (int i = 0; i < audios.size(); i++) {
			audios.get(i).clean();
		}
		
		audios.clear();
	}
	
	public void setMasterVolume(float v) {
		for (int i = 0; i < audios.size(); i++) {
			audios.get(i).setVolume(v);
		}
	}
}

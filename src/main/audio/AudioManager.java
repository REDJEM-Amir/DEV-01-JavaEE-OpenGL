package main.audio;

public class AudioManager {

	private Audio audio;

	public AudioManager() {
		audio = new Audio();
	}

	public void initAudio() {
		add(new AudioClip("addlife.wav", Audio.ADD_LIFE));
		add(new AudioClip("hit.wav", Audio.HIT));
		add(new AudioClip("walk.wav", Audio.WALK));
		add(new AudioClip("wpickup.wav", Audio.WEAPON_PICKUP));
		add(new AudioClip("pickupKey.wav", Audio.KEY_PICKUP));
		add(new AudioClip("levelChange.wav", Audio.LEVEL_CHANGE));
		add(new AudioClip("pickPotato.wav", Audio.POTATO_PICKUP));
		add(new AudioClip("hirt.wav", Audio.HIRT));
		add(new AudioClip("explosion.wav", Audio.EXPLOSION));
		add(new AudioClip("die.wav", Audio.DIE));
	}

	public void cleanAudio() {
		audio.cleanAll();
	}

	public void add(AudioClip clip) {
		audio.add(clip);
	}

	public Audio getAudio() {
		return audio;
	}
}

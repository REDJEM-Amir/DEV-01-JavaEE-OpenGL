package main.audio;

import org.lwjgl.util.WaveData;

import static org.lwjgl.openal.AL10.*;

public class AudioClip {
	int id;
	int buffer;
	int source;
	
	public AudioClip(String path, int id) {
		this.id = id;
		WaveData data = WaveData.create(Audio.class.getResource("/snd/" + path));
		buffer = alGenBuffers();
		alBufferData(buffer, data.format, data.data, data.samplerate);
		data.dispose();
		source = alGenSources();
		alSourcei(source, AL_BUFFER, buffer);
	}
	
	public void setVolume(float v) {
		alSourcef(source, AL_GAIN, v);
	}
	
	public void play() {
		alSourcePlay(source);
	}
	
	public void clean() {
		alDeleteBuffers(buffer);
	}
}

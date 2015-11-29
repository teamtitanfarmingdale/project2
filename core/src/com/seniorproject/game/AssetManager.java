package com.seniorproject.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.seniorproject.game.helpers.FontAsset;
import com.seniorproject.game.particles.BaseExplosion;
import com.seniorproject.game.particles.EngineThrust;
import com.seniorproject.game.particles.FireExplosion;
import com.seniorproject.game.particles.InfiniteParticle;
import com.seniorproject.game.particles.ParticleInterface;
import com.seniorproject.game.particles.SmokeExplosion;

public class AssetManager {

	private ArrayList<Asset<Texture>> textureList;
	private ArrayList<Asset<Sound>> soundList;
	private ArrayList<FontAsset<BitmapFont>> fontList;
	private ArrayList<Asset<ParticleInterface>> particleList;
	
	private Sound sound;
	
	
	public AssetManager() {
		textureList = new ArrayList<Asset<Texture>>();
		soundList = new ArrayList<Asset<Sound>>();
		fontList = new ArrayList<FontAsset<BitmapFont>>();
		particleList = new ArrayList<Asset<ParticleInterface>>();
	}
	
	public Asset<ParticleInterface> getParticle(String particleName, String particleType) {
		Asset<ParticleInterface> particle = null;
		
		if(particleName.equals("")) {
			particleName = particleType;
		}
		
		
		if(!particleList.isEmpty()) {
			for(Asset<ParticleInterface> p : particleList) {
				if(p.fileLocation.equals(particleName) && !p.inUse) {
					System.out.println("FOUND PARTICLE: "+particleName);
					p.inUse = true;
					particle = p;
					break;
				}
			}
		}
		
		
		if(particle == null) {
			
			particle = new Asset<ParticleInterface>();
			particle.inUse = true;
			
			particle.fileLocation = particleName;
			
			System.out.println("CREATING PARTICLE: "+particle.fileLocation);
			
			switch(particleType) {
				case "BaseExplosion":
					particle.asset = new BaseExplosion(particleName);
					break;
				case "EngineThrust":
					particle.asset = new EngineThrust();
					break;
				case "FireExplosion":
					particle.asset = new FireExplosion();
					break;
				case "InfiniteParticle":
					particle.asset = new InfiniteParticle(particleName);
					break;
				case "SmokeExplosion":
					particle.asset = new SmokeExplosion();
					break;
			}
			
			particleList.add(particle);

		}
		
		return particle;
	}
	
	public Asset<ParticleInterface> getParticle(String particleType) {
		return this.getParticle("", particleType);
	}
	
	public void releaseParticle(Asset<ParticleInterface> p, float time) {
		Timer.schedule(new ManagerTask<ParticleInterface>(p), time);
	}

	public Texture getTexture(String fileName) {
		
		Asset<Texture> textureAsset;
		Texture texture = null;
		boolean foundTexture = false;
		
		if(!textureList.isEmpty()) {
			for(Asset<Texture> t : textureList) {
				if(t.fileLocation.equals(fileName)) {
					//System.out.println("FOUND TEXTURE:"+fileName);
					texture = t.asset;
					foundTexture = true;
					break;
				}
			}
		}
		
		
		
		if(!foundTexture) {
			//System.out.println("CREATED TEXTURE:"+fileName);
			textureAsset = new Asset<Texture>();
			
			textureAsset.fileLocation = fileName;
			textureAsset.asset = new Texture(Gdx.files.internal(fileName));
			
			textureList.add(textureAsset);
			
			
			texture = textureAsset.asset;
		}
		
		
		return texture;
		
	}
	
	public BitmapFont getFont(String fileName, int size, Color color) {
		
		FontAsset<BitmapFont> font = null;
		
		if(!fontList.isEmpty()) {
			for(FontAsset<BitmapFont> f : fontList) {
				if(f.fileLocation.equals(fileName) && f.fontSize == size && f.color.equals(color)) {
					font = f;
					//System.out.println("Font Found: "+fileName+", Size: "+size+", Color: "+color.toString());
					break;
				}
			}
		}
		
		if(font == null) {
			font = new FontAsset<BitmapFont>();
			FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fileName));
			FreeTypeFontParameter parameter = new FreeTypeFontParameter();
			parameter.size = size;
			font.fileLocation = fileName;
			font.fontSize = size;
			font.color = color;
			font.asset = generator.generateFont(parameter);
			generator.dispose();	
			fontList.add(font);
			//System.out.println("Creating Font: "+fileName+", Size: "+size+", Color: "+color.toString());
		}
		
		
		return font.asset;
	}
	
	public void playSound(String fileName, float length) {
		
		Asset<Sound> soundAsset = null;
		
		sound = null;
		
		if(!soundList.isEmpty()) {
			for(Asset<Sound> s : soundList) {
				if(s.fileLocation.equals(fileName) && !s.inUse) {
					soundAsset = s;
					s.inUse = true;
					sound = s.asset;
					//System.out.println("FOUND UNUSED SOUND:" + fileName);
					break;
				}
				
			}
		}
		
		if(sound == null) {
			soundAsset = new Asset<Sound>();
			soundList.add(soundAsset);
			soundAsset.inUse = true;
			soundAsset.fileLocation = fileName;
			soundAsset.asset = Gdx.audio.newSound(Gdx.files.internal(fileName));
			//System.out.println("CREATED NEW SOUND:" + fileName);
			sound = soundAsset.asset;
		}
		
		sound.play(ShooterGame.SFX_VOLUME);
		Timer.schedule(new ManagerTask<Sound>(soundAsset), length);
	}
	
	class ManagerTask<T> extends Task {

		Asset<T> asset;
		
		public ManagerTask(Asset<T> s) {
			asset = s;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			asset.inUse = false;
			if(!asset.asset.getClass().getSimpleName().equals("Sound")) {
				System.out.println("Released "+asset.fileLocation+" - "+asset.asset.getClass().getSimpleName());
			}
		}
		
	}
	
	
	
}

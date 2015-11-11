package com.seniorproject.game.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class SliderHelper {

	
	Texture sliderTexture;
	Texture sliderKnobTexture;
	Slider slider; 
	
	public SliderHelper(String barFile, String knobFile) {
		
		sliderTexture = new Texture(Gdx.files.internal(barFile));
		TextureRegion sliderTextureRegion = new TextureRegion(sliderTexture);
		TextureRegionDrawable sliderTextureDrawable = new TextureRegionDrawable(sliderTextureRegion);
		
		sliderKnobTexture = new Texture(Gdx.files.internal(knobFile));
		TextureRegion sliderKnobTextureRegion = new TextureRegion(sliderKnobTexture);
		TextureRegionDrawable sliderKnobTextureDrawable = new TextureRegionDrawable(sliderKnobTextureRegion);
		
		
		SliderStyle sliderStyle = new SliderStyle(sliderTextureDrawable, sliderKnobTextureDrawable);
		
		slider = new Slider(0, 1, .01f, false, sliderStyle);
		
	}
	
	public Slider getSlider() {
		return slider;
	}
	
	
	public void dispose() {
		
		sliderTexture.dispose();
		sliderKnobTexture.dispose();
		
	}
}

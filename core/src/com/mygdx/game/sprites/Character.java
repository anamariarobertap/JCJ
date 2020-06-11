package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Character extends Sprite {


	final float PIXELS_TO_METERS = 100f;


	final short PHYSICS_ENTITY = 0x1;    // 0001
	final short WORLD_ENTITY = 0x1 << 1;

	protected World world;
	protected Body body;
	protected BodyDef bDef;
	protected FixtureDef fDef;
	protected PolygonShape pShape;
	
	protected CharacterState currentState;
	protected CharacterState previousState;
	protected boolean runningRight;
	protected float stateTimer;
	protected TextureAtlas atlas;
	
	public Character(World world) {
		this.world = world;
		defineCharacter();
		stateTimer = 0;
	}

	protected abstract void defineCharacter();
	
	public abstract void update(float delta);
	
	protected abstract TextureRegion getFrame(float delta);
	
	protected abstract CharacterState getState();
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Animation createAnimation(String atlasFile) {
		atlas = new TextureAtlas(atlasFile);
		
		return new Animation(0.1f, atlas.createSprites());
	}
	
	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	public BodyDef getbDef() {
		return bDef;
	}

	public void setbDef(BodyDef bDef) {
		this.bDef = bDef;
	}

	public FixtureDef getfDef() {
		return fDef;
	}

	public void setfDef(FixtureDef fDef) {
		this.fDef = fDef;
	}
	
	public PolygonShape getcShape() {
		return pShape;
	}

	public void setcShape(PolygonShape cShape) {
		this.pShape = pShape;
	}
}

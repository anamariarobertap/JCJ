package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.JumpCookieJump;

public class Player extends Character {
	
	private Animation happy;
	private Animation idle;
	private Animation jump;
	private Animation run;
	private Animation sad;
	
	public Player(World world) {
		super(world);
		
		currentState = CharacterState.IDLE;
		previousState = CharacterState.IDLE;
		runningRight = true;
				
		setBounds(0, 0, 35 / JumpCookieJump.PPM, 35 / JumpCookieJump.PPM);
		
		happy = createAnimation("sprites/cookie/happy.atlas");
		idle = createAnimation("sprites/cookie/idle.atlas");
		jump = createAnimation("sprites/cookie/jump_right.atlas");
		run = createAnimation("sprites/cookie/run_right.atlas");
		sad = createAnimation("sprites/cookie/sad.atlas");
	}

	@Override
	protected void defineCharacter() {
		bDef = new BodyDef();
		bDef.type = BodyDef.BodyType.DynamicBody;
		bDef.position.set(200 / JumpCookieJump.PPM, 200 / JumpCookieJump.PPM);
		
		pShape = new PolygonShape();
		pShape.setAsBox(8 / JumpCookieJump.PPM, 13 / JumpCookieJump.PPM);
		
		fDef = new FixtureDef();
		fDef.shape = pShape;
		fDef.density =0.1f;
		fDef.restitution = 0.3f;
		fDef.filter.categoryBits = PHYSICS_ENTITY;
		fDef.filter.maskBits = WORLD_ENTITY|PHYSICS_ENTITY;
		
		body = world.createBody(bDef);
		body.createFixture(fDef);
		pShape.dispose();
	}

	@Override
	public void update(float delta) {
		setPosition(body.getPosition().x - getWidth() / 2 - 1.5f / JumpCookieJump.PPM, 
					body.getPosition().y - getHeight() / 2 + 2.5f / JumpCookieJump.PPM);
		setRegion(getFrame(delta));
	}

	@Override
	protected TextureRegion getFrame(float delta) {		
		currentState = getState();
		
		boolean loopingAnim;
		Animation currentAnim;
		switch (currentState) {
			case JUMP:
				loopingAnim = true;
				currentAnim = jump;
				break;
			case RUN:
				loopingAnim = true;
				currentAnim = run;
				break;
			default:
				loopingAnim = true;
				currentAnim = idle;
				break;
		}

		TextureRegion region = (TextureRegion) currentAnim.getKeyFrame(stateTimer, loopingAnim);
		
		if ((body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
			region.flip(true, false);
			runningRight = false;
		} else if ((body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
			region.flip(true, false);
			runningRight = true;
		}
		
		stateTimer = currentState == previousState ? stateTimer + delta : 0;
		previousState = currentState;
		
		return region;
	}

	@Override
	protected CharacterState getState() {
		if (body.getLinearVelocity().y > 0 || (body.getLinearVelocity().y < 0 && previousState == CharacterState.JUMP)) {
			return CharacterState.JUMP;
		} else if (body.getLinearVelocity().y < 0) {
			return CharacterState.FALL;
		} else if (body.getLinearVelocity().x != 0) {
			return CharacterState.RUN;
		} else {
			return CharacterState.IDLE;
		}
	}
}

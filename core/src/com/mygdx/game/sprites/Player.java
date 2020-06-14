package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.JumpCookieJump;

import static com.mygdx.game.JumpCookieJump.COOKIE_BIT;

public class Player extends Character {

	private final Animation<TextureRegion> happy;
	private final Animation<TextureRegion> idle;
	private final Animation<TextureRegion> jump;
	private final Animation<TextureRegion> run;
	private final Animation<TextureRegion> sad;

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

	public void hitByNurse(Nurse nurse) {

	}

	@Override
	protected void defineCharacter() {
		bDef = new BodyDef();
		bDef.type = BodyDef.BodyType.DynamicBody;
		//bDef.position.set(200 / JumpCookieJump.PPM, 200 / JumpCookieJump.PPM);

		pShape = new PolygonShape();
		pShape.setAsBox(8 / JumpCookieJump.PPM, 13 / JumpCookieJump.PPM);

		fDef = new FixtureDef();
		fDef.shape = pShape;
		fDef.filter.categoryBits = JumpCookieJump.COOKIE_BIT;
		fDef.filter.maskBits = JumpCookieJump.DEFAUL_BIT | JumpCookieJump.COCOA_BIT;

		body = world.createBody(bDef);
		body.createFixture(fDef);
		pShape.dispose();
		EdgeShape head = new EdgeShape();
		head.set(new Vector2(-2/JumpCookieJump.PPM, 7/JumpCookieJump.PPM), new Vector2(2/JumpCookieJump.PPM, 7/JumpCookieJump.PPM));
		fDef.shape = head;
		fDef.isSensor = true;

		body.createFixture(fDef).setUserData("head");
		
		setSpawnPosition(200 / JumpCookieJump.PPM, 200 / JumpCookieJump.PPM);
	}

	@Override
	public void update(float delta) {
		setPosition(body.getPosition().x - getWidth() / 2 - 1.5f / JumpCookieJump.PPM,
				body.getPosition().y - getHeight() / 2 + 2.5f / JumpCookieJump.PPM);
		setRegion(getFrame(delta));
		
		// TODO: remove one lifepoint here
		if (body.getPosition().y < 0) {
			setSpawnPosition(200 / JumpCookieJump.PPM, 200 / JumpCookieJump.PPM);
		}
	}

	@Override
	protected TextureRegion getFrame(float delta) {
		currentState = getState();

		boolean loopingAnim;
		Animation<TextureRegion> currentAnim;
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

		TextureRegion region = currentAnim.getKeyFrame(stateTimer, loopingAnim);

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
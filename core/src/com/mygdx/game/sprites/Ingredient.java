package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.JumpCookieJump;

import javax.swing.*;

public class Ingredient extends Character {


    private final int BLANK_COCOA = 28;
    private Animation cocoa;

    public Ingredient(World world) {
        super(world);

        currentState = CharacterState.cocoa;
        previousState = CharacterState.cocoa;
        cocoa = createAnimation("sprites/incredients/cocoa.atlas");
        setBounds(3, 3, 25 / JumpCookieJump.PPM, 25 / JumpCookieJump.PPM);


    }

    @Override
    protected void defineCharacter() {
        bDef = new BodyDef();
        bDef.type = BodyDef.BodyType.StaticBody;
        bDef.position.set(250/ JumpCookieJump.PPM, 200 / JumpCookieJump.PPM);

        // testing rectangle
        pShape = new PolygonShape();
        pShape.setAsBox(10 / JumpCookieJump.PPM, 10 / JumpCookieJump.PPM);

        fDef = new FixtureDef();
        fDef.shape = pShape;
        fDef.isSensor = true;


        body = world.createBody(bDef);
        Fixture fixture = body.createFixture(fDef);
        fixture.setUserData(this);

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

        if (currentState == CharacterState.cocoa) {
            loopingAnim = false;
            currentAnim = cocoa;
        }
        else {
            loopingAnim = true;
            currentAnim = cocoa;
        }

        TextureRegion region = (TextureRegion) currentAnim.getKeyFrame(stateTimer, loopingAnim);
        stateTimer = currentState == previousState ? stateTimer + delta : 0;
        previousState = currentState;

        return region;
    }

    @Override
    protected CharacterState getState() {
        return CharacterState.cocoa;
    }



}

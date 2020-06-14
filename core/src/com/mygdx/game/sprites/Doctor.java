package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.JumpCookieJump;

import java.util.Random;

public class Doctor extends Character {

    private final Animation<TextureRegion> idle;
    public Doctor(World world) {
        super(world);

        setBounds(0, 0, 30 / JumpCookieJump.PPM, 30 / JumpCookieJump.PPM);
        idle = createAnimation("sprites/doctor/idle" + getRandomDoctor() + ".atlas");
    }

    @Override
    protected void defineCharacter() {

        bDef = new BodyDef();
        bDef.type = BodyDef.BodyType.StaticBody;
        bDef.position.set(200 / JumpCookieJump.PPM, 200 / JumpCookieJump.PPM);

        // testing rectangle
        pShape = new PolygonShape();
        pShape.setAsBox(10 / JumpCookieJump.PPM, 10 / JumpCookieJump.PPM);

        fDef = new FixtureDef();
        fDef.shape = pShape;

        body = world.createBody(bDef);
        body.createFixture(fDef);
    }

    @Override
    public void update(float delta) {
        setPosition(body.getPosition().x - getWidth() / 2 - 1.5f / JumpCookieJump.PPM,
                body.getPosition().y - getHeight() / 2 + 2.5f / JumpCookieJump.PPM);
        setRegion(getFrame(delta));
    }

    @Override
    protected TextureRegion getFrame(float delta) {
        stateTimer+=delta*0.75;
        return idle.getKeyFrame(stateTimer, true);
    }

    @Override
    protected CharacterState getState() {
        return CharacterState.IDLE;
    }

    private int getRandomDoctor() {
        Random r = new Random();
        return r.nextInt(4) + 1;
    }
}
package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.JumpCookieJump;

public class Doctor extends Character {

    private Animation idle;
    public Doctor(World world) {
        super(world);

        currentState = CharacterState.IDLE;
        previousState = CharacterState.IDLE;

        setBounds(0, 0, 20 / JumpCookieJump.PPM, 20 / JumpCookieJump.PPM);

        idle = createAnimation("sprites/doctor/idle/idle.atlas");

    }

    @Override
    protected void defineCharacter() {

        bDef = new BodyDef();
        bDef.type = BodyDef.BodyType.DynamicBody;
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
        currentState = getState();
        Animation currentAnim;
        boolean loopingAnim;
        switch (currentState) {
            default:
                loopingAnim = true;
                currentAnim = idle;
                break;}
        TextureRegion region = (TextureRegion) currentAnim.getKeyFrame(stateTimer, loopingAnim);

        stateTimer = currentState == previousState ? stateTimer + delta : 0;
        previousState = currentState;

        return region;
    }

    @Override
    protected CharacterState getState() {
        return CharacterState.IDLE;
    }
}

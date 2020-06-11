package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.JumpCookieJump;

public class Nurse extends Character {


    private Animation idle;
    private Animation run;

    public Nurse(World world) {
        super(world);

        currentState = CharacterState.IDLE;
        previousState = CharacterState.IDLE;
        runningRight = true;

        setBounds(3, 3, 25 / JumpCookieJump.PPM, 25 / JumpCookieJump.PPM);


        run = createAnimation("sprites/nurse/run_right.atlas");
        idle = createAnimation("sprites/nurse/idle.atlas");
    }




    @Override
    protected void defineCharacter() {
        bDef = new BodyDef();
        bDef.type = BodyDef.BodyType.DynamicBody;
        bDef.position.set(100 / JumpCookieJump.PPM, 200 / JumpCookieJump.PPM);

        // testing rectangle
        pShape = new PolygonShape();
        pShape.setAsBox(10 / JumpCookieJump.PPM, 10 / JumpCookieJump.PPM);

        fDef = new FixtureDef();
        fDef.shape = pShape;
        fDef.density =0.1f;
        fDef.restitution = 0.5f;
        //fDef.filter.categoryBits = PHYSICS_ENTITY;
        //fDef.filter.maskBits = WORLD_ENTITY|PHYSICS_ENTITY;

        body = world.createBody(bDef);
        body.createFixture(fDef);

        pShape.dispose();
        body.setLinearVelocity(0, 0.75f);
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
       if (body.getLinearVelocity().x != 0) {
            return CharacterState.RUN;
        } else {
            return CharacterState.IDLE;
        }
    }
}

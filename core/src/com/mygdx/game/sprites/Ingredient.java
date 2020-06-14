package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.JumpCookieJump;

import java.util.Random;

public class Ingredient extends Character {

    private static final float SCALE = 0.5f;
    private final Animation<TextureRegion> ingredient;

    public Ingredient(World world) {
        super(world);

        ingredient = createAnimation("sprites/ingredients/" + getRandomIncredient() + ".atlas");
        super.setScale(SCALE);
        setBounds(0, 0, 25 / JumpCookieJump.PPM, 25 / JumpCookieJump.PPM);
    }

    @Override
    protected void defineCharacter() {
        bDef = new BodyDef();
        bDef.type = BodyDef.BodyType.StaticBody;
        bDef.position.set(250/ JumpCookieJump.PPM, 200 / JumpCookieJump.PPM);

        pShape = new PolygonShape();
        pShape.setAsBox(5 / JumpCookieJump.PPM, 5 / JumpCookieJump.PPM);

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
        setPosition(body.getPosition().x - getWidth()*SCALE/2,
                body.getPosition().y - getHeight()*SCALE/2);
        setRegion(getFrame(delta));
    }

    @Override
    protected TextureRegion getFrame(float delta) {
        stateTimer += delta;
        return ingredient.getKeyFrame(stateTimer, true);
    }

    @Override
    protected CharacterState getState() {
        return CharacterState.ingredient;
    }

    private String getRandomIncredient() {
        Random r = new Random();
        switch (r.nextInt(4)) {
            case 0:
                return "cocoa";
            case 1:
                return "egg";
            case 2:
                return "milk";
            default:
                return "sugar";
        }
    }
}
package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.JumpCookieJump;
import com.mygdx.game.screen.button.MenuButton;
import com.mygdx.game.sprites.Doctor;
import com.mygdx.game.sprites.Ingredient;
import com.mygdx.game.sprites.Nurse;
import com.mygdx.game.sprites.Player;

import java.util.Arrays;

import static java.lang.Math.max;
import static java.lang.Math.nextUp;
import static java.lang.StrictMath.min;

public class GameScreen extends AbstractScreen {


    private Texture backgroundTexture;
    private Image backgroundImage;

    public int t=0;

    private MenuButton backButton;

    // cam variable
    private OrthographicCamera cam;

    // map variables
    private TmxMapLoader mapLoader;
    private TiledMap gameMap;

    private OrthogonalTiledMapRenderer mapRenderer;

    // physics variables
    private World world;
    private Box2DDebugRenderer b2dRenderer;

    // CookieMaster
    private Player cookieMaster;
    private int jumpCounter = 0;

    // Nurse 1
    private Nurse nurse1;

    //Doctor1

    private Doctor doc1;

    //Ingredient - Cocoa
    private Ingredient cocoa;
    private Array<Ingredient> ingredientArrays;

    //score label
    private static Label scoreLabel;
    public BitmapFont font = new BitmapFont();

    // music
    private final Music gameMusic;
    private final Music nurseMusic;
    public static int score=0;

    final float PIXELS_TO_METERS = 5f;
    protected Fixture fixture;
    public static String scoreName = "";

    public GameScreen(final JumpCookieJump game) {
        this(game, 1);
    }

    public GameScreen(final JumpCookieJump game, int level) {
        super(game);


        // orthographic: no 3 axis
        cam = new OrthographicCamera();
        // fitviewport is good bc the screens scale themselves to match the app window
        viewport = new FitViewport((JumpCookieJump.V_WIDTH / 4) / JumpCookieJump.PPM, (JumpCookieJump.V_HEIGHT / 4) / JumpCookieJump.PPM, cam);
        stage = new Stage(viewport, game.batch);

        mapLoader = new TmxMapLoader();
        gameMap = mapLoader.load("Maps/Tilemap/level" + level + ".tmx");

        mapRenderer = new OrthogonalTiledMapRenderer(gameMap, 1 / JumpCookieJump.PPM);
        // sets it to the middle
        cam.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        buildMapCollisionObjects();



        cookieMaster = new Player(world);
        nurse1 = new Nurse(world);
        doc1 = new Doctor(world);

        cocoa = new Ingredient(world);

        // adding the background
        backgroundTexture = new Texture("background/game_background.jpg");
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setSize(JumpCookieJump.V_WIDTH, JumpCookieJump.V_HEIGHT);
        //stage.addActor(backgroundImage);

        // Creating a style for the Title // can be done in future with style.json
        Label.LabelStyle label1Style = new Label.LabelStyle();
        label1Style.font = new BitmapFont();
        label1Style.fontColor = Color.RED;

        Label title = new Label("Game Screen", label1Style);
        title.setAlignment(Align.center);
        title.setY((float) Gdx.graphics.getHeight()*2/3);
        title.setWidth(Gdx.graphics.getWidth());
        stage.addActor(title);



        handleBackButton();

        // game music
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/ingame/game.mp3"));
        nurseMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/ingame/nurse/nurse1.mp3"));
        nurseMusic.setVolume(0.4f);
        nurseMusic.setLooping(false);

        gameMusic.setVolume(0.3f);
        gameMusic.setLooping(true);

    }

    public static void addScore(int value){
        score += value;
        scoreName = "score" + score;

    }

    private void buildMapCollisionObjects() {
        // vector: gravity, true: objects that are at rest sleep (no calculations)
        world = new World(new Vector2(0, -1000 / JumpCookieJump.PPM), true);
        b2dRenderer = new Box2DDebugRenderer();

        Rectangle rect;
        BodyDef bDef = new BodyDef();
        PolygonShape pShape = new PolygonShape();
        FixtureDef fDef = new FixtureDef();
        Body body;

        // every object in tilemap editor gets a body and fdef
        // layer 1 = ground; rectangles on the ground
        // layer 4 = collision for level1.tmx
        for (MapObject obj : gameMap.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            rect = ((RectangleMapObject) obj).getRectangle();

            bDef.type = BodyDef.BodyType.StaticBody; // stuff like ground doesnt move
            bDef.position.set((rect.getX() + rect.getWidth() / 2) / JumpCookieJump.PPM, (rect.getY() + rect.getHeight() / 2) / JumpCookieJump.PPM);

            pShape.setAsBox((rect.getWidth() / 2) / JumpCookieJump.PPM, (rect.getHeight() / 2) / JumpCookieJump.PPM);

            fDef.shape = pShape;

            body = world.createBody(bDef);
            fixture = body.createFixture(fDef);
        }
        // ground which is a polygon (e.g. corners)
        for (MapObject obj : gameMap.getLayers().get(4).getObjects().getByType(PolygonMapObject.class)) {
            Polygon poly = ((PolygonMapObject) obj).getPolygon();

            bDef.type = BodyDef.BodyType.StaticBody; // stuff like ground doesnt move
            bDef.position.set(poly.getX() / JumpCookieJump.PPM, poly.getY() / JumpCookieJump.PPM);

            body = world.createBody(bDef);

            //pShape.setAsBox(poly.getBoundingRectangle().getWidth() / 2, poly.getBoundingRectangle().getHeight() / 2);
            pShape.set(convertDoublesToFloats(Arrays.stream(convertFloatsToDoubles(poly.getVertices())).map(v -> v / JumpCookieJump.PPM).toArray()));
            fDef.shape = pShape;
            body.createFixture(fDef);
        }

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {

                // check the collision with the Ingredient
                if ((contact.getFixtureA().getBody() == cocoa.getBody()) && contact.getFixtureB().getBody() == cookieMaster.getBody() ||
                        (contact.getFixtureA().getBody() == cookieMaster.getBody() &&
                                contact.getFixtureB().getBody() == cocoa.getBody())) {
                    System.out.println("ingredient touched");
                    addScore(1);
                    System.out.println(score);
                }
                //check the collision with the nurse
                if ((contact.getFixtureA().getBody() == cookieMaster.getBody() &&
                        contact.getFixtureB().getBody() == nurse1.getBody())
                        ||
                        (contact.getFixtureA().getBody() == nurse1.getBody() &&
                                contact.getFixtureB().getBody() == cookieMaster.getBody())) {

                    //cookieMaster.getBody().applyForceToCenter(0, MathUtils.random(0f, 0.2f), true);
                    nurse1.getBody().setLinearVelocity(10f / JumpCookieJump.PPM,Gdx.input.getY());
                    nurseMusic.play();
                    System.out.println("Nurse touched");
                    addScore(-1);
                    System.out.println(score);
                }
            }

            @Override
            public void endContact(Contact contact) {


            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
        }


	public static double[] convertFloatsToDoubles(float[] input) {
        double[] output = new double[input.length];
        for (int i = 0; i < input.length; i++)
        {
            output[i] = input[i];
        }
        return output;
    }

    public static float[] convertDoublesToFloats(double[] input) {
        float[] output = new float[input.length];
        for (int i = 0; i < input.length; i++)
        {
            output[i] = (float) input[i];
        }
        return output;
    }

    public void handleInput(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            if (jumpCounter < 2 && cookieMaster.getBody().getLinearVelocity().y <= 0) {
                cookieMaster.getBody().applyLinearImpulse(new Vector2(0, 400f / JumpCookieJump.PPM), cookieMaster.getBody().getWorldCenter(), true);
                jumpCounter++;
            }

        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && cookieMaster.getBody().getLinearVelocity().x <= 200f / JumpCookieJump.PPM) {
            cookieMaster.getBody().applyLinearImpulse(new Vector2(10f / JumpCookieJump.PPM, 0), cookieMaster.getBody().getWorldCenter(), true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && cookieMaster.getBody().getLinearVelocity().x >= -200f / JumpCookieJump.PPM) {
            cookieMaster.getBody().applyLinearImpulse(new Vector2(-10f / JumpCookieJump.PPM, 0), cookieMaster.getBody().getWorldCenter(), true);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            switchMusic();
            game.setScreen(game.menuScreen);
        }

    }



        /*if(Gdx.input.isTouched()) { // Detect is finger on the screen
            int x = Gdx.input.getX(); // get x touch coordination
            int y = Gdx.input.getY(); // get y touch coordination

            if(x < Gdx.graphics.getWidth()/2) { // First half Screen ==> move backward
                nurse.getBody().setLinearVelocity(-10f / JumpCookieJump.PPM,y);
            }else if(x >= Gdx.graphics.getHeight() /2) { // Second half Screen ==> move forward
                nurse.getBody().setLinearVelocity(10f / JumpCookieJump.PPM,y);
            }
        }else { // no finger is on the screen
        nurse.getBody().setLinearVelocity(0,0); // ==> no moving body
           // nurse.getWorld().destroyBody(nurse.getBody());
    }

    }*/




    @Override
    public void show() {
        super.show();
    }

    private void handleBackButton() {
        backButton = MenuButton.getMenuButton("Back to Menu", 75, 300);
        backButton.setPosition((float) JumpCookieJump.V_WIDTH/2 - backButton.getWidth(), (float) JumpCookieJump.V_HEIGHT/2 - backButton.getHeight());

        stage.addActor(backButton);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("GameScreen");
                switchMusic();
                game.setScreen(game.menuScreen);
            }
        });
    }

    @Override
    public void render(float delta) {
        //check if player is on the ground
        if (cookieMaster.getBody().getLinearVelocity().y == 0)
            jumpCounter = 0;

        handleInput(delta);

        //handleNurse(nurse1);
        // second two parameters: how bodies collide (high nr --> much calculations)
        world.step(1/60f, 6, 2);

        cam.position.x = cookieMaster.getBody().getPosition().x;
        cam.position.y = cookieMaster.getBody().getPosition().y;


        // limit camera to the map boundaries
        cam.position.x = clamp(cam.position.x, ((int) mapRenderer.getMap().getProperties().get("width")*32) / JumpCookieJump.PPM - viewport.getWorldWidth()/2, viewport.getWorldWidth()/2);
        cam.position.y = clamp(cam.position.y, ((int) mapRenderer.getMap().getProperties().get("height")*32) / JumpCookieJump.PPM - viewport.getWorldHeight()/2, viewport.getWorldHeight()/2);



        //System.out.println("clamp: " + cam.position.x + " : " + cam.position.y);
        cam.update();
        mapRenderer.setView(cam);

        // clear screen
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // this line has to be after those two above! (after screen got cleared)
        mapRenderer.render();

        // render collision lines
        b2dRenderer.render(world, cam.combined);

        cookieMaster.update(delta);
        nurse1.update(delta);
        cocoa.update(delta);
        doc1.update(delta);



        // render player textures
        stage.getBatch().setProjectionMatrix(cam.combined);
        Batch batch = stage.getBatch();
        batch.begin();

        font.draw(batch,scoreName,0.05f,0.5f);



        cookieMaster.draw(stage.getBatch());
        cocoa.draw(stage.getBatch());
        nurse1.draw(stage.getBatch());
        doc1.draw(stage.getBatch());
        stage.getBatch().end();

        stage.getBatch().setProjectionMatrix(cam.combined);


        stage.act(delta);
        stage.draw();
    }

    public float clamp(float var, float max, float min) {
        if(var > min) {
            if(var < max) {
                return var;
            } else return max;
        } else return min;
        /*
         * var = your variable that you want to clamp.
         * max = the maximum value of this variable.
         * min = the minimum value of this variable.
         */
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        super.dispose();
        backgroundTexture.dispose();
        gameMap.dispose();
        mapRenderer.dispose();
        world.dispose();
        b2dRenderer.dispose();
        gameMusic.dispose();
    }

    protected void stopMusic() {
        gameMusic.stop();
    }

    protected void startMusic() {
        gameMusic.play();
    }

    protected void switchMusic() {
        stopMusic();
        game.menuScreen.startMusic();
    }
}
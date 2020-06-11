package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.JumpCookieJump;
import com.mygdx.game.screen.button.MenuButton;

public class SettingsScreen extends AbstractScreen {

    private final Texture backgroundTexture;
    private final Image backgroundImage;

    private MenuButton backButton;

    // For Map Loading
    private TiledMap tiledMap;
    private AssetManager manager;
    private OrthogonalTiledMapRenderer tiledMapRenderer;

    public SettingsScreen(JumpCookieJump game) {
        super(game);

        // adding the background
        backgroundTexture = new Texture("background/SettingsWallpaper.png");
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setSize(JumpCookieJump.V_WIDTH, JumpCookieJump.V_HEIGHT);
        stage.addActor(backgroundImage);

        // create a TextButton
//        handlePlayButton();
//        handleBackButton();

        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        Container<Table> tableContainer = new Container<Table>();

        float sw = Gdx.graphics.getWidth();
        float sh = Gdx.graphics.getHeight();

        float cw = sw * 0.7f;
        float ch = sh * 0.5f;

        tableContainer.setSize(cw, ch);
        tableContainer.setPosition((sw - cw) / 2.0f, (sh - ch) / 2.0f);
        tableContainer.fillX();

        Table table = new Table(skin);

        // creating a background
        Pixmap pix = new Pixmap((int) cw, (int) ch, Pixmap.Format.RGBA8888);
        pix.setColor(0.38f,0.38f,0.38f,0.6f);
        pix.fillRectangle(0,75, (int) cw, (int) ch - 240);
        table.setBackground(new TextureRegionDrawable(new Texture(pix)));
        pix.dispose();

        Label topLabel = new Label("Volume", skin);
        topLabel.setAlignment(Align.center);
        Slider slider = new Slider(0, 100, 1, false, skin);
        Label moveLabel = new Label("Movement", skin);
        moveLabel.setAlignment(Align.center);
        Label upLabel = new Label("Up / Jump", skin);
        upLabel.setAlignment(Align.center);
        Label downLabel = new Label("Down", skin);
        downLabel.setAlignment(Align.center);
        Label rightLabel = new Label("Right", skin);
        rightLabel.setAlignment(Align.center);
        Label leftLabel = new Label("Left", skin);
        leftLabel.setAlignment(Align.center);

        // TODO : add the actual key bindings for the movement

        CheckBox checkBoxA = new CheckBox("Checkbox Left", skin);
        CheckBox checkBoxB = new CheckBox("Checkbox Center", skin);
        CheckBox checkBoxC = new CheckBox("Checkbox Right", skin);

        Table buttonTable = new Table(skin);

        TextButton buttonA = new TextButton("LEFT", skin);
        TextButton buttonB = new TextButton("RIGHT", skin);

        backButton = MenuButton.getMenuButton("Back to Menu", 75, 300);
//        backButton.setPosition((float) JumpCookieJump.V_WIDTH / 2 - backButton.getWidth() / 2, (float) JumpCookieJump.V_HEIGHT / 2 - backButton.getHeight() / 2);

        stage.addActor(backButton);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("SettingsScreen");
                game.setScreen(game.menuScreen);
            }
        });

        table.row().colspan(3).expandX().fillX();
        table.add(topLabel).fillX();
        table.row().colspan(2).expandX().fillX();
        table.add(topLabel).width(cw/3.0f);
        table.add(slider).width(cw/3.0f);
        table.row().colspan(3).expandX().fillX().pad(15, 0, 15, 0);
        table.add(moveLabel).center();
        table.row().colspan(2).expandX().fillX().padBottom(15);
        table.add(upLabel).fillX();
        table.row().colspan(2).expandX().fillX().padBottom(15);
        table.add(downLabel).fillX();
        table.row().colspan(2).expandX().fillX().padBottom(15);
        table.add(rightLabel).fillX();
        table.row().colspan(2).expandX().fillX().padBottom(15);
        table.add(leftLabel).fillX();

        table.row().expandX().fillX();;
        table.add(checkBoxA).expandX().fillX();
        table.add(checkBoxB).expandX().fillX();
        table.add(checkBoxC).expandX().fillX();
        table.row().expandX().fillX();

        table.add(buttonTable).colspan(3);

        buttonTable.pad(16);
        buttonTable.row().fillX().expandX();
        buttonTable.add(backButton).width(cw/3.0f);
//        buttonTable.add(buttonB).width(cw/3.0f);

        tableContainer.setActor(table);
        stage.addActor(tableContainer);

    }

    private void handleBackButton() {
        backButton = MenuButton.getMenuButton("Back to Menu", 75, 300);
        backButton.setPosition((float) JumpCookieJump.V_WIDTH / 2 - backButton.getWidth() / 2, (float) JumpCookieJump.V_HEIGHT / 2 - backButton.getHeight() / 2);

        stage.addActor(backButton);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("SettingsScreen");
                game.setScreen(game.menuScreen);
            }
        });
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}

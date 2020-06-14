package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.JumpCookieJump;
import com.mygdx.game.screen.button.LevelSelectButton;
import com.mygdx.game.screen.button.MenuButton;

public class LevelSelectScreen extends AbstractScreen {

    private final Texture backgroundTexture;
    private final Image backgroundImage;

    public LevelSelectScreen(JumpCookieJump game) {
        super(game);

        // adding the background
        backgroundTexture = new Texture("background/LevelSelectWallpaper.png");
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setSize(JumpCookieJump.V_WIDTH, JumpCookieJump.V_HEIGHT);
        stage.addActor(backgroundImage);

        // create a TextButton
//        handlePlayButton();
        handleButtons(3);
        handleBackButton();
    }

    private void handleBackButton() {
        MenuButton backButton = MenuButton.getMenuButton("Back to Menu", 75, 300);
        backButton.setPosition((float) JumpCookieJump.V_WIDTH / 2 - backButton.getWidth() / 2, (float) JumpCookieJump.V_HEIGHT / 2 - backButton.getHeight() / 2 - 180);

        stage.addActor(backButton);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.menuScreen.buttonClick();
                game.setScreen(game.menuScreen);
            }
        });
    }

    private void handleButtons(int sum) {
        for(int i = 0; i < sum; i++) {
        	LevelSelectButton button = LevelSelectButton.getLevelSelectButton("Level " + (i + 1), 290, 300);
            button.setPosition((float) JumpCookieJump.V_WIDTH / 2 - button.getWidth() / 2 - (button.getWidth() * ((sum-1)/2)) + (button.getWidth()*(i)), (float) JumpCookieJump.V_HEIGHT / 2 - button.getHeight() / 2);

            stage.addActor(button);
            int finalI = i;
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("LevelSelectScreen");
                    game.menuScreen.buttonClick();
                    GameScreen gameScreen = new GameScreen(game, finalI +1);
                    game.menuScreen.switchMusic(gameScreen);
                    game.setScreen(gameScreen);
                }
            });
        }
    }
}

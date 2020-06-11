package com.mygdx.game.screen.button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class LevelSelectButton extends TextButton {

    private static final String defaultFontFile = "UI/Magic Dreams.ttf";
    private static final int defaultFontSize = 62;
    private static final String defaultBackgroundFile = "UI/SelectLevel.png";
    private static final String level1BackgroundFile = "background/level1.jpg";
    private static final String level2BackgroundFile = "background/level2.png";

    /**
     * @param text  Text on the button
     * @param style Style of the button
     */
    private LevelSelectButton(String text, TextButton.TextButtonStyle style) {
        super(text, style);
    }

    /**
     * Return a MenuButton
     *
     * @param text       Text on the button
     * @param background Backgroundtexture of the button
     * @param height     Height of the button
     * @param width      Width of the button
     * @return MenuButton with parameters
     */
    public static LevelSelectButton getLevelSelectButton(String text, Texture background, int height, int width) {
        return getLevelSelectButton(text, defaultFontFile, defaultFontSize, background, height, width);
    }

    /**
     * Return a MenuButton
     *
     * @param text     Text on the button
     * @param fontFile Font File path
     * @param fontSize Size of the font
     * @param height   Height of the button
     * @param width    Width of the button
     * @return MenuButton with parameters
     */
    public static LevelSelectButton getLevelSelectButton(String text, String fontFile, int fontSize, int height, int width) {
        return getLevelSelectButton(text, fontFile, fontSize, new Texture(defaultBackgroundFile), height, width);
    }

    /**
     * Return a MenuButton
     *
     * @param text   Text on the button
     * @param height Height of the button
     * @param width  Width of the button
     * @return MenuButton with parameters
     */
    public static LevelSelectButton getLevelSelectButton(String text, int height, int width) {
        return getLevelSelectButton(text, defaultFontFile, defaultFontSize, new Texture(defaultBackgroundFile), height, width);
    }

    /**
     * Return a MenuButton
     *
     * @param text       Text on the button
     * @param fontFile   Font File path
     * @param fontSize   Size of the font
     * @param background Backgroundtexture of the button
     * @param height     Height of the button
     * @param width      Width of the button
     * @return MenuButton with parameters
     */
    public static LevelSelectButton getLevelSelectButton(String text, String fontFile, int fontSize, Texture background, int height, int width) {
//        Drawable drawable = new TextureRegionDrawable(new TextureRegion(background));
        Drawable drawable = new LevelImage(background, new Texture(level1BackgroundFile)/*, height, width*/);
        drawable.setMinHeight(height);
        drawable.setMinWidth(width);

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(drawable, drawable, drawable, getButtonFont(fontFile, fontSize));
        return new LevelSelectButton(text, style);
    }

    /**
     * Setting the font of the menu buttons
     *
     * @param fontFile File + path of *.ttf-File
     */
    private static BitmapFont getButtonFont(String fontFile, int fontSize) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontFile));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = fontSize;
        parameter.color = Color.WHITE;
//        parameter.borderColor = Color.BLACK;

        return generator.generateFont(parameter);
    }

    private static class LevelImage extends BaseDrawable {
        private final Image img1;
        private final Image img2;

//        private final int height;
//        private final int width;

        public LevelImage(Texture texture1, Texture texture2/*, int height, int width*/) {
            img1 = new Image(texture1);
            img2 = new Image(texture2);

//            this.height = height;
//            this.width = width;
        }

        @Override
        public void draw(Batch batch, float x, float y, float width, float height) {
            img1.getDrawable().draw(batch, x, y, width, height);
            img2.getDrawable().draw(batch, x + 35, y + 40, width-80, width-80);
        }

    }
}

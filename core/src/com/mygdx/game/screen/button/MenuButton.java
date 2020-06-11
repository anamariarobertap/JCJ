package com.mygdx.game.screen.button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MenuButton extends TextButton {

    private static final String defaultFontFile = "UI/Magic Dreams.ttf";
    private static final int defaultFontSize = 46;
    private static final String defaultBackgroundFile = "UI/button.png";

    /**
     * @param text Text on the button
     * @param style Style of the button
     */
    private MenuButton(String text, TextButtonStyle style) {
        super(text, style);
    }

    /**
     * Return a MenuButton
     *
     * @param text Text on the button
     * @param background Backgroundtexture of the button
     * @param height Height of the button
     * @param width Width of the button
     * @return MenuButton with parameters
     */
    public static MenuButton getMenuButton(String text, Texture background, int height, int width) {
        return getMenuButton(text, defaultFontFile, defaultFontSize, background, height, width);
    }

    /**
     * Return a MenuButton
     *
     * @param text Text on the button
     * @param fontFile Font File path
     * @param fontSize Size of the font
     * @param height Height of the button
     * @param width Width of the button
     * @return MenuButton with parameters
     */
    public static MenuButton getMenuButton(String text, String fontFile, int fontSize, int height, int width) {
        return getMenuButton(text, fontFile, fontSize, new Texture(defaultBackgroundFile), height, width);
    }

    /**
     * Return a MenuButton
     *
     * @param text Text on the button
     * @param height Height of the button
     * @param width Width of the button
     * @return MenuButton with parameters
     */
    public static MenuButton getMenuButton(String text, int height, int width) {
        return getMenuButton(text, defaultFontFile, defaultFontSize, new Texture(defaultBackgroundFile), height, width);
    }

    /**
     * Return a MenuButton
     *
     * @param text Text on the button
     * @param fontFile Font File path
     * @param fontSize Size of the font
     * @param background Backgroundtexture of the button
     * @param height Height of the button
     * @param width Width of the button
     * @return MenuButton with parameters
     */
    public static MenuButton getMenuButton(String text, String fontFile, int fontSize, Texture background, int height, int width) {
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(background));
        drawable.setMinHeight(height);
        drawable.setMinWidth(width);

        TextButtonStyle style = new TextButtonStyle(drawable, drawable, drawable, getButtonFont(fontFile, fontSize));
        return new MenuButton(text, style);
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

       return generator.generateFont(parameter);
    }
}

package com.mygdx.game.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.JumpCookieJump;

public class DesktopLauncher {
	public static void main (String[] arg) {
		// Daniel was here
		// felix was here
		//rebecca2
		// daniel r also
		// second test by DR
		// trying to commit out of eclipse
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = JumpCookieJump.V_HEIGHT;
		config.width = JumpCookieJump.V_WIDTH;
		config.addIcon("icon.jpg", Files.FileType.Internal);
		new LwjglApplication(new JumpCookieJump(), config);
	}
}

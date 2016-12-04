/**
 * 
 */
package de.tu_clausthal.mini_verkehrssimulation.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.tu_clausthal.mini_verkehrssimulation.Simulation;
import de.tu_clausthal.mini_verkehrssimulation.classes.Parameters;

/**
 * @author Ehsan Tatasadi
 *
 */
public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Mini-Verkehrssimulation";
		config.width = Parameters.SCREEN_WIDTH;
		config.height = Parameters.SCREEN_HEIGHT;
		new LwjglApplication(new Simulation(), config);
	}
}

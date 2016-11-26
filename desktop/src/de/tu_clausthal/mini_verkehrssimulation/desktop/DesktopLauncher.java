/**
 * 
 */
package de.tu_clausthal.mini_verkehrssimulation.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.tu_clausthal.mini_verkehrssimulation.Simulation;

/**
 * @author Ehsan Tatasadi
 *
 */
public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Mini-Verkehrssimulation";
		config.width = 1024;
		config.height = 1024;
		new LwjglApplication(new Simulation(), config);
	}
}

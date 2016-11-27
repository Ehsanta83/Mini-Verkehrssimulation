/**
 * 
 */
package de.tu_clausthal.mini_verkehrssimulation.classes;

import java.util.HashMap;

/**
 * @author Ehsan Tatasadi
 *
 */
public class Parameters {
	public static int SCREEN_WIDTH = 1024;
	public static int SCREEN_HEIGHT = 1024;
	public static int CAR_WIDTH = 32;
	public static int CAR_HEIGHT = 16;
	public static int STREETWEST_START_POINT_WIDTH = -32;
	public static int STREETWEST_START_POINT_HEIGHT = 488;
	public static int STREETSOUTH_START_POINT_WIDTH = 512;
	public static int STREETSOUTH_START_POINT_HEIGHT = -24;
	public static int STREETEAST_START_POINT_WIDTH = 1024;
	public static int STREETEAST_START_POINT_HEIGHT = 520;
	public static int STREETNORTH_START_POINT_WIDTH = 480;
	public static int STREETNORTH_START_POINT_HEIGHT = 1032;
	public static int[][][][][] COORDINATIONS =
		{
			{//west
				{//east
					{{-50, 0}, {488, 488}},
					{{0, 432}, {488, 488}},
					{{432, 512}, {488, 488}},
					{{512, 592}, {488, 488}},
					{{592, 1024}, {488, 488}},
					{{1024, 1074}, {488, 488}},
					{{-1, -1}, {-1, -1}},
				},
				{//south
					{{-50, 0}, {488, 488}},
					{{0, 480}, {488, 488}},
					{{480, 480}, {488, 432}},
					{{480, 480}, {432, 0}},
					{{480, 480}, {0, -50}},
					{{1024, 1074}, {488, 488}},
					{{-1, -1}, {-1, -1}},
				},
				{//north
					{{-50, 0}, {488, 488}},
					{{0, 432}, {488, 488}},
					{{432, 512}, {488, 488}},
					{{512, 512}, {488, 512}},
					{{512, 512}, {512, 592}},
					{{512, 512}, {592, 1024}},
					{{512, 512}, {1024, 1074}},
				}
			}
		};
}

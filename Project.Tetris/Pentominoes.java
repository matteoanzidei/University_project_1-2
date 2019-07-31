import java.awt.Point;
import java.awt.Color;

public class Pentominoes{
	public static Point[][] Pentomino_array = {
		//I piece
		{new Point(0, 3), new Point(0, 2), new Point(0, 1), new Point(0, 0), new Point(0, -1)},
		//L piece
		{new Point(0, 2), new Point(0, 1), new Point(0, 0), new Point(0, -1), new Point(1, -1)},
		//L piece mirrored
		{new Point(0, 2), new Point(0, 1), new Point(0, 0), new Point(0, -1), new Point(-1, -1)},
		//T piece
		{new Point(0, 2), new Point(0, 1), new Point(0, 0), new Point(-1, 2), new Point(1, 2)},
		//X piece
		{new Point(0, 0), new Point(0, 1), new Point(0, -1), new Point(1, 0), new Point(-1, 0)},
		//V piece
		{new Point(0, 2), new Point(0, 1), new Point(0, 0), new Point(1, 0), new Point(2, 0)},
		//W piece
		{new Point(0, 0), new Point(-1, 0), new Point(-1, 1), new Point(0, -1), new Point(1, -1)},
		//F piece
		{new Point(0, 2), new Point(0, 1), new Point(0, 0), new Point(-1, 1), new Point(1, 2)},
		//F piece mirrored
		{new Point(0, 2), new Point(0, 1), new Point(0, 0), new Point(1, 1), new Point(-1, 2)},
		//P piece
		{new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1), new Point(0, -1)},
		//P piece mirrored
		{new Point(0, 0), new Point(0, 1), new Point(-1, 0), new Point(-1, 1), new Point(0, -1)},
		//Z piece
		{new Point(0, 2), new Point(0, 1), new Point(0, 0), new Point(-1, 2), new Point(1, 0)},
		//Z piece mirrored
		{new Point(0, 2), new Point(0, 1), new Point(0, 0), new Point(1, 2), new Point(-1, 0)},
		//Y piece
		{new Point(0, 1), new Point(0, 2), new Point(0, 0), new Point(0, -1), new Point(1, 1)},
		//Y piece mirrored
		{new Point(0, 1), new Point(0, 2), new Point(0, 0), new Point(0, -1), new Point(-1, 1)},
		//U piece
		{new Point(0, 2), new Point(0, 1), new Point(0, 0), new Point(1, 2), new Point(1, 0)},
		//N piece
		{new Point(0, 1), new Point(0, 0), new Point(-1, 2), new Point(0, -1), new Point(-1, 1)},
		//N piece mirrored
		{new Point(0, 1), new Point(0, 0), new Point(1, 2), new Point(0, -1), new Point(1, 1)},
		//null
		{}
	};

	public static Color[] color_array = {
		Color.CYAN,
		Color.GREEN,
		Color.MAGENTA,
		Color.YELLOW,
		Color.ORANGE,
		Color.PINK,
		Color.BLACK,
		Color.BLUE,
		Color.RED,
		Color.DARK_GRAY,
	};

	public static String[] contributors = {
		"Group 12 Contributors:",
		"Matteo Anzidei",
		"Amanda Kane",
		"Ramses Kamanda",
		"Philippe Jacob",
		"Andrei Atomei",
		"Jarod Simonet"
	};
}

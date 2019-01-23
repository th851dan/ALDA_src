package minimalspanningTree;

public class TelKnoten {
	int x;
	int y;
	public TelKnoten(int x, int y) {
		this.x = x;
		this.y = y;
	}
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}

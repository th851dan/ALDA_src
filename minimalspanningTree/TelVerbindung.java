package minimalspanningTree;

public class TelVerbindung implements Comparable<TelVerbindung> {
	TelKnoten u;
	TelKnoten v;
	int c;
	public TelVerbindung(TelKnoten u, TelKnoten v, int c) {
		this.u = u;
		this.v = v;
		this.c = c;
	}
	@Override
	public int compareTo(TelVerbindung tV) {
		if (this.c < tV.c)
			return -1;
		else if(this.c > tV.c)
			return 1;
		return 0;
	}
	@Override
	public String toString() {
		return u + "->" + v;
	}

}

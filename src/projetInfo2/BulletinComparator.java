package projetInfo2;

import java.util.Comparator;

public class BulletinComparator implements Comparator<Bulletin> {

	public WorldCell cell;
	
	public BulletinComparator(WorldCell cell){
		super();
		this.cell = cell;
	}
	@Override
	public int compare(Bulletin b1, Bulletin b2) {
		float distanceTo1 = b1.distanceTo(cell);
		float distanceTo2 = b2.distanceTo(cell);
		
		if(distanceTo1 < distanceTo2){
			return -1;
		}
		if (distanceTo1 > distanceTo2){
			return 1;
		}
		else return 0;
	}

}

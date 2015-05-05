package projetInfo2;

import java.util.Comparator;

public class ResidentComparator implements Comparator<Resident> {

	public WorldCell cell;
	
	public ResidentComparator(WorldCell cell){
		super();
		this.cell = cell;
	}
	@Override
	public int compare(Resident r1, Resident r2) {
		float distanceTo1 = r1.distanceTo(cell);
		float distanceTo2 = r2.distanceTo(cell);
		
		if(distanceTo1 < distanceTo2){
			return -1;
		}
		if (distanceTo1 > distanceTo2){
			return 1;
		}
		else return 0;
	}

}

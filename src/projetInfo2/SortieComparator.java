package projetInfo2;

import java.util.Comparator;

public class SortieComparator implements Comparator<WorldCell> {
	
	private WorldCell cell;
	
	public SortieComparator(WorldCell cell){
		super();
		this.cell = cell;
	}

	@Override
	public int compare(WorldCell o1, WorldCell o2) {
		
		float distanceTo1 = o1.distanceTo(cell);
		float distanceTo2 = o1.distanceTo(cell);
		
		if(distanceTo1 < distanceTo2){
			return -1;
		}
		if (distanceTo1 > distanceTo2){
			return 1;
		}
		
		else return 0;
	}

}

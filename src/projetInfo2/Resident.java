package projetInfo2;

import fr.ensai.simulator.astar.AStarPathFinder;
import fr.ensai.simulator.world.Occupant;

public class Resident implements Occupant{

	private int col;
	private int row;
	
	
	
	public java.util.ArrayList<java.lang.Class<? extends Occupant>> listIgnorer = new java.util.ArrayList<java.lang.Class<? extends Occupant>>();
	
	public AStarPathFinder pathFinder;

	public Resident(int row, int col){
		this.setCol(col);
		this.setRow(row);
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void goToCell(int row, int col){
		if(WorldCell.world.map[row][col].isEmpty()){
			WorldCell.world.map[row][col].setOccupant(this); 
			
			WorldCell.world.map[this.row][this.col].removeOccupant();

			this.setCol(col);
			this.setRow(row);
			WorldCell.world.mouvement = true;
		}
	}
	
	public float distanceTo(WorldCell cell){
		//distance entre la case de ce gang et une autre cell 
		
		return WorldCell.world.heuristic.getGlobalCost(WorldCell.world, WorldCell.world.localCost, WorldCell.world.map[row][col], cell);
	}
	
	public void move(){
		
	}
	
	public void agit(){
		
	}
	
	public void sortir(){
		
	}



}

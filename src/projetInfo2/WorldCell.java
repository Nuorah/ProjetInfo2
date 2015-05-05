package projetInfo2;

import fr.ensai.simulator.world.Cell;
import fr.ensai.simulator.world.Occupant;

public class WorldCell implements Cell{
	
	public static World world = new World();
	
	private int col;
	private int row;
	
	private Resident occupant;
	
	public WorldCell(int row, int col){
		this.col = col;
		this.row = row;
	}
	
	public boolean isEmpty(){
		if (this.occupant == null) return true;
		else return false;
	}
	
	public int getCol(){
		return col;
	}
	
	public int getRow(){
		return row;
	}
	
	public Occupant getOccupant(){
		return occupant;
	}
	
	public void setOccupant(Resident occupant){
		this.occupant = occupant;
	}
	
	public void removeOccupant(){
		this.occupant = null;
	}
	
	public float distanceTo(WorldCell cell){
		
		return WorldCell.world.heuristic.getGlobalCost(WorldCell.world, WorldCell.world.localCost, this, cell);
	}
	
	public String toString(){
		return "" + this.getRow() + " " + this.getCol();
	}

}

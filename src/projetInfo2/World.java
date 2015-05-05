package projetInfo2;

import java.util.ArrayList;

import fr.ensai.simulator.astar.EuclideanDistanceHeuristic;
import fr.ensai.simulator.astar.PreferEmptyCellsLocalCost;
import fr.ensai.simulator.world.SimulationMap;
import fr.ensai.simulator.world.Cell;

public class World implements SimulationMap{

	public WorldCell[][] map;	

	public ArrayList<Gang> listeGang = new ArrayList<Gang>();
	public ArrayList<Mousta> listeMousta = new ArrayList<Mousta>(); 
	public ArrayList<Bulletin> listeBulletin = new ArrayList<Bulletin>(); 
	public ArrayList<Barriere> listeBarriere = new ArrayList<Barriere>();

	public ArrayList<WorldCell> listeSorties = new ArrayList<WorldCell>();

	public EuclideanDistanceHeuristic heuristic = new EuclideanDistanceHeuristic();

	public PreferEmptyCellsLocalCost localCost = new  PreferEmptyCellsLocalCost(1,3);
	
	public boolean mouvement = true;

	private int nbRows;
	private int nbCols;

	public int getNbRows(){
		return nbRows;
	}

	public int getNbCols(){
		return nbCols;
	}

	public Cell get(int row, int col){
		return map[row][col];
	}

	public void setNbRows(int nbRows) {
		this.nbRows = nbRows;
	}

	public void setNbCols(int nbCols) {
		this.nbCols = nbCols;
	}

	public void setMap(){
		map = new WorldCell[nbRows][nbCols];

		for(int i = 0; i < nbRows; i++){
			for(int j = 0; j < nbCols; j++){
				map[i][j] = new WorldCell(i, j);
			}
		}
	}

	public void viderSorties(){
		for(int i = 0; i<nbRows ; i++){
			for(int j = 0; j < nbCols; j++){
				if (listeSorties.contains(map[i][j])){
					listeGang.remove(map[i][j].getOccupant());
					this.map[i][j].removeOccupant();
				}
			}
		}
	}



}

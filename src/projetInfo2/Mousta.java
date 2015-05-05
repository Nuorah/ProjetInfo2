package projetInfo2;

import java.util.ArrayList;

import fr.ensai.simulator.astar.AStarPathFinder;
import fr.ensai.simulator.world.Cell;
import fr.ensai.simulator.world.OtherTeam;

public class Mousta extends Resident implements OtherTeam {

	public static int compteur = 1;
	
	private boolean peutAgir = true;
	
	private boolean peutBouger = true;

	public int nbBulletins = 0;
	public int id;

	public java.util.ArrayList<Cell> chemin = new java.util.ArrayList<Cell>(100);
	public ArrayList<Gang> listeGangVoisin = new ArrayList<Gang>();
	
	public ArrayList<Gang> listeGangsCible = new ArrayList<Gang>();

	public Mousta(int row, int col) {
		super(row, col);
		this.id = Mousta.compteur;
		Mousta.compteur ++;
		this.setPathFinder();
	}

	public void setListGangCible(){

		this.listeGangsCible.clear();
		for (Gang g : WorldCell.world.listeGang){
			if(this.pathFinder.getShortestPath(WorldCell.world.map[this.getRow()][this.getCol()],WorldCell.world.map[g.getRow()][g.getCol()]) != null){
				this.listeGangsCible.add(g);
			}
		}
		this.listeGangsCible.sort(new ResidentComparator(WorldCell.world.map[this.getRow()][this.getCol()]));
	}

	/*
	public void setCible(){
		this.cible = this.getCellClosestGang();
	}
	*/

	public void createChemin(){
		this.setListGangCible();
		if(!this.listeGangsCible.isEmpty()){
			Gang tempCible = this.listeGangsCible.get(0);

			WorldCell tempCibleCell = WorldCell.world.map[tempCible.getRow()][tempCible.getCol()];
			this.chemin = this.pathFinder.getShortestPath(WorldCell.world.map[this.getRow()][this.getCol()], tempCibleCell);
		}
		else this.chemin = null;
	}

	public void move(){
		//this.setCible();
		this.createChemin();
		if(chemin != null && chemin.size() > 1 && this.peutBouger){

			this.goToCell(this.chemin.get(1).getRow(), this.chemin.get(1).getCol());
		}
		this.peutAgir = true;
		this.peutBouger  = true;
	}

	public void setListeGangVoisins(){
		ArrayList<Gang> listeGangVoisin = new ArrayList<Gang>();

		for(int i=-1; i<=1; i++){
			for (int j =-1; j<=1; j++){
				if(this.getRow()+i >= 0 && this.getRow()+i < WorldCell.world.getNbRows() && this.getCol()+j >= 0 && this.getCol()+j < WorldCell.world.getNbCols()){
					WorldCell cell = WorldCell.world.map[this.getRow()+i][this.getCol()+j];
					if(i==0 && j==0){}
					if(!cell.isEmpty() && (cell.getOccupant() instanceof Gang )){
						listeGangVoisin.add((Gang) cell.getOccupant());
					}
				}
			}
		}

		this.listeGangVoisin = listeGangVoisin;
	}

	public void agit(){
		this.setListeGangVoisins();

		if (!this.listeGangVoisin.isEmpty() && this.peutAgir){
			this.nbBulletins += listeGangVoisin.get(0).nbBulletins;
			listeGangVoisin.get(0).peutBouger = false;
			WorldCell.world.listeGang.remove(listeGangVoisin.get(0));
			WorldCell.world.map[listeGangVoisin.get(0).getRow()][listeGangVoisin.get(0).getCol()].removeOccupant();
			//this.goToCell(this.listeGangVoisin.get(0).getRow(), this.listeGangVoisin.get(0).getCol());
			System.out.println("Mousta " + this.id + " : " + nbBulletins);
			this.peutAgir = false;
		}
	} 

	public void setPathFinder(){
		
		listIgnorer.add(Mousta.class);
		listIgnorer.add(Gang.class);
		
		this.pathFinder = new AStarPathFinder(WorldCell.world, WorldCell.world.heuristic, WorldCell.world.localCost, listIgnorer);
	}
	
}

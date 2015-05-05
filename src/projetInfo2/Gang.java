package projetInfo2;

import java.util.ArrayList;
import fr.ensai.simulator.astar.AStarPathFinder;
import fr.ensai.simulator.world.Cell;
import fr.ensai.simulator.world.GangStat;

public class Gang extends Resident implements GangStat {

	private WorldCell cible;

	public static int scoreTotal = 0;

	public static int compteur = 1;

	public int compteurTour = 0;

	public int nbBulletins = 0;
	public int id;

	private boolean peutAgir = true;

	public boolean peutBouger = true;

	public java.util.ArrayList<Cell> chemin = new java.util.ArrayList<Cell>(100);
	public ArrayList<Bulletin> listeBulletinVoisin = new ArrayList<Bulletin>(100);
	public ArrayList<Bulletin> listeBulletinsCible = new ArrayList<Bulletin>();
	public ArrayList<WorldCell> listeSortiesCible = new ArrayList<WorldCell>();

	public Gang(int row, int col){
		super(row, col);
		this.setPathFinder();
		this.id = Gang.compteur;
		Gang.compteur ++;
	}

	public void setListeBulletinsCible(){
		this.listeBulletinsCible.clear();
		for (Bulletin b : WorldCell.world.listeBulletin){
			if(this.pathFinder.getShortestPath(WorldCell.world.map[this.getRow()][this.getCol()],WorldCell.world.map[b.getRow()][b.getCol()]) != null){
				this.listeBulletinsCible.add(b);
			}
		}
		this.listeBulletinsCible.sort(new ResidentComparator(WorldCell.world.map[this.getRow()][this.getCol()]));
	}

	public void setListeSortiesCible(){
		
		this.listeSortiesCible.clear();
		for (WorldCell s : WorldCell.world.listeSorties){
			if(this.pathFinder.getShortestPath(WorldCell.world.map[this.getRow()][this.getCol()],s) != null){
				this.listeSortiesCible.add(s);
			}
		}
		this.listeSortiesCible.sort(new SortieComparator(WorldCell.world.map[this.getRow()][this.getCol()]));

	}
		/*
		WorldCell sortie = WorldCell.world.listeSorties.get(0);
		float distance = this.distanceTo(sortie);
		for (WorldCell s : WorldCell.world.listeSorties){
			//System.out.print(s + " ");
			if(this.distanceTo(s) <= distance || this.pathFinder.getShortestPath(WorldCell.world.map[this.getRow()][this.getCol()],WorldCell.world.map[s.getRow()][s.getCol()]) != null){
				System.out.println(s);
				sortie = s;
				distance = this.distanceTo(s);
			}
		}
		
		return sortie;
		*/
	


	public void createChemin(){		
		this.setListeBulletinsCible();
		this.setListeSortiesCible();
		if(!this.listeBulletinsCible.isEmpty() && nbBulletins < 2){
			Bulletin tempCible = this.listeBulletinsCible.get(0);

			WorldCell tempCibleCell = WorldCell.world.map[tempCible.getRow()][tempCible.getCol()];

			this.chemin = this.pathFinder.getShortestPath(WorldCell.world.map[this.getRow()][this.getCol()], tempCibleCell);
		}
		else if(nbBulletins == 2 && !this.listeSortiesCible.isEmpty()){
			this.chemin = this.pathFinder.getShortestPath(WorldCell.world.map[this.getRow()][this.getCol()], this.listeSortiesCible.get(0));
		}
		else this.chemin = null;

	}



	public void move(){
		if(!WorldCell.world.listeSorties.contains(this.cible)){
			//this.setCible();
		}
		this.createChemin();
		//System.out.println(this.chemin);
		if(chemin != null && chemin.size() > 1 && compteurTour == 0 && this.peutBouger){
			this.goToCell(this.chemin.get(1).getRow(), this.chemin.get(1).getCol());
		}
		compteurTour = (compteurTour + nbBulletins) % 3;
		this.peutAgir = true;
		this.peutBouger = true;
	}

	public void setListeBulletinVoisins(){
		ArrayList<Bulletin> listeBulletinVoisin = new ArrayList<Bulletin>();

		for(int i=-1; i<=1; i++){
			for (int j =-1; j<=1; j++){
				if(this.getRow()+i >= 0 && this.getRow()+i < WorldCell.world.getNbRows() && this.getCol()+j >= 0 && this.getCol()+j < WorldCell.world.getNbCols()){
					WorldCell cell = WorldCell.world.map[this.getRow()+i][this.getCol()+j];
					if(i==0 && j==0){}
					if(!cell.isEmpty() && (cell.getOccupant() instanceof Bulletin )){
						listeBulletinVoisin.add((Bulletin) cell.getOccupant());
					}
				}
			}
		}


		this.listeBulletinVoisin = listeBulletinVoisin;
	}

	public void agit(){
		if(this.nbBulletins < 2 && !WorldCell.world.listeBulletin.isEmpty()){
			this.setListeBulletinVoisins();

			if (!this.listeBulletinVoisin.isEmpty() && this.peutAgir){
				WorldCell.world.listeBulletin.remove(listeBulletinVoisin.get(0));
				WorldCell.world.map[listeBulletinVoisin.get(0).getRow()][listeBulletinVoisin.get(0).getCol()].removeOccupant();
				//this.goToCell(this.listeBulletinVoisin.get(0).getRow(), this.listeBulletinVoisin.get(0).getCol());
				listeBulletinVoisin.remove(0);
				nbBulletins++;
				System.out.print(" Gang " + this.id + " a " + nbBulletins + " bulletins! ");
				//if (nbBulletins == 2){this.setCible();}
				this.peutAgir = false;
			}
		}
	}

	public void sortir(){
		if (WorldCell.world.listeSorties.contains(WorldCell.world.map[this.getRow()][this.getCol()])){
			scoreTotal+=nbBulletins;
		}
	}

	public void setPathFinder(){
		//listIgnorer.add(Mousta.class);
		//listIgnorer.add(Gang.class);
		//listIgnorer.add(Bulletin.class);
		this.pathFinder = new AStarPathFinder(WorldCell.world, WorldCell.world.heuristic, WorldCell.world.localCost, listIgnorer);
	}
}

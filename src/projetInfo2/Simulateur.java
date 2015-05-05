package projetInfo2;

import fr.ensai.simulator.*;
import fr.ensai.simulator.gui.MapFrame;
import fr.ensai.simulator.gui.MapPanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Simulateur extends Simulator implements KeyListener{

	MapFrame mapFrame = new MapFrame(WorldCell.world);
	MapPanel mapPanel = new MapPanel(WorldCell.world);
	
	public int nbTours = 0;

	public Simulateur(File file){
		super(file);
	}

	public void loadMap(File file){

		String initialisation[];
		int ligneMapString = 0;

		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);

			initialisation = reader.readLine().split(" ");

			WorldCell.world.setNbRows(Integer.parseInt(initialisation[0]));
			WorldCell.world.setNbCols(Integer.parseInt(initialisation[1]));

			String [][] mapString = new String[WorldCell.world.getNbRows()][WorldCell.world.getNbCols()];

			while(reader.ready()){
				mapString[ligneMapString] = reader.readLine().split(" ");

				ligneMapString++;
			}

			reader.close();

			WorldCell.world.setMap();
			

			for(int i = 0; i < WorldCell.world.getNbRows(); i++){
				for(int j = 0; j < WorldCell.world.getNbCols(); j++){
					switch(mapString[i][j]){
					case "G" : WorldCell.world.map[i][j].setOccupant(new Gang(i, j));
					WorldCell.world.listeGang.add((Gang) WorldCell.world.map[i][j].getOccupant());
					break;
					case "M" : WorldCell.world.map[i][j].setOccupant(new Mousta(i, j));
					WorldCell.world.listeMousta.add((Mousta) WorldCell.world.map[i][j].getOccupant());
					break;
					case "#" : WorldCell.world.map[i][j].setOccupant(new Barriere(i, j));
					WorldCell.world.listeBarriere.add((Barriere) WorldCell.world.map[i][j].getOccupant());
					break;
					case "_" : if(i == 0 || i == WorldCell.world.getNbRows()-1  || j == 0 || j == WorldCell.world.getNbCols()-1 ) {
						WorldCell.world.listeSorties.add(WorldCell.world.map[i][j]);
					}
					break;
					case "$" : WorldCell.world.map[i][j].setOccupant(new Bulletin(i, j));
					WorldCell.world.listeBulletin.add((Bulletin) WorldCell.world.map[i][j].getOccupant());
					break;
					}
				}
			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public void mousta(){
		ArrayList<Resident> liste = new ArrayList<Resident>();
		liste.addAll(WorldCell.world.listeMousta);
		liste.addAll(WorldCell.world.listeGang);
		
		Collections.shuffle(liste);
		WorldCell.world.mouvement = false;
		for (Resident r : liste){
			mapFrame.repaint(10);
			//System.out.println("gggg"+liste.indexOf(r));
			r.move();
			mapFrame.repaint(10);
			r.agit();
			r.sortir();

		}
		WorldCell.world.viderSorties();
	}
	
	public void runOneStep(){
		this.mousta();
		this.nbTours++;
		System.out.println("Nombre de tours : " + this.nbTours);
	}

	public void runOneStep(KeyEvent e){
		int id = e.getID();
		if(id == KeyEvent.KEY_PRESSED){
		this.mousta();
		this.nbTours++;
		mapFrame.repaint(100);
		
		System.out.println("Nombre de tours : " + this.nbTours);
		}
	}

	public void run(){
		//mapFrame.addKeyListener(this);
		while(!this.isOver()){

			this.runOneStep();

		}
		//mapFrame.repaint(100);
		System.out.println("Gangstats: " + Gang.scoreTotal);
	}

	public boolean isOver(){
		if(!WorldCell.world.mouvement){return true;}
		for(int i = 0; i < WorldCell.world.getNbRows(); i++){
			for(int j = 0; j < WorldCell.world.getNbCols(); j++){
				if (!WorldCell.world.listeGang.isEmpty()){return false;}

			}
		}
		return true;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		this.runOneStep(e);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}

package projetInfo2;

import java.io.File;

public class Main {

	public static void main(String[] args) {
		File map = new File("map1.txt");

		Simulateur simulateur = new Simulateur(map);

		
		simulateur.run();
			
	}

}

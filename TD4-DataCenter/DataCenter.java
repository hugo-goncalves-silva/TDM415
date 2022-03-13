
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.Scanner;

public class DataCenter {
	private String[][] center;
	private List<Server> servers = new ArrayList<Server>();

	public DataCenter(Scanner s) {
		// ReupÃ©re la 1ere ligne avec toutes les infos
		String[] tab = s.nextLine().split(" ");
		this.center = new String[Integer.parseInt(tab[0])][Integer.parseInt(tab[1])];
		// CrÃ©ation des slots inaccessibles
		for (int i = 0; i < Integer.parseInt(tab[2]); i++) {
			String[] unavailables = s.nextLine().split(" ");
			this.center[Integer.parseInt(unavailables[0])][Integer.parseInt(unavailables[1])] = "-1";
		}
		// CrÃ©ation de tous les serveurs
		for (int i = 0; i < Integer.parseInt(tab[4]); i++) {
			String[] servers = s.nextLine().split(" ");
			this.servers.add(new Server("S" + i + 1, Integer.parseInt(servers[0]), Integer.parseInt(servers[1])));
		}
		while (s.hasNextLine()) {
			System.out.println(s.nextLine());
		}
	}

	private void displayDoc(String nom) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter("DC_Output/" + nom + ".out");
		writer.println();
		writer.println("     ");
		for (int s = 0; s < this.center[0].length; s++) {
			writer.printf("  %3d ", s);
		}
		writer.println();
		writer.println("    ");
		for (int s = 0; s < this.center[0].length; s++) {
			writer.print("______");
		}
		writer.println("_");
		for (int r = 0; r < this.center.length; r++) {
			writer.printf(" %3d ", r);
			for (int s = 0; s < this.center[0].length; s++) {
				writer.printf("| %s ", this.center[r][s]);
			}
			writer.println(" |\n");
		}
		writer.println();
		writer.println();
		writer.close();
	}

	private void display() {
		System.out.println();
		System.out.println("     ");
		for (int s = 0; s < this.center[0].length; s++) {
			System.out.printf("  %3d ", s);
		}
		System.out.println();
		System.out.println("    ");
		for (int s = 0; s < this.center[0].length; s++) {
			System.out.print("___________");
		}
		System.out.println("_");
		for (int r = 0; r < this.center.length; r++) {
			System.out.printf(" %3d ", r);
			for (int s = 0; s < this.center[0].length; s++) {
				System.out.printf("| %3d ", this.center[r][s]);
			}
			System.out.println(" |\n");
		}
		System.out.println();
		System.out.println();
	}

	public String[][] getCenter() {
		return center;
	}

	public void setCenter(String[][] center) {
		this.center = center;
	}

	public List<Server> getServers() {
		return servers;
	}

	public void setServers(List<Server> servers) {
		this.servers = servers;
	}

	public HashMap<Integer,Integer> getLengthRow(int rowNumber) {
		HashMap<Integer,Integer> lengthAvailable = new HashMap<>();
		int taille = 0, start =0;
		for (int i=0;i < this.center[rowNumber].length;i++) {
			if (this.center[rowNumber][i] == null) {
				taille++;
			} else {
				lengthAvailable.put(taille,start);
				start = i+1;taille=0;
			}
		}
		return lengthAvailable;
	}
	
	public void putServer(Server S, int row, int indiceStart) {
		System.out.println(indiceStart+"\n");
		for(int i=0;i<S.getLength();i++) {
			this.center[row][i+indiceStart] = S.getName();
		}
	}
	

	public static void main(String[] argsStrings) throws Exception {
		// Choix du premier dossier
		Scanner scannerIn = new Scanner(System.in);
		System.out.println("Which document do you want to read ?\n");
		String value = scannerIn.next();
		Boolean Continu = true;
		while (Continu) {
			// Lecture du fichier
			File file = new File("DC/" + value + ".in");
			Scanner sc = new Scanner(file);
			DataCenter dc = new DataCenter(sc);
			// Affichage des servers
			dc.getServers().toString();
			//Insertion des servers
			
			for(Server S : dc.getServers()) {
				for(int i=0;i<dc.center.length;i++) {
				if(dc.getLengthRow(i).containsKey(S.getLength())) { 
					System.out.println("Let's goo");
					dc.putServer(S, i,dc.getLengthRow(i).get(S.getLength()));
				}
			}
			}
			// Affichage du datacenter
			dc.displayDoc(value);
			//dc.display();
			// Est-ce qu'on lit un autre fichier ?
			System.out.println("Do you want to read another document ?(Y/N)\n");
			if(scannerIn.next().charAt(0)=='N')
				Continu = false;
			if (Continu) {
				System.out.println("Which document do you want to read ?\n");
				value = scannerIn.next();
			}
			sc.close();
		}
		scannerIn.close();
	}

}

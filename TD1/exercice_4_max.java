package TD1;
public class exercice_4_max {

	public static int max(int[] tab) {
		if(tab.length == 0){
			return -1;
		}else{
		int valMax = tab[0], max = 0;
		for (int i = 1; i < tab.length; i++) {
			if (valMax < tab[i]) {
				valMax = tab[i];
				max = i;
			}
		}
		return max;
	}
}
	
	

	public static void main(String[] args) {
		int[] tab = { 3, 6, 15, 21, 30, 33, 35, 40 };
		int[] tabNeg = { -3, -6, -15, -21, -30, -33, -35, -40 };
		int[] tabMixte = { 3, -6, 15, -21, 30, -33, 35, -40 };
		int[] tabVide = {};
		int max = max(tab);
		int maxMixt = max(tabMixte);
		int maxNeg = max(tabNeg);
		int maxVide = max(tabVide);
		System.out.println("L'indice de la valeur max du tableau positif " + max);
		System.out.println("L'indice de la valeur max du tableau négatif " + maxNeg);
		System.out.println("L'indice de la valeur max du tableau Mixte " + maxMixt);
		System.out.println("L'indice de la valeur max du tableau Vide " + maxVide);
	}
}
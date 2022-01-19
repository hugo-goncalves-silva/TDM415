package TD1;

public class exercice_4_maxTrie {
    public static int maxTrie(int[] tab) {
		if(tab.length <1){
			return tab.length-1;
		}else{
		int i=0;
		while(i < tab.length-1 && tab[i] < tab[i+1]) {
			i++;
		}
		return i;
	}
}
public static void main(String[] args) {
    int[] tab3 = { 3, 6, 15, 21, 30, 25, 24 , 20 };
    int[] tab2 = { 3, 2 };
    int[] tab1 = { 3 };
    int[] tabVide = {};
    int maxtab1 = maxTrie(tab1);
    int maxtab2 = maxTrie(tab2);
    int maxtab3 = maxTrie(tab3);
    int maxVide = maxTrie(tabVide);
    System.out.println("L'indice de la valeur max du tableau tab1 = " + maxtab1);
    System.out.println("L'indice de la valeur max du tableau tab2 = " + maxtab2);
    System.out.println("L'indice de la valeur max du tableau tab3 = " + maxtab3);
    System.out.println("L'indice de la valeur max du tableau Vide " + maxVide);
}
}

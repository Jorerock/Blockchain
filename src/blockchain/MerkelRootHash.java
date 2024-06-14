package blockchain;

import java.util.ArrayList;

public class MerkelRootHash {

	public static String hashRoot(ArrayList<String> transaction) {
		ArrayList<String> transactionHashListe = new ArrayList<>();
		ArrayList<String> tempEtage = new ArrayList<>();
		
		//Conversion des transactions en Hash et ajout dans la liste
		for (int i = 0; i < transaction.size(); i++) {
			transactionHashListe.add(HashUtil.applySha256(transaction.get(i)));
		}
		while (tempEtage.size() != 1) {
			tempEtage.clear();
			//test si la liste est impaire et duplication du dernier élément si c'est le cas
			if ((transactionHashListe.size()) % 2 != 0) {
				transactionHashListe.add(transactionHashListe.get(transactionHashListe.size()-1));
			}
			//boucle de concaténation des éléments deux par deux
			for (int i = 0; i<transactionHashListe.size(); i = i+2) {
				String tempHashConca = transactionHashListe.get(i)+transactionHashListe.get(i+1);
				tempEtage.add(HashUtil.applySha256(tempHashConca));
			}
			//transfère du contenu de la liste pour nouvelle itération
			transactionHashListe.clear();
			for (int i = 0; i< tempEtage.size(); i++) {
				transactionHashListe.add(tempEtage.get(i));
			}
		}
		return tempEtage.get(0);
	}
}

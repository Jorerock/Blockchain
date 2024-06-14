package blockchain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class Transaction {

	public static Block generationGenesis(BlockChain bc){
		int nbTransaction = 1;
		Date date = new Date();
		Timestamp timestp = new Timestamp(date.getTime());
		ArrayList<String> transaction = new ArrayList<String>();
		transaction.add("génésis");
		String hashRoot = MerkelRootHash.hashRoot(transaction);

		Block bloc = new Block(0, timestp, "0", hashRoot, "erreur Hash courant", transaction ,nbTransaction, 0);

		String hashCurr = bloc.miner(0);

		bloc.setHash_courant(hashCurr);

		return bloc;
	}

	public static Block generationBloc(BlockChain bc, String hashPrec, int index, ArrayList<String> listeTransac){
		Date date = new Date();
		Timestamp timestp = new Timestamp(date.getTime());

		String hashRoot = MerkelRootHash.hashRoot(listeTransac);

		return new Block(index, timestp, hashPrec, hashRoot, "erreur Hash courant", listeTransac ,listeTransac.size() , 0);

	}

	public static ArrayList<String> generationTransactionAlea(BlockChain bc, int nbTransaction){
		ArrayList<String> listTransaction= new ArrayList<>();
		int max_Value = 100; //Valeur max d'argent pour une transaction aléatoire.
		int persA, persB;
		float argent = -1;
		Utilisateur user1 = null;
		Utilisateur user2 = null;
		for(int i=0; i<nbTransaction;i++) {
			float user1Money = -1;
			while(user1Money <= 0) {

				persA = (int) (Math.random() * bc.getNbUtilisateur());
				persB = (int) (Math.random() * bc.getNbUtilisateur());
				
				persA++;
				persB++;
				
				while (persA == persB) {
					persB = (int) (Math.random() * bc.getNbUtilisateur());
					persB++;
				}
				argent = (float) (Math.random() * max_Value);
				user1 = bc.getTabUtilisateur(persA);
				user2 = bc.getTabUtilisateur(persB);

				user1Money = user1.getArgent();
			}
			listTransaction.add(transactionAtoB(user1, user2, argent));
		}

		return listTransaction;
	}
	
	public static String transactionAtoB(Utilisateur user1, Utilisateur user2, float argent) {
		float user1Money = user1.getArgent();
		if (user1Money < argent) {
			argent = user1Money;
		}
		
		user1.subArgent(argent);
		user2.addArgent(argent);
		
		return user1.getNom() + " envoi " + argent + " Bnb à " + user2.getNom();
	}


}

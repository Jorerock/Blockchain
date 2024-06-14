package blockchain;

import java.util.ArrayList;

import affichage.BCJsonUtils;

public class BlockChain {
	
	private int difficulte;
	private int nbTransactionParBloc;
	private int nbBloc;
	private int nbUtilisateur;
	private Block[] tabBloc;
	private Utilisateur[] tabUtilisateur;
	
	private ArrayList<String> fileTransactionGlobal = new ArrayList<String>();
	
	
	public BlockChain(int nbBloc, int nbUtilisateur, int difficulte, int nbTransactionParBloc) {
		this.difficulte = difficulte;
		this.nbBloc = nbBloc;
		this.nbTransactionParBloc = nbTransactionParBloc;
		this.nbUtilisateur = nbUtilisateur;
	}
	
	public void execution(BlockChain bc) {
		tabUtilisateur = new Utilisateur[nbUtilisateur+1];
		tabBloc = new Block[nbBloc];
		
		float recompense = 50; //montant reçu pour miner un bloc
		int nbBlocAvantReduc = 2; //Mettre à 5 pour test et 10 pour exam
		
		tabUtilisateur[0] = new Utilisateur("Coinbase"); //Utilisateur coinbase
		tabUtilisateur[0].addArgent(100000); //Coind prend 100 000 de monnais pour avoir de la marge (pas un problème si coin base est en négatif)
		
		tabUtilisateur[1] = new Utilisateur("Creator"); //utilisateur Creator
		tabBloc[0]= Transaction.generationGenesis(bc); //Initialisation genesis
		fileTransactionGlobal.add(Transaction.transactionAtoB(tabUtilisateur[0], tabUtilisateur[1], 50)); //creator reçois 50 Bnb pour la création de génésis et ajout dans la file des transactions
		
		for(int i=2; i<nbUtilisateur+1; i++) { //Phase Helipoter money
			tabUtilisateur[i] = new Utilisateur(String.valueOf(i)); //ajout de nouveaux utilisateurs à la liste 
			fileTransactionGlobal.add(Transaction.transactionAtoB(tabUtilisateur[0], tabUtilisateur[i], 50)); //tout les utilisateurs reçoivent 50 Bnb et on ajoute ça dans la file global des transactions
		}
				
		for (int i = 1; i < nbBloc; i++) { //Simulation de marché, une itération = une "période"
			int nbTransactionPeriode=(int) (Math.random() * (bc.getnbTransactionParBloc())+1); //nombre de transaction à générer durant la période
			ArrayList<String> newTransac = Transaction.generationTransactionAlea(bc, nbTransactionPeriode); //génération des transaction de la période
			for (int j = 0; j < newTransac.size(); j++) {
				fileTransactionGlobal.add(newTransac.get(j)); //ajout à la liste de transaction globale
			}
			
			int nbTransactionPourBloc = nbTransactionParBloc+1; //initialisation pour entrer dans le while
			while (nbTransactionPourBloc > nbTransactionParBloc || nbTransactionPourBloc >= fileTransactionGlobal.size() || nbTransactionPourBloc <= 0) {
				nbTransactionPourBloc=(int) (Math.random() * (bc.getnbTransactionParBloc())+1); //génération d'un nombre aléatoire entre 1 et le nombre max de transaction par bloc et inférieur aux nopmbre de transac dispo
			}
			
			ArrayList<String> transacPourBloc = new ArrayList<>(); //liste de transaction du bloc de la période
			for (int h = 0; h < nbTransactionPourBloc; h++) {
				transacPourBloc.add(fileTransactionGlobal.get(0)); //ajout du premier élem de la file de transaction globale
				fileTransactionGlobal.remove(0); //on retire le premier élem qui vient d'être ajouté.
			}
			
			tabBloc[i] = Transaction.generationBloc(bc, tabBloc[i-1].getHash_courant(), i, transacPourBloc);
			
			String hashCurr = tabBloc[i].miner(bc.getDifficulte()); //minage du bloc
			tabBloc[i].setHash_courant(hashCurr); //mise à jour du hash du bloc
			
			if (i%nbBlocAvantReduc == 0) { //mise à jour de la récompense de minage en fonction du nombre de bloc miné
				recompense = recompense/2;
				System.out.println(recompense);
			}
			
			fileTransactionGlobal.add(Transaction.transactionAtoB(tabUtilisateur[0], tabUtilisateur[i] , recompense));  //ajout de la récompense pour le minage du bloc

		}
		
		setTabUtilisateur(tabUtilisateur);
		setTabBloc(tabBloc);

		BCJsonUtils.BCJsonWriter(bc, "BlockChainFile");
	}
	
	public int getnbTransactionParBloc() {
		return nbTransactionParBloc;
	}
	
	public int getNbUtilisateur() {
		return nbUtilisateur;
	}
	
	public Block getTabBlock(int n) {
		return tabBloc[n];
	}
	
	public Utilisateur getTabUtilisateur(int n) {
		return tabUtilisateur[n];
	}
	
	public int getNbBloc() {
		return nbBloc;
	}

	public int getDifficulte() {
		return difficulte;
	}

	public void setDifficulte(int difficulte) {
		this.difficulte = difficulte;
	}

	public void setNbTransactionParBloc(int nbTransactionParBloc) {
		this.nbTransactionParBloc = nbTransactionParBloc;
	}

	public void setNbBloc(int nbBloc) {
		this.nbBloc = nbBloc;
	}

	public void setNbUtilisateur(int nbUtilisateur) {
		this.nbUtilisateur = nbUtilisateur;
	}

	public void setTabBloc(Block[] tabBloc) {
		this.tabBloc = tabBloc;
	}

	public void setTabUtilisateur(Utilisateur[] tabUtilisateur) {
		this.tabUtilisateur = tabUtilisateur;
	}

	public ArrayList<String> getFileTransactionGlobal() {
		return fileTransactionGlobal;
	}

	public void setFileTransactionGlobal(ArrayList<String> fileTransactionGlobal) {
		this.fileTransactionGlobal = fileTransactionGlobal;
	}
	

}

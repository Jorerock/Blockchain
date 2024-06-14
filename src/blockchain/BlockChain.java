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
		
		float recompense = 50; //montant re�u pour miner un bloc
		int nbBlocAvantReduc = 2; //Mettre � 5 pour test et 10 pour exam
		
		tabUtilisateur[0] = new Utilisateur("Coinbase"); //Utilisateur coinbase
		tabUtilisateur[0].addArgent(100000); //Coind prend 100 000 de monnais pour avoir de la marge (pas un probl�me si coin base est en n�gatif)
		
		tabUtilisateur[1] = new Utilisateur("Creator"); //utilisateur Creator
		tabBloc[0]= Transaction.generationGenesis(bc); //Initialisation genesis
		fileTransactionGlobal.add(Transaction.transactionAtoB(tabUtilisateur[0], tabUtilisateur[1], 50)); //creator re�ois 50 Bnb pour la cr�ation de g�n�sis et ajout dans la file des transactions
		
		for(int i=2; i<nbUtilisateur+1; i++) { //Phase Helipoter money
			tabUtilisateur[i] = new Utilisateur(String.valueOf(i)); //ajout de nouveaux utilisateurs � la liste 
			fileTransactionGlobal.add(Transaction.transactionAtoB(tabUtilisateur[0], tabUtilisateur[i], 50)); //tout les utilisateurs re�oivent 50 Bnb et on ajoute �a dans la file global des transactions
		}
				
		for (int i = 1; i < nbBloc; i++) { //Simulation de march�, une it�ration = une "p�riode"
			int nbTransactionPeriode=(int) (Math.random() * (bc.getnbTransactionParBloc())+1); //nombre de transaction � g�n�rer durant la p�riode
			ArrayList<String> newTransac = Transaction.generationTransactionAlea(bc, nbTransactionPeriode); //g�n�ration des transaction de la p�riode
			for (int j = 0; j < newTransac.size(); j++) {
				fileTransactionGlobal.add(newTransac.get(j)); //ajout � la liste de transaction globale
			}
			
			int nbTransactionPourBloc = nbTransactionParBloc+1; //initialisation pour entrer dans le while
			while (nbTransactionPourBloc > nbTransactionParBloc || nbTransactionPourBloc >= fileTransactionGlobal.size() || nbTransactionPourBloc <= 0) {
				nbTransactionPourBloc=(int) (Math.random() * (bc.getnbTransactionParBloc())+1); //g�n�ration d'un nombre al�atoire entre 1 et le nombre max de transaction par bloc et inf�rieur aux nopmbre de transac dispo
			}
			
			ArrayList<String> transacPourBloc = new ArrayList<>(); //liste de transaction du bloc de la p�riode
			for (int h = 0; h < nbTransactionPourBloc; h++) {
				transacPourBloc.add(fileTransactionGlobal.get(0)); //ajout du premier �lem de la file de transaction globale
				fileTransactionGlobal.remove(0); //on retire le premier �lem qui vient d'�tre ajout�.
			}
			
			tabBloc[i] = Transaction.generationBloc(bc, tabBloc[i-1].getHash_courant(), i, transacPourBloc);
			
			String hashCurr = tabBloc[i].miner(bc.getDifficulte()); //minage du bloc
			tabBloc[i].setHash_courant(hashCurr); //mise � jour du hash du bloc
			
			if (i%nbBlocAvantReduc == 0) { //mise � jour de la r�compense de minage en fonction du nombre de bloc min�
				recompense = recompense/2;
				System.out.println(recompense);
			}
			
			fileTransactionGlobal.add(Transaction.transactionAtoB(tabUtilisateur[0], tabUtilisateur[i] , recompense));  //ajout de la r�compense pour le minage du bloc

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

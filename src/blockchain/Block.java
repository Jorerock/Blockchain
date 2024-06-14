package blockchain;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Block {

	private int index;
	private Timestamp timestp;
	private String hash_precedent, hash_root, hash_courant;
	private ArrayList<String> transaction;
	private int nbTransaction;
	private int nonce;

	private HashUtil hash = new HashUtil();

	public Block(int index, Timestamp timestp, String hash_precedent, String hash_root, String hash_courant, ArrayList<String> transaction, int nbTransaction, int nonce) {
		this.index=index;
		this.timestp=timestp;
		this.hash_precedent=hash_precedent; 
		this.hash_root=hash_root; 
		this.hash_courant=hash_courant;
		this.transaction=transaction; 
		this.nbTransaction=nbTransaction;
		this.nonce=nonce;
	}

	public void printBloc(){
		System.out.println("* bloc numero : " + index);
		System.out.println("* bloc timeStamp : " + timestp);
		System.out.println("* bloc hash_precedent : " + hash_precedent);
		System.out.println("* bloc hash_root : " + hash_root);
		System.out.println("* bloc hash_courant : " + hash_courant);
		System.out.println("* bloc transaction : " + transaction);
		System.out.println("* bloc nbTransaction : " + nbTransaction);
		System.out.println("* bloc nonce : " + nonce + "\n");
	}

	private boolean critere(String hash, int difficulte) {
		for(int i =0;i< difficulte; i++) {
			if(hash.charAt(i) != '0')
				return false;
		}
		return true;
	}


	public String miner(int diff) {
		String hash;

		this.nonce =0; /* on initialise la nonce a zero*/
		hash = this.hashage ();
		while (!critere(hash,diff)) {
			this.nonce ++;
			hash = this.hashage ();
		}
		this.hash_courant = hash;
		return hash;
	}



	public String hashage() {
		return HashUtil.applySha256(this.index + this.timestp.toString() + this.hash_precedent + hash_root + this.nonce);
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Timestamp getTimestp() {
		return timestp;
	}

	public void setTimestp(Timestamp timestp) {
		this.timestp = timestp;
	}

	public String getHash_precedent() {
		return hash_precedent;
	}

	public void setHash_precedent(String hash_precedent) {
		this.hash_precedent = hash_precedent;
	}

	public String getHash_root() {
		return hash_root;
	}

	public void setHash_root(String hash_root) {
		this.hash_root = hash_root;
	}

	public String getHash_courant() {
		return hash_courant;
	}

	public void setHash_courant(String hash_courant) {
		this.hash_courant = hash_courant;
	}

	public ArrayList<String> getTransaction() {
		return transaction;
	}

	public void setTransaction(ArrayList<String> transaction) {
		this.transaction = transaction;
	}

	public int getNbTransaction() {
		return nbTransaction;
	}

	public void setNbTransaction(int nbTransaction) {
		this.nbTransaction = nbTransaction;
	}

	public int getNonce() {
		return nonce;
	}

	public void setNonce(int nonce) {
		this.nonce = nonce;
	}

	public HashUtil getHash() {
		return hash;
	}

	public void setHash(HashUtil hash) {
		this.hash = hash;
	}



}
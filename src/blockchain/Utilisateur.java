package blockchain;

public class Utilisateur {
	
	private String nom;
	private long argent;

    public Utilisateur(String num){
        this.nom="Utilisateur" + num;
        this.argent = 0;
    }

    public String getNom(){
        return nom;
    }

    public void printNom(){
        System.out.println(nom);
    }

	public float getArgent() {
		return (float) argent/100000000;
	}
	
	public long getSatoArgent() {
		return argent;
	}

	public void addArgent(float newArgent) {
		newArgent = newArgent*100000000;
		this.argent = (long) (this.argent + newArgent);
	}
	
	public void subArgent(float newArgent) {
		newArgent = newArgent*100000000;
		this.argent = (long) (this.argent - newArgent);
	}
	
	public void setArgent(float argent) {
		argent = argent*100000000;
		this.argent = (long) argent;
	}
}

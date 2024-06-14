package blockchain;

import java.util.Scanner;

import affichage.BCJsonUtils;

public class Main {
	
	public static void main(String[] args) {
		
		System.out.println("Creation de la blockchain : ");
		
		Scanner s1 = new Scanner(System.in);
		System.out.println("Nombre de Bloc : ");
		int nbBloc = s1.nextInt();
		
		
		Scanner s2 = new Scanner(System.in);
		System.out.println("Nombre d'Utilisateur : ");
		int nbUtilisateur = s2.nextInt();
		while (nbUtilisateur<2) {
			System.out.println("Il n'y a pas assez d'utilisateur il en faut au moins 2.");
			s2 = new Scanner(System.in);
			System.out.println("Nombre d'Utilisateur : ");
			nbUtilisateur = s2.nextInt();
		}
		
		Scanner s3 = new Scanner(System.in);
		System.out.println("Niveau de difficulte : ");
		int difficulte = s3.nextInt();
		
		Scanner s4 = new Scanner(System.in);
		System.out.println("Nombre de transaction par bloc : ");
		int nbTransactionParBloc = s4.nextInt();
		
		BlockChain B1 = new BlockChain(nbBloc, nbUtilisateur, difficulte, nbTransactionParBloc);
		
		B1.execution(B1);

	}
}

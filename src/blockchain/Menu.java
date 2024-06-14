package blockchain;

import java.util.Scanner;

public class Menu {
	
	public static int affichageMenu() {
		System.out.println("---------------- Menu ----------------");
		System.out.println("1 - Information sur la blockchain");
		System.out.println("2 - Voir tous les utilisateurs");
		System.out.println("3 - Voir un bloc");
		System.out.println("4 - Voir tous les blocs");
		System.out.println("0 - Quitter le programme");
		
		Scanner s = new Scanner(System.in);
		System.out.println("Saisir un choix : ");
		int choix = s.nextInt();
		return choix;
	}
	
	public static void menu(BlockChain bc) {
		while(true) {
			switch (affichageMenu()) {
				case 1:
					System.out.println("-------- Information sur la blockchain --------");
					System.out.println("Nombre de transaction par bloc : " + bc.getnbTransactionParBloc());
					System.out.println("Nombre de Bloc : " + bc.getNbBloc());
					System.out.println("Nombre d'Utilisateur : " + bc.getNbUtilisateur());
					break;
				
				case 2:
					System.out.println("Affichage des utilisateurs : ");
					for(int i=0; i<bc.getNbUtilisateur(); i++) {
						bc.getTabUtilisateur(i).printNom();
					}
					break;
				
				case 3:
					while(true) {
						Scanner s =new Scanner(System.in);
						System.out.println("Ouvrir quel bloc? (0 pour quitter) [1-" + bc.getNbBloc() + "]");
						int choix = s.nextInt();
						if(choix==0) {
							break;
						}
						bc.getTabBlock(choix-1).printBloc();
					}
					break;
				
				case 4:
					System.out.println("Affichage des blocs :");
					for(int i=0; i<bc.getNbBloc(); i++) {
						bc.getTabBlock(i).printBloc();
					}
					break;
				
				default:
					System.out.println("Fin du programme");
					System.exit(0);
					break;
			}
		}
	}
}

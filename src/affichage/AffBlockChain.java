package affichage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import blockchain.BlockChain;

public class AffBlockChain extends JPanel {
	private static final long serialVersionUID = 1L;

	BCJsonUtils gson = new BCJsonUtils();
	
	public AffBlockChain(String nomFichier) {
		
		//Sauvegarde JSon
		JLabel infosSaveLabel = new JLabel("Entrez un nom de fichier et appuyez sur le bouton Save");
		JLabel nomFichierSaveLabel = new JLabel("Nom du fichier : ");
		JTextField nomFichierSaveTxt = new JTextField();
		nomFichierSaveTxt.setPreferredSize(new Dimension(150,20));
		JButton saveButton = new JButton("Save");
		JPanel saveButtonPan = new JPanel();
		saveButtonPan.add(saveButton);

		JLabel infosLoadLabel = new JLabel("Entrez un nom de fichier et appuyez sur le bouton Load");
		JLabel nomFichierLoadLabel = new JLabel("Nom du fichier : ");
		JTextField nomFichierLoadTxt = new JTextField();
		nomFichierLoadTxt.setPreferredSize(new Dimension(150,20));
		JButton loadButton = new JButton("Load");
		JPanel loadButtonPan = new JPanel();
		loadButtonPan.add(loadButton);


		JPanel nomFichiersavePan = new JPanel();
		nomFichiersavePan.add(nomFichierSaveLabel);
		nomFichiersavePan.add(nomFichierSaveTxt);

		JPanel nomFichierLoadPan = new JPanel();
		nomFichierLoadPan.add(nomFichierLoadLabel);
		nomFichierLoadPan.add(nomFichierLoadTxt);

		JPanel savePan = new JPanel(new BorderLayout());
		savePan.add(infosSaveLabel, BorderLayout.PAGE_START);
		savePan.add(nomFichiersavePan, BorderLayout.CENTER);
		savePan.add(saveButtonPan, BorderLayout.PAGE_END);

		JPanel loadPan = new JPanel(new BorderLayout());
		loadPan.add(infosLoadLabel, BorderLayout.PAGE_START);
		loadPan.add(nomFichierLoadPan, BorderLayout.CENTER);
		loadPan.add(loadButtonPan, BorderLayout.PAGE_END);

		JPanel saveLoadPan = new JPanel(new GridLayout(0,2));
		saveLoadPan.add(savePan);
		saveLoadPan.add(loadPan);

		JPanel saveLoadPanEtInfo = new JPanel(new BorderLayout(0,10));
		saveLoadPanEtInfo.add(new JLabel("Appuyez sur le bouton Load sans rien mettre en nom de fichier pour charger la dernière BlockChain généré."), BorderLayout.PAGE_START);
		saveLoadPanEtInfo.add(saveLoadPan, BorderLayout.CENTER);
		
		JPanel dataPan = new JPanel(); 
		affBlockChainPan(nomFichier, dataPan);
		JPanel finalPan = new JPanel(new BorderLayout());
		finalPan.add(saveLoadPanEtInfo, BorderLayout.PAGE_START);
		finalPan.add(dataPan, BorderLayout.CENTER);
		
		this.add(finalPan);
		
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean test = false;
				boolean test2 = true;
				String nomF = nomFichierSaveTxt.getText();
				for (int i = 0; i< nomF.length(); i++) {
					if (nomF.charAt(i) != ' ' ) {
						test = true;
					}
					if (nomF.charAt(i) == '/' || nomF.charAt(i) == ':' || nomF.charAt(i) == ';') {
						test2 = false;
					}
				}
				if (!test || !test2) {
					construPopUpErrFichier("Nom de Fichier incorrect");
					return;
				}
				File f = new File(nomF);
				if (f.isFile()) {
					construPopUpErrFichier("Ce fichier existe déjà");
					return;
				}
				BlockChain bc = BCJsonUtils.BCJsonReader(nomFichier);
				BCJsonUtils.BCJsonWriter(bc, nomFichierSaveTxt.getText());

			}
		});

		loadButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (nomFichierLoadTxt.getText().compareTo("") == 0) {
					affBlockChainPan("BlockChainFile", dataPan);
				}
				else {
					File f = new File(nomFichierLoadTxt.getText());
					if (f.isFile()) {
						affBlockChainPan(nomFichierLoadTxt.getText(), dataPan);
					}
					else {
						construPopUpErrFichier("Erreur lors de l'ouverture du fichier, vérifiez bien le nom");
						return;
					}
				}

			}
		});
	}
	
	public void affBlockChainPan(String nomFichier, JPanel donneePan) {
		
		donneePan.removeAll();

		String newLine = System.getProperty("line.separator");
		boolean isFile = true;
		BlockChain bc;
		try {
			BCJsonUtils.BCJsonReader(nomFichier);
		} catch (Exception e) {
			isFile = false;
			construPopUpErrFichier("Erreur lors de l'ouverture du fichier, vérifiez bien le nom");
			return;
		}
		if (isFile) {
			bc = BCJsonUtils.BCJsonReader(nomFichier);

			//Information sur la Blockchain
			int nbBloc = bc.getNbBloc();
			int nbUtilisateur = bc.getNbUtilisateur();
			int nbTransaction = bc.getnbTransactionParBloc();

			JTextArea infosBlockChain = new JTextArea("Information sur la BlockChain :"+newLine+ 
					"Nombre de blocs : " + nbBloc + newLine +
					"Nombre d'utilisateur : " + nbUtilisateur + newLine +
					"Nombre de transaction : " + nbTransaction);
			infosBlockChain.setEditable(false);

			//Liste Utilisateurs
			String[] listeUser = new String[nbUtilisateur+1];
			for(int i = 0; i < nbUtilisateur+1; i++) {
				listeUser[i] = bc.getTabUtilisateur(i).getNom()+ " : " + bc.getTabUtilisateur(i).getSatoArgent()+" SatoBnb";
			}
			ComboBoxModel<String> listeUserModel = new DefaultComboBoxModel<String>(listeUser);
			JComboBox<String> listeUserCombo = new JComboBox<String>(listeUserModel); 
			JLabel listeUserLabel = new JLabel("Liste des Utilisateurs : ");

			//Tableau pour blocs
			ModelTableau modele = new ModelTableau();
			JTable tableau = new JTable(modele);
			tableau.updateUI();
			tableau.repaint();
			modele.fireTableDataChanged();
			JScrollPane tableauScroll = new JScrollPane(tableau); 
			TableRowSorter<TableModel> sorter = new TableRowSorter<>(tableau.getModel()); 

			sorter.setSortsOnUpdates(true);
			tableau.setRowSorter(sorter);
			tableau.getTableHeader().setReorderingAllowed(false);

			tableauScroll.setPreferredSize(new Dimension(510,200));

			//Liste des blocs
			String[] listeBlocs = new String[nbBloc+1];
			listeBlocs[0] = "Choisir un bloc";
			for(int i = 1; i < nbBloc+1; i++) {
				listeBlocs[i] = "Bloc "+(i-1);
			}

			for(int i = 0; i < nbBloc; i++) {
				modele.addBloc(bc.getTabBlock(i));
			}
			tableau.updateUI();
			tableau.repaint();
			modele.fireTableDataChanged();

			ComboBoxModel<String> listeBlocsModel = new DefaultComboBoxModel<String>(listeBlocs);
			JComboBox<String> listeBlocsCombo = new JComboBox<String>(listeBlocsModel); 
			JLabel listeBlocsLabel = new JLabel("Liste des Blocs : ");

			JPanel infoBlocPan = new JPanel(new BorderLayout(8,8));
			JLabel infoBlocLabel = new JLabel("Information sur le bloc : ");

			listeBlocsCombo.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					int numBloc = listeBlocsCombo.getSelectedIndex()-1;
					if (numBloc == -1) {
						return;
					}
					else {
						Timestamp timestpBloc = bc.getTabBlock(numBloc).getTimestp();
						String hashPrecBloc = bc.getTabBlock(numBloc).getHash_precedent();
						String hashRootBloc = bc.getTabBlock(numBloc).getHash_root();
						String hashCurrBloc = bc.getTabBlock(numBloc).getHash_courant();
						ArrayList<String> transactionBloc = bc.getTabBlock(numBloc).getTransaction();
						int nbTransactionBloc = bc.getTabBlock(numBloc).getNbTransaction();
						int nonceBloc = bc.getTabBlock(numBloc).getNonce();

						JLabel numBlocLabel = new JLabel("Numéro du bloc : ");
						JLabel timestpLabel = new JLabel("Date de création : ");
						JLabel hashPrecLabel = new JLabel("Hash précédent : ");
						JLabel hashRootLabel = new JLabel("Hash root : ");
						JLabel hashCurrLabel = new JLabel("Hash courant : ");
						JLabel transactionLabel = new JLabel("Transactions : ");
						JLabel nbTransactionLabel = new JLabel("Nombre de transaction : ");
						JLabel nonceLabel = new JLabel("Nonce : ");

						JLabel numBlocVal = new JLabel(""+numBloc);
						JLabel timestpVal = new JLabel(timestpBloc.toString());
						JLabel hashPrecpVal = new JLabel(hashPrecBloc.toString());
						JLabel hashRootpVal = new JLabel(hashRootBloc.toString());
						JLabel hashCurrVal = new JLabel(hashCurrBloc.toString());

						JLabel nbTransactionVal = new JLabel(""+nbTransactionBloc);
						JLabel nonceVal = new JLabel(""+nonceBloc);

						JPanel grilleInfoBloc = new JPanel(new GridLayout(8,2,5,5));

						JButton affTransac = new JButton("Afficher");
						affTransac.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								construPopUpAffTransac(numBloc, transactionBloc);

							}
						});

						grilleInfoBloc.add(numBlocLabel);
						grilleInfoBloc.add(numBlocVal);

						grilleInfoBloc.add(timestpLabel);
						grilleInfoBloc.add(timestpVal);

						grilleInfoBloc.add(hashPrecLabel);
						grilleInfoBloc.add(hashPrecpVal);

						grilleInfoBloc.add(hashRootLabel);
						grilleInfoBloc.add(hashRootpVal);

						grilleInfoBloc.add(hashCurrLabel);
						grilleInfoBloc.add(hashCurrVal);

						grilleInfoBloc.add(transactionLabel);
						grilleInfoBloc.add(affTransac);

						grilleInfoBloc.add(nbTransactionLabel);
						grilleInfoBloc.add(nbTransactionVal);

						grilleInfoBloc.add(nonceLabel);
						grilleInfoBloc.add(nonceVal);

						infoBlocPan.removeAll();
						infoBlocPan.add(infoBlocLabel, BorderLayout.PAGE_START);
						infoBlocPan.add(grilleInfoBloc, BorderLayout.CENTER);
						infoBlocPan.repaint();
						infoBlocPan.revalidate();
					}
				}
			});


			//Transaction restante :
			JLabel transacResteLabel = new JLabel("Transactions non stocké : ");
			JButton transacResteButton = new JButton("Afficher");
			transacResteButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					construPopUpAffTransac(-1, bc.getFileTransactionGlobal());
				}
			});

			JPanel transacRestePan = new JPanel();
			transacRestePan.add(transacResteLabel);
			transacRestePan.add(transacResteButton);

			//Information sur les blocs
			JPanel infoBlockChainPan = new JPanel(new GridLayout(0,3,5,5));
			infoBlockChainPan.add(infosBlockChain);
			infoBlockChainPan.add(new JPanel());
			infoBlockChainPan.add(transacRestePan);

			JPanel listeUserBlockPan = new JPanel(new GridLayout(2,2,5,5));
			listeUserBlockPan.add(listeUserLabel);
			listeUserBlockPan.add(listeUserCombo);
			listeUserBlockPan.add(listeBlocsLabel);
			listeUserBlockPan.add(listeBlocsCombo);

			JPanel tableauPan = new JPanel(new BorderLayout());
			tableauPan.add(new JLabel("Liste des blocs de la BlockChain :"), BorderLayout.PAGE_START);
			tableauPan.add(tableauScroll, BorderLayout.CENTER);

			JPanel hautPagePan = new JPanel(new BorderLayout(15,15));
			hautPagePan.add(infoBlockChainPan, BorderLayout.CENTER);
			hautPagePan.add(listeUserBlockPan, BorderLayout.PAGE_END);

			JPanel tout = new JPanel(new BorderLayout(15,15));
			tout.add(hautPagePan, BorderLayout.PAGE_START);
			tout.add(tableauPan, BorderLayout.CENTER);
			tout.add(infoBlocPan, BorderLayout.PAGE_END);
			
			donneePan.add(tout);
			donneePan.repaint();
			donneePan.revalidate();
		}
	}

	public void construPopUpAffTransac(int index, ArrayList<String> transacListe) {
		String headtxt = "Transactions du bloc "+index;
		if (index == -1) {
			headtxt = "Transactions non stocké";
		}
		JFrame popUpError = new JFrame(headtxt);
		popUpError.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel popPan = new JPanel(new BorderLayout(5, 10));
		String newLine = System.getProperty("line.separator");
		String message = transacListe.get(0);
		for(int i = 1; i<transacListe.size(); i++) {
			message = message + newLine + transacListe.get(i);
		}
		JTextArea messageErr = new JTextArea(message);
		messageErr.setEditable(false);
		JButton boutonOk = new JButton("OK");
		Dimension dimBouton = new Dimension(80, 25);
		boutonOk.setPreferredSize(dimBouton);
		boutonOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				popUpError.dispose();
			}
		});
		JPanel tmp = new JPanel();
		tmp.add(boutonOk);
		popPan.add(messageErr, BorderLayout.PAGE_START);
		popPan.add(tmp, BorderLayout.CENTER);
		popUpError.getContentPane().add(popPan);
		popUpError.pack();
		popUpError.setLocationRelativeTo(null);
		popUpError.setVisible(true);
	}

	public void construPopUpErrFichier(String erreur) {
		JFrame popUpError = new JFrame("Erreur ouverture fichier");
		popUpError.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		popUpError.setPreferredSize(new Dimension(400, 100));

		JPanel popPan = new JPanel(new BorderLayout(5, 10));
		JLabel messageErr = new JLabel(erreur);
		JButton boutonOk = new JButton("OK");
		Dimension dimBouton = new Dimension(80, 25);
		boutonOk.setPreferredSize(dimBouton);
		boutonOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				popUpError.dispose();
			}
		});
		JPanel tmp = new JPanel();
		tmp.add(boutonOk);
		popPan.add(messageErr, BorderLayout.PAGE_START);
		popPan.add(tmp, BorderLayout.CENTER);
		popUpError.getContentPane().add(popPan);
		popUpError.pack();
		popUpError.setLocationRelativeTo(null);
		popUpError.setVisible(true);
	}
	
	
}

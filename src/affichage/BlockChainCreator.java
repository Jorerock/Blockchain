package affichage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import blockchain.BlockChain;

public class BlockChainCreator extends JPanel{
	private static final long serialVersionUID = 1L;
	
	public BlockChainCreator() {
		
		JLabel nbBlocLabel = new JLabel("Nombre de Blocs : ");
		JLabel nbUtilisateurLabel = new JLabel("Nombre d'utilisateurs : ");
		JLabel nbTransactionLabel = new JLabel("Nombre de transaction par blocs : ");
		JLabel difficulteLabel = new JLabel("Difficulté : ");
		
		JEditorPane nbBlocEdit = new JEditorPane();
		JEditorPane nbUtilisateurEdit = new JEditorPane();
		JEditorPane nbTransactionEdit = new JEditorPane();
		JEditorPane difficulteEdit = new JEditorPane();
		
		JButton creation = new JButton("Création");
		creation.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int nbBlocVal;
				int nbUtilisateurVal;
				int nbTransactionVal;
				int difficulteVal;
				String textErr = " Erreur : ";
				String newLine = System.getProperty("line.separator");
				boolean testErr = true;
				try {
					nbBlocVal = Integer.parseInt(nbBlocEdit.getText());
					nbUtilisateurVal = Integer.parseInt(nbUtilisateurEdit.getText());
					nbTransactionVal = Integer.parseInt(nbTransactionEdit.getText());
					difficulteVal = Integer.parseInt(difficulteEdit.getText());
				} catch (Exception e2) {
					construPopUpCreation("Veuillez entrer uniquement des valeurs numérique");
					return;
				}
				if (nbBlocVal < 1) {
					testErr = false;
					textErr = textErr+ newLine + " - Le nombre de bloc ne doit pas être inférieur à 1 ";
				}
				if (nbUtilisateurVal < 2) {
					testErr = false;
					textErr = textErr + newLine + " - Le nombre d'utilisateur ne doit pas être inférieur à 2 ";
				}
				if (nbTransactionVal < 0) {
					testErr = false;
					textErr = textErr + newLine + " - Le nombre de transaction ne doit pas être inférieur à 0 ";
				}
				if (difficulteVal < 0) {
					testErr = false;
					textErr = textErr + newLine + " - La difficulté ne doit pas être inférieur à 0 ";
				}
				if (!testErr) {
					construPopUpCreation(textErr);
					return;
				}
				BlockChain bc = new BlockChain(nbBlocVal, nbUtilisateurVal, difficulteVal, nbTransactionVal);
				bc.execution(bc);
				new Onglets(false, "BlockChainFile");
			}
		});
		
		JPanel EditorEtLabel = new JPanel(new GridLayout(4,2,5,5));
		EditorEtLabel.add(nbBlocLabel);
		EditorEtLabel.add(nbBlocEdit);
		
		EditorEtLabel.add(nbUtilisateurLabel);
		EditorEtLabel.add(nbUtilisateurEdit);
		
		EditorEtLabel.add(nbTransactionLabel);
		EditorEtLabel.add(nbTransactionEdit);
		
		EditorEtLabel.add(difficulteLabel);
		EditorEtLabel.add(difficulteEdit);
		
		JPanel boutons = new JPanel();
		boutons.add(creation);
		
		JPanel tout = new JPanel(new BorderLayout(0,10));
		tout.add(EditorEtLabel, BorderLayout.CENTER);
		tout.add(boutons, BorderLayout.PAGE_END);
		
		this.add(tout);		
	}
	
	public void construPopUpCreation(String erreur) {
		BorderLayout layoutB = new BorderLayout(5, 10);
		JFrame popUpError = new JFrame("Erreur sélection de donnée");
		popUpError.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel popPan = new JPanel(layoutB);
		JTextArea messageErr = new JTextArea(erreur);
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
}

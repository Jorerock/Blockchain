package affichage;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class Lancement {
	
	Onglets ogl = new Onglets(true, "BlockChainFile");
		
	public Lancement() {
		JFrame mainFrame = new JFrame("Bonobo Central Bank");
		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel fondOnglet = new JPanel();
		fondOnglet.add(ogl);

		mainFrame.getContentPane().add(new JScrollPane(fondOnglet, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);

	}

	public static void main(String[] args) {
		new Lancement();
	}

}

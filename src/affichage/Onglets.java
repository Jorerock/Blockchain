package affichage;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class Onglets extends JTabbedPane{

	JPanel blockChainCreator = new JPanel();
	JPanel blockChainAff = new JPanel();

	public Onglets(boolean first, String nomFile) {
		if (first) {
			this.setOpaque(true);

			this.addTab("BlockChain Creator", createBlockChainCreator());
			this.addTab("BlockChain", createBlockChainAff(nomFile));
		}
		else {
			this.remove(blockChainAff);
			this.addTab("BlockChain", createBlockChainAff(nomFile));
			this.repaint();
		}
	}

	public JPanel createBlockChainCreator() {
		setBlockChainCreator(new BlockChainCreator());
		getBlockChainCreator().repaint();
		return blockChainCreator;
	}

	public JPanel createBlockChainAff(String nomFile) {
		setBlockChainAff(new AffBlockChain(nomFile));
		getBlockChainAff().repaint();
		return blockChainAff;
	}

	public JPanel createBlockChainAffStart() {
		getBlockChainAff().add(new JLabel("Pas de BlockChain Active"));
		return getBlockChainAff();
	}

	public JPanel getBlockChainCreator() {
		return blockChainCreator;
	}

	public void setBlockChainCreator(JPanel blockChainCreator) {
		this.blockChainCreator = blockChainCreator;
	}

	public JPanel getBlockChainAff() {
		return blockChainAff;
	}

	public void setBlockChainAff(JPanel blockChainAff) {
		this.blockChainAff = blockChainAff;
	}

}

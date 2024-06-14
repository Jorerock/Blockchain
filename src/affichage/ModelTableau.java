package affichage;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import blockchain.Block;

public class ModelTableau extends AbstractTableModel {
	
	private static final long serialVersionUID = 1L;
	
	private final ArrayList<Block> Blocs = new ArrayList<>();
	private final String[] entete = {"Num Bloc", "TimeStamp", "Hash Précédent", "Hash root", "Hash courant", "Transactions", "Nombre de transactions", "Nonce" };
	
	public int getRowCount() {
		return Blocs.size();
	}
	
	public int getColumnCount() {
		return entete.length;
	}
	
	@Override
	public String getColumnName(int columnIndex) {
		return entete[columnIndex];
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex) {
			case 0:
				return Blocs.get(rowIndex).getIndex();
			case 1:
				return Blocs.get(rowIndex).getTimestp();
			case 2:
				return Blocs.get(rowIndex).getHash_precedent();
			case 3:
				return Blocs.get(rowIndex).getHash_root();
			case 4:
				return Blocs.get(rowIndex).getHash_courant();
			case 5:
				return Blocs.get(rowIndex).getTransaction();
			case 6:
				return Blocs.get(rowIndex).getNbTransaction();
			case 7:
				return Blocs.get(rowIndex).getNonce();
			default:
				return null;
		}
	}
	
	public void addBloc(Block bloc) {
		Blocs.add(bloc);
		fireTableDataChanged();
	}
	
	public void removeBloc(int i) {
		Blocs.remove(i);
		fireTableDataChanged();
	}
}

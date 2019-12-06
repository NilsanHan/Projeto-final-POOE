package com.portaria.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class TabelaControleModel extends AbstractTableModel {

	private static final long serialVersionUID = 6893553424676264421L;

	private final String colunas[] = { "Id", "Estado", "Horário" };

	private List<Controle> listaControles;

	private static final int ID = 0;
	private static final int ESTADO = 1;
	private static final int HORARIO = 2;

	public TabelaControleModel() {
		listaControles = new ArrayList<Controle>();
	}

	public Controle getControle(int linhaIndex) {
		return this.getListaControles().get(linhaIndex);
	}

	public void addControle(Controle controle) {
		this.getListaControles().add(controle);
		fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
	}

	public void updateControle(Controle controle, int linhaIndex) {
		this.getListaControles().set(linhaIndex, controle);
		fireTableRowsUpdated(linhaIndex, linhaIndex);
	}

	public void removeControle(int linhaIndex) {
		this.getListaControles().remove(linhaIndex);
		fireTableRowsDeleted(linhaIndex, linhaIndex);
	}

	public void removeTodosControles() {
		this.getListaControles().clear();
	}

	@Override
	public String getColumnName(int nomeColuna) {
		return this.colunas[nomeColuna];
	}

	@Override
	public int getColumnCount() {
		return this.getColunas().length;
	}

	@Override
	public int getRowCount() {
		return this.getListaControles().size();
	}

	@Override
	public Object getValueAt(int linhaIndex, int colunaIndex) {

		Controle controle = this.getListaControles().get(linhaIndex);

		switch (colunaIndex) {
		case ID:
			return controle.getId();
		case ESTADO:
			return controle.getEstado();
		case HORARIO:
			return controle.getHorario();
		default:
			return controle;

		}
	}

	@Override
	public Class<?> getColumnClass(int colunaIndex) {
		switch (colunaIndex) {
		case ID:
			return Long.class;
		case ESTADO:
			return String.class;
		case HORARIO:
			return Date.class;
		}
		return null;
	}

	public String[] getColunas() {
		return colunas;
	}

	public List<Controle> getListaControles() {
		return listaControles;
	}

	public void setListaControles(List<Controle> listaControles) {
		this.listaControles = listaControles;
	}

	public static int getId() {
		return ID;
	}

	public static int getEstado() {
		return ESTADO;
	}

	public static int getHorario() {
		return HORARIO;
	}

}

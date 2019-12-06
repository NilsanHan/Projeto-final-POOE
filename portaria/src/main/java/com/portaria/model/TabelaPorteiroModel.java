package com.portaria.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class TabelaPorteiroModel extends AbstractTableModel{

	private static final long serialVersionUID = 6893553424676264421L;

	private final String colunas[] = {"Id","Nome","Bairro",
									   "Cidade","Endereço",
									   "Cep","Número"};
	
	private List<Porteiro> listaPorteiros;
	
	private static final int ID       = 0;
	private static final int NOME     = 1;
	private static final int BAIRRO   = 2;
	private static final int CIDADE   = 3;
	private static final int ENDERECO = 4;
	private static final int CEP      = 5;
	private static final int NUMERO   = 6;
	
	public TabelaPorteiroModel() {
           listaPorteiros = new ArrayList<Porteiro>(); 
	}

	
	public Porteiro getPorteiro(int linhaIndex) {
		return this.getListaPorteiros().get(linhaIndex);
	}
	
	public void addPorteiro(Porteiro porteiro) {
		this.getListaPorteiros().add(porteiro);
		fireTableRowsInserted(getRowCount()-1, getRowCount()-1 );
	}
	
	public void updatePorteiro(Porteiro porteiro, int linhaIndex) {
		this.getListaPorteiros().set(linhaIndex, porteiro);
		fireTableRowsUpdated(linhaIndex, linhaIndex);
	}
	
	public void removePorteiro(int linhaIndex) {
		this.getListaPorteiros().remove(linhaIndex);
		fireTableRowsDeleted(linhaIndex, linhaIndex);
	}
	
	public void removeTodosPorteiros() {
		this.getListaPorteiros().clear();
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
		return this.getListaPorteiros().size();
	}

	@Override
	public Object getValueAt(int linhaIndex, int colunaIndex) {
		
		Porteiro porteiro = this.getListaPorteiros().get(linhaIndex);
		
		switch(colunaIndex) {
		case ID:
			return porteiro.getId();
		case NOME:
			return porteiro.getNome();
		case BAIRRO:
			return porteiro.getBairro();
		case CIDADE:
			return porteiro.getCidade();
		case ENDERECO:
			return porteiro.getEndereco();
		case CEP:
			return porteiro.getCep();
		case NUMERO:
			return porteiro.getNumero();
		default:
			return porteiro;
		
		}
	}
	
	@Override
	public Class<?> getColumnClass(int colunaIndex){
		switch(colunaIndex) {
		case ID:
			return Long.class;
		case NOME:
			return String.class;
		case BAIRRO:
			return String.class;
		case CIDADE:
			return String.class;
		case ENDERECO:
			return String.class;
		case CEP:
			return String.class;
		case NUMERO:
			return String.class;
		}
		return null;
	}
	

	public String[] getColunas() {
		return colunas;
	}

	public List<Porteiro> getListaPorteiros() {
		return listaPorteiros;
	}
	
	
	public void setListaPorteiros(List<Porteiro> listaPorteiros) {
		this.listaPorteiros = listaPorteiros;
	}


	public static int getId() {
		return ID;
	}

	public static int getNome() {
		return NOME;
	}

	public static int getBairro() {
		return BAIRRO;
	}

	public static int getCidade() {
		return CIDADE;
	}

	public static int getEndereco() {
		return ENDERECO;
	}


	public static int getCep() {
		return CEP;
	}

	public static int getNumero() {
		return NUMERO;
	}

	
}

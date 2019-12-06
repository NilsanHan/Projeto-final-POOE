package com.portaria.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TAB_PORTEIRO")
public class Porteiro {
	
	
	private Long   id;
	private String nome;
	private String bairro;
	private String cidade;
	private String endereco;
	private String telefone;
	private String cep;
	private String numero;
	
	public Porteiro() {
	}


	public Porteiro(Long id, String nome, String bairro, String cidade, 
			       String telefone, String cep, String numero, String endereco) {
		this.id = id;
		this.nome = nome;
		this.bairro = bairro;
		this.cidade = cidade;
		this.telefone = telefone;
		this.endereco = endereco;
		this.cep = cep;
		this.numero = numero;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PORTEIRO_ID")
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	@Column(name = "PORTEIRO_NOME", nullable = false, length = 100 )
	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	@Column(name = "PORTEIRO_BAIRRO", nullable = false, length = 50 )
	public String getBairro() {
		return bairro;
	}


	public void setBairro(String bairro) {
		this.bairro = bairro;
	}


	@Column(name = "PORTEIRO_CIDADE", nullable = false, length = 100 )
	public String getCidade() {
		return cidade;
	}


	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	
	@Column(name = "PORTEIRO_TELEFONE", nullable = false, length = 100 )
	public String getTelefone() {
		return telefone;
	}


	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	
	
	
	
	
	/*
	 * @OneToOne(mappedBy = "porteiro",targetEntity = Telefone.class, cascade =
	 * CascadeType.ALL,fetch = FetchType.LAZY)
	 */
	
	
	/*
	 * @ManyToMany(fetch = FetchType.LAZY)
	 * 
	 * @JoinTable(name = "TAB_PORTEIRO_TELEFONE", joinColumns = @JoinColumn(name =
	 * "PORTEIRO_ID"), inverseJoinColumns = @JoinColumn(name="TELEFONE_ID"))
	 */

	
//	@OneToMany(mappedBy = "porteiro",targetEntity = Telefone.class, cascade =
//	CascadeType.ALL,fetch = FetchType.LAZY)
//	@JoinColumn(name = "TELEFONE_ID", referencedColumnName = "TELEFONE_ID", foreignKey = @ForeignKey(name = "FK_TELEFONE_TELEFONE"), nullable = false, columnDefinition = "BIGINT(20)")
//	public List<Telefone> getTelefones() {
//		return telefones;
//	}
//
//
//	public void setTelefones(List<Telefone>telefones) {
//		this.telefones = telefones;
//	}
	


	@Column(name = "PORTEIRO_CEP", nullable = false, length = 10 )
	public String getCep() {
		return cep;
	}


	public void setCep(String cep) {
		this.cep = cep;
	}

	
	@Column(name = "PORTEIRO_NUMERO", nullable = false, length = 5 )
	public String getNumero() {
		return numero;
	}


	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	@Column(name = "PORTEIRO_ENDERECO", nullable = false, length = 100 )
	public String getEndereco() {
		return endereco;
	}


	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Porteiro other = (Porteiro) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Porteiro [id=" + id + ", nome=" + nome + ", bairro=" + bairro + ", cidade=" + cidade + ", cep=" + cep + ", numero=" + numero + "]";
	}
	
	
	
	
	
	
	
	
	

}

package com.portaria.model;
//package com.exemplo.model;
//
//import java.util.List;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.ForeignKey;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//
//@Entity
//@Table(name = "TAB_TELEFONE")
//public class Telefone {
//
//	private Long id;
//	private String numeroTelefone;
//	private String tipoTelefone;
//	private List<Porteiro> porteiros;
//	
//	
//	
//	public Telefone() {
//		super();
//	}
//	
//	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name="TELEFONE_ID")
//	public Long getId() {
//		
//		return id;
//	}
//	public void setId(Long id) {
//		this.id = id;
//	}
//	public String getNumeroTelefone() {
//		return numeroTelefone;
//	}
//	public void setNumeroTelefone(String numeroTelefone) {
//		this.numeroTelefone = numeroTelefone;
//	}
//	
//	@Column(name = "TIPO_TELEFONE", 
//			length = 20, 
//			columnDefinition = "VARCHAR(20)", 
//			nullable = false )
//	public String getTipoTelefone() {
//		return tipoTelefone;
//	}
//	
//	public void setTipoTelefone(String tipoTelefone) {
//		this.tipoTelefone = tipoTelefone;
//	}
//	
//	
//    //@OneToOne(fetch = FetchType.LAZY,targetEntity = Porteiro.class)
//	
//	/*
//	 * @ManyToOne(fetch = FetchType.LAZY,targetEntity = Porteiro.class)
//	 * 
//	 * @JoinColumn(name = "CLIENTE_ID", referencedColumnName = "CLIENTE_ID",
//	 * foreignKey = @ForeignKey(name="FK_CLIENTE_TELEFONE"), nullable = false,
//	 * columnDefinition = "BIGINT(20)")
//	 */
//	
//	//@ManyToMany(mappedBy = "telefones")
//	
//	@ManyToOne(fetch = FetchType.LAZY,targetEntity = Porteiro.class)
//	@JoinColumn(name = "CLIENTE_ID", referencedColumnName = "CLIENTE_ID",
//		foreignKey = @ForeignKey(name="FK_CLIENTE_TELEFONE"), nullable = false,
//		columnDefinition = "BIGINT(20)")
//	public List<Porteiro> getPorteiro() {
//		return porteiros;
//	}
//
//	public void setPorteiro(List<Porteiro> porteiros) {
//		this.porteiros = porteiros;
//	}
//
//
//	@Override
//	public String toString() {
//		return numeroTelefone;
//	}
//	
//	
//	
//	
//}

package com.portaria.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TAB_CONTROLE")
public class Controle {

	private Long id;
	private String estado;
	private Date horario;

	public Controle() {
	}

	public Controle(Long id, String estado, Date horario) {
		this.id = id;
		this.estado = estado;
		this.horario = horario;

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CONTROLAR_ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "CONTROLE_ESTADO", nullable = false, length = 100 )
	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Column(name = "CONTROLE_HORARIO", nullable = false, length = 100 )
	public Date getHorario() {
		return horario;
	}

	public void setHorario(Date horario) {
		this.horario = horario;
	}

	@Override
	public String toString() {
		return "Controle [id=" + id + ", estado=" + estado + ", horario=" + horario + "]";
	}

}

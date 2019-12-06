package com.portaria.service;


import java.util.List;

import javax.persistence.EntityTransaction;

import com.portaria.model.Porteiro;
import com.portaria.repository.PorteiroRepository;

public class PorteiroService extends ConexaoBancoService {
	
	private PorteiroRepository porteiroRepository;
	
	private static Integer ERRO_INCLUSAO = 1;
	private static Integer ERRO_ALTERACAO = 2;
	private static Integer ERRO_EXCLUSAO = 3;
	private static Integer SUCESSO_TRANSACAO = 0;
	
	public PorteiroService() {
		porteiroRepository = new PorteiroRepository(getEntityManager());
	}
	
		
	public Integer salvarPorteiro(Porteiro porteiro) {
		EntityTransaction transacao = this.getEntityManager().getTransaction();
		try {
			transacao.begin();
			porteiroRepository.save(porteiro);
			transacao.commit();
		}catch (Throwable e) {
			e.printStackTrace();
	        if (transacao.isActive()) {
	        	transacao.rollback();
	        	close();
	        }
			return ERRO_INCLUSAO;
		} finally {
			close();
		}
		return SUCESSO_TRANSACAO;
	}
	

	public Integer alterarPorteiro(Porteiro porteiro) {
		EntityTransaction transacao = this.getEntityManager().getTransaction();
		try {
			transacao.begin();
			porteiroRepository.update(porteiro);
			transacao.commit();
		} catch(Throwable e) {
			e.printStackTrace();
			if (transacao.isActive()) {
				transacao.rollback();
				close();
			}
			return ERRO_ALTERACAO;
		} finally {
			close();
		}
		return SUCESSO_TRANSACAO;
	}
	
	public Integer excluirPorteiro(Porteiro porteiro) {
		EntityTransaction transacao = this.getEntityManager().getTransaction();
		try {
			Porteiro porteiroRemovido = consultarPorteiro(porteiro.getId());
			transacao.begin();
			porteiroRepository.remove(porteiroRemovido);
			transacao.commit();
		} catch(Throwable e) {
			e.printStackTrace();
			if (transacao.isActive()) {
				transacao.rollback();
				close();
			}
			return ERRO_EXCLUSAO;
		} finally {
			close();
		}
		return SUCESSO_TRANSACAO;
	}
	
	
	public Porteiro consultarPorteiro(Long id) {
		return porteiroRepository.findById(id);
	}
	
	public List<Porteiro> listarTodosPorteiros(){
		return porteiroRepository.findAll(Porteiro.class);
	}

	public Integer calcularTotalRegistros() {
		return porteiroRepository.calcularTotalRegistros(Porteiro.class);
	}
	
	public List<Porteiro> listarTodosRegistrosComPaginacao(Integer primeiro, Integer tamanhoPagina){
	    return porteiroRepository.listarTodosRegistrosComPaginacao(primeiro, tamanhoPagina);
	}
	
}

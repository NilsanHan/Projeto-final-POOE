package com.portaria.service;


import java.util.List;

import javax.persistence.EntityTransaction;

import com.portaria.model.Controle;
import com.portaria.repository.ControleRepository;

public class ControleService extends ConexaoBancoService {
	
	private ControleRepository porteiroRepository;
	
	private static Integer ERRO_INCLUSAO = 1;
	private static Integer ERRO_ALTERACAO = 2;
	private static Integer ERRO_EXCLUSAO = 3;
	private static Integer SUCESSO_TRANSACAO = 0;
	
	public ControleService() {
		porteiroRepository = new ControleRepository(getEntityManager());
	}
	
		
	public Integer salvarControle(Controle porteiro) {
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
	

	public Integer alterarControle(Controle porteiro) {
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
	
	public Integer excluirControle(Controle porteiro) {
		EntityTransaction transacao = this.getEntityManager().getTransaction();
		try {
			Controle porteiroRemovido = consultarControle(porteiro.getId());
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
	
	
	public Controle consultarControle(Long id) {
		return porteiroRepository.findById(id);
	}
	
	public List<Controle> listarTodosControles(){
		return porteiroRepository.findAll(Controle.class);
	}

	public Integer calcularTotalRegistros() {
		return porteiroRepository.calcularTotalRegistros(Controle.class);
	}
	
	public List<Controle> listarTodosRegistrosComPaginacao(Integer primeiro, Integer tamanhoPagina){
	    return porteiroRepository.listarTodosRegistrosComPaginacao(primeiro, tamanhoPagina);
	}
	
}

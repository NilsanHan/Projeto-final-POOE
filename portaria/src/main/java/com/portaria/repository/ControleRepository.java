package com.portaria.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;


import com.portaria.model.Controle;

public class ControleRepository extends GenericRepository<Controle, Long>{

	public ControleRepository(EntityManager entityManager) {
		super(entityManager);
	}

	
	public List<Controle> listarTodosRegistrosComPaginacao(Integer primeiro, Integer tamanhoPagina){
		List<Controle> listaControle = new ArrayList<Controle>();
		try {
			TypedQuery<Controle> query = this.getEntityManager()
			                                .createQuery("SELECT c FROM Controle c", Controle.class)
											.setFirstResult(primeiro)
											.setMaxResults(tamanhoPagina);
		listaControle = query.getResultList();
		} catch( Exception e) {
			e.printStackTrace();
		}

		return listaControle;
	}
	
}

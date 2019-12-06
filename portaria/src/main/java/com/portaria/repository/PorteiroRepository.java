package com.portaria.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;


import com.portaria.model.Porteiro;

public class PorteiroRepository extends GenericRepository<Porteiro, Long>{

	public PorteiroRepository(EntityManager entityManager) {
		super(entityManager);
	}

	
	public List<Porteiro> listarTodosRegistrosComPaginacao(Integer primeiro, Integer tamanhoPagina){
		List<Porteiro> listaPorteiro = new ArrayList<Porteiro>();
		try {
			TypedQuery<Porteiro> query = this.getEntityManager()
			                                .createQuery("SELECT c FROM Porteiro c", Porteiro.class)
											.setFirstResult(primeiro)
											.setMaxResults(tamanhoPagina);
		listaPorteiro = query.getResultList();
		} catch( Exception e) {
			e.printStackTrace();
		}

		return listaPorteiro;
	}
	
}

package com.contabancaria.api.repositorys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.contabancaria.api.entitys.Transacao;

import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;

@Transactional(readOnly = true)
@NamedQueries({
	@NamedQuery(name = "TransacaoRepository.findByCorrentistaId", 
			query = "SELECT tran FROM transacao tran WHERE tran.correntista.id =: correntistaId")			
})
public interface TransacaoRepository extends JpaRepository<Transacao, Long>{
	Page<Transacao> findByCorrentistaId(@Param("correntistaId") Long correntistaId, Pageable pageable);
}

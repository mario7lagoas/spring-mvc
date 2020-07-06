package com.algaworks.cobranca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.algaworks.cobranca.model.Titulo;
import com.algaworks.cobranca.model.enuns.StatusTitulo;
import com.algaworks.cobranca.repository.Titulos;
import com.algaworks.cobranca.repository.filter.TituloFilter;

@Service
public class TituloService {

	@Autowired
	private Titulos titulos;

	public void save(Titulo titulo) {
		try {
			this.titulos.save(titulo);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Formato de data invalido");
		}
	}

	public void excluir(Long id) {
		titulos.deleteById(id);
	}

	public String receber(Long id) {
		Titulo titulo = titulos.getOne(id);
		titulo.setStatus(StatusTitulo.RECEBIDO);
		
		this.titulos.save(titulo);
		
		return StatusTitulo.RECEBIDO.getDescricao();
		
	}
	
	public List<Titulo> filtrar(TituloFilter filtro){
		String descricao = filtro.getDescricao() == null ? "%" : filtro.getDescricao();
		
		return titulos.findByDescricaoContaining(descricao);
	}

}

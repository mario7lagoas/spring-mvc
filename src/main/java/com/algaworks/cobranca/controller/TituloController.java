package com.algaworks.cobranca.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.cobranca.model.Titulo;
import com.algaworks.cobranca.model.enuns.StatusTitulo;
import com.algaworks.cobranca.repository.Titulos;
import com.algaworks.cobranca.repository.filter.TituloFilter;
import com.algaworks.cobranca.service.TituloService;

@Controller
@RequestMapping("/titulos")
public class TituloController {

	private static final String CAD_TITULO = "CadastroTitulo";

	@Autowired
	private Titulos titulos;

	@Autowired
	private TituloService tituloService;

	@RequestMapping("/novo")
	public ModelAndView novo() {

		ModelAndView mv = new ModelAndView(CAD_TITULO);
		mv.addObject(new Titulo());
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Titulo titulo, Errors erros, RedirectAttributes attributes) {

		if (erros.hasErrors()) {
			return CAD_TITULO;
		}
		try {
			this.tituloService.save(titulo);
			attributes.addFlashAttribute("mensagem", "Titulo salvo com sucesso!");

			return "redirect:/titulos/novo";
		} catch (IllegalArgumentException e) {

			erros.rejectValue("dataVencimento", null, e.getMessage());
			return CAD_TITULO;
		}
	}

	@RequestMapping
	public ModelAndView pesquisar(@ModelAttribute("filtro") TituloFilter filtro) {
		
		List<Titulo> titulosList = tituloService.filtrar(filtro);

		ModelAndView mv = new ModelAndView("PesquisaTitulos");

		mv.addObject("titulos", titulosList);

		return mv;
	}

	@RequestMapping("/{id}")
	public ModelAndView editar(@PathVariable("id") Long id) {
		Titulo titulo = titulos.getOne(id);

		ModelAndView mv = new ModelAndView(CAD_TITULO);
		mv.addObject(titulo);
		return mv;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String excluir(@PathVariable("id") Long id, RedirectAttributes attributes) {

		this.tituloService.excluir(id);

		attributes.addFlashAttribute("mensagem", "Titulo excluido com Sucesso!");
		return "redirect:/titulos";
	}

	@RequestMapping(value = "/{id}/receber", method = RequestMethod.PUT)
	public @ResponseBody String receber(@PathVariable("id") Long id) {

		return this.tituloService.receber(id);
	}

	@ModelAttribute("todosStatusTitulo")
	public List<StatusTitulo> todosStatus() {
		return Arrays.asList(StatusTitulo.values());
	}

}

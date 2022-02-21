package com.generation.blogpessoal.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity // fala q vai ser uma tabela
@Table(name = "tb_temas") // declara o nome da tabela
public class Tema {
	
	@Id // fala q vai ser primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // fala q vai ser autoincrement
	private Long id;
	
	@NotBlank(message = "O atributo título é obrigatório!")
	private String descricao;
	
	@OneToMany(mappedBy = "tema", cascade = CascadeType.ALL) //declaro o tipo de relacao, cascade faz oq acontecer no tema acontece na postagem
	@JsonIgnoreProperties("tema") //
	private List<Postagem> postagem; // criei a chave estrangeira

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Postagem> getPostagem() {
		return postagem;
	}

	public void setPostagem(List<Postagem> postagem) {
		this.postagem = postagem;
	}
}

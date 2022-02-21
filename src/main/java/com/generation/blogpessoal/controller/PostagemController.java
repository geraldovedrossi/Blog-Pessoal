package com.generation.blogpessoal.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;
import com.generation.blogpessoal.repository.TemaRepository;

@RestController
@RequestMapping("/postagens")
@CrossOrigin(origins = "*", allowedHeaders = "*") // deixa eu rodar origens de lugares diferentes
public class PostagemController {

	@Autowired // da acesso ao meu controller a responsabilidade de criar e instanciar objetos
	private PostagemRepository postagemRepository;
	
	@Autowired
	private TemaRepository temaRepository;

	@GetMapping // n podem existir 2 getmapping iguais
	public ResponseEntity<List<Postagem>> getAll() {
		return ResponseEntity.ok(postagemRepository.findAll());
	} // equivalente a fazer = select * from tb_postagens;

	@GetMapping("/{id}") // variavel de caminho
	public ResponseEntity<Postagem> getById(@PathVariable Long id) {
		return postagemRepository.findById(id) // procura pelo id
				.map(resposta -> ResponseEntity.ok(resposta)) // realiza se resposta n for nulla
				.orElse(ResponseEntity.notFound().build()); // realiza se a resposta for nulla
		// Lambda = (resposta -> oqueretorna)
	}

	@GetMapping("/titulo/{titulo}") // n podem existir 2 getmapping iguais
	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo) {
		// o List não gera erro igual findById pois ele n gera null, gera uma lista
		// vazia
		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
	} // select * from tb_postagens where titulo like "%titulo%";

	@PostMapping
	public ResponseEntity<Postagem> postPostagem(@Valid @RequestBody Postagem postagem) {
		if(temaRepository.existsById(postagem.getTema().getId()))
			return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem));

		return ResponseEntity.notFound().build();
		
				//ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem));
		// chamo o status Created e no corpo do meu status eu salvo o objeto postagem e
		// recebo o ok
	}

	@PutMapping
	public ResponseEntity<Postagem> putPostagem(@Valid @RequestBody Postagem postagem) {
		if(temaRepository.existsById(postagem.getTema().getId())) {
			return postagemRepository.findById(postagem.getId()) // procura pelo id
					.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(postagemRepository.save(postagem))) 
					// realiza se resposta n for nulla
					.orElse(ResponseEntity.notFound().build()); 
					// realiza se a resposta for nulla
		} 
			return ResponseEntity.notFound().build();
		
	}

//	@DeleteMapping("/{id}")
//	public void deletePostagem(@PathVariable Long id) {
//		if (postagemRepository.existsById(id)) {
//			postagemRepository.deleteById(id);
//			ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//		} else {
//			ResponseEntity.notFound().build();
//		}
//	}
		
//	@DeleteMapping("/{id}")
//	public ResponseEntity<Object> deletePostagem(@PathVariable Long id) {
//		if (postagemRepository.existsById(id)) {
//			postagemRepository.deleteById(id);
//			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//		} else {
//			return ResponseEntity.notFound().build();
//		}
//	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deletePostagem(@PathVariable Long id) { // postagemRepository.deleteById(id);
		return postagemRepository.findById(id).map(resposta -> {
			postagemRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}).orElse(ResponseEntity.notFound().build());
	}

}
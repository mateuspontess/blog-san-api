package br.com.blogsanapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.blogsanapi.model.comment.Comment;
import br.com.blogsanapi.model.comment.request.CommentRepliRequestDTO;
import br.com.blogsanapi.model.comment.request.CommentRequestDTO;
import br.com.blogsanapi.model.comment.request.CommentUpdateDTO;
import br.com.blogsanapi.model.comment.response.CommentResponseDTO;
import br.com.blogsanapi.service.CommentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/blog/comments")
@SecurityRequirement(name = "bearer-key")
public class CommentController {

	@Autowired
	private CommentService service;
	

	@PostMapping("/create")
	public ResponseEntity<CommentResponseDTO> createComment(@RequestBody @Valid CommentRequestDTO dto, UriComponentsBuilder uriBuilder) {
		Comment comment = service.createComment(dto);
		
		var uri = uriBuilder.path("/blog/comments/{id}").buildAndExpand(dto).toUri();
		
		return ResponseEntity.created(uri).body(new CommentResponseDTO(comment));
	}
	@PostMapping("/create/reply")
	public ResponseEntity<CommentResponseDTO> replyComment(@RequestBody @Valid CommentRepliRequestDTO dto, UriComponentsBuilder uriBuilder) {
		Comment comment = service.replyComment(dto);
		
		var uri = uriBuilder.path("/blog/comments/{id}").buildAndExpand(dto).toUri();
		
		return ResponseEntity.created(uri).body(new CommentResponseDTO(comment));
	}
	@GetMapping("/replies/{id}")
	public ResponseEntity<Page<CommentResponseDTO>> getAllRepliesByComment(@PageableDefault(size = 5) Pageable pageable, @PathVariable Long id) {
		return ResponseEntity.ok(service.getRepliesByComment(pageable, id));
	}
	@GetMapping("/by-user/{id}")
	public ResponseEntity<Page<CommentResponseDTO>> getAllCommentsByUser(@PageableDefault(size = 10) Pageable pageable, @PathVariable Long id) {
		return ResponseEntity.ok(service.getCommentsByUser(pageable, id).map(CommentResponseDTO::new));
	}
	@GetMapping("/by-publication/{id}")
	public ResponseEntity<Page<CommentResponseDTO>> getCommentsByPublication(@PageableDefault(size = 10) Pageable pageable, @PathVariable Long id) {
		return ResponseEntity.ok(service.getCommentsByPublicationId(pageable, id).map(CommentResponseDTO::new));
	}
	
	@PutMapping("/update")
	public ResponseEntity<CommentResponseDTO> updateComment(@RequestBody @Valid CommentUpdateDTO dto) {
		Comment comment = service.updateComment(dto);
		return ResponseEntity.ok(new CommentResponseDTO(comment));
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteComment(@PathVariable Long id) {
		service.deleteComment(id);
		return ResponseEntity.noContent().build();
	}
	
}

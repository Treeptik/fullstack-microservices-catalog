package fr.treeptik.fsms.catalog.controller;

import java.net.URI;
import java.util.function.Function;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.treeptik.fsms.catalog.domain.Item;
import fr.treeptik.fsms.catalog.repository.ItemRepository;

@Controller
@RequestMapping("/items")
public class ItemController {
	@Autowired
	private ItemRepository repository;
	
	@Autowired
	private ItemResourceAssembler assembler;
	
	@Autowired
	private ModelMapper mapper;
	
	@GetMapping
	public ResponseEntity<?> findAll() {
		Resources<ItemResource> resources = new Resources<>(assembler.toResources(repository.findAll()));
		resources.add(ControllerLinkBuilder.linkTo(ItemController.class).withSelfRel());
		return ResponseEntity.ok(resources);
	}
	
	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody ItemResource request) {
		Item item = mapper.map(request, Item.class);
		item = repository.save(item);
		ItemResource resource = assembler.toResource(item);
		return ResponseEntity.created(URI.create(resource.getId().getHref())).body(resource);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> find(@PathVariable Long id) {
		return withItem(id, item -> ResponseEntity.ok(assembler.toResource(item)));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody ItemResource request) {
		return withItem(id, item -> {
			mapper.map(request, item);
			item = repository.save(item);
			
			return ResponseEntity.ok(assembler.toResource(item));
		});
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		return withItem(id, item -> {
			repository.delete(item);
			
			return ResponseEntity.noContent().build();
		});
	}
	
	private ResponseEntity<?> withItem(Long id, Function<Item, ResponseEntity<?>> f) {
		return repository.findById(id)
				.map(f)
				.orElse(ResponseEntity.notFound().build());
	}
}

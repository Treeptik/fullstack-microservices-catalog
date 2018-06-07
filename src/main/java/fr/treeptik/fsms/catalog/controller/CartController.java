package fr.treeptik.fsms.catalog.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.function.Function;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.treeptik.fsms.catalog.domain.Cart;
import fr.treeptik.fsms.catalog.domain.CartItem;
import fr.treeptik.fsms.catalog.domain.Item;
import fr.treeptik.fsms.catalog.repository.CartRepository;
import fr.treeptik.fsms.catalog.repository.ItemRepository;

@Controller
@RequestMapping("/carts/{user}/items")
public class CartController {
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private CartItemResourceAssembler assembler;
	
	@GetMapping
	public ResponseEntity<?> getItems(@PathVariable String user) {
		return withCart(user, cart -> {
			Resources<CartItemResource> resources = new Resources<>(assembler.toResources(cart, cart.getItems()));
			resources.add(linkTo(methodOn(CartController.class).getItems(user))
					.withSelfRel());
			
			return ResponseEntity.ok(resources);
		});
	}
	
	@PostMapping
	public ResponseEntity<?> addItem(@PathVariable String user, @Valid @RequestBody CartItemResource request) {
		return withCart(user, cart -> {
			Item item = itemRepository.findById(getItemId(request.getItem()))
					.orElseThrow(() -> new IllegalArgumentException(""));
			
			CartItem cartItem = cart.addItem(item, request.getQuantity());
			
			cart = cartRepository.save(cart);
			
			CartItemResource resource = assembler.toResource(cart, cartItem);
			
			return ResponseEntity.created(URI.create(resource.getId().getHref())).body(resource);
		});
	}
	
	@GetMapping("/{itemId}")
	public ResponseEntity<?> getItem(@PathVariable String user, @PathVariable Long itemId) {
		return withCart(user, cart -> {
			return cart.getItems().stream()
				.filter(i -> i.getItem().getId() == itemId)
				.findAny()
				.map(i -> assembler.toResource(cart, i))
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
		});
	}
	
	private ResponseEntity<?> withCart(String user, Function<Cart, ResponseEntity<?>> f) {
		Cart cart = cartRepository.findByUsername(user)
				.orElseGet(() -> cartRepository.save(new Cart(user)));
		return f.apply(cart);
	}
	
	private Long getItemId(ItemResource item) {
		String uriTemplate = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(ItemController.class).find(null)).withSelfRel().getHref();
		String itemUri = item.getId().getHref();
		
		return Long.parseLong(new AntPathMatcher().extractUriTemplateVariables(uriTemplate, itemUri).get("id"));
	}
}

package fr.treeptik.fsms.catalog.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.treeptik.fsms.catalog.domain.Cart;
import fr.treeptik.fsms.catalog.domain.CartItem;

@Component
public class CartItemResourceAssembler {
	@Autowired
	private ModelMapper mapper;

	public CartItemResource toResource(Cart cart, CartItem entity) {
		CartItemResource resource = new CartItemResource();
		mapper.map(entity, resource);
		resource.add(linkTo(methodOn(CartController.class).getItem(cart.getUsername(), entity.getItem().getId()))
				.withSelfRel());
		return resource;
	}
	
	public List<CartItemResource> toResources(Cart cart, Iterable<? extends CartItem> items) {
		return StreamSupport.stream(items.spliterator(), false)
				.map(item -> toResource(cart, item))
				.collect(Collectors.toList());
	}

}

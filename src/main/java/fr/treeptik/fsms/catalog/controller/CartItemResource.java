package fr.treeptik.fsms.catalog.controller;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

@Relation(value = "item", collectionRelation = "items")
@lombok.Getter @lombok.Setter @lombok.ToString
public class CartItemResource extends ResourceSupport {
	@NotNull
	private ItemResource item;
	
	@Positive
	private int quantity;
}

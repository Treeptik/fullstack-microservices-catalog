package fr.treeptik.fsms.catalog.controller;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

@Relation(value = "item", collectionRelation = "items")
@lombok.Getter @lombok.Setter @lombok.ToString
public class ItemResource extends ResourceSupport {
	@NotBlank
	private String name;
	
	@NotBlank
	private String description;
	
	@Positive
	private double price;
}

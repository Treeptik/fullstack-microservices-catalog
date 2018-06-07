package fr.treeptik.fsms.catalog.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import fr.treeptik.fsms.catalog.domain.Item;

@Component
public class ItemResourceAssembler extends ResourceAssemblerSupport<Item, ItemResource> {
	@Autowired
	private ModelMapper mapper;

	public ItemResourceAssembler() {
		super(ItemController.class, ItemResource.class);
	}

	@Override
	public ItemResource toResource(Item item) {
		ItemResource resource = createResourceWithId(item.getId(), item);
		mapper.map(item, resource);
		return resource;
	}

}

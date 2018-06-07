package fr.treeptik.fsms.catalog.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

import fr.treeptik.fsms.catalog.domain.Item;

public interface ItemRepository extends Repository<Item, Long> {
	public Optional<Item> findById(Long id);
	
	public List<Item> findAll();
	
	public Item save(Item item);
	
	public void delete(Item item);
}

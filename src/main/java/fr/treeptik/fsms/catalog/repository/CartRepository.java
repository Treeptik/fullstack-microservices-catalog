package fr.treeptik.fsms.catalog.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import fr.treeptik.fsms.catalog.domain.Cart;

public interface CartRepository extends Repository<Cart, Long> {
	Optional<Cart> findByUsername(String username);
	
	Cart save(Cart cart);
}

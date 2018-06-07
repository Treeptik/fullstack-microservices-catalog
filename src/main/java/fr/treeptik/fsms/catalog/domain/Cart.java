package fr.treeptik.fsms.catalog.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.Validate;

@Entity
@lombok.NoArgsConstructor
@lombok.Getter @lombok.ToString
public class Cart {
	@Id
	@GeneratedValue
	private Long id;
	
	private String username;
	
	@OneToMany(mappedBy = "id.cart", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<CartItem> items;
	
	public Cart(String username) {
		this.username = username;
		this.items = new ArrayList<>();
	}
	
	public CartItem addItem(Item item, int quantity) {
		Validate.isTrue(items.stream().noneMatch(i -> i.getItem().equals(item)),
				"This item is already in the cart");
		
		CartItem cartItem = new CartItem(this, item, quantity);
		
		items.add(cartItem);
		
		return cartItem;
	}
	
	public void removeItem(Item item) {
		CartItem cartItem = items.stream()
		.filter(i -> i.getItem().equals(item))
		.findAny()
		.orElseThrow(() -> new IllegalArgumentException("This item is not in the cart"));
		
		items.remove(cartItem);
	}

	public List<CartItem> getItems() {
		return Collections.unmodifiableList(items);
	}
}

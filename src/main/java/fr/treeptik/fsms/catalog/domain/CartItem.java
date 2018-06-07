package fr.treeptik.fsms.catalog.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.Validate;


@Entity
public class CartItem {
	@Embeddable
	@lombok.Data
	@lombok.AllArgsConstructor @lombok.NoArgsConstructor
	protected static class Id implements Serializable {
		private static final long serialVersionUID = 733900041605687118L;
		
		@ManyToOne
		private Cart cart;
		
		@ManyToOne
		private Item item;
	}
	
	private static void checkQuantity(int quantity) {
		Validate.isTrue(quantity > 0, "Quantity cannot be negative or zero: %d", quantity);
	}
	
	@EmbeddedId
	private Id id;

	private int quantity;
	
	@SuppressWarnings("unused")
	private CartItem() {}
	
	public CartItem(Cart cart, Item item, int quantity) {
		checkQuantity(quantity);
		
		this.id = new Id(cart, item);
		this.quantity = quantity;
	}
	
	public Item getItem() {
		return id.getItem();
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		checkQuantity(quantity);
		
		this.quantity = quantity;
	}
}

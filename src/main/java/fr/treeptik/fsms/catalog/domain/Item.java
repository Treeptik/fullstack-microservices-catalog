package fr.treeptik.fsms.catalog.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import lombok.AccessLevel;

@Entity
@lombok.Getter @lombok.Setter @lombok.ToString
public class Item {
	@Id
	@GeneratedValue
	@lombok.Setter(AccessLevel.PRIVATE)
	private Long id;
	
	private String name;
	private String description;
	private double price;
	
	public Item() {}
	
	@lombok.Builder
	private Item(String name, String description, double price) {
		this.name = name;
		this.description = description;
		this.price = price;
	}
	
	public void setPrice(double price) {
		Validate.isTrue(price > 0.0, "A price must be strictly positive: %s", price);
		this.price = price;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof Item)) return false;
		
		Item other = (Item) obj;
		
		return new EqualsBuilder()
				.append(this.name, other.name)
				.isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(name)
				.toHashCode();
	}
}

package fr.treeptik.fsms.catalog.domain;

import org.junit.Test;

public class ItemTest {

	@Test
	public void testSetPrice() {
		Item item = new Item();
		
		item.setPrice(-1.0);
	}
}

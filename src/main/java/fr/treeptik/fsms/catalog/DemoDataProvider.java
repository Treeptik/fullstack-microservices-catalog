package fr.treeptik.fsms.catalog;

import java.util.List;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import fr.treeptik.fsms.catalog.domain.Item;
import fr.treeptik.fsms.catalog.repository.ItemRepository;

@Component
@Profile("demo")
public class DemoDataProvider implements ApplicationRunner {
	private static final List<Item> ITEMS = Arrays.asList(
			Item.builder()
				.name("Nokia 3310")
				.description("The indestructible, if not outdated, mobile phone")
				.price(10.00)
				.build(),
			Item.builder()
				.name("Floppy")
				.description("It's bigger than other options, and it loses data more quickly, but it's easier to label")
				.price(3.00)
				.build(),
			Item.builder()
				.name("Sony Dreamcast")
				.description("The best and worst gaming console ever!")
				.price(299.99)
				.build());
	
	@Autowired
	private ItemRepository repository;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		ITEMS.forEach(item -> {
			repository.save(item);
		});
	}

}

package leonardo.lil_mart;

import jakarta.persistence.EntityManager;
import leonardo.lil_mart.product.dto.ProductDTO;
import leonardo.lil_mart.product.model.Product;
import leonardo.lil_mart.product.model.ProductBuilder;
import leonardo.lil_mart.product.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
class LilMartApplicationTests {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	EntityManager entityManager;

	@Test
	@DisplayName("Should get products by their name")
	void searchByNameSucess() {
		ProductDTO productDTO = new ProductDTO(1, "manteiga", "Laticínio", "Manteiga cremosa", 1.0,"Quilos", 2.0);
		this.createProduct(productDTO);
		List<Product> result = this.productRepository.searchByName("manteiga");

		assertThat(result.get(0).getName()).isEqualTo("manteiga");
	}

	private Product createProduct(ProductDTO productDTO) {
		Product newProduct = ProductBuilder.builder()
				.name(productDTO.name())
				.description(productDTO.description())
				.category(productDTO.category())
				.price(productDTO.price())
				.unitMeasurement(productDTO.unitMeasurement())
				.stockQuantity(productDTO.stockQuantity())
				.isActive(true)
				.createdAt(LocalDateTime.now())
				.lastUpdateAt(LocalDateTime.now())
				.build();

		this.entityManager.persist(newProduct);
		return newProduct;
	}

}

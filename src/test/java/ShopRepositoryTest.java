import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.example.product.AlreadyExistsException;
import org.example.product.NotFoundException;
import org.example.product.Product;
import org.example.product.ShopRepository;

public class ShopRepositoryTest {
    private ShopRepository shopRepository;

    @BeforeEach
    public void setup() {
        shopRepository = new ShopRepository();
        shopRepository.add(new Product(1, "Масло", 150));
        shopRepository.add(new Product(2, "Рыба", 170));
        shopRepository.add(new Product(3, "Вода", 20));
    }

    @Test
    public void testRemoveExistingProduct() {
        int idToRemove = 2;

        shopRepository.removeById(idToRemove);
        Product[] products = shopRepository.findAll();

        Assertions.assertEquals(2, products.length);
        Product[] expectedProducts = {new Product(1, "Масло", 150), new Product(3, "Вода", 20)};
        Assertions.assertArrayEquals(expectedProducts, products);
    }

    @Test
    public void testRemoveNonExistingProduct() {
        int nonExistingProductId = 8;

        Assertions.assertThrows(NotFoundException.class, () -> shopRepository.removeById(nonExistingProductId));
    }

    @Test
    public void shouldAddProductTest() {
        Product product = new Product(4, "Творог", 200);
        shopRepository.add(product);

        Product[] products = shopRepository.findAll();
        Assertions.assertEquals(4, products.length);
        Product[] expectedProducts = {
                new Product(1, "Масло", 150),
                new Product(2, "Рыба", 170),
                new Product(3, "Вода", 20),
                new Product(4, "Творог", 200)
        };
        Assertions.assertArrayEquals(expectedProducts, products);
    }

    @Test
    public void shouldNotAddAlreadyExistsProductTest() {
        Product duplicateProduct = new Product(1, "Масло c существующим  ID", 150);
        Assertions.assertThrows(AlreadyExistsException.class, () -> shopRepository.add(duplicateProduct));
    }
}
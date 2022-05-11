package com.finalproyect;

import com.finalproyect.controllers.CheckoutController;
import com.finalproyect.controllers.OrderController;
import com.finalproyect.controllers.UserController;
import com.finalproyect.entities.*;
import com.finalproyect.model.dtos.*;
import com.finalproyect.model.exceptions.BadOrderException;
import com.finalproyect.model.exceptions.CheckoutNotFoundException;
import com.finalproyect.model.exceptions.LackOfStockException;
import com.finalproyect.model.exceptions.ProductNotFoundException;
import com.finalproyect.model.patterns.PaymentStrategiesEnum;
import com.finalproyect.repositories.*;
import com.finalproyect.services.KeycloakContextService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@SpringBootTest
class DemoApplicationTests {

    @MockBean
    KeycloakContextService keycloakContextService;

    @Autowired
    CheckoutController controllerApi;

    @Autowired
    OrderController orderController;

    @Autowired
    UserController userController;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public ProductRepository productRepository;

    @Autowired
    public ShopppingCartRepository shopppingCartRepository;

    @Autowired
    public CountryRepostory countryRepostory;

    @Autowired
    public CityRepository cityRepository;

    @Autowired
    ShippingAddressRepository shippingAddressRepository;

    @Autowired
    public CheckoutRepository checkoutRepository;

    public ShippingAddress shippingAddress;

    public ShoppingCart shopppingCart;

    public Users usersRicardo;

    public Users usersWithCheckout;


    @BeforeEach
    public void setUp() {


        Country country = this.countryRepostory.save(new Country(null, "Argentina", "iso-3434"));

        City city = this.cityRepository.save(new City(null, "bs as", country));

        this.shippingAddress = this.shippingAddressRepository.save(new ShippingAddress(null, city, 1313));


        shopppingCart = this.shopppingCartRepository.save(new ShoppingCart());


        Checkout checkout = checkoutRepository.save(new Checkout(null, shopppingCart, shippingAddress, PaymentStrategiesEnum.PAYPAL));

        usersRicardo = new Users(null, "ricardo", "mail@mail.com", null, "yyy-zzz");

        usersWithCheckout = new Users(null, "juan", "otromail@mail.com", checkout, "jjj-zzz");

    }

    @AfterEach
    public void postTest() {
        this.userRepository.deleteAll();
        this.checkoutRepository.deleteAll();
        this.productRepository.deleteAll();
    }


    @DisplayName("tests de endpoints de casos de exito")
    @Nested
    class SuccessfulTests {
        @Test
        void test_successful_CreateCheckout() throws Exception {
            when(keycloakContextService.contextData()).thenReturn(new KeycloakUserDataDto("yyy-zzz", "ricardo", null, "mail@mail.com"));
            userRepository.save(usersRicardo);
            CheckoutDto checkout = new CheckoutDto(null, shippingAddress, PaymentStrategiesEnum.PAYPAL);
            ResponseEntity<CheckoutDto> responseEntity = controllerApi.createCheckout(checkout);
            assertThat(responseEntity.getStatusCode(), is(HttpStatus.CREATED));
            assertThat(responseEntity.getBody().getPaymentStrategiesEnum(), is(PaymentStrategiesEnum.PAYPAL));
        }

        @Test
        void test_successful_UpdateCheckout() throws Exception {
            when(keycloakContextService.contextData()).thenReturn(new KeycloakUserDataDto("jjj-zzz", "juan", null, "email@Random.com"));
            usersWithCheckout.setEmail("email@Random.com");
            userRepository.save(usersWithCheckout);
            CheckoutDto checkoutDto = new CheckoutDto(null, null, PaymentStrategiesEnum.PAYONEER);
            ResponseEntity<CheckoutDto> responseEntity = controllerApi.updateCheckout(checkoutDto);
            assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
            assertThat(responseEntity.getBody().getPaymentStrategiesEnum(), is(PaymentStrategiesEnum.PAYONEER));
        }

        @Test
        void test_successful_addProductToTheCheckout() {
            when(keycloakContextService.contextData()).thenReturn(new KeycloakUserDataDto("jjj-zzz", "juan", null, "enserio@hotmail.com"));
            usersWithCheckout.setEmail("enserio@hotmail.com");
            Users persistedUsersWithCheckout = userRepository.save(usersWithCheckout);
            assertThat(persistedUsersWithCheckout.getCheckout().getShoppingCart().getProductsInShoppingCart().isEmpty(), is(true));
            Product productShoes = productRepository.save(new Product(null, "zapatos", "c123", 10, 20D));
            ProductForPrucharase productForPrucharase = new ProductForPrucharase();
            productForPrucharase.setProductCode(productShoes.getProductCode());
            ProductDto productDto = new ProductDto(productShoes);
            ResponseEntity<CheckoutDto> responseEntity = controllerApi.addProduct(productDto);
            assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
            assertThat(responseEntity.getBody().getShoppingCart().getProductsInShoppingCart().contains(productForPrucharase), is(true));
        }

        @Test
        void test_successful_modifyQuantityOfProductInShoppingCart() {
            when(keycloakContextService.contextData()).thenReturn(new KeycloakUserDataDto("jjj-zzz", "juan", null, "otromail@mail.com"));
            userRepository.save(usersWithCheckout);
            Product productCoat = productRepository.save(new Product(null, "abrigo", "p222", 100, 10D));
            ProductDto productForReserve = new ProductDto(productCoat);
            productForReserve.setQuantity(20);
            controllerApi.addProduct(productForReserve);
            productForReserve.setQuantity(10);
            ResponseEntity<CheckoutDto> responseEntity = controllerApi.addProduct(productForReserve);
            assertThat(responseEntity.getBody().getShoppingCart().getProductsInShoppingCart(), hasSize(1));
            assertThat(responseEntity.getBody().getShoppingCart().getProductsInShoppingCart().get(0).getQuantity(), is(30));
        }

        @Test
        void test_successful_printPrintCheckout() {
            when(keycloakContextService.contextData()).thenReturn(new KeycloakUserDataDto("jjj-zzz", "juan", null, "otromail@mail.com"));
            userRepository.save(usersWithCheckout);
            ResponseEntity<UserDto> responseEntity = controllerApi.printCheckout();
            assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
            assertThat(responseEntity.getBody().getCheckoutDto().getPaymentStrategiesEnum(), is(PaymentStrategiesEnum.PAYPAL));
        }

        @Test
        void test_successful_generateOrder() {
            when(keycloakContextService.contextData()).thenReturn(new KeycloakUserDataDto("jjj-zzz", "juan", null, "otromail@mail.com"));
            userRepository.save(usersWithCheckout);
            Product productShoes = productRepository.save(new Product(null, "zapatos", "a222", 10, 10D));
            Product productHat = productRepository.save(new Product(null, "sombrero", "s222", 10, 2D));
            controllerApi.addProduct(new ProductDto(productShoes));
            controllerApi.addProduct(new ProductDto(productHat));
            ResponseEntity<OrderDto> responseEntity = orderController.generateOrder();
            assertThat(responseEntity.getStatusCode(), is(HttpStatus.CREATED));
            assertThat(responseEntity.getBody().getShoppingCart().getProductsInShoppingCart(), hasSize(2));
            assertThat(responseEntity.getBody().getTotalCost(), is(120D));
        }

        @Test
        void test_successful_printUser() {
            when(keycloakContextService.contextData()).thenReturn(new KeycloakUserDataDto("jjj-zzz", "juan", null, "otromail@mail.com"));
            userRepository.save(usersWithCheckout);
            ResponseEntity<UserDto> responseEntity = userController.printUser();
            assertThat(responseEntity.getBody().getEmail(), is(usersWithCheckout.getEmail()));
        }

    }

    @DisplayName("tests de endpoints de falla con excepciones manejadas")
    @Nested
    class FailTests {
        @Test
        void test_fail_addProduct_lackOfStock() {
            when(keycloakContextService.contextData()).thenReturn(new KeycloakUserDataDto("jjj-zzz", "juan", null, "yes@mail.com"));
            usersWithCheckout.setEmail("yes@mail.com");
            Users persistedUsersWithCheckout = userRepository.save(usersWithCheckout);
            assertThat(persistedUsersWithCheckout.getCheckout().getShoppingCart().getProductsInShoppingCart().isEmpty(), is(true));
            Product product = productRepository.save(new Product(null, "gorra", "h30", 10, 20D));
            ProductDto productDto = new ProductDto(product);
            productDto.setQuantity(11);
            Assertions.assertThrows(LackOfStockException.class, () -> controllerApi.addProduct(productDto));
        }


        @Test
        void test_fail_addProduct_checkoutNotFound() {
            when(keycloakContextService.contextData()).thenReturn(new KeycloakUserDataDto("jjj-zzz", "ricardo", null, "mail@diferente.com"));
            usersRicardo.setEmail("mail@diferente.com");
            Product product = productRepository.save(new Product(null, "computadora", "atr123", 10, 1D));
            ProductDto productDto = new ProductDto(product);
            Assertions.assertThrows(CheckoutNotFoundException.class, () -> controllerApi.addProduct(productDto));
        }

        @Test
        void test_fail_addProduct_productNotFound() {
            when(keycloakContextService.contextData()).thenReturn(new KeycloakUserDataDto("jjj-zzz", "juan", null, "mailForTesting@mail"));
            usersWithCheckout.setEmail("mailForTesting@mail");
            userRepository.save(usersWithCheckout);
            ProductDto productDto = new ProductDto();
            productDto.setProductCode("thisDoesNotExist");
            productDto.setQuantity(1);
            productDto.setName("zapatos");
            Assertions.assertThrows(ProductNotFoundException.class, () -> controllerApi.addProduct(productDto));

        }

        @Test
        void test_fail_generateOrder_emptyShoppingCart() {
            when(keycloakContextService.contextData()).thenReturn(new KeycloakUserDataDto("jjj-zzz", "juan", null, "otromail@mail.com"));
            userRepository.save(usersWithCheckout);
            Assertions.assertThrows(BadOrderException.class, () -> orderController.generateOrder());
        }

        @Test
        void fail_generateOrder_nullShippingAddress() {
            when(keycloakContextService.contextData()).thenReturn(new KeycloakUserDataDto("jjj-zzz", "juan", null, "otromail@mail.com"));
            usersWithCheckout.getCheckout().setShippingAddress(null);
            checkoutRepository.save(usersWithCheckout.getCheckout());
            userRepository.save(usersWithCheckout);
            Product productShoes = productRepository.save(new Product(null, "zapatos", "a222", 10, 10D));
            Product productHat = productRepository.save(new Product(null, "sombrero", "s222", 10, 2D));
            controllerApi.addProduct(new ProductDto(productShoes));
            controllerApi.addProduct(new ProductDto(productHat));
            Assertions.assertThrows(BadOrderException.class, () -> orderController.generateOrder());
        }

    }

    @DisplayName("tests para validar la correcta pre-carga de datos a la BD")
    @Nested
    class data_tests {
        @Test
        void test_successful_preloadData() {
            Optional<Users> optionalUser = userRepository.findById(12L);
            assertThat(optionalUser.isPresent(), is(true));
            Users users = optionalUser.get();
            assertThat(users.getName(), is("juan"));
            assertThat(users.getEmail(), is("miMail@hotmail"));
            assertThat(users.getCheckout().getPaymentStrategy(), is(PaymentStrategiesEnum.PAYPAL));
        }
    }


}



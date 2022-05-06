package com.finalproyect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproyect.controllers.CheckoutController;
import com.finalproyect.entities.*;
import com.finalproyect.exceptions.CheckoutNotFoundException;
import com.finalproyect.exceptions.LackOfStockException;
import com.finalproyect.exceptions.ProductNotFoundException;
import com.finalproyect.exceptions.UserNotFoundException;
import com.finalproyect.model.dtos.CheckoutDto;
import com.finalproyect.model.dtos.ProductDto;
import com.finalproyect.model.dtos.UserDto;
import com.finalproyect.model.patterns.PaymentStrategiesEnum;
import com.finalproyect.repositories.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    CheckoutController controllerApi;

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

    public ObjectMapper objectMapper;

    public ShippingAddress shippingAddress;

    public ShoppingCart shopppingCart;

    public Users usersRicardo;

    public Users usersWithCheckout;
    @BeforeEach
    public void setUp() {


        Country country = this.countryRepostory.save(new Country(null, "Argentina", "iso-3434"));

        City city = this.cityRepository.save(new City(null, "bs as", country));

        this.shippingAddress =this.shippingAddressRepository.save(new ShippingAddress(null, city, 1313));


        shopppingCart = this.shopppingCartRepository.save( new ShoppingCart());


        Checkout checkout = checkoutRepository.save(new Checkout(null, shopppingCart, shippingAddress, PaymentStrategiesEnum.PAYPAL));

        usersRicardo = new Users(null, "ricardo", "mail@mail.com", null,null);

        usersWithCheckout = new Users(null, "juan", "otromail@mail.com", checkout,null);

    }


    @Test
    void test_successful_CreateCheckout() throws Exception {
        Users persistedUsers = this.userRepository.save(usersRicardo);
        CheckoutDto checkout = new CheckoutDto(shopppingCart, shippingAddress, PaymentStrategiesEnum.PAYPAL);
        checkout.setId(persistedUsers.getUserId());
        ResponseEntity<CheckoutDto> responseEntity = controllerApi.createCheckout(checkout);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(responseEntity.getBody().getPaymentStrategiesEnum(), is(PaymentStrategiesEnum.PAYPAL));
    }

    @Test
    void test_successful_UpdateCheckout() throws Exception{
        usersWithCheckout.setEmail("email@Random.com");
        Users persistedUsersWithCheckout = this.userRepository.save(usersWithCheckout);
        CheckoutDto checkoutDto=new CheckoutDto(null,null,PaymentStrategiesEnum.PAYONEER);
        ResponseEntity<CheckoutDto> responseEntity=this.controllerApi.updateCheckout(persistedUsersWithCheckout.getUserId(), checkoutDto);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody().getPaymentStrategiesEnum(),is(PaymentStrategiesEnum.PAYONEER));
    }

    @Test
    void test_successful_addProductToTheCheckout(){
        usersWithCheckout.setEmail("enserio@hotmail.com");
        Users persistedUsersWithCheckout =this.userRepository.save(usersWithCheckout);
        assertThat(persistedUsersWithCheckout.getCheckout().getShoppingCart().getProductsInShoppingCart().isEmpty(), is(true));
        Product productShoes =productRepository.save(new Product(null, "zapatos", "c123", 10));
        ProductForPrucharase productForPrucharase= new ProductForPrucharase();
        productForPrucharase.setProductCode(productShoes.getProductCode());
        ResponseEntity<CheckoutDto> responseEntity=this.controllerApi.addProduct(persistedUsersWithCheckout.getUserId(),new ProductDto(productShoes));
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody().getShoppingCart().getProductsInShoppingCart(), contains(productForPrucharase));
    }

    @Test
    void test_successful_printPrintCheckout(){
        usersWithCheckout.setEmail("funciona@hotmail.com");
        Users users = this.userRepository.save(usersWithCheckout);
        ResponseEntity<UserDto> responseEntity=this.controllerApi.printCheckout(users.getUserId());
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody().getCheckoutDto().getPaymentStrategiesEnum(), is(PaymentStrategiesEnum.PAYPAL));
    }

    @Test
    void test_fail_addProduct_lackOfStock(){
        usersWithCheckout.setEmail("yes@mail.com");
        Users persistedUsersWithCheckout =this.userRepository.save(usersWithCheckout);
        assertThat(persistedUsersWithCheckout.getCheckout().getShoppingCart().getProductsInShoppingCart().isEmpty(), is(true));
        Product product =productRepository.save(new Product(null, "gorra", "h30", 10));
        ProductDto productDto=new ProductDto(product);
        productDto.setQuantity(11);
        Assertions.assertThrows(LackOfStockException.class,()->this.controllerApi.addProduct(persistedUsersWithCheckout.getUserId(), productDto));
    }

    @Test
    void test_fail_printCheckout_userDontExistInDB(){
        Assertions.assertThrows(UserNotFoundException.class,()->this.controllerApi.printCheckout(-1L));
    }

    @Test
    void test_fail_addProduct_checkoutNotFound(){
        usersRicardo.setEmail("mail@diferente.com");
        Users persistedUsersWithoutCheckout =this.userRepository.save(usersRicardo);
        Product product =productRepository.save(new Product(null, "computadora", "atr123", 10));
        ProductDto productDto= new ProductDto(product);
        Assertions.assertThrows(CheckoutNotFoundException.class,()->this.controllerApi.addProduct(persistedUsersWithoutCheckout.getUserId(), productDto));
    }

    @Test
    void test_fail_addProduct_productNotFound(){
        this.usersWithCheckout.setEmail("mailForTesting");
        Users persistedUsersWithCheckout =this.userRepository.save(usersWithCheckout);
        ProductDto productDto=new ProductDto();
        productDto.setProductCode("thisDoesNotExist");
        productDto.setQuantity(1);
        productDto.setName("zapatos");
        Assertions.assertThrows(ProductNotFoundException.class,()->this.controllerApi.addProduct(persistedUsersWithCheckout.getUserId(), productDto));

    }

    @Test
    void test_successful_preloadData(){
        Optional<Users> optionalUser=this.userRepository.findById(15L);
        assertThat(optionalUser.isPresent(), is(true));
        Users users =optionalUser.get();
        assertThat(users.getName(), is("juan"));
        assertThat(users.getEmail(), is("miMail@hotmail"));
        assertThat(users.getCheckout().getPaymentStrategy(), is(PaymentStrategiesEnum.PAYPAL));
    }
}



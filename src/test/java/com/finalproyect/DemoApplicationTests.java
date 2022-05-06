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

    public User userRicardo;

    public User userWithCheckout;
    @BeforeEach
    public void setUp() {


        Country country = this.countryRepostory.save(new Country(null, "Argentina", "iso-3434"));

        City city = this.cityRepository.save(new City(null, "bs as", country));

        this.shippingAddress =this.shippingAddressRepository.save(new ShippingAddress(null, city, 1313));


        shopppingCart = this.shopppingCartRepository.save( new ShoppingCart());


        Checkout checkout = checkoutRepository.save(new Checkout(null, shopppingCart, shippingAddress, PaymentStrategiesEnum.PAYPAL));

        userRicardo = new User(null, "ricardo", "mail@mail.com", null);

        userWithCheckout= new User(null, "juan", "otromail@mail.com", checkout);

    }


    @Test
    void test_successful_CreateCheckout() throws Exception {
        User persistedUser= this.userRepository.save(userRicardo);
        CheckoutDto checkout = new CheckoutDto(shopppingCart, shippingAddress, PaymentStrategiesEnum.PAYPAL);
        checkout.setId(persistedUser.getUserId());
        ResponseEntity<CheckoutDto> responseEntity = controllerApi.createCheckout(checkout);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(responseEntity.getBody().getPaymentStrategiesEnum(), is(PaymentStrategiesEnum.PAYPAL));
    }

    @Test
    void test_successful_UpdateCheckout() throws Exception{
        userWithCheckout.setEmail("email@Random.com");
        User persistedUserWithCheckout= this.userRepository.save(userWithCheckout);
        CheckoutDto checkoutDto=new CheckoutDto(null,null,PaymentStrategiesEnum.PAYONEER);
        ResponseEntity<CheckoutDto> responseEntity=this.controllerApi.updateCheckout(persistedUserWithCheckout.getUserId(), checkoutDto);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody().getPaymentStrategiesEnum(),is(PaymentStrategiesEnum.PAYONEER));
    }

    @Test
    void test_Successful_addProductToTheCheckout(){
        userWithCheckout.setEmail("enserio@hotmail.com");
        User persistedUserWithCheckout=this.userRepository.save(userWithCheckout);
        assertThat(persistedUserWithCheckout.getCheckout().getShoppingCart().getProductsInShoppingCart().isEmpty(), is(true));
        Product productShoes =productRepository.save(new Product(null, "zapatos", "c123", 10));
        ProductForPrucharase productForPrucharase= new ProductForPrucharase();
        productForPrucharase.setProductCode(productShoes.getProductCode());
        ResponseEntity<CheckoutDto> responseEntity=this.controllerApi.addProduct(persistedUserWithCheckout.getUserId(),new ProductDto(productShoes));
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody().getShoppingCart().getProductsInShoppingCart(), contains(productForPrucharase));
    }

    @Test
    void test_Successful_printPrintCheckout(){
        userWithCheckout.setEmail("funciona@hotmail.com");
        User user= this.userRepository.save(userWithCheckout);
        ResponseEntity<UserDto> responseEntity=this.controllerApi.printCheckout(user.getUserId());
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody().getCheckoutDto().getPaymentStrategiesEnum(), is(PaymentStrategiesEnum.PAYPAL));
    }

    @Test
    void test_fail_addProduct_lackOfStock(){
        userWithCheckout.setEmail("yes@mail.com");
        User persistedUserWithCheckout=this.userRepository.save(userWithCheckout);
        assertThat(persistedUserWithCheckout.getCheckout().getShoppingCart().getProductsInShoppingCart().isEmpty(), is(true));
        Product product =productRepository.save(new Product(null, "gorra", "h30", 10));
        ProductDto productDto=new ProductDto(product);
        productDto.setQuantity(11);
        Assertions.assertThrows(LackOfStockException.class,()->this.controllerApi.addProduct(persistedUserWithCheckout.getUserId(), productDto));
    }

    @Test
    void test_fail_printCheckout_userDontExistInDB(){
        Assertions.assertThrows(UserNotFoundException.class,()->this.controllerApi.printCheckout(-1L));
    }

    @Test
    void test_fail_addProduct_checkoutNotFound(){
        userRicardo.setEmail("mail@diferente.com");
        User persistedUserWithoutCheckout=this.userRepository.save(userRicardo);
        Product product =productRepository.save(new Product(null, "computadora", "atr123", 10));
        ProductDto productDto= new ProductDto(product);
        Assertions.assertThrows(CheckoutNotFoundException.class,()->this.controllerApi.addProduct(persistedUserWithoutCheckout.getUserId(), productDto));
    }

    @Test
    void test_fail_addProduct_productNotFound(){
        this.userWithCheckout.setEmail("mailForTesting");
        User persistedUserWithCheckout=this.userRepository.save(userWithCheckout);
        ProductDto productDto=new ProductDto();
        productDto.setProductCode("thisDoesNotExist");
        productDto.setQuantity(1);
        productDto.setName("zapatos");
        Assertions.assertThrows(ProductNotFoundException.class,()->this.controllerApi.addProduct(persistedUserWithCheckout.getUserId(), productDto));

    }
}



package lesson5;

import com.github.javafaker.Faker;
import lesson5.api.ProductService;
import lesson5.dto.Product;
import lesson5.utils.RetrofitUtils;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;


public class ProductTest {

    static ProductService productService;
    Product product = null;
    Faker faker = new Faker();
    int id;

    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit()
                .create(ProductService.class);
    }

    @BeforeEach
    void setUp() {
        product = new Product()
                .withTitle(faker.food().ingredient())
                .withCategoryTitle("Food")
                .withCategoryTitle("Electronic")
                .withPrice((int) (Math.random() * 10000));
    }

    @Test
    void createProductInFoodCategoryPositiveTest() throws IOException {
        Response<Product> response = productService.createProduct(product.withCategoryTitle("Food"))
                .execute();
        id =  response.body().getId();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }
    @Test
    void createProductInElectronicCategoryPositiveTest() throws IOException {
        Response<Product> response = productService.createProduct(product.withCategoryTitle("Electronic"))
                .execute();
        id =  response.body().getId();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }


    @Test
    void putModifyProductInFoodCategoryPositiveTest() throws IOException {
        Response<Product> response = productService.modifyProduct(product.withId(1))
                .execute();
        id = response.body().getId();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));



    }

    @Test
    void putModifyProductInElectronicCategoryPositiveTest() throws IOException {
        Response<Product> response = productService.modifyProduct(product.withId(5))
                .execute();
       id =  response.body().getId();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }

    @Test
    void getProductByIdFoodCategoryPositiveTest() throws IOException {
        Response<Product> response = productService.getProductById(id = 1)
                .execute();
        id =  response.body().getId();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }

    @Test
    void getProductByIdElectronicCategoryPositiveTest() throws IOException {
        Response<Product> response = productService.getProductById(id = 4)
                .execute();
        id =  response.body().getId();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }


    @Test
    void deleteProductByFoodIdNegativeTest() throws IOException {
        Response<ResponseBody> response = productService.deleteProduct(id = 10).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(false));

    }

    @Test
    void deleteProductByElectronicIdNegativeTest() throws IOException {
        Response<ResponseBody> response = productService.deleteProduct(id = 9).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(false));

    }

    @SneakyThrows
    @AfterEach
    void tearDown() {
        Response<ResponseBody> response = productService.deleteProduct(id).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }





}

package xmu.oomall.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xmu.oomall.domain.MallProductPo;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductMapperTest {
    private ProductMapper productMapper;

    @Test
    public void addProduct() {
        MallProductPo mallProductPo=new MallProductPo();
        mallProductPo.setId(1);
        mallProductPo.setGoodsId(1);
        mallProductPo.setPicUrl("....");
        mallProductPo.setSafetyStock(50);
        mallProductPo.setSpecifications("xxx");
        mallProductPo.setPrice(BigDecimal.valueOf(19.99));
        mallProductPo.setBeDeleted(false);
        productMapper.addProduct(mallProductPo);
    }

    @Test
    public void deleteProductById() {
    }

    @Test
    public void updateProduct() {
    }

    @Test
    public void findProductById() {
    }

    @Test
    public void findSubProductsById() {
    }

    @Test
    public void addProducts() {
    }
}

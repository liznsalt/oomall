package xmu.oomall.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import xmu.oomall.CartApplication;
import xmu.oomall.domain.MallCartItem;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CartApplication.class)
@AutoConfigureMockMvc
//@Transactional
public class CartMapperTest {

    @Autowired
    private CartMapper cartMapper;

    @Test
    public void add() {
        MallCartItem mallCartItem = new MallCartItem();
        mallCartItem.setNumber(1);
        mallCartItem.setUserId(1);
        mallCartItem.setProductId(1);
        mallCartItem.setBeCheck(false);
        mallCartItem.setGmtCreate(LocalDateTime.now());
        mallCartItem.setGmtModified(LocalDateTime.now());
        cartMapper.insert(mallCartItem);
        System.out.println(mallCartItem);
    }

    @Test
    public void test() {
        Example example = new Example(MallCartItem.class);
        example.createCriteria().andCondition("id = 1");
        List<MallCartItem> cartItems = cartMapper.selectByExample(example);
        System.out.println(cartItems);
    }
}
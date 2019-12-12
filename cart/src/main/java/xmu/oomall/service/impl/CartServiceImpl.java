package xmu.oomall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import xmu.oomall.domain.MallCartItem;
import xmu.oomall.mapper.CartMapper;
import xmu.oomall.service.CartService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author liznsalt
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper cartMapper;

    @Override
    public List<MallCartItem> list(Integer userId) {
        Example example = new Example(MallCartItem.class);
        example.createCriteria().andCondition("user_id = " + userId);
        return cartMapper.selectByExample(example);
    }

    @Override
    public MallCartItem add(Integer userId, MallCartItem cartItem) {
        cartItem.setUserId(userId);
        cartItem.setGmtModified(LocalDateTime.now());
        if (cartItem.getId() == null) {
            // new cart
            cartItem.setGmtCreate(LocalDateTime.now());
            cartMapper.insert(cartItem);
        } else {
            MallCartItem oldCartItem = cartMapper.selectByPrimaryKey(cartItem.getId());
            // id 不存在
            if (oldCartItem == null) {
                // new cart
                cartItem.setGmtCreate(LocalDateTime.now());
                cartMapper.insert(cartItem);
            } else {
                // add cart
                cartItem.setNumber(cartItem.getNumber() + oldCartItem.getNumber());
                cartMapper.updateByPrimaryKeySelective(cartItem);
            }
        }
        return cartItem;
    }

    @Override
    public MallCartItem fastAdd(Integer userId, MallCartItem cartItem) {
        cartItem.setUserId(userId);
        cartItem.setGmtModified(LocalDateTime.now());
        if (cartItem.getId() == null) {
            cartItem.setGmtCreate(LocalDateTime.now());
            cartMapper.insert(cartItem);
        } else {
            MallCartItem oldCartItem = cartMapper.selectByPrimaryKey(cartItem.getId());
            // id 不存在
            if (oldCartItem == null) {
                cartItem.setGmtCreate(LocalDateTime.now());
                cartMapper.insert(cartItem);
            } else {
                // 此处不需要修改数量，直接覆盖
                cartMapper.updateByPrimaryKeySelective(cartItem);
            }
        }
        return cartItem;
    }

    @Override
    public MallCartItem update(Integer userId, MallCartItem cartItem) {
        cartItem.setUserId(userId);
        cartItem.setGmtModified(LocalDateTime.now());
        cartMapper.updateByPrimaryKeySelective(cartItem);
        return cartItem;
    }

    @Override
    public boolean delete(Integer id) {
        int count = cartMapper.deleteByPrimaryKey(id);
        return count >= 1;
    }

    @Override
    public int goodsCount(Integer userId) {
        Example example = new Example(MallCartItem.class);
        example.createCriteria().andCondition("user_id = " + userId);
        return cartMapper.selectCountByExample(example);
    }

    @Override
    public boolean clear(Integer userId) {
        Example example = new Example(MallCartItem.class);
        example.createCriteria().andCondition("user_id = " + userId);
        cartMapper.deleteByExample(example);
        return true;
    }
}

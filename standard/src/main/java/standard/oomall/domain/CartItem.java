package standard.oomall.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Transient;

/**
 * @Author: 数据库与对象模型标准组
 * @Description:购物车明细对象
 * @Data:Created in 14:50 2019/12/11
 **/
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class CartItem extends CartItemPo {

    @Transient
    private Product product;

}

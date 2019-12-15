package xmu.oomall.domain;

import org.apache.ibatis.type.Alias;
import standard.oomall.domain.CartItem;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author liznsalt
 */
@Alias("mallCartItem")
@Table(name = "oomall_cart_item")
public class MallCartItem extends CartItem {
}

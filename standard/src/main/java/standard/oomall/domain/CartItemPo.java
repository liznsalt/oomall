package standard.oomall.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @Author: 数据库与对象模型标准组
 * @Description: 购物车明细
 * @Date: Created in 14:30 2019/12/11
 **/
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CartItemPo {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;
    /**
     * 购物车归属的用户id
     */
    private Integer userId;
    /**
     * 货品ID
     */
    private Integer productId;
    /**
     * 是否选中，0未选中，1已选中
     */
    @Column(name = "is_check")
    private Boolean beCheck;
    /**
     * 数量
     */
    private Integer number;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

}

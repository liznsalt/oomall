package xmu.oomall.mapper;

import org.apache.ibatis.annotations.Mapper;
import xmu.oomall.domain.MallGoods;
import xmu.oomall.domain.MallProductPo;

import java.util.List;

/**
 * @author liznsalt
 */
@Mapper
public interface GoodsMapper {
    /**
     * 增加商品
     * @param goods 商品信息
     * @return 添加后的ID
     */
    int addGoods(MallGoods goods);

    /**
     * 根据商品ID删除商品
     * @param id 商品ID
     * @return 删除行数
     */
    int deleteGoodsById(Integer id);

    /**
     * 更新商品
     * @param goods 商品信息
     * @return 修改行数
     */
    int updateGoods(MallGoods goods);

    /**
     * 通过商品ID检索商品
     * @param id 商品ID
     * @return 商品信息
     */
    MallGoods findGoodsById(Integer id);

    /**
     * 得到所有商品
     * @return 商品列表
     */
    List<MallGoods> getAllGoods();

    /**
     * 通过商品ID检索其下所有产品
     * @param id 商品ID
     * @return 产品列表
     */
    List<MallProductPo> findProductsById(Integer id);

    /**
     * 获得数据库中的货品库存量
     * @param id 货品id
     * @return 库存量
     */
    Integer getStockInDb(Integer id);
}

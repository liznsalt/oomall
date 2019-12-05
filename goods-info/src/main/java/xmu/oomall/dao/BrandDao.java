package xmu.oomall.dao;

import org.springframework.stereotype.Repository;

/**
 * @author liznsalt
 */
@Repository
public class BrandDao {
    /**
     * 删除品牌，级联将商品的品牌ID设NULL
     * @param id 品牌ID
     */
    void deleteBrandById(Integer id) {

    }
}

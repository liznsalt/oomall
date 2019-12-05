package xmu.oomall.dao;

import org.springframework.stereotype.Repository;

/**
 * @author liznsalt
 */
@Repository
public class GoodsCategoryDao {
    /**
     * 删除种类，级联将商品的种类ID设NULL
     * @param id 种类ID
     */
    void deleteGoodsCategoryById(Integer id) {

    }
}

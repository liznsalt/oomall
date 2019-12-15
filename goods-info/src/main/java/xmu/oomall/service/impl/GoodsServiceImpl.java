package xmu.oomall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.oomall.dao.BrandDao;
import xmu.oomall.dao.GoodsCategoryDao;
import xmu.oomall.dao.GoodsDao;
import xmu.oomall.dao.ProductDao;
import xmu.oomall.domain.*;
import xmu.oomall.mapper.GoodsMapper;
import xmu.oomall.service.GoodsService;
import xmu.oomall.service.RedisService;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private GoodsCategoryDao goodsCategoryDao;

    @Autowired
    private BrandDao brandDao;

    @Autowired
    private RedisService redisService;

    @Override
    public Boolean goodsOn(Goods goods) {
        if (goods.getId() == null) {
            return false;
        }
        goods.setStatusCode(1);
        goodsDao.updateGoods(goods);
        return true;
    }

    @Override
    public Boolean goodsOff(Goods goods) {
        if (goods.getId() == null) {
            return false;
        }
        goods.setStatusCode(0);
        goodsDao.updateGoods(goods);
        return true;
    }

    @Override
    public Integer getStockInDB(Integer id) {
        Product product = productDao.findProductById(id);
        return product.getSafetyStock();
    }

    @Override
    public void updateStockInDB(Integer id, Integer quantity) {
        Product product = productDao.findProductById(id);
        product.setSafetyStock(quantity);
        productDao.updateProduct(product);
    }

    @Override
    public Goods findAllGoodsById (Integer id) {
        if (id == null || id <= 0) {
            return null;
        }
        return goodsDao.findAllGoodsById(id);
    }

    @Override
    public Goods findGoodsById(Integer id){
        if (id == null || id <= 0) {
            return null;
        }
        return goodsDao.findGoodsById(id);
    }

    @Override
    public Product addProductByGoodsId(Integer id, Product product) {
        if (id == null || id <= 0 || product == null) {
            return null;
        }
        Goods goods = goodsDao.findGoodsById(id);
        if (goods == null) {
            return null;
        }
        product.setGoodsId(id);
        Product newProduct = productDao.addProduct(product);
        goods.getProductPoList().add(newProduct);
        redisService.set("GOODS" + goods.getId(), goods);
        return product;
    }

    @Override
    public Product findProductById(Integer id){
        if(id==null||id<=0){
            return null;
        }
        return productDao.findProductById(id);
    }

    @Override
    public Product updateProductById(Integer id, Product product) {
        if (id == null || id <= 0 || product == null) {
            return null;
        }
        product.setId(id);
        Product product1 = productDao.updateProduct(product);
        return product1;
    }

    @Override
    public Boolean deleteProductById(Integer id) {
        if (id == null || id <= 0) {
            return false;
        }
        Product product = productDao.findProductById(id);
        if (product == null) {
            return false;
        }
        productDao.deleteProductById(id);
        return true;
    }

    @Override
    public Goods addGoods(Goods goods) {
        if (goods == null) {
            return null;
        }
        return goodsDao.addGoods(goods);
    }

    @Override
    public Goods updateGoodsById(Integer id, Goods goods) {
        if (id == null || id <= 0|| goods == null) {
            return null;
        }
        goods.setId(id);
        return goodsDao.updateGoods(goods);
    }

    @Override
    public Boolean deleteGoodsById(Integer id) {
        if (id == null || id <= 0) {
            return false;
        }
        Goods goods = goodsDao.findGoodsById(id);
        if (goods == null) {
            return false;
        }
        goodsDao.deleteGoodsById(id);
        return true;
    }

    @Override
    public List<GoodsPo> getCategoriesInfoById(Integer id, Integer page, Integer limit) {
        if (id == null || id <= 0) {
            return null;
        }
        return goodsCategoryDao.findGoodsByCategoryId(id,page,limit);
    }

    @Override
    public Brand addBrand(Brand brand) {
        if (brand == null) {
            return null;
        }
        return brandDao.addBrand(brand);
    }

    @Override
    public Brand findBrandById(Integer id) {
        if (id == null || id <= 0) {
            return null;
        }
        return brandDao.findBrandById(id);
    }

    @Override
    public Brand updateBrandById(Integer id, Brand brand) {
        if (id == null || id <= 0 || brand == null) {
            return null;
        }
        brand.setId(id);
        return brandDao.updateBrand(brand);
    }

    @Override
    public List<GoodsPo> getGoodsByBrandId(Integer id){
        if(id == null || id <= 0){
            return null;
        }
        return brandDao.findGoodsByBrandId(id);
    }

    @Override
    public Boolean deleteBrandById(Integer id) {
        if (id == null || id <= 0) {
            return false;
        }
        if (brandDao.findBrandById(id) == null) {
            return false;
        }
        return brandDao.deleteBrandById(id);
    }

    @Override
    public List<GoodsCategoryPo> getGoodsCategory(Integer page, Integer limit) {
        return goodsCategoryDao.findAllGoodsCategories(page,limit);
    }

    @Override
    public GoodsCategory addGoodsCategory(GoodsCategory goodsCategory) {
        if (goodsCategory == null) {
            return null;
        }
        return goodsCategoryDao.addGoodsCategory(goodsCategory);
    }

    @Override
    public GoodsCategory findGoodsCategoryById(Integer id) {
        if (id == null || id <= 0) {
            return null;
        }
        return goodsCategoryDao.findGoodsCategoryById(id);
    }

    @Override
    public GoodsCategory updateGoodsCategoryById(Integer id, GoodsCategory goodsCategory) {
        if (id == null || id <= 0 || goodsCategory == null) {
            return null;
        }
        goodsCategory.setId(id);
        return goodsCategoryDao.updateGoodsCategory(goodsCategory);
    }

    @Override
    public Boolean deleteGoodsCategoryById(Integer id) {
        if (id == null || id <= 0) {
            return false;
        }
        if (goodsCategoryDao.findGoodsCategoryById(id) == null) {
            return false;
        }
        goodsCategoryDao.deleteGoodsCategoryById(id);
        return true;
    }

    @Override
    public List<GoodsCategory> getOneLevelGoodsCategory() {
        return goodsCategoryDao.findAllGoodsCategoriesOfL1();
    }

}

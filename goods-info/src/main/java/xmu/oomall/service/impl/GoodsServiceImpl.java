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
    public Boolean goodsOn(MallGoods goods) {
        if (goods.getId() == null) {
            return false;
        }
        goods.setStatusCode(false);
        goodsDao.updateGoods(goods);
        return true;
    }

    @Override
    public Boolean goodsOff(MallGoods goods) {
        if (goods.getId() == null) {
            return false;
        }
        goods.setStatusCode(true);
        goodsDao.updateGoods(goods);
        return true;
    }

    @Override
    public Integer getStockInDB(Integer id) {
        MallProduct product = productDao.findProductById(id);
        return product.getSafetyStock();
    }

    @Override
    public void updateStockInDB(Integer id, Integer quantity) {
        MallProduct product = productDao.findProductById(id);
        product.setSafetyStock(quantity);
        productDao.updateProduct(product);
    }

    @Override
    public MallGoods findGoodsById(Integer id) {
        if (id == null || id <= 0) {
            return null;
        }
        return goodsDao.findGoodsById(id);
    }

    @Override
    public MallProduct addProductByGoodsId(Integer id, MallProduct product) {
        if (id == null || id <= 0 || product == null) {
            return null;
        }
        MallGoods goods = goodsDao.findGoodsById(id);
        if (goods == null) {
            return null;
        }
        product.setGoodsId(id);
        MallProduct newProduct = productDao.addProduct(product);
        goods.getProductPoList().add(newProduct);
        redisService.set("G_" + goods.getId(), goods);
        return product;
    }

    @Override
    public MallProduct updateProductById(Integer id, MallProduct product) {
        if (id == null || id <= 0 || product == null) {
            return null;
        }
        product.setId(id);
        MallProduct mallProduct = productDao.updateProduct(product);
        return mallProduct;
    }

    @Override
    public Boolean deleteProductById(Integer id) {
        if (id == null || id <= 0) {
            return false;
        }
        MallProduct mallProduct = productDao.findProductById(id);
        if (mallProduct == null) {
            return false;
        }
        productDao.deleteProductById(id);
        return true;
    }

    @Override
    public MallGoods addGoods(MallGoods goods) {
        if (goods == null) {
            return null;
        }
        return goodsDao.addGoods(goods);
    }

    @Override
    public MallGoods updateGoodsById(Integer id, MallGoods goods) {
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
        MallGoods mallGoods = goodsDao.findGoodsById(id);
        if (mallGoods == null) {
            return false;
        }
        goodsDao.deleteGoodsById(id);
        return true;
    }

    @Override
    public List<MallGoods> getCategoriesInfoById(Integer id) {
        if (id == null || id <= 0) {
            return null;
        }
        // TODO: dao层需要提供查询根据 category id 查询 goods 的接口

        return null;
    }

    @Override
    public MallBrand addBrand(MallBrand brand) {
        if (brand == null) {
            return null;
        }
        return brandDao.addBrand(brand);
    }

    @Override
    public MallBrand findBrandById(Integer id) {
        if (id == null || id <= 0) {
            return null;
        }
        return brandDao.findBrandById(id);
    }

    @Override
    public MallBrand updateBrandById(Integer id, MallBrand brand) {
        if (id == null || id <= 0 || brand == null) {
            return null;
        }
        brand.setId(id);
        return brandDao.updateBrand(brand);
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
    public List<MallGoodsCategory> getGoodsCategory() {
        return goodsCategoryDao.findAllGoodsCategoriesOfL1();
    }

    @Override
    public MallGoodsCategory addGoodsCategory(MallGoodsCategory goodsCategory) {
        if (goodsCategory == null) {
            return null;
        }
        return goodsCategoryDao.addGoodsCategory(goodsCategory);
    }

    @Override
    public MallGoodsCategory findGoodsCategoryById(Integer id) {
        if (id == null || id <= 0) {
            return null;
        }
        return goodsCategoryDao.findGoodsCategoryById(id);
    }

    @Override
    public MallGoodsCategory updateGoodsCategoryById(Integer id, MallGoodsCategory goodsCategory) {
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
    public List<MallGoodsCategory> getOneLevelGoodsCategory() {
        return goodsCategoryDao.findAllGoodsCategoriesOfL1();
    }

}

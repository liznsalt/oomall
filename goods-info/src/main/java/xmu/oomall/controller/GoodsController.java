package xmu.oomall.controller;

import common.oomall.api.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import standard.oomall.domain.Brand;
import standard.oomall.domain.Goods;
import standard.oomall.domain.GoodsCategory;
import standard.oomall.domain.Product;
import xmu.oomall.domain.*;
import xmu.oomall.service.GoodsService;

import java.util.List;

/**
 * @author liznsalt
 * @author YaNai
 */
@RestController
@RequestMapping("/goodsService")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     *管理员查询商品下的产品
     * @param id
     * @return List<ProductVo>，所属该商品的产品列表
     */
    @GetMapping("/goods/{id}/products")
    public Object listProductByGoodsId(@PathVariable Integer id) {
        MallGoods goods = goodsService.findGoodsById(id);
        if (goods == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(goods.getProducts());
    }

    /**
     * 管理员添加商品下的产品
     * @param id
     * @param product
     * @return Product，新添加的产品信息
     */
    @PostMapping("/goods/{id}/products")
    public Object addProductByGoodsId(@PathVariable Integer id, @RequestBody Product product) {
        MallProductPo mallProductPo = (MallProductPo) product;
        MallProduct mallProduct = new MallProduct(mallProductPo);
        MallProduct resultMallProduct = goodsService.addProductByGoodsId(id, mallProduct);
        if (resultMallProduct == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(resultMallProduct);
    }

    /**
     * 管理员修改商品下的某个产品信息
     * @param id
     * @param product
     * @return Product，修改后的产品信息
     */
    @PutMapping("/products/{id}")
    public Object updateProductById(@PathVariable Integer id, @RequestBody Product product) {
        MallProductPo productPo = (MallProductPo) product;
        MallProduct mallProduct = new MallProduct(productPo);
        MallProduct newMallProduct = goodsService.updateProductById(id, mallProduct);
        if (newMallProduct == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(newMallProduct);
    }

    /**
     * 管理员删除商品下的某个产品信息
     * @param id
     * @return 无（ResponseUtil.ok()即可）
     */
    @DeleteMapping("/products/{id}")
    public Object deleteProductById(@PathVariable Integer id) {
        if (goodsService.deleteProductById(id)) {
            CommonResult.success("OK");
        }
        return CommonResult.failed();
    }

    /**
     * 新建/上架一个商品
     *
     * @param goods
     * @return Goods，即新建的一个商品
     */
    @PostMapping("/goods")
    public Object addGoods(@RequestBody Goods goods) {
        MallGoods mallGoods = goodsService.addGoods((MallGoods)goods);
        if (mallGoods == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(mallGoods);
    }

    /**
     * 根据id获取某个商品
     * @param id
     * @return GoodsVo，即商品的信息，此URL与WX端是同一个URL
     */
    @GetMapping("/goods/{id}")
    public Object getGoodsById(Integer id) {
        MallGoods mallGoods = goodsService.findGoodsById(id);
        if (mallGoods == null) {
            return CommonResult.failed();
        }
        //
        return null;
    }

    /**
     * 根据id更新商品信息
     * @param id
     * @param goods
     * @return Goods，修改后的商品信息
     */
    @PutMapping("/goods/{id}")
    public Object updateGoodsById(@PathVariable Integer id, @RequestBody Goods goods) {
        MallGoods mallGoods = goodsService.updateGoodsById(id, (MallGoods)goods);
        if (mallGoods == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(mallGoods);
    }

    /**
     * 根据id删除商品信息
     * @param id
     * @return  无（即ResponseUtil.ok()即可）
     */
    @DeleteMapping("/goods/{id}")
    public Object deleteGoodsById(@PathVariable Integer id) {
        if (goodsService.deleteGoodsById(id)) {
            return CommonResult.success("OK");
        }
        return CommonResult.failed();
    }

    /**
     * 获取商品分类信息
     * @param id
     * @return
     */
    @GetMapping("/categories/{id}/goods")
    public Object getCategoriesInfoById(@PathVariable Integer id) {
        List<MallGoods> mallGoodsList = goodsService.getCategoriesInfoById(id);
        if (mallGoodsList == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(mallGoodsList);
    }

    /**
     * 根据条件搜索商品
     *
     * @param goodsSn 商品的序列号
     * @param name 商品的名字
     * @param page 第几页
     * @param limit 一页多少
     * @return
     */
    @GetMapping("/goods")
    public Object listGoods(@RequestParam String goodsSn,
                            @RequestParam String name,
                            @RequestParam Integer page,
                            @RequestParam Integer limit) {
        // TODO: 不懂，商品序列号有相同的情况嘛？ name是指前缀查找嘛？

        return null;
    }

    /**
     * 根据条件搜索品牌
     * @return List<Brand>
     */
    @GetMapping("/admins/brands")
    public Object listBrand() {
        // TODO: emm,你条件呢？
        return null;
    }

    /**
     * 创建一个品牌
     * @param brand
     * @return
     */
    @PostMapping("/brands")
    public Object addBrand(@RequestBody Brand brand) {
        MallBrand mallBrand = goodsService.addBrand((MallBrand)brand);
        if (mallBrand == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(brand);
    }

    /**
     * 查看品牌详情,此API与商城端/brands/{id}完全相同
     * @param id
     * @return
     */
    @GetMapping("/brands/{id}")
    public Object getBrandById(@PathVariable Integer id) {
        MallBrand brand = goodsService.findBrandById(id);
        if (brand == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(brand);
    }

    /**
     * 修改单个品牌的信息
     * @param id
     * @param brand
     * @return
     */
    @PutMapping("/brands/{id}")
    public Object updateBrandById(@PathVariable Integer id, @RequestBody Brand brand) {
        MallBrand mallBrand = goodsService.updateBrandById(id, (MallBrand)brand);
        if (mallBrand == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(mallBrand);
    }

    /**
     * 删除一个品牌
     * @param id
     * @return
     */
    @DeleteMapping("/brands/{id}")
    public Object deleteBrandById(@PathVariable Integer id) {
        if (goodsService.deleteBrandById(id)) {
            return CommonResult.success("OK");
        }
        return CommonResult.failed();
    }

    /**
     * 查看所有的分类
     * @return
     */
    @GetMapping("/categories")
    public Object listGoodsCategory() {
        List<MallGoodsCategory> categoryList = goodsService.getGoodsCategory();
        return CommonResult.success(categoryList);
    }

    /**
     * 新建一个分类
     * @param goodsCategory
     * @return
     */
    @PostMapping("/categories")
    public Object addGoodsCategory(@RequestBody GoodsCategory goodsCategory) {
        MallGoodsCategory mallGoodsCategory = goodsService
                .addGoodsCategory((MallGoodsCategory) goodsCategory);
        if (mallGoodsCategory == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(mallGoodsCategory);
    }

    /**
     * 查看单个分类信息
     * @param id
     * @return
     */
    @GetMapping("/categories/{id}")
    public Object getGoodsCategoryById(@PathVariable Integer id) {
        MallGoodsCategory goodsCategory = goodsService.findGoodsCategoryById(id);
        if (goodsCategory == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(goodsCategory);
    }

    /**
     * 修改分类信息
     * @param id
     * @param goodsCategory
     * @return
     */
    @PutMapping("/categories/{id}")
    public Object updateGoodsCategoryById(@PathVariable Integer id,
                                          @RequestBody GoodsCategory goodsCategory) {
        MallGoodsCategory mallGoodsCategory = goodsService
                .updateGoodsCategoryById(id, (MallGoodsCategory) goodsCategory);
        if (mallGoodsCategory == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(goodsCategory);
    }

    /**
     * 删除单个分类
     * @param id
     * @param goodsCategory
     * @return
     */
    @DeleteMapping("/categories/{id}")
    public Object deleteGoodsCategory(@PathVariable Integer id,
                                      @RequestBody GoodsCategory goodsCategory) {
        if (goodsService.deleteGoodsCategoryById(id)) {
            return CommonResult.success("OK");
        }
        return CommonResult.failed();
    }

    /**
     * 查看所有一级分类
     * @return
     */
    @GetMapping("/categories/l1")
    public Object listOneLevelGoodsCategory() {
        List<MallGoodsCategory> mallGoodsCategoryList = goodsService.getOneLevelGoodsCategory();
        return CommonResult.success(mallGoodsCategoryList);
    }

//    /**
//     * 查看所有品牌
//     * @return List<Brand>
//     */
//    @GetMapping("/brands")
//    public Object listBrand() {
//        return null;
//    }
}

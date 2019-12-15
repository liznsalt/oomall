package xmu.oomall.controller;

import common.oomall.api.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.oomall.dao.ProductDao;
import xmu.oomall.domain.*;
import xmu.oomall.service.GoodsService;

import java.util.List;

/**
 * @author liznsalt
 * @author YaNai
 * @author ZYK
 */
@RestController
@RequestMapping("/goodsService")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 获取商品分类信息
     * @param id
     * @param page 第几页
     * @param limit 一页多少
     * @return List<GoodsPo> 即是商品的一个列表
     */
    @GetMapping("/categories/{id}/goods")
    public Object getCategoriesInfoById(@PathVariable Integer id,
                                        @RequestParam Integer page,
                                        @RequestParam Integer limit) {
        //TODO 分页
        List<GoodsPo> goodsPoList = goodsService.getCategoriesInfoById(id,page,limit);
        if (goodsPoList == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(goodsPoList);
    }

    /**
     * 根据条件搜索商品(用户）
     *
     * @param goodsSn 商品的序列号
     * @param goodsName 商品的名字
     * @param status 商品是否上架
     * @param page 第几页
     * @param limit 一页多少
     * @return List<GoodsPo> Goods的一个列表
     */
    @GetMapping("/goods")
    public Object listGoods(@RequestParam String goodsSn,
                            @RequestParam String name,
                            @RequestParam Integer status,
                            @RequestParam Integer page,
                            @RequestParam Integer limit) {
        // TODO: 不懂，商品序列号有相同的情况嘛？ name是指前缀查找嘛？
        //TODO: 不太明白标准组status类型是什么情况
        //TODO: 标准组未确定参数

        return null;
    }

    /**
     * 根据id获取某个商品
     * @param id
     * @return Goods
     */
    @GetMapping("/goods/{id}")
    public Object getGoodsById(@PathVariable Integer id) {
        Goods goods = goodsService.findGoodsById(id);
        if (goods == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(goods);
    }

    /**
     * 新建一个商品
     *
     * @param goods
     * @return GoodsPo，即新建的一个商品
     */
    @PostMapping("/goods")
    public Object addGoods(@RequestBody Goods goods) {
        if(goods.getId()!=null||goods.getGmtCreate()!=null||goods.getGmtModified()!=null||goods.getShareUrl()!=null||goods.getBeDeleted()!=null)
            return CommonResult.failed();
        GoodsPo goodsPo = goodsService.addGoods(goods).getGoodsPo();
        if (goodsPo == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(goodsPo);
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
     * 根据id更新商品信息
     * @param id
     * @param goodsPo
     * @return GoodsPo，修改后的商品信息
     */
    @PutMapping("/goods/{id}")
    public Object updateGoodsById(@PathVariable Integer id, @RequestBody GoodsPo goodsPo) {
        if(goodsPo.getId()!=null||goodsPo.getGmtCreate()!=null||goodsPo.getGmtModified()!=null||goodsPo.getShareUrl()!=null||goodsPo.getBeDeleted()!=null)
            return CommonResult.failed();
        Goods goods = goodsService.updateGoodsById(id, new Goods(goodsPo));
        if (goods == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(goods.getGoodsPo());
    }

    /**
     * 根据条件搜索商品(用户）
     *
     * @param goodsSn 商品的序列号
     * @param goodsName 商品的名字
     * @param status 商品是否上架
     * @param page 第几页
     * @param limit 一页多少
     * @return List<GoodsPo> Goods的一个列表
     */
    @GetMapping("/admin/goods")
    public Object listGoodsToAdmin() {
        //TODO: 标准组未确定参数

        return null;
    }

    /**
     * 管理员查看商品详情（含已下架）
     * @param id 商品ID
     * @return Goods
     */
    @GetMapping("/admin/goods/{id}")
    public Object getAllGoodsById(@PathVariable Integer id){
        Goods goods = goodsService.findAllGoodsById(id);
        if (goods == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(goods);
    }

    /**
     *管理员查询商品下的产品
     * @param id
     * @return List<ProductPo>，所属该商品的产品列表
     */
    @GetMapping("/goods/{id}/products")
    public Object listProductByGoodsId(@PathVariable Integer id) {
        Goods goods = goodsService.findGoodsById(id);
        if (goods == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(goods.getProductPoList());
    }

    /**
     * 管理员添加商品下的产品
     * @param id
     * @param productPo
     * @return GoodsPo，
     */
    @PostMapping("/goods/{id}/products")
    public Object addProductByGoodsId(@PathVariable Integer id, @RequestBody ProductPo productPo) {

        Product product = new Product(productPo);
        Product resultMallProduct = goodsService.addProductByGoodsId(id, product);
        if (resultMallProduct == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(goodsService.findGoodsById(id).getGoodsPo());
    }

    /**
     * 根据产品id查询产品信息
     * @param id
     * @return Product, 查询到的产品信息
     */
    @GetMapping("/products/{id}")
    public Object getProductById(@PathVariable Integer id){
        Product product = goodsService.findProductById(id);
        if(product == null){
            return CommonResult.failed();
        }
        return CommonResult.success(product);
    }

    /**
     * 管理员修改商品下的某个产品信息
     * @param id
     * @param productPo
     * @return ProductPo，修改后的产品信息
     */
    @PutMapping("/products/{id}")
    public Object updateProductById(@PathVariable Integer id, @RequestBody ProductPo productPo) {
        Product product = new Product(productPo);
        Product newProduct = goodsService.updateProductById(id, product);
        if (newProduct == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(newProduct.getProductPo());
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
     * 根据条件搜索品牌
     * @param page
     * @param limit
     * @return List<BrandPo>
     */
    @GetMapping("/admins/brands")
    public Object listBrand() {

        return null;
    }

    /**
     * 创建一个品牌
     * @param brandPo
     * @return brandPo
     */
    @PostMapping("/brands")
    public Object addBrand(@RequestBody BrandPo brandPo) {
        Brand brand = goodsService.addBrand(new Brand(brandPo));
        if (brand == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(brand.getBrandPo());
    }

    /**
     * 查看品牌详情,此API与商城端/brands/{id}完全相同
     * @param id
     * @return BrandPo
     */
    @GetMapping("/brands/{id}")
    public Object getBrandById(@PathVariable Integer id) {
        Brand brand = goodsService.findBrandById(id);
        if (brand == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(brand.getBrandPo());
    }

    /**
     * 查看品牌下的所有商品
     * @param id
     * @return List<GoodsPo> 商品列表
     */
    @GetMapping("/brands/{id}/goods")
    public Object getGoodsByBrandId(@PathVariable Integer id){
        List<GoodsPo> goodsPoList = goodsService.getGoodsByBrandId(id);
        if(goodsPoList==null){
            return CommonResult.failed();
        }
        return CommonResult.success(goodsPoList);
    }

    /**
     * 修改单个品牌的信息
     * @param id
     * @param brandPo
     * @return brandPo
     */
    @PutMapping("/brands/{id}")
    public Object updateBrandById(@PathVariable Integer id, @RequestBody BrandPo brandPo) {
        Brand brand = goodsService.updateBrandById(id, new Brand(brandPo));
        if (brand == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(brand.getBrandPo());
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
     * @param page 第几页
     * @param limit 一页多少
     * @return List<goodsCategoryPo>
     */
    @GetMapping("/categories")
    public Object listGoodsCategory(@RequestParam Integer page,
                                    @RequestParam Integer limit) {
        List<GoodsCategoryPo> categoryPoList = goodsService.getGoodsCategory(page,limit);
        return CommonResult.success(categoryPoList);
    }

    /**
     * 新建一个分类
     * @param goodsCategoryPo
     * @return goodsCategoryPo
     */
    @PostMapping("/categories")
    public Object addGoodsCategory(@RequestBody GoodsCategoryPo goodsCategoryPo) {
        GoodsCategory goodsCategory = goodsService
                .addGoodsCategory(new GoodsCategory(goodsCategoryPo));
        if (goodsCategory == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(goodsCategory.getGoodsCategoryPo());
    }

    /**
     * 查看单个分类信息
     * @param id
     * @return GoodsCategoryPo
     */
    @GetMapping("/categories/{id}")
    public Object getGoodsCategoryById(@PathVariable Integer id) {
        GoodsCategory goodsCategory = goodsService.findGoodsCategoryById(id);
        if (goodsCategory == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(goodsCategory.getGoodsCategoryPo());
    }

    /**
     * 修改分类信息
     * @param id
     * @param GoodsCategoryPo
     * @return GoodsCategoryPo
     */
    @PutMapping("/categories/{id}")
    public Object updateGoodsCategoryById(@PathVariable Integer id,
                                          @RequestBody GoodsCategoryPo goodsCategoryPo) {
        GoodsCategory goodsCategory = goodsService
                .updateGoodsCategoryById(id, new GoodsCategory(goodsCategoryPo));
        if (goodsCategory == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(goodsCategory.getGoodsCategoryPo());
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
     * @return List<GoodsCategory>
     */
    @GetMapping("/categories/l1")
    public Object listOneLevelGoodsCategory() {
        List<GoodsCategory> goodsCategoryList = goodsService.getOneLevelGoodsCategory();
        return CommonResult.success(goodsCategoryList);
    }


    /**
     *
     * @param id
     * @return GoodsCategory
     */
    @GetMapping("/categories/l1/{id}/l2")
    public Object getL2ByL1Id(@PathVariable Integer id){
        //TODO
    }
}

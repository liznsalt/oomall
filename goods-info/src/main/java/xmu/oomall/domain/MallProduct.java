package xmu.oomall.domain;

import xmu.oomall.domain.standard.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author liznsalt
 */
public class MallProduct {
    private MallProductPo realObj;

    public MallProduct(MallProductPo productPo) {
        realObj = productPo;
    }

    public MallProductPo getRealObj() {
        return realObj;
    }

    public void setRealObj(MallProductPo realObj) {
        this.realObj = realObj;
    }

    /****************************************************
     * TODO 真实操作
     ****************************************************/

    public List<MallProduct> getSubProducts() {
        return null;
    }

    public void setSubProducts(List<MallProduct> products) {

    }

    public Map<String, String> getSpecificationMap() {
        return null;
    }

    public void setSpecificationMap(Map<String, String> specificationMap) {

    }

    /****************************************************
     * PO操作
     ****************************************************/

    public Integer getId() {
        return realObj.getId();
    }

    public void setId(Integer id) {
        realObj.setId(id);
    }

    public Integer getGoodsId() {
        return realObj.getGoodsId();
    }

    public void setGoodsId(Integer goodsId) {
        realObj.setGoodsId(goodsId);
    }

    public String getProductIds() {
        return realObj.getProductIds();
    }

    public void setProductIds(String productIds) {
        realObj.setProductIds(productIds);
    }

    public String getPicUrl() {
        return realObj.getPicUrl();
    }

    public void setPicUrl(String picUrl) {
        realObj.setPicUrl(picUrl);
    }

    public String getSpecifications() {
        return realObj.getSpecifications();
    }

    public void setSpecifications(String specifications) {
        realObj.setSpecifications(specifications);
    }

    public BigDecimal getPrice() {
        return realObj.getPrice();
    }

    public void setPrice(BigDecimal price) {
        realObj.setPrice(price);
    }

    public Integer getSaftyStock() {
        return realObj.getSaftyStock();
    }

    public void setSaftyStock(Integer saftyStock) {
        realObj.setSaftyStock(saftyStock);
    }


    public LocalDateTime getGmtCreate() {
        return realObj.getGmtCreate();
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        realObj.setGmtCreate(gmtCreate);
    }

    public LocalDateTime getGmtModified() {
        return realObj.getGmtModified();
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        realObj.setGmtModified(gmtModified);
    }

    public Boolean getBeDeleted() {
        return realObj.getBeDeleted();
    }

    public void setBeDeleted(Boolean beDeleted) {
        realObj.setBeDeleted(beDeleted);
    }

    public MallGoods getGoods() {
        return realObj.getGoods();
    }

    public void setGoods(MallGoods goods) {
        realObj.setGoods(goods);
    }
}

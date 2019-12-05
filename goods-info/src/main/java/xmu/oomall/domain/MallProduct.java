package xmu.oomall.domain;

import xmu.oomall.domain.standard.Product;

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
}

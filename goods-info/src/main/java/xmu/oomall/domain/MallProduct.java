package xmu.oomall.domain;

import xmu.oomall.domain.standard.Product;

import java.util.List;
import java.util.Map;

/**
 * @author liznsalt
 */
public class MallProduct extends Product {
    private MallGoods goods;
    private List<MallProduct> products;
    private Map<String, String> specificationMap;
}

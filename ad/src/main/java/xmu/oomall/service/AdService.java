package xmu.oomall.service;

import tk.mybatis.mapper.entity.Example;
import xmu.oomall.domain.MallAd;

import java.util.List;

/**
 * @author liznsalt
 */
public interface AdService {
    List<MallAd> adminList(String adTitle, String adContent, Integer page, Integer limit);
    List<MallAd> userList();
    MallAd find(Integer id);
//    List<MallAd> list(Example example);

    MallAd add(MallAd ad);
    MallAd update(MallAd ad);
    boolean deleteById(Integer id);
}

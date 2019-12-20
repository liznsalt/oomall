package xmu.oomall.service;

import tk.mybatis.mapper.entity.Example;
import xmu.oomall.domain.MallAd;

import java.util.List;

/**
 * @author liznsalt
 */
public interface AdService {
    /**
     * 管理员条件查询
     * @param adTitle
     * @param adContent
     * @param page
     * @param limit
     * @return
     */
    List<MallAd> adminList(String adTitle, String adContent, Integer page, Integer limit);

    /**
     * 用户查看广告
     * @return
     */
    List<MallAd> userList();

    /**
     * 管理员根据id查看广告
     * @param id
     * @return
     */
    MallAd find(Integer id);
//    List<MallAd> list(Example example);

    /**
     * 管理员新增广告
     * @param ad
     * @return
     */
    MallAd add(MallAd ad);

    /**
     * 管理员修改广告信息
     * @param ad
     * @return
     */
    MallAd update(MallAd ad);

    /**
     * 管理员根据id删除广告
     * @param id
     * @return
     */
    boolean deleteById(Integer id);
}

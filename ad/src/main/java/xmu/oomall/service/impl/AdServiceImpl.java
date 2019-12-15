package xmu.oomall.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import common.oomall.util.MallDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import xmu.oomall.domain.MallAd;
import xmu.oomall.mapper.AdMapper;
import xmu.oomall.service.AdService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author liznsalt
 */

@Service
public class AdServiceImpl implements AdService {

    @Autowired
    private AdMapper adMapper;

    @Override
    public List<MallAd> adminList(String adTitle, String adContent,
                                  Integer page, Integer limit) {
        Example example = new Example(MallAd.class);
        Example.Criteria criteria = example.createCriteria();
        if (adTitle != null) {
            criteria.andLike("name", "%" + adTitle + "%");
        }
        if (adContent != null) {
            criteria.andLike("content", "%" + adContent + "%");
        }
        criteria.andCondition("is_deleted = 0");
        PageHelper.startPage(page, Math.min(limit, 100));
        return adMapper.selectByExample(example);
    }

    @Override
    public List<MallAd> userList() {
        Example example = new Example(MallAd.class);
        example.createCriteria()
                .andGreaterThanOrEqualTo("endTime", LocalDateTime.now().format(MallDateUtil.FORMATTER))
                .andLessThanOrEqualTo("startTime", LocalDateTime.now().format(MallDateUtil.FORMATTER))
                .andCondition("is_deleted = 0")
                .andCondition("is_enabled = 1");
        return adMapper.selectByExample(example);
    }

    @Override
    public MallAd find(Integer id) {
        return adMapper.selectByPrimaryKey(id);
    }

    @Override
    public MallAd add(MallAd ad) {
        ad.setGmtCreate(LocalDateTime.now());
        ad.setGmtModified(LocalDateTime.now());
        ad.setBeDeleted(false);
        if (ad.getBeEnabled() == null) {
            ad.setBeEnabled(true);
        }
        adMapper.insert(ad);
        return ad;
    }

    @Override
    public MallAd update(MallAd ad) {
        ad.setGmtModified(LocalDateTime.now());
        adMapper.updateByPrimaryKeySelective(ad);
        return ad;
    }

    @Override
    public boolean deleteById(Integer id) {
        MallAd ad = adMapper.selectByPrimaryKey(id);
        if (ad == null) {
            return false;
        } else {
            ad.setGmtModified(LocalDateTime.now());
            ad.setBeDeleted(true);
            adMapper.updateByPrimaryKeySelective(ad);
            return true;
        }
    }
}

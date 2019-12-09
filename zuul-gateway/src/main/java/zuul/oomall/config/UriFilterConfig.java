package zuul.oomall.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author liznsalt
 */
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "zuul")
public class UriFilterConfig {
    /**
     * 白名单
     */
    private List<String> whiteList;
    /**
     * 需要具有管理员权限
     */
    private List<String> needAdminAuthList;

    public List<String> getWhiteList() {
        return whiteList;
    }

    public void setWhiteList(List<String> whiteList) {
        this.whiteList = whiteList;
    }

    public List<String> getNeedAdminAuthList() {
        return needAdminAuthList;
    }

    public void setNeedAdminAuthList(List<String> needAdminAuthList) {
        this.needAdminAuthList = needAdminAuthList;
    }
}

package xmu.oomall.domain;

import org.apache.ibatis.type.Alias;
import standard.oomall.domain.Ad;

import javax.persistence.Table;

/**
 * @author liznsalt
 */
@Alias("mallAd")
@Table(name = "oomall_ad")
public class MallAd extends Ad {
}

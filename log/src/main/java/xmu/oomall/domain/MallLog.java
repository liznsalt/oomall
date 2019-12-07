package xmu.oomall.domain;

import org.apache.ibatis.type.Alias;
import standard.oomall.domain.Log;

import javax.persistence.Table;

/**
 * @author liznsalt
 */
@Alias("mallLog")
@Table(name = "log")
public class MallLog extends Log {
}

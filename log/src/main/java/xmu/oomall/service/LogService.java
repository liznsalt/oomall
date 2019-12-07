package xmu.oomall.service;

import tk.mybatis.mapper.entity.Example;
import xmu.oomall.domain.MallLog;

import java.util.List;

/**
 * @author liznsalt
 */
public interface LogService {
    /**
     * 添加日志
     * @param log 日志信息
     * @return 日志
     */
    MallLog addLog(MallLog log);

    /**
     * 得到所有日志
     * @return 日志列表
     */
    List<MallLog> getAllLogs();

    /**
     * 通过条件检索
     * @param example 检索条件
     * @return 日志列表
     */
    List<MallLog> findLogsByExample(Example example);
}

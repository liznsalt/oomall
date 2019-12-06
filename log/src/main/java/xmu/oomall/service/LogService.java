package xmu.oomall.service;

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
}

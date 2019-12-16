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

    /**
     * 通过条件检索
     * @param page 页数
     * @param limit 每页行数
     * @return 日志列表
     */
    List<MallLog> findLogsByCondition(Integer page, Integer limit);

    /**
     * 通过adminId
     * @param page 页数
     * @param limit 每页行数
     * @param adminId adminId
     * @return 日志列表
     */
    List<MallLog> findByAdminId(Integer page, Integer limit, Integer adminId);
}

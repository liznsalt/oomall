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
     * @param sort 根据什么排序
     * @param order 升序或者降序
     * @return 日志列表
     */
    List<MallLog> findLogsByCondition(Integer page, Integer limit, String sort, String order);
}

package xmu.oomall.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xmu.oomall.domain.MallLog;

import java.util.List;

/**
 * @author liznsalt
 */
@Component
@Mapper
public interface LogMapper {
    /**
     * 添加日志
     * @param log 日志信息
     * @return 行数
     */
    int addLog(MallLog log);

    /**
     * 得到所有日志
     * @return 日志列表
     */
    List<MallLog> getAllLogs();

    /**
     * 检索日志
     * @param id 日志ID
     * @return 日志信息
     */
    MallLog findLogById(Integer id);

    /**
     * 根据Example条件进行查询
     * @param page 页数
     * @param limit 每页行数
     * @return 日志列表
     */
    List<MallLog> findLogsByCondition(Integer page, Integer limit);

    /**
     * 根据Example条件进行查询
     * @param page 页数
     * @param limit 每页行数
     * @param adminId 管理员id
     * @return 日志列表
     */
    List<MallLog> findLogsByAdminId(Integer page, Integer limit, Integer adminId);
}

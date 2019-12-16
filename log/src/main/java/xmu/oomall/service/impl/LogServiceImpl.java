package xmu.oomall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.oomall.domain.MallLog;
import xmu.oomall.mapper.LogMapper;
import xmu.oomall.service.LogService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author liznsalt
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;

    @Override
    public MallLog addLog(MallLog log) {
        log.setGmtCreate(LocalDateTime.now());
        log.setGmtModified(LocalDateTime.now());
        logMapper.addLog(log);
        return log;
    }

    @Override
    public List<MallLog> getAllLogs() {
        return logMapper.getAllLogs();
    }

    @Override
    public List<MallLog> findLogsByCondition(Integer page, Integer limit) {
        return logMapper.findLogsByCondition(page, limit);
    }

    @Override
    public List<MallLog> findByAdminId(Integer page, Integer limit, Integer adminId) {
        return logMapper.findLogsByAdminId(page, limit, adminId);
    }
}

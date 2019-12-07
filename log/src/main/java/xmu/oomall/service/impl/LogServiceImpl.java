package xmu.oomall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import xmu.oomall.domain.MallLog;
import xmu.oomall.mapper.LogMapper;
import xmu.oomall.service.LogService;

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
        logMapper.addLog(log);
        return log;
    }

    @Override
    public List<MallLog> getAllLogs() {
        return logMapper.getAllLogs();
    }

    @Override
    public List<MallLog> findLogsByExample(Example example) {
        return logMapper.selectByExample(example);
    }
}

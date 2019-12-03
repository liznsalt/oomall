package xmu.oomall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.oomall.domain.TestTable;
import xmu.oomall.mapper.TestTableMapper;
import xmu.oomall.service.TestTableService;

/**
 * @author liznsalt
 */
@Service
public class TestTableServiceImpl implements TestTableService {
    @Autowired
    TestTableMapper testTableMapper;

    @Override
    public TestTable findTestTableById(Integer id) {
        return testTableMapper.findById(id);
    }
}

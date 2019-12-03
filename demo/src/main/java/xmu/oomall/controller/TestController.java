package xmu.oomall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import xmu.oomall.domain.TestTable;
import xmu.oomall.service.TestTableService;

/**
 * @author liznsalt
 */
@RestController
public class TestController {

    @Autowired
    private TestTableService testTableService;

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/test")
    @ResponseBody
    public TestTable findTestTableById(Integer id) {
        return testTableService.findTestTableById(id);
    }
}

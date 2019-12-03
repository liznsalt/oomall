package xmu.oomall.domain;

/**
 * @author liznsalt
 */
public class TestTable {
    private Integer id;
    private Integer testName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTestName() {
        return testName;
    }

    public void setTestName(Integer testName) {
        this.testName = testName;
    }

    @Override
    public String toString() {
        return "TestTable{" +
                "id=" + id +
                ", testName=" + testName +
                '}';
    }
}

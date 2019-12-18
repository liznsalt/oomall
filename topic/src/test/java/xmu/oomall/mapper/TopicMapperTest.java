package xmu.oomall.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xmu.oomall.domain.MallTopic;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TopicMapperTest {
    @Autowired
    private TopicMapper topicMapper;

    @Test
    public void addTopic() {
        MallTopic topic=new MallTopic();
        topic.setContent("父亲节");
        topic.setPicUrlList("{\"pictures\":[http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E6%AF%8D%E4%BA%B2%E8%8A%82&step_word=&hs=0&pn=16&spn=0&di=107250&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=undefined&cs=1102082523%2C3781714004&os=773283296%2C810038009&simid=3438615684%2C318272217&adpicid=0&lpn=0&ln=1601&fr=&fmq=1575902810445_R&fm=&ic=undefined&s=undefined&hd=undefined&latest=undefined&copyright=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fdealer2.autoimg.cn%2Fdealerdfs%2Fg25%2FM06%2F6D%2FA5%2F620x0_1_q87_autohomedealer__wKgHIFr1LcWAEISwAAHDybd1B78056.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bw7p5i54j_z%26e3Bv54_z%26e3BvgAzdH3F1jwsj6AzdH3Fda8bacAzdH3Fd889dm80n_z%26e3Bip4s&gsm=&rpstart=0&rpnum=0&islist=&querylist=&force=undefined]}");
        topic.setGmtCreate(LocalDateTime.now());
        topic.setGmtModified(LocalDateTime.now());
        topic.setBeDeleted(false);
        topicMapper.addTopic(topic);
    }

    @Test
    public void deleteTopicById() {


    }

    @Test
    public void updateTopic() {
    }

    @Test
    public void findTopicById() {
        topicMapper.findTopicById(1);
    }

    @Test
    public void findNotDeletedTopicById() {
    }

    @Test
    public void findTopicsByCondition() {
    }

    @Test
    public void findNotDeletedTopicsByCondition() {
    }
}

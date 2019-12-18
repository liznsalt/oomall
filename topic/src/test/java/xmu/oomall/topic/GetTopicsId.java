package xmu.oomall.topic;

import org.junit.Test;

import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.http.*;

import org.springframework.test.context.junit4.SpringRunner;

import standard.oomall.domain.Topic;

import common.oomall.util.JacksonUtil;



import java.net.URI;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GetTopicsId {
    @Value("http://localhost:8084/topics/{id}")
    String url;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void test1() throws Exception{
        /* 设置请求头部*/
        URI uri = new URI(url.replace("{id}", "1"));
        HttpHeaders httpHeaders = testRestTemplate.headForHeaders(uri);
        httpHeaders.add("userId","1");
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        /*exchange方法模拟HTTP请求*/
        ResponseEntity<String> response = testRestTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        /*取得响应体*/
        String body = response.getBody();
        Integer errno = JacksonUtil.parseInteger(body, "errno");
        assertEquals(new Integer(0), errno);
        /*assert判断*/
        Topic topic = JacksonUtil.parseObject(body, "data", Topic.class);
        assertEquals(new Integer(1), topic.getId());
        assertEquals("母亲节", topic.getContent());
        assertEquals("http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E6%AF%8D%E4%BA%B2%E8%8A%82&step_word=&hs=0&pn=16&spn=0&di=107250&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=undefined&cs=1102082523%2C3781714004&os=773283296%2C810038009&simid=3438615684%2C318272217&adpicid=0&lpn=0&ln=1601&fr=&fmq=1575902810445_R&fm=&ic=undefined&s=undefined&hd=undefined&latest=undefined&copyright=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fdealer2.autoimg.cn%2Fdealerdfs%2Fg25%2FM06%2F6D%2FA5%2F620x0_1_q87_autohomedealer__wKgHIFr1LcWAEISwAAHDybd1B78056.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bw7p5i54j_z%26e3Bv54_z%26e3BvgAzdH3F1jwsj6AzdH3Fda8bacAzdH3Fd889dm80n_z%26e3Bip4s&gsm=&rpstart=0&rpnum=0&islist=&querylist=&force=undefined", topic.getPicUrlList());
    }
}

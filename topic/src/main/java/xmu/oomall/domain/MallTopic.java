package xmu.oomall.domain;

import org.apache.ibatis.type.Alias;
import standard.oomall.domain.Topic;
import standard.oomall.domain.TopicPo;

import java.util.Arrays;
import java.util.List;

/**
 * @author liznsalt
 */
@Alias("mallTopic")
public class MallTopic extends Topic {
    public MallTopic(){}
    public MallTopic(TopicPo topicPo)
    {
        this.setId(topicPo.getId());
        this.setGmtModified(topicPo.getGmtModified());
        this.setGmtCreate(topicPo.getGmtCreate());
        this.setPicUrlList(topicPo.getPicUrlList());
        this.setBeDeleted(topicPo.getBeDeleted());
        this.setContent(topicPo.getContent());
        String[] arr=this.getPicUrlList().split(",");
        if(arr[0].startsWith("{\"pictures\":[")){
            arr[0]=arr[0].replace("{\"pictures\":[","");
        }
        if(arr[arr.length-1].endsWith("]}")){
            arr[arr.length-1]=arr[arr.length-1].replace("]}","");
        }
        List<String> list= Arrays.asList(arr);
        this.setPictures(list);
    }
    public TopicPo toTopicPo(){
        TopicPo topicPo=new TopicPo();
        topicPo.setBeDeleted(this.getBeDeleted());
        topicPo.setContent(this.getContent());
        topicPo.setGmtCreate(this.getGmtCreate());
        topicPo.setId(this.getId());
        topicPo.setGmtModified(this.getGmtModified());
        topicPo.setPicUrlList(this.getPicUrlList());
        return topicPo;
    }
    public MallTopic picturesset(){
        String[] arr=this.getPicUrlList().split(",");
        if(arr[0].startsWith("{\"pictures\":[")){
            arr[0]=arr[0].replace("{\"pictures\":[","");
        }
        if(arr[arr.length-1].endsWith("]}")){
            arr[arr.length-1]=arr[arr.length-1].replace("]}","");
        }
        List<String> list= Arrays.asList(arr);
        this.setPictures(list);
        return this;
    }
}

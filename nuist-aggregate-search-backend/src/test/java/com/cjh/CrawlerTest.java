package com.cjh;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cjh.model.entity.Picture;
import com.cjh.service.PostService;
import com.cjh.model.entity.Post;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class CrawlerTest {

    @Resource
    private PostService postService;

    @Test
    void testFetchPassage(){

        // 1.获取数据
        String json = "{\"current\":1,\"pageSize\":8,\"sortField\":\"createTime\",\"sortOrder\":\"descend\",\"category\":\"文章\",\"reviewStatus\":1}";
        String url = "https://www.code-nav.cn/api/post/search/page/vo";
        String result2 = HttpRequest
                .post(url)
                .body(json)
                .execute()
                .body();
//        System.out.println(result2);
        // 2.JSON转对象
        Map<String, Object> map = JSONUtil.toBean(result2, Map.class);
        JSONObject data = (JSONObject) map.get("data");
        JSONArray records =(JSONArray) data.get("records");
        List<Post> postList = new ArrayList<>();
        for (Object record: records ){
         JSONObject  tempRecord =(JSONObject) record ;
            Post post = new Post();

            post.setTitle(tempRecord.getStr("title"));
            post.setContent(tempRecord.getStr("content"));
            JSONArray tags = (JSONArray) tempRecord.get("tags");
            List<String> tagList = tags.toList(String.class);
            post.setTags(JSONUtil.toJsonStr(tagList));
            post.setThumbNum(0);
            post.setFavourNum(0);
            post.setUserId(1L);

            postList.add(post);
        }
        boolean b = postService.saveBatch(postList);
        System.out.println(b);
    }

    @Test
    List<Picture> testFetchPicture() throws IOException {

        int current = 2;
        String url = "https://www.bing.com/images/search?q=海贼王&first=" + current;
        Document doc = Jsoup.connect(url).get();
        List<Picture> list = new ArrayList<>();
        Elements elements = doc.select(".iuscp.isv");
        for (Element element : elements) {
            Picture picture = new Picture();
           // 取图片地址（murl）
            String m = element.select(".iusc").get(0).attr("m");

            String title = element.select(".inflnk").get(0).attr("aria-label");
            Map map = JSONUtil.toBean(m, Map.class);
            String murl = (String) map.get("murl");
            picture.setTitle(title);
            picture.setUrl(url);
            list.add(picture);
        }
        return list;
    }
}

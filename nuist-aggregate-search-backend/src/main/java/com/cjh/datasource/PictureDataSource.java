package com.cjh.datasource;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjh.common.ErrorCode;
import com.cjh.exception.BusinessException;
import com.cjh.model.entity.Picture;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *  图片服务实现类
 */
@Service
@Slf4j
public class PictureDataSource implements DataSource<Picture> {
    @Override
    public Page<Picture> doSearch(String searchText, long pageNum, long pagesize) {
        long current = (pageNum-1)*pagesize;
        String url = String.format("https://www.bing.com/images/search?q=%s&first=%s",searchText,current);
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"数据获取异常");
        }
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
            picture.setUrl(murl);
            list.add(picture);
            if(list.size()>=pagesize){
                break;
            }

        }
        Page<Picture> picturePage = new Page<>(pageNum,pagesize);
        picturePage.setRecords(list);
        return picturePage;
    }
}

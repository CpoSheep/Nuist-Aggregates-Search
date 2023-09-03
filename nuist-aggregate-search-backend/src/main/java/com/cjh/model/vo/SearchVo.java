package com.cjh.model.vo;


import com.cjh.model.entity.Picture;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SearchVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<UserVO> userVOList;

    private List<PostVO> postVOList;

    private List<Picture> pictureList;

    private List<Object> dataList;
}

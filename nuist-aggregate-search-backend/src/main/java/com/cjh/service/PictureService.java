package com.cjh.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjh.model.entity.Picture;

public interface PictureService {

  Page<Picture> searchPicture(String searchText, long pageNum, long pagesize);
}

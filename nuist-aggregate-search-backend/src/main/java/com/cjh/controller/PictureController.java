package com.cjh.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjh.model.entity.Picture;
import com.cjh.common.BaseResponse;
import com.cjh.common.ErrorCode;
import com.cjh.common.ResultUtils;
import com.cjh.exception.ThrowUtils;
import com.cjh.model.dto.picture.PictureQueryRequest;
import com.cjh.service.PictureService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 帖子接口
 *
 * @author cjh
 */
@RestController
@RequestMapping("/picture")
@Slf4j
public class PictureController {

    @Resource
    private PictureService pictureService;


    /**
     * 分页获取列表（封装类）
     *
     * @param
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<Picture>> listPictureByPage(@RequestBody PictureQueryRequest pictureQueryRequest,
                                                         HttpServletRequest request) {
        long current = pictureQueryRequest.getCurrent();
        long size = pictureQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);

        String searchText = pictureQueryRequest.getSearchText();

        Page<Picture> picturePage = pictureService.searchPicture(searchText,current,size);

        return ResultUtils.success(picturePage);
    }


}


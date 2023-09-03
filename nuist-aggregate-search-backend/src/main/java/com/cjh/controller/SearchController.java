package com.cjh.controller;

import com.cjh.manager.SearchFacade;
import com.cjh.common.BaseResponse;
import com.cjh.common.ResultUtils;
import com.cjh.model.dto.search.SearchRequest;
import com.cjh.model.vo.SearchVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/search")
@Slf4j
public class SearchController {

    @Resource
    private SearchFacade searchFacade;
    /**
     * 分页获取列表（封装类）
     *
     * @param
     * @param
     * @return
     */
    @PostMapping("/all")
    public BaseResponse<SearchVo> searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request){
        return ResultUtils.success(searchFacade.searchAll(searchRequest,request));
    }
}

package com.cjh.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjh.datasource.*;
import com.cjh.model.enums.SearchTypeEnum;
import com.cjh.common.ErrorCode;
import com.yupi.springbootinit.datasource.*;
import com.cjh.exception.ThrowUtils;
import com.cjh.model.dto.post.PostQueryRequest;
import com.cjh.model.dto.search.SearchRequest;
import com.cjh.model.dto.user.UserQueryRequest;
import com.cjh.model.entity.Picture;
import com.cjh.model.vo.PostVO;
import com.cjh.model.vo.SearchVo;
import com.cjh.model.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 门面模式
 * 搜索门面
 */
@Component
@Slf4j
public class SearchFacade {

    @Resource
    private PostDataSource postDataSource;

    @Resource
    private PictureDataSource pictureDataSource;

    @Resource
    private UserDataSource userDataSource;

    @Resource
    private DataSourceRegister dataSourceRegister;

    public SearchVo searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request){
        String type = searchRequest.getType();
        SearchTypeEnum searchTypeEnum = SearchTypeEnum.getEnumByValue(type);
        ThrowUtils.throwIf(StringUtils.isBlank(type), ErrorCode.PARAMS_ERROR);
        long current = searchRequest.getCurrent();
        long pageSize = searchRequest.getPageSize();
        String searchText = searchRequest.getSearchText();
        if (searchTypeEnum == null){
            Page<Picture> picturePage = pictureDataSource.doSearch(searchText,current,pageSize);

            UserQueryRequest userQueryRequest = new UserQueryRequest();
            userQueryRequest.setUserName(searchText);
            Page<UserVO> userVOPage = userDataSource.doSearch(searchText,current,pageSize);

            PostQueryRequest postQueryRequest= new PostQueryRequest();
            postQueryRequest.setSearchText(searchText);
            Page<PostVO> postVOPage = postDataSource.doSearch(searchText,current,pageSize);

            SearchVo searchVo = new SearchVo();
            searchVo.setPictureList(picturePage.getRecords());
            searchVo.setPostVOList(postVOPage.getRecords());
            searchVo.setUserVOList(userVOPage.getRecords());

            return searchVo;
        }else {
            SearchVo searchVo = new SearchVo();
            DataSource dataSource = dataSourceRegister.getDataSourceByType(type);
            Page page = dataSource.doSearch(searchText, current, pageSize);
            searchVo.setDataList(page.getRecords());
            return searchVo;
        }



    }
}

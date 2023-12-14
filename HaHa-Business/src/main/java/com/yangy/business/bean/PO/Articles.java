package com.yangy.business.bean.PO;

import com.yangy.common.bean.PageQuery;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * tb_articles
 * @author 
 */
@Data
public class Articles extends PageQuery implements Serializable{
    private Integer id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章内容
     */
    private String content;

	private Integer coverImgId;
	
    /**
     * 创作者
     */
    private Integer creator;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 文章状态：1=草稿，2=已发布，3=已下架，4=已删除
     */
    private Integer status;

    /**
     * 文章可见：1=公开所有人可见，2=仅个人可见，3=仅指定好友可见
     */
    private Integer view;

    /**
     * 发布时间
     */
    private Date publishTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}
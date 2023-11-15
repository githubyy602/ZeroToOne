package com.yangy.file.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * tb_file
 * @author 
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class File implements Serializable {
    private Integer id;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件大小
     */
    private String fileSize;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 文件路径
     */
    private String path;

    /**
     * 文件访问url
     */
    private String url;

    /**
     * 是否加密：1=是，0=否
     */
    private Integer encrypted;

    /**
     * 是否已删除：1=是，0=否
     */
    private Integer deleted;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}
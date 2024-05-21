package com.yangy.business.constant;

/**
 * @author Yangy
 * @create 2024-05-17 17:02
 */
public enum ArticleInteractEnum {
    VIEW(1,"查看"),
    LIKE(2,"喜欢收藏"),
    SHARE(3,"分享"),
    DISLIKE(4,"不喜欢");

    private int type;

    private String desc;

    ArticleInteractEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}

package com.yc.favorite.biz;

import com.yc.favorite.bean.TagFavorite;

/*
 * Favorite
 * 1  淘宝   taobao.com  好网站         购物，生活
 * 2  网易   163.com     常用网站    门户，军事，生活
 * 
 * tagfavorite(中间表：tid fid)       tag
 * 1 1                             1购物1
 * 2 1                             2生活2
 * 3 2                             3门户1
 * 4 2                             4军事
 * 2 2
 * 
 */
//添加链接到 favorite表
//拆分分类 ftags
//直接修改分类数量
//如果返回结果为0  表示没有修改到对应记录，那么新增
//如果返回结果为1 表示修改数量成功
//根据分类tid 和链接fid，向中间表写入记录
public class TagFavoriteBiz {

	public void addFavorite(TagFavorite tf) {
		
		
	}
	public void updateFavorite(TagFavorite tf) {
		
	}
}

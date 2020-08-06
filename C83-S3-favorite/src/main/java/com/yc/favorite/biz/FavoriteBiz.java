package com.yc.favorite.biz;

import org.apache.ibatis.session.SqlSession;

import com.yc.favorite.bean.Favorite;
import com.yc.favorite.bean.Tag;
import com.yc.favorite.bean.TagFavorite;
import com.yc.favorite.dao.FavoriteMapper;
import com.yc.favorite.dao.TagFavoriteMapper;
import com.yc.favorite.dao.TagMapper;
import com.yc.favorite.util.MyBatisHelper;

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

public class FavoriteBiz {

	public void addFavorite(Favorite f) {
		SqlSession session=MyBatisHelper.openSession();
		FavoriteMapper fm=session.getMapper(FavoriteMapper.class);
		TagMapper tm=session.getMapper(TagMapper.class);
		TagFavoriteMapper tfm=session.getMapper(TagFavoriteMapper.class);
		
		try{
			//添加链接到 favorite表
			fm.insert(f);
			//拆分分类 ftags
			String []tags=f.getFtags().split("[,，；;]");
			for(String tag:tags) {
			
			Tag tagObj=new Tag();
			//直接修改分类数量
			if(tm.updateCount(tag)==0) {
				//如果返回结果为0  表示没有修改到对应记录，那么新增
			
				tagObj.setTname(tag);
			tm.insert(tagObj);
			}else {
				//如果返回结果为1 表示修改数量成功
				//并且查询数据库tag对象
				tagObj=tm.selectByName(tag);
			}
			//根据分类tid 和链接fid，向中间表写入记录
			TagFavorite tf=new TagFavorite();
			tf.setTid(tagObj.getTid());
			tf.setFid(f.getFid());;
			tfm.insert(tf);
			session.commit();
		}	
	}catch(Exception e) {
		e.printStackTrace();
		session.rollback();
	}finally {
		session.close();
	}
	}
	public void updateFavorite(Favorite f) {
		
	}
}

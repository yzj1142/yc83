package com.yc.favorite.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.yc.favorite.bean.Tag;

public interface TagMapper {
	@Insert("insert into tag values(null,#{tname},1)")
	@Options(useGeneratedKeys=true,
	keyColumn="tid",keyProperty="tid")
	int insert(Tag tf);
	
	  @Update("update tag set tcount=tcount+1 where tname=#{tname} ")
	int updateCount(String tag);

	  @Select("select * from tag where tname=#{tname}")
	Tag selectByName(String tag);
}

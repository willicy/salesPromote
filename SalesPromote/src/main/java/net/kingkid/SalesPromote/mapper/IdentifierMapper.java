package net.kingkid.SalesPromote.mapper;

/**
 * 处理id数据的持久层
 */

public interface IdentifierMapper {

	
	/**
	 * 根据表名查询id
	 * @param tablename 表名
	 * @return 匹配的id
	 */
	Integer findIdentifierByTablename(String tablename);
	
	/**
	 * 根据更新id
	 * @param tablename 表名
	 * @return 回成功的行数
	 */
	Integer updateIdentifiers(String tablename);
	

	
}




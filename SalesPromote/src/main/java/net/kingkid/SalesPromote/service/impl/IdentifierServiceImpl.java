package net.kingkid.SalesPromote.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.kingkid.SalesPromote.mapper.IdentifierMapper;
import net.kingkid.SalesPromote.service.IIdentifierService;

/**
 * 处理id的业务层实现类
 */
@Service
public class IdentifierServiceImpl  extends BaseService 
	implements IIdentifierService {

	@Autowired
	private IdentifierMapper identifierMapper;

	@Override
	public Integer getIdForCreate(String tablename) {
		identifierMapper.updateIdentifiers(tablename);
		
		return identifierMapper.findIdentifierByTablename(tablename);
	}


	


	
	
}






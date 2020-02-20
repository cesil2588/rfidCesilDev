package com.systemk.spyder.Config.MultiDataBase;

import org.hibernate.dialect.SQLServerDialect;
import org.hibernate.type.StringType;
import static java.sql.Types.NVARCHAR;
 
/**
 * NVARCHAR 타입에 대한 Mapping Custom
 * @author 최의선
 * 
 */
public class CustomSQLServerDialect extends SQLServerDialect{

	public CustomSQLServerDialect(){
		super();
		registerHibernateType(NVARCHAR, StringType.INSTANCE.getName());
	}
}

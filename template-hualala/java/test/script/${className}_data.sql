<#assign className = table.className>
<#assign classNameFirstLower = table.classNameFirstLower> 
INSERT INTO ${table.sqlName} 
	(
		<#list table.columns as column>${column.sqlName}<#if column_has_next>,</#if></#list>
	) VALUES 
	(<#list table.columns as column><#if column.jdbcType=='VARCHAR'>'str'</#if><#if column.jdbcType=='TINYINT'>1</#if><#if column.jdbcType=='BIGINT'>123456</#if><#if column.jdbcType=='INTEGER'>123</#if><#if column_has_next>,</#if></#list>);


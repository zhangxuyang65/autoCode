<#assign className = table.className>
<#assign classNameFirstLower = table.classNameFirstLower> 
drop table ${table.sqlName}  if exists;
CREATE TABLE ${table.sqlName}(
	<#list table.columns as column>
	${column.sqlName} ${column.jdbcType} <#if column.sqlName=='id' > PRIMARY KEY NOT </#if> NULL <#if column_has_next>,</#if>
	</#list>
);
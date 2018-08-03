<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${package_vo};

import lombok.Data;
import java.io.Serializable;
<#list table.columns as column>
<#if column.jdbcType=='DECIMAL'>
import java.math.BigDecimal;
</#if>
</#list>

<#include "/java_hualala_imports.include">
@Data
public class ${className}Vo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5669557348305980269L;

	<#list table.columns as column>
	/** ${column.columnAlias!}  db_column: ${column.sqlName} */	
	private ${column.javaType} ${column.columnNameLower}<#if column.columnNameLower=='isDeleted'>=0</#if>;
	<#if column.jdbcType=='INTEGER'>
	private String str${column.columnName};
	</#if>
	</#list>
}
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${package_query};

import com.alibaba.fastjson.JSON;
import lombok.Data;
import java.math.BigDecimal;
import ${package_base_query};

<#list table.columns as column>
<#if column.jdbcType=='DECIMAL'>
</#if>
</#list>

<#include "/java_hualala_imports.include">
@Data
public class ${className}Query extends QueryParam {
    private static final long serialVersionUID = 5454155825314635342L;

	<#list table.columns as column>
	/** ${column.columnAlias!}  db_column: ${column.sqlName} */
	private ${column.javaType} ${column.columnNameLower}<#if column.columnNameLower=='isDeleted'>=0</#if>;
	</#list>

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}

<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${package_query};

import com.alibaba.fastjson.JSON;

import ${package_base_query};

<#list table.columns as column>
<#if column.jdbcType=='DECIMAL'>
import java.math.BigDecimal;
</#if>
</#list>

<#include "/java_hualala_imports.include">
public class ${className}Query extends QueryParam {
    private static final long serialVersionUID = 5454155825314635342L;

	<#list table.columns as column>
	/** ${column.columnAlias!}  db_column: ${column.sqlName} */	
	private ${column.javaType} ${column.columnNameLower}<#if column.columnNameLower=='isDeleted'>=0</#if>;
	</#list>
    
	public ${className}Query(){
	}
	public ${className}Query(Long id){
	    this.id = id;
	}
	<#list table.columns as column>
	<#if column.sqlName=='code'>
	public ${className}Query(String code){
	    this.code = code;
	}
	</#if>
	</#list>
	
	<@generateJavaColumns/>
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}

<#macro generateJavaColumns>
	<#list table.columns as column>
		<#if column.isDateTimeColumn>
	public String get${column.columnName}String() {
		return DateConvertUtils.format(get${column.columnName}(), FORMAT_${column.constantName});
	}
	public void set${column.columnName}String(String value) {
		set${column.columnName}(DateConvertUtils.parse(value, FORMAT_${column.constantName},${column.javaType}.class));
	}
	
		</#if>	
	public ${column.javaType} get${column.columnName}() {
		return this.${column.columnNameLower};
	}
	public void set${column.columnName}(${column.javaType} value) {
		this.${column.columnNameLower} = value;
	}
	
	</#list>
</#macro>
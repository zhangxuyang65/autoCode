<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   
package ${package_mapper};

import org.apache.ibatis.annotations.Mapper;

import ${package_base_mapper}.BaseMapper;
import ${package_model}.${className};
import ${package_query}.${className}Query;

<#include "/java_hualala_imports.include">
@Mapper
public interface ${className}Mapper extends BaseMapper<${className},${className}Query>{

}

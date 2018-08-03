<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${package_manager};

import ${package_base_manager}.BaseManager;
import ${package_model}.${className};
import ${package_query}.${className}Query;

<#include "/java_hualala_imports.include">
public interface ${className}Manager extends BaseManager<${className},${className}Query>{

}

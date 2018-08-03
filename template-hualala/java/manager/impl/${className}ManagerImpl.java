<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${package_manager_impl};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ${package_base_manager_impl}.BaseManagerImpl;
import ${package_base_mapper}.BaseMapper;
import ${package_manager}.${className}Manager;
import ${package_mapper}.${className}Mapper;
import ${package_model}.${className};
import ${package_query}.${className}Query;

<#include "/java_hualala_imports.include">
@Component
public class ${className}ManagerImpl extends BaseManagerImpl<${className},${className}Query> implements ${className}Manager{
	@SuppressWarnings("unused")
	private Log logger = LogFactory.getLog(${className}ManagerImpl.class);
    @Autowired
    private ${className}Mapper ${classNameLower}Mapper;

    @Override
    public BaseMapper<${className},${className}Query> getMapper() {
        return this.${classNameLower}Mapper;
    }

}

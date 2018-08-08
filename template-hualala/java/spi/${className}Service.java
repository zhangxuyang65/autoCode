<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${package_service};

import java.util.List;
import java.util.Set;

import com.hualala.mes3.model.ServiceResult;
import com.github.pagehelper.PageInfo;

import ${package_model}.${className};
import ${package_query}.${className}Query;

<#include "/java_hualala_imports.include">
public interface ${className}Service{
	/**
	 * if id==null goto insert else goto update
	 * @param t
	 * @return 
	 */
    ServiceResult<${className}>		insertOrUpdate(${className} t);
    /**
     * if index_0.id==null goto insert else goto update
     * @param list
     * @return
     */
    ServiceResult<Integer>		insertOrUpdate(Long groupID,List<${className}> list);
    /**
     * id!=null || ( ids!=null and ids.size()>0 ) goto delete 
     * @param query
     * @return
     */
    ServiceResult<Integer>		delete(${className}Query query);
    ServiceResult<Integer>		delete(long id);
    ServiceResult<Integer>		delete(Long groupID,Set<Long> ids);
    /**
     * by id goto query
     * @param id
     * @return 
     */
    ServiceResult<${className}>		query(long id);
    /**
     * by ids goto query
     * @param ids
     * @return
     */
    ServiceResult<List<${className}>>	query(Long groupID,Set<Long> ids);
        ServiceResult<PageInfo<${className}>>	query(${className}Query query);
    
    /**
     * 同步数据
     *
     * @author 2015年11月9日
     * @param dataList
     * @return
     */
    public ServiceResult<Long> sync(Long groupID,List<${className}> dataList);
    /**
     * 增加
     * 
     * @author LiRuigui 2015年11月20日
     * @param t
     *            要增加的对象
     * @return 对象
     */
    public ServiceResult<${className}> insert(${className} t);

    /**
     * 删除
     * 
     * @author LiRuigui 2015年11月20日
     * @param t
     *            要删除的对象
     * @return 0:失败;1:成功
     */
    public ServiceResult<Integer> delete(${className} t);

    /**
     * 更新
     *
     * @author LiRuigui 2015年11月20日
     * @param t
     *            要更新的对象
     * @return 0:失败;1:成功
     */
    public ServiceResult<Integer> update(${className} t);

    /**
     * 按主键查询
     * 
     * @author LiRuigui 2015年11月20日
     * @param id
     *            过虑参数
     * @return 对象
     */
    public ServiceResult<${className}> selectByPrimaryKey(long id);

    /**
     * 查询条数
     * 
     * @author LiRuigui 2015年11月20日
     * @param query
     * @return
     */
    public ServiceResult<Long> count(${className}Query query);

    /**
     * 按表XXX列查询集合
     * 
     * @author LiRuigui 2015年11月20日
     * @param query
     *            参数内必需字段：columnName(数据库表的列名);list(要查询的数据数组 )
     * @return 集合
     */
    public ServiceResult<List<${className}>> selectByColumn(${className}Query query);

    /**
     * 查询集合
     * 
     * @author LiRuigui 2015年11月20日
     * @param query
     *            查询时的过虑条件
     * @return 集合
     */
    public ServiceResult<List<${className}>> queryList(${className}Query query);

    /**
     * 批量增加
     * 
     * @author LiRuigui 2015年11月20日
     * @param list
     *            要增加的对象集合
     * @return 成功增加的行数
     */
    public ServiceResult<Integer> batchInsert(Long groupID,List<${className}> list);


    /**
     * 批量删除
     * 
     * @author LiRuigui 2015年11月20日
     * @param query
     *            要删除的对象主键的集合
     * @return 0:失败;1:成功
     */
    public ServiceResult<Integer> batchDelete(${className}Query query);

    /**
     * 批量更新
     * 
     * @author LiRuigui 2015年11月20日
     * @param list
     *            要新的对象集合
     * @return 0:失败;1:成功
     */
    public ServiceResult<Integer> batchUpdate(Long groupID,List<${className}> list);
}

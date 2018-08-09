<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
package ${basepackage_controller};

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hualala.mes3.util.OptCodeConstant;
import com.github.pagehelper.PageInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.hualala.supplychain.annotation.RequestAttribute;
import com.hualala.mes3.model.ServiceResult;

import ${package_model}.${className};
import ${package_query}.${className}Query;
import ${package_service}.${className}Service;
import ${package_vo}.${className}Vo;

import com.hualala.passport.common.model.User;
import com.hualala.mes3.exception.InvalidParamException;
import com.hualala.mes3.model.DataResult;
import com.hualala.mes3.model.ErrorResult;
import com.hualala.mes3.model.ExcelBean;
import com.hualala.mes3.model.ListResult;
import com.hualala.mes3.util.ControllerUtil;
import com.hualala.mes3.util.DateUtil;
import com.hualala.mes3.util.ExcelUtil;

<#include "/java_hualala_imports.include">
@SuppressWarnings({"serial","unused", "unchecked", "rawtypes" })
@RestController("${classNameLower}Controller")
@RequestMapping(value="/${classNameLower}",produces="application/json;charset=UTF-8")
public class ${className}Controller {
	private static final String  DATA_ITEM_STATUS="status";
	private Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private ${className}Service ${classNameLower}Service;
	

	/**
	 * 增加
	 * @param strJson
	 * @return
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, ?> insert(HttpServletResponse response,@RequestBody String strJson,@RequestAttribute("user") User user) {
		log.info("insert param:"+strJson);
		List<${className}> listParam=ControllerUtil.strJsonToArray(strJson,${className}.class);
		this.filterInsertParams(listParam,user);
		ServiceResult<Integer> sr=this.${classNameLower}Service.insertOrUpdate(user.getGroupId(),listParam);
		log.info("insert result:"+JSON.toJSONString(sr));
		if(sr.getSuccess()){
			return new DataResult(sr.getErrorCode(), sr.getMessage()).toMap();
		}else{
			return new ErrorResult(sr.getErrorCode(),sr.getMessage()).toMap();
		}
	}
	private void filterInsertParams(List<${className}> listParam,User user){
		for(${className} rom : listParam){
			if(rom.getGroupId()==null||rom.getGroupId()==0){
				throw new InvalidParamException(OptCodeConstant.ERROR_PARAM_FORMAT.getCode(),"集团groupID为空，不能操作数据！");
			}
			rom.setAction(1);
			rom.setActionBy(user.getUserName());
			rom.setActionTime(DateUtil.getActionTime());
			rom.setCreateBy(user.getUserName());
			rom.setCreateTime(DateUtil.getActionTime());
		}
	}
	/**
	 * 删除
	 * @param strJson
	 * @return
	 */
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, ?> del(HttpServletResponse response,@RequestBody String strJson,@RequestAttribute("user") User user) {
		log.info("del param:"+strJson);
		${className}Query query = JSON.parseObject(strJson,${className}Query.class);
		if(query.getGroupId()==null||query.getGroupId()==0){
			throw new InvalidParamException(OptCodeConstant.ERROR_PARAM_FORMAT.getCode(),"集团groupID为空，不能操作数据！");
		}
		query.setActionBy(user.getUserName());
		query.setActionTime(DateUtil.getActionTime());
		ServiceResult<Integer> sr=this.${classNameLower}Service.delete(query);
		log.info("del result:"+JSON.toJSONString(sr));
		if(sr.getSuccess()){
			return new DataResult(sr.getErrorCode(),sr.getMessage()).toMap();
		}else{
			return new ErrorResult(sr.getErrorCode(),sr.getMessage()).toMap();
		}
	}
	/**
	 * 更新
	 * @param strJson
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, ?> update(HttpServletResponse response,@RequestBody String strJson,@RequestAttribute("user") User user) {
		log.info("update param:"+strJson);
		List<${className}> listParam=ControllerUtil.strJsonToArray(strJson,${className}.class);
		this.filterUpdateParams(listParam,user);
		ServiceResult<Integer> sr=this.${classNameLower}Service.insertOrUpdate(user.getGroupId(),listParam);
		log.info("update result"+JSON.toJSONString(sr));
		if(sr.getSuccess()){
			return new DataResult(sr.getErrorCode(), sr.getMessage()).toMap();
		}else{
			return new ErrorResult(sr.getErrorCode(),sr.getMessage()).toMap();
		}
	}
	private void filterUpdateParams(List<${className}> listParam,User user){
		for(${className} rom : listParam){
			if(rom.getGroupId()==null||rom.getGroupId()==0){
				throw new InvalidParamException(OptCodeConstant.ERROR_PARAM_FORMAT.getCode(),"集团groupID为空，不能操作数据！");
			}
			rom.setActionBy(user.getUserName());
			rom.setActionTime(DateUtil.getActionTime());
		}
	}
	/**
	 * 获取数据
	 * @param response
	 * @param strJson
	 * @return
	 */
	@RequestMapping(value = "/listData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, ?> listData(HttpServletResponse response,@RequestBody String strJson,@RequestAttribute("user") User user) {
		log.info("listData param:"+strJson);
		${className}Query query=JSON.parseObject(strJson,${className}Query.class);
		if(query.getGroupId()==null||query.getGroupId()==0||query.getDistributionId()==null||query.getDistributionId()==0){
			throw new InvalidParamException(OptCodeConstant.ERROR_PARAM_FORMAT.getCode(),"集团groupID或配送中心orgID为空，不能操作数据！");
		}
		this.filterQueryParams(query);
		ServiceResult<PageInfo<${className}>> sr = this.${classNameLower}Service.query(query);
		log.info("listData result"+JSON.toJSONString(sr));
		PageInfo<${className}> body = sr.getBody();
		if(null == body) {
			return new ListResult(sr.getErrorCode(),sr.getMessage(),false).toMap();
		}
		if(body.getPageSize()>0&&body.getPageNum()>0){
			if(sr.getSuccess()){
				return new ListResult(body.getTotal(),body.getPageSize(),body.getPageNum(),body.getPages(),body.getList()).toMap();
			}else{
				return new ListResult(sr.getErrorCode(),sr.getMessage(), sr.getSuccess(),query.getPageSize(), query.getPageNo()).toMap();
			}
		}else{
			if(sr.getSuccess()){
				return new ListResult(body.getList()).toMap();
			}else{
				return new ListResult(sr.getErrorCode(),sr.getMessage(),sr.getSuccess()).toMap();
			}
		}
	}
	private void filterQueryParams(${className}Query query){
		/*Set<Long> setLongWids=SSOUtils.setLongCUWId(null);
		if(setLongWids!=null){
			if(query.getFwarehouse()==null){
				query.setWarehouseIds(setLongWids);
			}
		}*/
	}
	private List<${className}Vo> filterEntityToVo(List<${className}> body){
		List<${className}Vo> result=ControllerUtil.copyArray(body, ${className}Vo.class);
		if(result!=null){
			for (${className}Vo vo : result) {
				//设置其它数据
				/*try {
					vo.setStrUt(DateUtil.getDateStringByTimeStamp(entity.getUt(), DateUtil.YMDHMS));
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}*/
			}
		}else{
			result=new ArrayList<${className}Vo>();
		}
		return result;
	}
	 /**
     * 导出excel
     * @param query
     * @param response
     */
	@RequestMapping(value = "/export", method = RequestMethod.POST)
    public void export(HttpServletResponse response,@RequestBody ${className}Query query,@RequestAttribute("user") User user) {
        this.filterQueryParams(query);
    	List<ExcelBean> listEB=new ArrayList<ExcelBean>();
    	//这里写要导出的列,不建议导出id列
        <#list table.columns as column><#if column.sqlName!='${primaryKey}'>
        listEB.add(new ExcelBean("${column.columnNameLower}", "${column.columnAlias!}"));
        </#if></#list>
		ServiceResult<PageInfo<${className}>> sr = this.${classNameLower}Service.query(query);
		PageInfo<${className}> body = sr.getBody();
        ExcelUtil.parsingAndExport(response,"${className}","titleInfo",listEB,this.filterEntityToVo(body.getList()),${className}Vo.class);
    }
	
}


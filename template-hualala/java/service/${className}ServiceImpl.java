<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${package_service_impl};

import java.sql.SQLNonTransientConnectionException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hualala.mes3.model.ServiceResult;
import com.hualala.mes3.util.OptCodeConstant;
import com.hualala.mes3.util.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.log4j.Log4j2;
import com.alibaba.fastjson.JSON;

import ${package_base_manager}.BaseManager;
import ${package_base_service_impl}.BaseServiceImpl;
import ${package_manager}.${className}Manager;
import ${package_model}.${className};
import ${package_query}.${className}Query;
import ${package_service}.${className}Service;

<#include "/java_hualala_imports.include">

@SuppressWarnings({"serial", "unused", "unchecked", "rawtypes"})
@Log4j2
@Service
public class ${className}ServiceImpl implements ${className}Service{
	@SuppressWarnings("unused")
	private Log logger = LogFactory.getLog(${className}ServiceImpl.class);
    @Autowired
    private ${className}Manager ${classNameLower}Manager;

    /**
	 * if id==null goto insert else goto update
	 *
	 * @param t
	 * @return
	 */
	@Override
	public ServiceResult<${className}> insertOrUpdate(${className} t) throws Exception {
		ServiceResult<${className}> sr = new ServiceResult<${className}>();
		if (t.getId() == null) {
			this.filterNeedUpdateData(t, true);
			t = this.${classNameLower}Manager.insert(t);
			if (t.getId() != null) {
				sr.setSuccess(true);
				sr.setMessage("成功增加 1 条数据。");
			}else{
				sr.setMessage("增加 1 条数据失败。");
			}
		} else {
			this.filterNeedUpdateData(t, false);
			Integer opCount = this.${classNameLower}Manager.update(t);
			if (opCount > 0) {
				sr.setSuccess(true);
				sr.setMessage("成功操作 1 条数据。");
			}else{
				sr.setMessage("操作 1 条数据失败。");
			}
		}
		if (sr.getSuccess()) {
			sr.setErrorCode(OptCodeConstant.SUCCESS.getCode());
			sr.setBody(t);
		} else {
			sr.setErrorCode(OptCodeConstant.ERROR.getCode());
		}
		return sr;
	}

	/**
	 * if index_0.id==null goto insert else goto update
	 *
	 * @param list
	 * @return
	 */
	@Override
	public ServiceResult<Integer> insertOrUpdate(Long groupID,List<${className}> list) throws Exception {
		ServiceResult<Integer> sr = new ServiceResult<Integer>();
		int count = list.size();
		if (count == 0) {
			throw new NullPointerException();
		} else if (count == 1) {
			ServiceResult<${className}> srOne = this.insertOrUpdate(list.get(0));
			sr.setErrorCode(srOne.getErrorCode());
			sr.setSuccess(srOne.getSuccess());
			sr.setMessage(srOne.getMessage());
			if (srOne.getBody() != null) {
				List<${className}> lu = new ArrayList<${className}>();
				lu.add(srOne.getBody());
				sr.setBody(lu.size());
			}
		} else {
			Integer opCount = 0;
			if (list.get(0).getId() == null) {
				for (${className} item : list) {
					this.filterNeedUpdateData(item, true);
				}
				opCount = this.${classNameLower}Manager.batchInsert(list);
				if (opCount > 0) {
					sr.setSuccess(true);
					sr.setMessage("成功增加 " + opCount + " 条数据 。");
				}else{
					sr.setMessage("增加 " + list.size() + " 条数据失败 。");
				}
			} else {
				for (${className} item : list) {
					this.filterNeedUpdateData(item, false);
				}
				opCount = this.${classNameLower}Manager.batchUpdate(list);
				if (opCount > 0) {
					sr.setSuccess(true);
					sr.setMessage("成功操作 " + list.size() + " 条数据 。");
				}else{
					sr.setMessage("操作 " + list.size() + " 条数据失败 。");
				}
			}
			if (sr.getSuccess()) {
				sr.setErrorCode(OptCodeConstant.SUCCESS.getCode());
				sr.setBody(opCount);
			}
		}
		return sr;
	}

	/**
	 * id!=null || ( ids!=null and ids.size()>0 ) goto delete
	 *
	 * @param query
	 * @return
	 */
	@Override
	public ServiceResult<Integer> delete(${className}Query query) throws Exception {
		ServiceResult<Integer> sr = new ServiceResult<Integer>();
			if (query.getId() == null && query.getIds() == null && query.getIds().size() == 0) {
				sr.setMessage("没有要删除的数据（缺少标识数据）。");
			} else {
				Integer opCount = this.${classNameLower}Manager.batchDelete(query);
				if (opCount > 0) {
					sr.setSuccess(true);
					sr.setErrorCode(OptCodeConstant.SUCCESS.getCode());
					sr.setMessage("成功删除 " + opCount + " 条数据。");
				} else {
					sr.setErrorCode(OptCodeConstant.ERROR.getCode());
					sr.setMessage("没有要删除的数据！");
				}
			}
		return sr;
	}

	@Override
	public ServiceResult<Integer> delete(long id) throws Exception {
		${className}Query query = new ${className}Query();
		query.setId(id);
		return this.delete(query);
	}

	@Override
	public ServiceResult<Integer> delete(Long groupID,Set<Long> ids) throws Exception {
		${className}Query query = new ${className}Query();
		query.setIds(ids);
		return this.delete(query);
	}

	/**
	 * by id goto query
	 *
	 * @param id
	 * @return
	 */
	@Override
	public ServiceResult<${className}> query(long id) throws Exception {
		ServiceResult<${className}> sr = new ServiceResult<${className}>();
		${className}Query query = new ${className}Query();
		query.setId(id);
		query.setPageSize(-1);
		ServiceResult<PageInfo<${className}>> srList = this.query(query);
		PageInfo<${className}> body = srList.getBody();
		sr.setSuccess(srList.getSuccess());
		sr.setMessage(srList.getMessage());
		sr.setErrorCode(srList.getErrorCode());
		if (body.getList() != null) {
			sr.setBody(body.getList().get(0));
		}
		return sr;
	}

	/**
	 * by ids goto query
	 *
	 * @param ids
	 * @return
	 */
	@Override
	public ServiceResult<List<${className}>> query(Long groupID,Set<Long> ids) throws Exception {
		log.info("query params:" + ids);
		ServiceResult<List<${className}>> sr =  new ServiceResult<>();
		${className}Query query = new ${className}Query();
		query.setIds(ids);
		query.setPageSize(-1);
		ServiceResult<PageInfo<${className}>> srList = this.query(query);
		PageInfo<${className}> body = srList.getBody();
		sr.setSuccess(srList.getSuccess());
		sr.setErrorCode(srList.getErrorCode());
		sr.setMessage(srList.getMessage());
		sr.setBody(body.getList());
		return sr;
	}

	@Override
	public ServiceResult<PageInfo<${className}>> query(${className}Query query) throws Exception {
		ServiceResult<PageInfo<${className}>> sr = new ServiceResult<>();
		if (query.getPageSize() != -1) {
			PageHelper.startPage(query.getPageNum(), query.getPageSize());
		}
        long count =0;
        if(query.getAction()==null){
            query.setAction(1);
        }
        List<${className}> list = this.${classNameLower}Manager.queryList(query);
        PageInfo<${className}> pageInfo = new PageInfo(list);
        sr.setBody(pageInfo);
		return sr;
	}

	/**
	 * 过滤需要操作的数据
	 * @param t
	 * @param hasInsert 是否同时过滤增加的数据
	 * @throws Exception
	 */
	private void filterNeedUpdateData (${className} t,Boolean hasInsert) throws Exception{
		if(hasInsert){
			if(t.getCreateBy()==null){
				throw new Exception("缺少创建人");
			}
			t.setCreateTime(DateUtil.getActionTime());
		}
		if(t.getActionBy()==null){
			throw new Exception("缺少操作人");
		}
		t.setActionTime(DateUtil.getActionTime());
	}

	@Override
    public ServiceResult<Long> sync(Long groupID,List<${className}> dataList) throws Exception {
        Long data = ((Long) this.${classNameLower}Manager.sync(dataList));
        return new ServiceResult<Long>(data);
    }

    @Override
    public ServiceResult<${className}> insert(${className} t) throws Exception {
        return new ServiceResult<${className}>(this.${classNameLower}Manager.insert(t));
    }

    @Override
    public ServiceResult<Integer> delete(${className} t) throws Exception {
        return new ServiceResult<Integer>(this.${classNameLower}Manager.delete(t));
    }

    @Override
    public ServiceResult<Integer> update(${className} t) throws Exception {
        return new ServiceResult<Integer>(this.${classNameLower}Manager.update(t));
    }

    @Override
    public ServiceResult<${className}> selectByPrimaryKey(long id) throws Exception {
        return new ServiceResult<${className}>(this.${classNameLower}Manager.selectByPrimaryKey(id));
    }

    @Override
    public ServiceResult<Long> count(${className}Query query)  throws Exception{
        return new ServiceResult<Long>(this.${classNameLower}Manager.count(query));
    }

    @Override
    public ServiceResult<List<${className}>> selectByColumn(${className}Query query) throws Exception {
        return new ServiceResult<List<${className}>>(this.${classNameLower}Manager.selectByColumn(query));
    }

    @Override
    public ServiceResult<List<${className}>> queryList(${className}Query query) throws Exception {
        return new ServiceResult<List<${className}>>(this.${classNameLower}Manager.queryList(query));
    }

    @Override
    public ServiceResult<Integer> batchInsert(Long groupID,List<${className}> list) throws Exception {
		return new ServiceResult<Integer>(this.${classNameLower}Manager.batchInsert(list));
    }

    @Override
    public ServiceResult<Integer> batchDelete(${className}Query query)  throws Exception{
		return new ServiceResult<Integer>(this.${classNameLower}Manager.batchDelete(query));
    }

    @Override
    public ServiceResult<Integer> batchUpdate(Long groupID,List<${className}> list) throws Exception {
		return new ServiceResult<Integer>(this.${classNameLower}Manager.batchUpdate(list));
    }
}

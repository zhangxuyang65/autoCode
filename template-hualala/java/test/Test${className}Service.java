<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${package_test_service};

import com.alibaba.fastjson.JSON;
import ${package_model}.${className};
import ${package_query}.${className}Query;
import ${package_service}.${className}Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by meicai on 2015/11/30.
 */
public class Test${className}Service extends TestService<${className},${className}Query> {
    private static final Log log = LogFactory.getLog(Test${className}Service.class);
    @Override
    public  Log getLog() {
        return log;
    }

    @Autowired
    PackService service;
    @BeforeClass
    public static void setup() throws Exception {
        init(new String[] { "script/${className}_schema.sql" }, new String[] { "script/${className}_data.sql" });
    }

    /**
     *测试 增加
     */
    @Test
    public void add(){
        primaryKey=123456l;
        entity=new ${className}();
        entity.setId(primaryKey);
        entity.setStatus(1);
        entity.setIsDeleted(0);
        resultEntity=service.insert(entity);
        printEntity("增加",primaryKey, resultEntity.getId(), resultEntity);

    }
    /**
     *测试 删除
      */
    @Test
    public void delete(){
        primaryKey=1511l;
        entity=new ${className}();
        entity.setId(primaryKey);
        service.delete(entity);

        resultEntity=service.selectByPrimaryKey(entity);
        log.info(JSON.toJSONString(entity));
        Assert.assertTrue(resultEntity.getIsDeleted().equals(CODE_NUMBER_DELETE));
    }
    /**
     *测试 更新
     */
    @Test
    public void update(){
        primaryKey=1511l;
        entity=new ${className}();
        entity.setId(primaryKey);
        String code="Code:"+dadeFormat.format(new Date());
        entity.setCode(code);
        Pack updatePre=service.selectByPrimaryKey(entity);
        int updateNumber=service.update(entity);
        //更新后数据
        resultEntity=service.selectByPrimaryKey(entity);
        printUpdate(updateNumber,updatePre, resultEntity);
        Assert.assertTrue(ciW.intValue()==resultEntity.getCiWeight().intValue());
    }

    /**
     * 测试 按主键查询
     */
    @Test
    public void selectByPrimaryKey(){
        primaryKey=1511l;
        entity =new ${className}();
        entity.setId(primaryKey);
        resultEntity=service.selectByPrimaryKey(entity);
        printEntity("查询",primaryKey,resultEntity.getId(),resultEntity);
    }
    /**
     *测试 条件查询
     *
     * @return
     */
    @Test
    public void queryList() {
        query = new ${className}Query();
        /****** 分页（不给参数则返回所有） ***************************/
        // query.setPageNumber(1);// 当前页
        // query.setPageSize(5);// 每页显示数量
        /****** 过滤参数（不给参数则返回所有） ***************************/
        // query.setCode("111");
        // query........
        // 操作数据库
        resultList = service.queryList(query);
        // 打印集合
        printList(6,resultList);
    }
}

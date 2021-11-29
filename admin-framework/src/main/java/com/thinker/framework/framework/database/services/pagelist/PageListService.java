package com.thinker.framework.framework.database.services.pagelist;

import cn.hutool.core.lang.Validator;
import com.thinker.framework.framework.ThinkerAdmin;
import com.thinker.framework.framework.database.mybatis.ThinkerServiceImpl;
import com.thinker.framework.framework.database.services.BasePageServices;
import com.thinker.framework.framework.database.services.PageServiceResult;
import com.thinker.framework.framework.database.utils.ParamsParserUtil;
import com.thinker.framework.framework.database.wrapper.ThinkerWrapper;
import com.thinker.framework.framework.entity.vo.WrapperValue;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@Setter
@Accessors(chain = true)
public class PageListService<T> extends BasePageServices<T> {

    private String alias;
    private String[] withs;
    private String[] fields;

    // 分页参数
    private int page = 1;
    private int limit = 10;
    private String sort = "id";
    private String order = "DESC";

    private EachClosure<T> eachClosure;
    private ParserWrapper<T> parserWrapper;
    private List<WrapperValue> wrapperValues = new ArrayList<>();

    //使用闭包的形式
    public interface EachClosure<T> {
        void run(T item);
    }

    //使用闭包的形式
    public interface ParserWrapper<T> {
        void run(ThinkerWrapper<T> thinkerWrapper);
    }

    public PageListService(ThinkerServiceImpl<?, T> service) {
        super(service);

        HttpServletRequest httpServletRequest = ThinkerAdmin.request().getRequest();

        if(Validator.isNotEmpty(httpServletRequest.getParameter("page"))) {
            this.setPage(Integer.parseInt(httpServletRequest.getParameter("page")));
        }

        if(Validator.isNotEmpty(httpServletRequest.getParameter("limit"))) {
            this.setLimit(Integer.parseInt(httpServletRequest.getParameter("limit")));
        }

        if(Validator.isNotEmpty(httpServletRequest.getParameter("sort"))) {
            this.setSort(httpServletRequest.getParameter("sort"));
        }

        if(Validator.isNotEmpty(httpServletRequest.getParameter("order"))) {
            this.setOrder(httpServletRequest.getParameter("order"));
        }
    }

    /**
     * 获取结果
     * @return
     */
    public PageServiceResult<T> getResult() {
        //逐项设置参数
        if(Validator.isNotEmpty(this.alias)) {
            this.thinkerWrapper.alias(this.alias);
        }

        //设置选择的字段
        if(Validator.isNotEmpty(this.fields)) {
            this.thinkerWrapper.select(this.fields);
        } else {
            if(Validator.isNotEmpty(this.alias)) {
                this.thinkerWrapper.select(this.alias + ".*");
            }
        }

        //排序和分页必须设置
        this.thinkerWrapper.page(this.page);
        this.thinkerWrapper.limit(this.limit);

        //判断是否加入了alias，加入了就需要设置
        if(Validator.isNotEmpty(this.sort)) {
            String sortStr = (Validator.isNotEmpty(this.alias) ? this.alias+"." : "") + this.sort;
            if(this.order.equalsIgnoreCase("ASC")) {
                this.thinkerWrapper.orderByAsc(sortStr);
            }else{
                this.thinkerWrapper.orderByDesc(sortStr);
            }
        }

        //添加where参数
        if(this.wrapperValues.size() != 0) {
            ParamsParserUtil.parseWrapper(thinkerWrapper, this.wrapperValues);
        }

        //自定义的运算参数
        if(parserWrapper != null) {
            parserWrapper.run(thinkerWrapper);
        }

        if(withs != null) {
            this.thinkerWrapper.with(withs);
        }

        PageServiceResult<T> pageServiceResult = new PageServiceResult<>();
        pageServiceResult.setItems(this.thinkerService.thinkerList(this.thinkerWrapper));
        pageServiceResult.setTotal(this.thinkerService.thinkerCount(this.thinkerWrapper).intValue());

        if(this.eachClosure != null) {
            pageServiceResult.getItems().forEach(t -> this.eachClosure.run(t));
        }

        return pageServiceResult;
    }
}

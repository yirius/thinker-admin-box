package com.thinker.framework.token.extend;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Validator;
import com.thinker.framework.framework.ThinkerAdmin;
import com.thinker.framework.framework.abstracts.LoginAbstract;
import com.thinker.framework.framework.database.mybatis.ThinkerMapper;
import com.thinker.framework.framework.database.mybatis.ThinkerServiceImpl;
import com.thinker.framework.framework.database.services.pagelist.PageListService;
import com.thinker.framework.framework.database.wrapper.ThinkerWrapper;
import com.thinker.framework.framework.entity.vo.WrapperValue;
import com.thinker.framework.framework.factory.LoginFactory;
import com.thinker.framework.framework.support.SpringContext;
import com.thinker.framework.framework.widgets.ThinkerResponse;
import com.thinker.framework.token.factory.TokenFactory;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Getter
@Setter
@Accessors(chain = true)
@RestController
@RequestMapping(value = "/thinker/ThinkerRestful")
public class ThinkerRestful<M extends ThinkerMapper<T>, T> extends ThinkerController {

    private Class<? extends ThinkerServiceImpl<M, T>> useTable;

    public ThinkerServiceImpl<M, T> getTableImpl() {
        return SpringContext.getBean(useTable);
    }

    // 保存查询字段
    private List<WrapperValue> useWhere = new ArrayList<>();

    private String useAlias = null;
    private String[] useWith = null;
    private String[] useField = null;
    private PageListService.EachClosure<T> eachClosure = null;
    private PageListService.ParserWrapper<T> parserWrapper = null;

    @RequestMapping(method = RequestMethod.GET)
    public ThinkerResponse index() {
        PageListService<T> pageServiceResult = getTableImpl().pageList();

        pageServiceResult.setAlias(useAlias).setWiths(useWith)
                .setFields(useField).setEachClosure(eachClosure)
                .setWrapperValues(useWhere)
                .setParserWrapper(parserWrapper);

        return ThinkerAdmin.response().data(pageServiceResult.getResult()).success();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ThinkerResponse read(@PathVariable("id") Long id) {
        ThinkerWrapper<T> tThinkerWrapper = new ThinkerWrapper<>();
        _beforeRead(tThinkerWrapper, id);

        return ThinkerAdmin.response().data(
                _afterRead(getTableImpl().getOne(tThinkerWrapper))
        ).success();
    }

    public void _beforeRead(ThinkerWrapper<T> tThinkerWrapper, Long id) {
        tThinkerWrapper.eq("id", id);
    }

    public Object _afterRead(T entity) {
        return entity;
    }

    /**
     * 保存
     * @param entity
     * @param bindingResult
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ThinkerResponse save(@Valid T entity, BindingResult bindingResult) {
        //存在错误，直接打回
        List<FieldError> fieldErrors = new ArrayList<>(bindingResult.getFieldErrors());
        if(fieldErrors.size() > 0) {
            fieldErrors.sort(Comparator.comparingInt(o -> o.getField().hashCode()));

            return new ThinkerResponse().msg(
                    fieldErrors.get(0).getDefaultMessage()
            ).fail();
        }

        _beforeSave(entity);

        // 如果不存在id，那就是更新
        boolean isUpdate = !(BeanUtil.getFieldValue(entity, "id") == null);

        if(getTableImpl().saveOrUpdate(entity)) {
            _afterSave(entity, isUpdate);
            return ThinkerAdmin.response().msg("保存成功").success();
        }

        return ThinkerAdmin.response().msg("存在问题").fail();
    }

    public void  _beforeSave(T entity) {}

    public void _afterSave(T entity, boolean isUpdate) {}


    /**
     * 删除操作
     * @param ids
     * @return
     */
    protected boolean deleteNeedPassword = true;

    @RequestMapping(method = RequestMethod.DELETE)
    public ThinkerResponse delete(String ids, String password) {
        if(Validator.isEmpty(ids)) {
            return new ThinkerResponse().msg("message.thinker.admin.emptyData").fail();
        }

        if(deleteNeedPassword) {
            LoginAbstract<T> loginAbstract = LoginFactory.loadLogin();
            if(Validator.isEmpty(password)) {
                return new ThinkerResponse().msg("message.thinker.admin.emptyPassword").fail();
            }

            if(!loginAbstract.verifyPassword(password, loginAbstract.getUser(TokenFactory.loadToken().getLoginId()))) {
                return new ThinkerResponse().msg("message.thinker.admin.passwordIncorrect").fail();
            }
        }

        List<String> idsStr = _beforeDelete(ids);
        getTableImpl().removeByIds(idsStr);
        _afterDelete(idsStr);

        return new ThinkerResponse().msg("message.thinker.admin.deleteSuccess").success();
    }

    public List<String> _beforeDelete(String ids) {
        return Arrays.asList(ids.split(","));
    }

    public void _afterDelete(List<String> ids) {}
}

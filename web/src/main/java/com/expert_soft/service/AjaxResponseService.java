package com.expert_soft.service;


import com.expert_soft.exception.service.ajax.AjaxException;
import com.expert_soft.model.Cart;
import org.springframework.context.MessageSourceAware;

public interface AjaxResponseService extends MessageSourceAware{

    String buildAjaxSuccess(Cart cart, String modelAdded) throws AjaxException;

    String buildFailToWrite();

    String buildAjaxFailUnexpected();

    String buildAjaxFail(String additionalMsg, Integer code);

    String buildInvalidFormat();

    String buildFailUnexpected();

    String buildFail(String addititonalMsg);

    String buildAjaxFailInvalidFormat(Integer code);

}

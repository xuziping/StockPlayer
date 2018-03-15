package com.xuzp.stockplayer.web.controller;

import com.xuzp.stockplayer.common.result.ResultBase;
import com.xuzp.stockplayer.web.dto.request.DeleteRuleRequestDTO;
import com.xuzp.stockplayer.web.dto.request.QueryPlayerRequestDTO;
import com.xuzp.stockplayer.web.dto.request.SaveRuleRequestDTO;
import com.xuzp.stockplayer.web.dto.response.QueryPlayerResponseDTO;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author za-xuzhiping
 * @Date 2018/1/19
 * @Time 21:48
 */
@RequestMapping("/player")
public interface IPlayerShareService {

    /**
     * 查询用户信息
     * @param requestDTO
     * @return
     */
    @RequestMapping("/query")
    ResultBase<QueryPlayerResponseDTO> query(QueryPlayerRequestDTO requestDTO);

    /**
     * 保存规则
     * @param requestDTO
     * @return
     */
    @RequestMapping("/addRule")
    ResultBase<Void> addRule(SaveRuleRequestDTO requestDTO);

    /**
     * 删除规则
     * @param requestDTO
     * @return
     */
    @RequestMapping("/deleteRule")
    ResultBase<Void> deleteRule(DeleteRuleRequestDTO requestDTO);

}

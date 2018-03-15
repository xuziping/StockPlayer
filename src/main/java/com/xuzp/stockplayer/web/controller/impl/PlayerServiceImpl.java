package com.xuzp.stockplayer.web.controller.impl;

import com.xuzp.stockplayer.common.Constants;
import com.xuzp.stockplayer.common.result.ResultBase;
import com.xuzp.stockplayer.common.result.ResultBaseUtils;
import com.xuzp.stockplayer.model.Player;
import com.xuzp.stockplayer.storage.FileStorage;
import com.xuzp.stockplayer.web.controller.IPlayerShareService;
import com.xuzp.stockplayer.web.dto.request.DeleteRuleRequestDTO;
import com.xuzp.stockplayer.web.dto.request.QueryPlayerRequestDTO;
import com.xuzp.stockplayer.web.dto.request.SaveRuleRequestDTO;
import com.xuzp.stockplayer.web.dto.response.QueryPlayerResponseDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

public class PlayerServiceImpl implements IPlayerShareService {

    @Autowired
    private FileStorage fileStorage;

    @Override
    public ResultBase<QueryPlayerResponseDTO> query(QueryPlayerRequestDTO requestDTO) {
        Player player = fileStorage.loadPlayer();
        QueryPlayerResponseDTO responseDTO = new QueryPlayerResponseDTO();
        BeanUtils.copyProperties(player, responseDTO);
        return ResultBaseUtils.success(Constants.SUCCESS_CODE_QUERY_PLAYER, responseDTO);
    }

    @Override
    public ResultBase<Void> addRule(SaveRuleRequestDTO requestDTO) {
        return null;
    }

    @Override
    public ResultBase<Void> deleteRule(DeleteRuleRequestDTO requestDTO) {
        return null;
    }
}

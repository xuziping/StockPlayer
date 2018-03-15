package com.xuzp.stockplayer.web.controller;

import com.xuzp.stockplayer.common.result.ResultBase;
import com.xuzp.stockplayer.web.dto.request.NLPRequestDTO;
import com.xuzp.stockplayer.web.dto.response.NLPResponseDTO;
import org.springframework.web.bind.annotation.RequestMapping;

public interface INLPShareService {


    /**
     * 自然语言处理命令并执行
     * @param requestDTO
     * @return
     */
    @RequestMapping("/nlp")
    ResultBase<NLPResponseDTO> handler(NLPRequestDTO requestDTO);
}

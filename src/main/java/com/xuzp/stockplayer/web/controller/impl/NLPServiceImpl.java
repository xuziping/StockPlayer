package com.xuzp.stockplayer.web.controller.impl;

import com.google.common.collect.Lists;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.xuzp.stockplayer.common.BeanUtils;
import com.xuzp.stockplayer.common.Constants;
import com.xuzp.stockplayer.common.result.ResultBase;
import com.xuzp.stockplayer.nlp.INLPAdapter;
import com.xuzp.stockplayer.nlp.INLPTarget;
import com.xuzp.stockplayer.nlp.NLPAdapterResult;
import com.xuzp.stockplayer.web.controller.INLPShareService;
import com.xuzp.stockplayer.web.dto.request.NLPRequestDTO;
import com.xuzp.stockplayer.web.dto.response.NLPResponseDTO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class NLPServiceImpl implements INLPShareService {

    @Autowired
    private ClassLoader classLoader;

    @Autowired
    private BeanUtils beanUtils;

    @Override
    public ResultBase<NLPResponseDTO> handler(NLPRequestDTO requestDTO) {

        Segment segment = HanLP.newSegment().enablePlaceRecognize(true);
        List<Term> termList = segment.seg(requestDTO.getText());

        List<NLPAdapterResult> objects = getMatachedNLPs(Constants.OBJECTS, termList);
        List<NLPAdapterResult> operates = getMatachedNLPs(Constants.OPERATES, termList);

        if(CollectionUtils.isNotEmpty(objects) && CollectionUtils.isNotEmpty(operates)) {
            for(NLPAdapterResult obj: objects) {
                if (CollectionUtils.isNotEmpty(obj.getTargets())) {
                   Optional<INLPTarget> tar = obj.getTargets().stream().filter(target->{
                        for(NLPAdapterResult operate : operates) {
                            if (operate.getValue().getClass().isAssignableFrom(target.getClass())) {
                                return true;
                            }
                        }
                        return false;
                    }).findAny();

//                   if(tar.isPresent()) {
//                       obj.handle(tar.get());
//                   }

                }
            }
        }

        return null;
    }

    private List<NLPAdapterResult> getMatachedNLPs(List<String> beanNames, List<Term> termList){
        List<NLPAdapterResult> ret = Lists.newArrayList();
        for(String name: beanNames) {
            Object obj = beanUtils.getBean(name);
            if(obj.getClass().isAssignableFrom(INLPAdapter.class)) {
               NLPAdapterResult result =  ((INLPAdapter)obj).nlpAdapte(termList);
               if(result!=null) {
                   ret.add(result);
               }
            }
        }
        return ret;
    }


}

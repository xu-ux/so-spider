package com.github.xucux.sop.example;


import com.github.xucux.sop.common.model.pojo.ResultItems;
import com.github.xucux.sop.common.model.request.IResponse;
import com.github.xucux.sop.module.core.processor.ContentProcessor;
import com.github.xucux.sop.module.core.session.SpiderSession;
import org.jsoup.nodes.Document;

/**
 * @descriptions:
 * @author: xucl
 * @date: 2021/8/25
 * @version: 1.0
 */
public class CsdnProcessor implements ContentProcessor {


    @Override
    public void onSuccess(SpiderSession session, IResponse response, ResultItems resultItems) {
        Document document = response.parseByJsoup();
        String article = document.selectFirst("article").html();
        System.out.println("article = "+article);
    }

    @Override
    public boolean onFailed(SpiderSession session, IResponse response) {
        return false;
    }

    @Override
    public void onException(SpiderSession session, Exception exception) {

    }
}

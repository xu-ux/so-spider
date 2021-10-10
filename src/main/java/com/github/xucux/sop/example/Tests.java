package com.github.xucux.sop.example;


import com.github.xucux.sop.common.config.Option;
import com.github.xucux.sop.common.util.UserAgentUtils;
import com.github.xucux.sop.module.core.context.SpiderContext;

/**
 * @descriptions:
 * @author: xucl
 * @date: 2021/8/25
 * @version: 1.0
 */
public class Tests {


    public static void main(String [] args){
        String url = "https://blog.csdn.net/mrlin6688/article/details/106074547";
        SpiderContext.create().setTaskId("taskId").setOption(Option.make()
                .setUserAgent(UserAgentUtils.randomUserAgent())
        ).addUrl(new CsdnProcessor(),url).run();
        // 使用group url经过group出来
        // 使用单独的urlController 进过

    }
}

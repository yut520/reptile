package concurrent;

import excel.model.CommunityInfo;
import lombok.extern.slf4j.Slf4j;
import util.HtmlParserUtils;

import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * 根据小区地址获取小区html页面并解析
 * @author iyut
 */
@Slf4j
public class ParseCommunityWork implements Callable<CommunityInfo> {

    private String url;

    public  ParseCommunityWork(String url){
        this.url = url;
    }

    @Override
    public CommunityInfo call() throws IOException {
		return HtmlParserUtils.getCommunity(url);
    }
}

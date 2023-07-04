package com.yangy.send.strategy;

import com.yangy.send.constants.MailServerTypeEnum;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: Yangy
 * @Date: 2023/6/13 16:57
 * @Description
 */
@Component
public class StrategyContext {
	
	@Resource
	private final Map<String,AbstractStrategyConfig> STRATEGY_MAP = new ConcurrentHashMap<>(3);
	
	public AbstractStrategyConfig getStrategy(MailServerTypeEnum type){
		if(Objects.isNull(type)){
			type = MailServerTypeEnum.OUT_LOOK_MAIL;
		}
		
		AbstractStrategyConfig config = STRATEGY_MAP.get(type.getStrategy());
		if(Objects.isNull(config)){
			return chooseConfig(type);
		}
		return config;
	}
	
	private AbstractStrategyConfig chooseConfig(MailServerTypeEnum type){
		if(MailServerTypeEnum.OUT_LOOK_MAIL.equals(type)){
			return new OutLookConfigStrategy();
		}else if(MailServerTypeEnum.G_MAIL.equals(type)){
			return new GmailConfigStrategy();
		}else if(MailServerTypeEnum.QQ_MAIL.equals(type)){
			return new QQConfigStrategy();
		}
		return new OutLookConfigStrategy();
	}
}

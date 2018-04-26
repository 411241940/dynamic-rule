package rules

import com.bin.rule.core.handler.Handler

/**
 * todo
 * @author bin
 * @version 1.0 2018/4/25
 * */
class ConfigHandler implements Handler {

	@Override
	Object handle(Map<String, Object> params) {

		// 配置信息
		def config = [
				ifOpen : true,
				count : 3
		]

		return config
	}

}

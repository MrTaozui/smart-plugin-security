package org.smart4j.framework.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 编码与解码工具类
 * @author taojiajun
 *
 */
public final class CodeUtil {

	/**
	 * MD5加密
	 */
	public static String md5(String source){
		return DigestUtils.md5Hex(source);
	}
}

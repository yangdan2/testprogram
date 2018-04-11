package com.card.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class AppUtil {
	public static String md5Hex(String strInput) {
        if (null == strInput || strInput.isEmpty()) {
            return "";
        }
        return DigestUtils.md5Hex(strInput).toLowerCase();
	}
}
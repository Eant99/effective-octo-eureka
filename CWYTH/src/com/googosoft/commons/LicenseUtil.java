package com.googosoft.commons;

import com.googosoft.util.DateUtil;
import com.googosoft.util.security.EncryptUtils;

public class LicenseUtil {
	/**
	 * 检测系统是否在试用期
	 * @return true:在使用期 false:不在试用期
	 */
	public static boolean doLicenseCheck(){
		String licenseCode = PropertiesUtil.getValueByKey(PropertiesUtil.PROPERTIES_LICENSE_PATH, "license");
		String licenseTime = "";
		try {
			licenseTime = EncryptUtils.decryptByAES(PropertiesUtil.PROPERTIES_LICENSE_KEYW, licenseCode);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String currentTime = DateUtil.getDay();
		return DateUtil.compareDate(licenseTime, currentTime);
	}

	
	public static void main(String[] args) {
        String keyw=PropertiesUtil.PROPERTIES_LICENSE_KEYW;
        String code = "2018-06-30";
        
        try {
			String Aec= EncryptUtils.encryptToAES(keyw,code);
			System.out.println("【试用期】AES加密后为:" + Aec);
			
			String deAec= EncryptUtils.decryptByAES(keyw, Aec);
			System.out.println("【试用期】AES解密密后为:" + deAec);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	
}

package com.terminal.beans;

public class CodeMsg {
	
	/*
	 *     actionID     functionID    messageID
	 *	     xxx           xxx           xxx
	 * 
	 * */
	
	/**********************************UserAction*************************************/
	
	//login success
	public static final Integer USERACTION_LOGIN_SUCCESS_CODE = 0x010101;
	public static final String USERACTION_LOGIN_SUCCESS_MSG = "useraction.login.success";
	
	//login failed
	public static final Integer USERACTION_LOGIN_FAILED_CODE = 0x010102;
	public static final String USERACTION_LOGIN_FAILED_MSG = "useraction.login.failed";

	//login verifycode error
	public static final Integer USERACTION_LOGIN_VERIFYCODE_ERROR_CODE = 0x010103;
	public static final String USERACTION_LOGIN_VERIFYCODE_ERROR_MSG = "useraction.login.verifycode.error";

}

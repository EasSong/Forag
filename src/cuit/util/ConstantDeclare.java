package cuit.util;

public class ConstantDeclare {
	//操作结果代码
	//用户操作结果代码
	public static final int ERROR_USER_UNEXISTS = -1;
	public static final int ERROR_DB_ERROR = -2;
	public static final int ERROR_PASSWORD_LOGIN = -3;
	public static final int ERROR_PASSWORD_EDIT = -4;
	public static final int ERROR_MAIL_EXISTED = -5;
	public static final int SUCCESS_USER_REGISTER = -6;
	public static final int SUCCESS_USER_LOGIN = -7;
	public static final int SUCCESS_EDIT_PASSWORD = -8;
	public static final int SUCCESS_EDIT_USERHSOWINFOR = -9;

	/*
	Message 文章操作结果代码
	 */
	public static final int ERROR_MSG_UNEXISTS = -10;
	public static final int ERROR_UPDATE_LIKE = -12;
	public static final int ERROR_UPDATE_COLLECT = -13;
	public static final int ERROR_UPDATE_TRANSMIT = -14;
	/*
	Comment 评论操作结果代码
	 */
	public static final int ERROR_COMMENT_UNEXISTS = -15;
	public static final int ERROR_COMMENT_INSERT = -16;
	public static final int ERROR_COMMENT_SELECT = -17;
	public static final int ERROR_COMMENT_DELETE = -18;
	public static final int ERROR_COMMENT_UPDATE = -19;
	public static final int SUCCESS_COMMENT_INSERT = -20;

	public static final String DEFAULT_STRING = "Unknown";
	
	public static final String DEFAULT_USER_IMAGE_PATH = "..\\..\\WebRoot\\AdminLTE-2.3.11\\forag\\images\\userDefaultPic.jpg";
	public static final int MAX_FILE_READ_COUNT = 1024;
}

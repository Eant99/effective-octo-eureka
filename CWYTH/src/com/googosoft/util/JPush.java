package com.googosoft.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

/**
 * 极光推送服务端实现类
 * controller 层调用 ： 
 * 方法与说明：
 *  JPush.getInstance().sendPush();
 *  推送消息，如更新，紧急通知等。对象：所有人员，所有设备 
 *  JPush.getInstance().sendPush(String userID, String alert, String mklx);
 *  推送消息，如通知公告，待审批通知等。 对象：指定人员 注意：调用此方法，需要传入人员 userID，推送内容，模块名称。方便手机端进行模块区分，跳转页面
 *  JPush.getInstance().sendPush(String userID, String json) 
 *  推送消息  如：当用户提交一条申请单，服务端处理后，向手机端推送一条消息，手机端借此刷新页面的待办数量。 对象：指定人员 注意：调用此方法，需传入人员 userID，json字符串。  
 *  json 格式: {"success":"true","items":[{"mklx":"1","dspsl":"3"},{"mklx":"2","dspsl":"3"}]}
 * @整理 by liushuai 2017年2月10日 08:42:53
 */
public class JPush {
	protected static final Logger LOG = LoggerFactory.getLogger(JPush.class);

	private static JPush jpush;

	public static JPush getInstance() {
		if (jpush == null)
			return new JPush();
		return jpush;
	}

	/**
	 * 向全部人员推送
	 * 
	 * @throws APIRequestException
	 */
	public void sendPush() {
		JPushClient jpushClient = new JPushClient(Params.masterSecret, Params.appKey, null, ClientConfig.getInstance());
		// 生成推送的内容
		PushPayload payload = buildPushObject_all_all_alert();
		try {
			System.out.println(payload.toString());
			PushResult result = jpushClient.sendPush(payload);
			System.out.println(result + "................................");
			LOG.info("Got result - " + result);
		} catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ", e);
		} catch (APIRequestException e) {
			LOG.error("Error response from JPush server. Should review and fix it. ", e);
			LOG.error("如果异常在此抛出，原因是：别名还没有在任何客户端SDK提交设置成功。也就是说，目标用户从未登陆过app，首次登录成功以后，就不会抛出此异常了");
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Code: " + e.getErrorCode());
			LOG.info("Error Message: " + e.getErrorMessage());
			LOG.info("Msg ID: " + e.getMsgId());
		}
	}

	/**
	 * 向指定人员推送
	 * 
	 * @param userID
	 *            用户ID
	 * @param alert
	 *            推送内容
	 * @param mklx
	 *            模块名称
	 * @throws APIRequestException
	 */
	public void sendPush(String userID, String alert, String mklx) {
		JPushClient jpushClient = new JPushClient(Params.masterSecret, Params.appKey, null, ClientConfig.getInstance());
		// 生成推送的内容
		PushPayload payload = buildPushObject_all_alias_alert(userID, alert, mklx);
		try {
			System.out.println(payload.toString());
			PushResult result = jpushClient.sendPush(payload);
			System.out.println(result + "................................");
			LOG.info("Got result - " + result);
		} catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ", e);
		} catch (APIRequestException e) {
			LOG.error("Error response from JPush server. Should review and fix it. ", e);
			LOG.error("如果异常在此抛出，原因是：别名还没有在任何客户端SDK提交设置成功。也就是说，目标用户从未登陆过app，首次登录成功以后，就不会抛出此异常了");
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Code: " + e.getErrorCode());
			LOG.info("Error Message: " + e.getErrorMessage());
			LOG.info("Msg ID: " + e.getMsgId());
		}
	}

	/**
	 * 向指定用户推送，用于主界面刷新消息数量
	 * 
	 * @param userID
	 *            用户ID
	 * @param json
	 *            推送内容
	 * @throws APIRequestException
	 */
	public void sendPush(String userID, String json) {
		JPushClient jpushClient = new JPushClient(Params.masterSecret, Params.appKey, null, ClientConfig.getInstance());
		// 生成推送的内容
		PushPayload payload = buildPushObject_messageWithExtras(userID, json);
		try {
			System.out.println("userID==="+userID+",json===="+json);
			System.out.println(payload.toString());
			PushResult result = jpushClient.sendPush(payload);
			System.out.println(result + "................................");
			LOG.info("Got result - " + result);
		} catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ", e);
		} catch (APIRequestException e) {
			LOG.error("Error response from JPush server. Should review and fix it. ", e);
			LOG.error("如果异常在此抛出，原因是：别名还没有在任何客户端SDK提交设置成功。也就是说，目标用户从未登陆过app，首次登录成功以后，就不会抛出此异常了");
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Code: " + e.getErrorCode());
			LOG.info("Error Message: " + e.getErrorMessage());
			LOG.info("Msg ID: " + e.getMsgId());
		}
	}

	/**
	 * 构建推送对象：所有平台，所有设备，所有人，内容为 ALERT 的通知。
	 * 
	 * @return
	 */
	private PushPayload buildPushObject_all_all_alert() {
		return PushPayload.alertAll(Params.ALERT);// 基本通知
	}

	/**
	 * 构建推送对象：所有android_ios，推送目标是别名为 "usirID"，通知内容为 ALERT。附加字段为 mklx——"通知公告"
	 * 
	 * @return
	 */
	private PushPayload buildPushObject_all_alias_alert(String userID, String alert, String mklx) {
		return PushPayload.newBuilder().setPlatform(Platform.android_ios())// 平台:android_ios
				.setAudience(Audience.alias(userID))// 别名,这里如果是向指定用户推送事件，需要将用户的UserID传入
				.setNotification(Notification.newBuilder().setAlert(alert)
						.addPlatformNotification(AndroidNotification.newBuilder()
								// .setTitle(TITLE)//android 特有，设置app名称或标题的
								.addExtra("mklx", mklx).build())
						.addPlatformNotification(IosNotification.newBuilder()
								// .setBadge(5)//ios所特有的，用于显示有N条待办事件的
								.addExtra("mklx", mklx).build())
						.build())
				.build();
	}

	/**
	 * 构建推送对象：所有android_ios，推送目标是别名为 "usirID"，推送内容为json——"{key:1,key:2}"。
	 * 
	 * @return
	 */
	private PushPayload buildPushObject_messageWithExtras(String userID, String json) {
		return PushPayload.newBuilder().setPlatform(Platform.android_ios())
				.setAudience(Audience.alias(userID))// 别名,这里如果是向指定用户推送事件，需要将用户的UserID传入
				.setMessage(Message.newBuilder()
						.setMsgContent(json)
//						.addExtra("json", json)
						.build())
				.build();
	}

	public class Params {
		// 极光推送，注册帐号显示的Key和masterSecret
//		private static final String appKey = "4d30590c9f3f6ab53981c6a5";
//		private static final String masterSecret = "1790351f7d30702add9affb3";
		private static final String appKey = "9499b00ae19ed53cd4a0825d";
		private static final String masterSecret = "00c9bdaa4c977fbcac384378";
		// 推送字段
		public static final String TITLE = "申通快递";
		public static final String ALERT = "你有18项事件未处理";
		public static final String TZGG = "1";//通知公告
		public static final String DWQD = "2";//学生的签到定位
		public static final String RZSP = "5";//老师的日志审批
	}
}

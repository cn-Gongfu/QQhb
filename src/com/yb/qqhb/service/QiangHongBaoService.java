package com.yb.qqhb.service;

import java.util.List;
import java.util.Timer;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.os.IBinder;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

public class QiangHongBaoService extends AccessibilityService {

	String HONGBAO_STATE_TEXT = "点击拆开";
	int HONGBAO_TYPE_ALL = 1;
	int HONGBAO_TYPE_FULI = 2;
	public static int HONGBAO_TYPE = 2;
	
	//
	@Override
	public void onAccessibilityEvent(AccessibilityEvent event) {
		// 获取当前事件的根节点
		AccessibilityNodeInfo rootNodeInfo = event.getSource();
		if (rootNodeInfo != null) {
			// 在根节点中查找 含有文字 为 "点击拆开" 的子节点, 文字是点击拆开,说明红包还没有被打开
			List<AccessibilityNodeInfo> list = rootNodeInfo
					.findAccessibilityNodeInfosByText(HONGBAO_STATE_TEXT);
			System.out.println(list.size());
			performHongBao(list);
		}
	}

	/**
	 * 点击红包的节点
	 */
	private void performHongBao(List<AccessibilityNodeInfo> list) {
		for (AccessibilityNodeInfo nodeInfo : list) {
			// 获取该节点的父节点
			AccessibilityNodeInfo parentNode = nodeInfo.getParent();

			// 判断红包的类型
			switch (HONGBAO_TYPE) {
			case 1:
				// 点击父节点
				parentNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
				// 点击返回按钮,到聊天窗口.
				performBack(this);
				break;

			case 2:
				// 判断红包的类型
				boolean baoType = getHongBaoType(parentNode);
				if (baoType) {
					// 点击父节点
					parentNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
					// 点击返回按钮,到聊天窗口.
					performBack(this);
				}
				break;
			}

		}
	}

	/**
	 * 获取红包类型
	 * 
	 * @param parentNode
	 */
	public boolean getHongBaoType(AccessibilityNodeInfo parentNode) {
		List<AccessibilityNodeInfo> list = parentNode.getParent()
				.findAccessibilityNodeInfosByText("/");
		System.out.println("type:  " + list.size());
		for (AccessibilityNodeInfo nodeInfo : list) {
			String text = (String) nodeInfo.getText();
			System.out.println(text);
			return false;
		}
		return true;
	}

	/**
	 * 红包抢完之后,返回到聊天页面 模拟返回事件
	 * 
	 * @param service
	 */
	private void performBack(final AccessibilityService service) {
		if (service == null) {
			return;
		}
		new Thread() {
			public void run() {
				try {
					// 暂停800 毫秒
					Thread.sleep(800);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// 点击返回按钮
				service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
			};
		}.start();

	}

	@Override
	public void onInterrupt() {

	}

}

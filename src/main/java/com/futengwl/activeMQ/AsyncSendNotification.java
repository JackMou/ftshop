package com.futengwl.activeMQ;

import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

@Component
public class AsyncSendNotification extends BaseProcess {

	@Autowired
	@Qualifier("orderSettlementQueue")
	private Queue orderSettlementQueue;

	public AsyncSendNotification() {
		super();
	}

	// 插入消息队列--发布
	public void sendOrderSettlement(final List<Map<String, String>> mapList) throws Exception {
		System.out.println("生产数据开始1！" + mapList.toString());
		for (Map<String, String> map : mapList) {
			jmsTemplate.send(orderSettlementQueue, new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					System.out.println("生产数据开始2！");
					MapMessage mapMessage = session.createMapMessage();
					mapMessage.setObject("object", map);
					System.out.println("生产数据结束！");
					return mapMessage;
				}
			});
		}

	}

	@Autowired
	@Qualifier("orderAfterSalesQueue")
	private Queue orderAfterSalesQueue;

	// 插入消息队列--发布
	public void sendorderAfterSales(final List<Map<String, String>> mapList) throws Exception {
		System.out.println("退货生产数据开始1！" + mapList.toString());
		for (Map<String, String> map : mapList) {
			jmsTemplate.send(orderAfterSalesQueue, new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					System.out.println("退货生产数据开始2！");
					MapMessage mapMessage = session.createMapMessage();
					mapMessage.setObject("object", map);
					System.out.println("退货生产数据结束！");
					return mapMessage;
				}
			});
		}

	}

	@Autowired
	@Qualifier("orderChangeStatusQueue")
	private Queue orderChangeStatusQueue;

	// 插入消息队列--发布
	public void sendOrderChangeStatus(final Map<String, String> map) throws Exception {

		jmsTemplate.send(orderChangeStatusQueue, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				System.out.println("订单状态改变生产数据开始！");
				MapMessage mapMessage = session.createMapMessage();
				mapMessage.setObject("map", map);
				System.out.println("订单状态改变生产数据结束！");
				return mapMessage;
			}
		});

	}

}

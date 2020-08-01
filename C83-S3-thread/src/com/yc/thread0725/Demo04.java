package com.yc.thread0725;

import java.util.ArrayList;
import java.util.List;

public class Demo04 {
	public static void main(String []args) {
		ProducerConsumer pc=new ProducerConsumer();
		new Thread() {
			public void run() {
				try {
					pc.produce();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
		new Thread() {
			public void run() {
				try {
					pc.consume();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
		
	}
}
class ProducerConsumer{
	private List<Integer>list=new ArrayList<>();
	
	/*
	 * 生产者模式
	 */
	public synchronized void produce() throws InterruptedException {
		while(true) {
			if(list.isEmpty()) {
				for(int i=0;i<10;i++) {
					list.add(i);
					System.out.println("-------生产一个产品"+i);
					Thread.sleep(200);
				}
			}else {
				notifyAll();
				wait();
			}
		}
	}
	/*
	 * 消费者模式
	 */
	public synchronized void consume() throws InterruptedException {
		while(true) {
			if(list.isEmpty()==false) {
				for(int i=0;i<10;i++) {
					list.remove(0);
					System.out.println("======消费一个产品"+i);
					Thread.sleep(100);
				}
			}else {
				notifyAll();
				wait();
			}
		}
	}
}

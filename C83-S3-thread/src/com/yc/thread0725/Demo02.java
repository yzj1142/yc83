package com.yc.thread0725;

public class Demo02 {

	public static void main(String[] args) {
		//匿名内部类创建线程
		Thread t1=new Thread("线程1") {
			public void run() {
				for(int i=0;i<1000;i++) {
					System.out.println(Thread.currentThread().getName()+":"+i);
				}
			}
		};
		Thread t2=new Thread("============线程2") {
			//类定义，匿名方式
			public void run() {
				for(int i=0;i<1000;i++) {
					System.out.println(Thread.currentThread().getName()+":"+i);
					//t2线程执行到i=500时，join到t1中
					
						try {
							if(i==500) {
							t1.join();
							}
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
				}
			}
		};
		t1.start();
		t2.start();
	}

}

package com.dwarfeng.tp.core.control;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import com.dwarfeng.dutil.basic.io.CT;

public final class Foo {
	
	public static void main(String[] args) {
		final R r = new R();

		Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					try {
						CT.trace(r.take());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		Thread t2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					r.offer("12450");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		t1.start();
		t2.start();
	}

}

class R{
	
	public ReadWriteLock lock = new ReentrantReadWriteLock();
	
	public Condition condition = lock.writeLock().newCondition();
	
	public Queue<String> queue = new ArrayDeque<>();
	
	public String take() throws InterruptedException{
		lock.writeLock().lock();
		try{
			while(queue.size() == 0){
				condition.await();
			}
			return queue.poll();
		}finally {
			lock.writeLock().unlock();
		}
	}
	
	public void offer(String str){
		lock.writeLock().lock();
		try{
			queue.offer(str);
			condition.signalAll();
		}finally {
			lock.writeLock().unlock();
		}
	}
}

package com.dwarfeng.tp.core.control;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.dwarfeng.dutil.basic.io.CT;

public final class Foo {

	static ReadWriteLock lock = new ReentrantReadWriteLock();
	
	public static void main(String[] args) throws Exception{
	
		lock.writeLock().lock();
		try{
			new Thread(new Runnable() {
				@Override
				public void run() {
					lock.readLock().lock();
					try{
						CT.trace(123);
					}finally {
						lock.readLock().unlock();
					}
				}
			}).start();
			CT.trace(456);
		}finally {
			lock.writeLock().unlock();
		}
	}

}

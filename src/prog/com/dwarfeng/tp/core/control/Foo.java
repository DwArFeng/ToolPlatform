package com.dwarfeng.tp.core.control;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import com.dwarfeng.dutil.basic.io.CT;

public final class Foo {

	static ReadWriteLock lock = new ReentrantReadWriteLock();
	
	public static void main(String[] args) throws Exception{
		Logger logger = (Logger) LogManager.getRootLogger();
		logger.fatal("123", (Throwable)null);
	}

}

package com.dwarfeng.tp.core.control;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class Foo {
	
	public static void main(String[] args) {
		ExecutorService es = Executors.newSingleThreadScheduledExecutor();
		es.shutdown();
		es.submit(new Callable<Object>() {

			@Override
			public Object call() throws Exception {
				return null;
			}
		});
	}

}


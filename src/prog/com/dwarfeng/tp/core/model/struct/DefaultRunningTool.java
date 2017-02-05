package com.dwarfeng.tp.core.model.struct;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.dwarfeng.dutil.basic.prog.RuntimeState;
import com.dwarfeng.dutil.basic.threads.NumberedThreadFactory;
import com.dwarfeng.tp.core.model.obv.RunningToolObverser;

/**
 * Ĭ�������й��ߡ�
 * <p> �����й��ߵ�Ĭ��ʵ�֡�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public class DefaultRunningTool implements RunningTool{
	
	private static final ThreadFactory THREAD_FACTORY = new NumberedThreadFactory("running_tool", true, Thread.NORM_PRIORITY);
	
	private ReadWriteLock lock = new ReentrantReadWriteLock();
	private Condition startCondition = lock.writeLock().newCondition();

	private final Image image;
	private final String name;
	private final String[] libraries;
	private final String jarPath;
	private final String entryClass;
	private final File directory;
	
	private final Set<RunningToolObverser> obversers = Collections.newSetFromMap(new WeakHashMap<>());
	
	private RuntimeState runtimeState = RuntimeState.NOT_START;
	private InputStream in = null;
	private PrintStream out = null;
	private boolean streamLockFlag = false;
	
	private Process process = null;
	private int exitCode;
	
	/**
	 * 
	 * @param name
	 * @param libraries
	 * @param jarPath
	 * @param entryClass
	 */
	public DefaultRunningTool(Image image, String name, String[] libraries, String jarPath, String entryClass, File directory) {
		Objects.requireNonNull(image, "��ڲ��� image ����Ϊ null��");
		Objects.requireNonNull(name, "��ڲ��� name ����Ϊ null��");
		Objects.requireNonNull(libraries, "��ڲ��� libraries ����Ϊ null��");
		Objects.requireNonNull(jarPath, "��ڲ��� jarPath ����Ϊ null��");
		Objects.requireNonNull(entryClass, "��ڲ��� entryClass ����Ϊ null��");
		Objects.requireNonNull(directory, "��ڲ��� directory ����Ϊ null��");

		this.image = image;
		this.name = name;
		this.libraries = libraries;
		this.jarPath = jarPath;
		this.entryClass = entryClass;
		this.directory = directory;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.str.Name#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#getObversers()
	 */
	@Override
	public Set<RunningToolObverser> getObversers() {
		lock.readLock().lock();
		try{
			return Collections.unmodifiableSet(obversers);
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#addObverser(com.dwarfeng.dutil.basic.prog.Obverser)
	 */
	@Override
	public boolean addObverser(RunningToolObverser obverser) {
		lock.writeLock().lock();
		try{
			return obversers.add(obverser);
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#removeObverser(com.dwarfeng.dutil.basic.prog.Obverser)
	 */
	@Override
	public boolean removeObverser(RunningToolObverser obverser) {
		lock.writeLock().lock();
		try{
			return obversers.remove(obverser);
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#clearObverser()
	 */
	@Override
	public void clearObverser() {
		lock.writeLock().lock();
		try{
			obversers.clear();
		}finally {
			lock.writeLock().unlock();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.RunningTool#getImage()
	 */
	@Override
	public Image getImage() {
		return image;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.RunningTool#getInputStream()
	 */
	@Override
	public InputStream getInputStream() {
		lock.readLock().lock();
		try{
			return in;
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.RunningTool#setInputStream(java.io.InputStream)
	 */
	@Override
	public boolean setInputStream(InputStream in) {
		lock.writeLock().lock();
		try{
			if(streamLockFlag) return false;
			this.in = in;
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.RunningTool#getOutputStream()
	 */
	@Override
	public OutputStream getOutputStream() {
		lock.readLock().lock();
		try{
			return out;
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.RunningTool#setOutputStream(java.io.OutputStream)
	 */
	@Override
	public boolean setOutputStream(PrintStream out) {
		lock.writeLock().lock();
		try{
			if(streamLockFlag) return false;
			this.out = out;
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.RunningTool#lockStream()
	 */
	@Override
	public void lockStream() {
		lock.writeLock().lock();
		try{
			streamLockFlag = true;
			startCondition.signalAll();
		}finally {
			lock.writeLock().unlock();
		}
	}
	
	private boolean isStreamLock(){
		lock.readLock().lock();
		try{
			return streamLockFlag;
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.RunningTool#start()
	 */
	@Override
	public void start() throws InterruptedException {
		//�������״̬����δ��������ֱ���˳�
		if(! getRuntimeState().equals(RuntimeState.NOT_START)) return;
		
		lock.writeLock().lock();
		try{
			//���ù����Ƿ�������ǰ��ֹͣ�ˡ�
			if(getRuntimeState().equals(RuntimeState.ENDED)){
				setExitCode(-12450);
				return;
			}
			//����������ֹ���̷�����Ӧ���ܹ����Ѹõȴ�״̬��
			while(! isStreamLock() && ! getRuntimeState().equals(RuntimeState.ENDED)){
				startCondition.await();
				//����ڵȴ�ʱ�׳��ж��쳣���򲻻�ִ�к������䡣
			}
			//���õȴ�״̬�Ƿ�����ֹ�������ѵġ�
			if(getRuntimeState().equals(RuntimeState.ENDED)){
				setExitCode(-12451);
			}
			//���������ֹ�������ѵģ��������������ѵģ���ʱӦ��������������
			try{
				process = new ProcessBuilder(genCommandLine()).redirectErrorStream(true).directory(directory).start();
				THREAD_FACTORY.newThread(new OutputProcessor()).start();
				THREAD_FACTORY.newThread(new InputProcessor()).start();
				THREAD_FACTORY.newThread(new ExitedHandler()).start();
				setRuntimeState(RuntimeState.RUNNING);
				fireStarted();
			}catch (Exception e) {
				if(Objects.nonNull(out)){
					e.printStackTrace(out);
				}
				setExitCode(-12452);
				setRuntimeState(RuntimeState.ENDED);
				fireExited();
			}
		}finally {
			lock.writeLock().unlock();
		}
	}
	
	private void fireStarted() {
		for(RunningToolObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireStarted(this);
		}
	}
	
	private void fireExited(){
		for(RunningToolObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireExited(this);
		}
	}

	private List<String> genCommandLine(){
		List<String> cmdLine = new ArrayList<>();
		cmdLine.add("java");
		cmdLine.add("-cp");
		StringBuilder cpsb = new StringBuilder();
		for(int i = 0 ; i < libraries.length ; i ++){
			cpsb.append(libraries[i]);
			if(i < libraries.length - 1) cpsb.append(";");
		}
		if(libraries.length > 0){
			cpsb.append(";");
		}
		cpsb.append(jarPath);
		cmdLine.add(cpsb.toString());
		cmdLine.add(entryClass);
		return cmdLine;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.RunningTool#destroy()
	 */
	@Override
	public void destroy() {
		//�������״̬Ϊ�Ѿ���������ֱ�ӷ��ء�
		if(getRuntimeState().equals(RuntimeState.ENDED)) return;
		
		lock.writeLock().lock();
		try{
			if(Objects.nonNull(process)){
				process.destroy();
			}
			setRuntimeState(RuntimeState.ENDED);
			fireExited();
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.RunningTool#getRuntimeState()
	 */
	@Override
	public RuntimeState getRuntimeState() {
		lock.readLock().lock();
		try{
			return runtimeState;
		}finally {
			lock.readLock().unlock();
		}
	}
	
	private void setRuntimeState(RuntimeState runtimeState){
		lock.writeLock().lock();
		try{
			this.runtimeState = runtimeState;
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.RunningTool#getDirectory()
	 */
	@Override
	public File getDirectory() {
		lock.readLock().lock();
		try{
			return directory;
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.RunningTool#getExitCode()
	 */
	@Override
	public int getExitCode() {
		lock.readLock().lock();
		try{
			return exitCode;
		}finally {
			lock.readLock().unlock();
		}
	}
	
	private void setExitCode(int exitCode){
		lock.writeLock().lock();
		try{
			this.exitCode = exitCode;
		}finally {
			lock.writeLock().unlock();
		}
	}

	private final class OutputProcessor implements Runnable{
	
		private BufferedReader reader = null;
		
		public OutputProcessor() {
			if(Objects.nonNull(process)){
				reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			}
		}
		
		/*
		 * (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			while(! getRuntimeState().equals(RuntimeState.ENDED)){
				String str = null;
				if(Objects.isNull(reader)) break;
				try{
					if((str = reader.readLine()) != null){
						if(Objects.nonNull(out)){
							out.println(str);
						}
					}
				}catch (Exception e) {
					try{
						if(Objects.nonNull(out)){
							e.printStackTrace(out);
						}
					}catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
			//��ʱ��ϵͳ�Ѿ����н����ˡ�
			if(Objects.nonNull(reader)){
				try{
					reader.close();
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	private final class InputProcessor implements Runnable{
	
		private Scanner scanner = null;
		
		public InputProcessor() {
			if(Objects.nonNull(in)){
				scanner = new Scanner(in);
			}
		}
		
		/*
		 * (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run(){
			while(! getRuntimeState().equals(RuntimeState.ENDED)){
				if(Objects.isNull(scanner)) break;
				try{
					String nextLine = scanner.next() + "\n";
					if(Objects.nonNull(process)){
						process.getOutputStream().write(nextLine.getBytes());
						process.getOutputStream().flush();
					}
				}catch (Exception e) {
					try{
						if(Objects.nonNull(out)){
							e.printStackTrace(out);
						}
					}catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
			//��ʱ��ϵͳ�Ѿ����н����ˡ�
			if(Objects.nonNull(scanner)){
				scanner.close();
				if(Objects.nonNull(scanner.ioException())){
					scanner.ioException().printStackTrace();
				}
			}
		}
		
	}
	
	private final class ExitedHandler implements Runnable{

		/*
		 * (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			if(Objects.isNull(process)){
				return;
			}
			try {
				process.waitFor();
				setExitCode(process.exitValue());
				setRuntimeState(RuntimeState.ENDED);
				fireExited();
			} catch (InterruptedException ignore) {
				//�ж�ҲҪ���ջ�����
			}
			
		}
		
	}

}

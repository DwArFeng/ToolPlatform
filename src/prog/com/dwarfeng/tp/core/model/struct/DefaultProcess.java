package com.dwarfeng.tp.core.model.struct;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.dwarfeng.tp.core.model.obv.ProcessObverser;

/**
 * �����ࡣ
 * <p> ������һ������ʵ�ֵĹ��̡�
 * <p> ��������Ա����ã�ӵ�н������ԣ����ҿ�����ע��Ĺ۲����㲥������Ϣ��
 * <br> ���̸����Ƿ�֪�����ȿɷ�Ϊȷ�����̺Ͳ�ȷ�����̣��������� {@link #isDeterminate()} ȷ����
 * <p> �۲������̰߳�ȫ�ġ�
 * @author  DwArFeng
 * @since 1.8
 */
public abstract class DefaultProcess implements Process{
	
	/**�۲�������*/
	protected final Set<ProcessObverser> obversers = Collections.newSetFromMap(new WeakHashMap<>());
	/**ͬ����д��*/
	protected final ReadWriteLock lock = new ReentrantReadWriteLock();
	
	/**��ǰ�Ľ���*/
	private int progress = 0;
	/**�ܽ���*/
	private int totleProgress = 0;
	/**��ǰ�Ľ�����ȷ���Ļ��ǲ�ȷ����*/
	private boolean determinateFlag = true;
	
	/**�����Ƿ����*/
	private boolean doneFlag = false;
	/**�����Ƿ�ȡ��*/
	private boolean cancelFlag = false;
	/**�йع��̵���Ϣ*/
	private String message = "";
	/**�йع��̵Ŀ��׳�����*/
	private Throwable throwable = null;

	/* (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.Process#getProgress()
	 */
	@Override
	public int getProgress() {
		lock.readLock().lock();
		try{
			return progress;
		}finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * ���ù��̵Ľ��ȡ�
	 * <p> ����Ӧ����ѭ���¹淶�� <code> 0 &lt= progress &lt= totleProgress </code>
	 * <p> �ù��̽��Զ��Ľ������Ϲ淶����ڲ���ת��Ϊ��ӽ��ĺ���ֵ��
	 * @param progress ָ���Ľ��ȡ�
	 * @return �ò����Ƿ�ı��˹��̱���
	 */
	protected boolean setProgress(int progress) {
		lock.writeLock().lock();
		try{
			if(this.progress == progress) return false;
			int oldValue = this.progress;
			progress = Math.max(0, progress);
			progress = Math.min(progress, totleProgress);
			this.progress = progress;
			fireProgressChanged(oldValue, progress);
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireProgressChanged(int oldValue, int newValue) {
		for(ProcessObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireProgressChanged(this, oldValue, newValue);
		}
	}

	/* (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.Process#getTotleProgress()
	 */
	@Override
	public int getTotleProgress() {
		lock.readLock().lock();
		try{
			return totleProgress;
		}finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * ���ù����ܵĽ��ȡ�
	 * <p> ����Ӧ����ѭ���¹淶�� <code> 0 &lt= progress &lt= totleProgress </code>
	 * <p> �ù��̽��Զ��Ľ������Ϲ淶����ڲ���ת��Ϊ��ӽ��ĺ���ֵ��
	 * @param totleProgress ָ�����ܽ��ȡ�
	 * @return �ò����Ƿ�ñ��˹��̱���
	 */
	protected boolean setTotleProgress(int totleProgress) {
		lock.writeLock().lock();
		try{
			if(this.totleProgress == totleProgress) return false; 
			int oldValue = this.totleProgress;
			totleProgress = Math.max(0, totleProgress);
			totleProgress = Math.max(progress, totleProgress);
			this.totleProgress = totleProgress;
			fireTotleProgressChanged(oldValue, totleProgress);
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireTotleProgressChanged(int oldValue, int newValue) {
		for(ProcessObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireTotleProgressChanged(this, oldValue, newValue);
		}
	}

	/* (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.Process#isDeterminate()
	 */
	@Override
	public boolean isDeterminate() {
		lock.readLock().lock();
		try{
			return determinateFlag;
		}finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * ���øù����Ƿ�Ϊȷ�����̡�
	 * @param determinateFlag �ù����Ƿ�Ϊȷ�����̡�
	 * @return �ò����Ƿ�ı��˹��̱���
	 */
	protected boolean setDeterminate(boolean determinateFlag) {
		lock.writeLock().lock();
		try{
			if(this.determinateFlag == determinateFlag) return false;
			boolean oldValue = this.determinateFlag;
			this.determinateFlag = determinateFlag;
			fireDeterminateChanged(this, oldValue, determinateFlag);
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}
	
	private void fireDeterminateChanged(Process process, boolean oldValue, boolean newValue) {
		for(ProcessObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireDeterminateChanged(this, oldValue, newValue);
		}
	}

	/**
	 * ���ظù����Ƿ��ܹ�ȡ����
	 * @return �ù����Ƿ��ܹ�ȡ����
	 */
	protected boolean isCancelable(){
		lock.readLock().lock();
		try{
			return false;
		}finally {
			lock.readLock().unlock();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.Process#isCancel()
	 */
	@Override
	public boolean isCancel(){
		lock.readLock().lock();
		try{
			return cancelFlag;
		}finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * ȡ���ù��̡�
	 * @return �Ƿ�ȡ���ɹ���
	 */
	public boolean cancel(){
		lock.writeLock().lock();
		try{
			if(isCancelable()){
				cancelFlag = true;
				fireCanceled();
				return true;
			}else {
				return false;
			}
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireCanceled() {
		for(ProcessObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireCanceled(this);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.Process#isDone()
	 */
	@Override
	public boolean isDone(){
		lock.readLock().lock();
		try{
			return doneFlag;
		}finally {
			lock.readLock().unlock();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.Process#getMessage()
	 */
	@Override
	public String getMessage(){
		lock.readLock().lock();
		try{
			return message;
		}finally {
			lock.readLock().unlock();
		}
	}
	
	/**
	 * ���øù��̵���Ϣ��
	 * @param message �ù��̵���Ϣ��
	 * @return �÷����Ƿ���ɹ��̱���ĸı䡣
	 */
	protected boolean setMessage(String message){
		lock.writeLock().lock();
		try{
			if(Objects.equals(this.message, message)) return false;
			String oldValue = this.message;
			this.message = message;
			fireMessageChanged(oldValue, message);
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}
	
	private void fireMessageChanged(String oldValue, String newValue) {
		for(ProcessObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireMessageChanged(this, oldValue, newValue);
		}
	}
	
	/**
	 * ���ظù����еĿ��׳�����
	 * @return �����еĿ��׳�����
	 */
	public Throwable getThrowable(){
		lock.readLock().lock();
		try{
			return throwable;
		}finally {
			lock.readLock().unlock();
		}
	}
	
	/**
	 * ���øù����еĿ��׳�����
	 * <p> ����Ϊ <code>null</code> ����û�п��׳�����
	 * @param throwable ָ���Ŀ��׳�����
	 * @return �÷����Ƿ�ı��˹��̶�����
	 */
	protected boolean setThrowable(Throwable throwable){
		lock.writeLock().lock();
		try{
			if(Objects.equals(this.throwable, throwable)) return false;
			Throwable oldValue = this.throwable;
			this.throwable = throwable;
			fireThrowableChanged(oldValue, throwable);
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}
	
	private void fireThrowableChanged(Throwable oldValue, Throwable newValue) {
		for(ProcessObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireThrowableChanged(this, oldValue, newValue);
		}
	}

	private void done(){
		lock.writeLock().lock();
		try{
			doneFlag = true;
			fireDone();
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireDone() {
		for(ProcessObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireDone(this);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public Object call() {
		try{
			process();
		}catch (Exception e) {
			setThrowable(e);
		}
		done();
		return null;
	}
	
	/**
	 * �ù��̶���Ĺ��̷�����
	 */
	protected abstract void process();

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ReadWriteThreadSafe#getLock()
	 */
	@Override
	public ReadWriteLock getLock() {
		return this.lock;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#getObversers()
	 */
	@Override
	public Set<ProcessObverser> getObversers() {
		lock.readLock().lock();
		try{
			return obversers;
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#addObverser(com.dwarfeng.dutil.basic.prog.Obverser)
	 */
	@Override
	public boolean addObverser(ProcessObverser obverser) {
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
	public boolean removeObverser(ProcessObverser obverser) {
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
	
}

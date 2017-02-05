package com.dwarfeng.tp.core.view.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.LabelFieldKey;
import com.dwarfeng.dutil.basic.StringFieldKey;
import com.dwarfeng.dutil.basic.gui.swing.JMenuItemAction;
import com.dwarfeng.dutil.basic.num.NumberUtil;
import com.dwarfeng.dutil.basic.threads.NumberedThreadFactory;

/**
 * Swing 控制台。
 * <p> 该控制台是 {@link JConsole} 的升级版，无论是定义的良好性还是输入输出的效率都远远好于前者。
 * <br> 该控制台继承之前的特性：提供大致的行数保证，它允许文本达到一个最大的行数，当文本超过最大的行数时，控制台会按照一定的比例删掉
 * 最早输出的一部分行数，以控制行数不超过最大值。
 * <br> 控制台提供两个流：输入流和输出流，与 {@link JConsole}不同的是，输入流变为阻塞式的，当输入流没有数据时，不会立即返回 <code>-1</code>，
 * 而是显示输入栏，等待用于的输入——这个特性与系统输入流完全一致。该控制台的输入再也不需要依赖于事件，您可以完全按照同系统控制台通信的格式
 * 来编写该控制台的通信，同时不必担心无意识的调用流中的 <code>close()</code> 方法——因为该控制台的输入流和输出流的关闭不是通过 <code>close()</code> 方法，
 * 而是通过 {@link #dispose()}方法。
 * <br> 以下的例子完整的体现了上述的特性。
 * <pre>
 * JExconsole console = new JExconsole();
 * 
 * console.out.println("hello world"); //就像System.out.prinln(...)一样
 * 
 * Scanner scanner = new Scanner(console.in);
 * try{
 * 	console.out.println(scanner.nextLine());
 * }finally{
 * 	scanner.close(); //不用担心 Scanner 的 close()方法调用console.in.close()方法，因为控制台的输入流不响应该方法。
 * }
 * 
 * console.dispose(); //该方法才会真正的关闭控制台的输入输出流。
 * </pre>
 * 经优化后，该控制台的效率可以达到 cmd 控制台的 6900%，是一个货真价实的高效控制台。
 * @author  DwArFeng
 * @since 0.0.3-beta
 */
public class JExconsole extends JPanel {
	
	private static final long serialVersionUID = 8565782090438599219L;

	private final static ThreadFactory THREAD_FACTORY = new NumberedThreadFactory("jexconsole_cleaner");
	
	/**控制台的输入流*/
	public final InputStream in = new InnerInputStream();
	/**控制台的输出流*/
	public final PrintStream out = new PrintStream(new InnerOutputStream(), true);
	
	private final Lock renderLock = new ReentrantLock();
	private final Condition renderCondition = renderLock.newCondition();
	private final Queue<String> string2Render = new ArrayDeque<>();
	private final Lock inputLock = new ReentrantLock();
	private final Condition inputCondition = inputLock.newCondition();
	private final Lock outputLock = new ReentrantLock();
	private final List<String> rollbackStrings = new ArrayList<>();
	
	
	private final Thread renderer = THREAD_FACTORY.newThread(new Runnable() {
		
		private StringBuilder sb = new StringBuilder();
		private boolean appendFlag;
		@Override
		public void run() {
			next:
			while(! disposeFlag.get()){
				
				renderLock.lock();
				try{
					while(
							string2Render.isEmpty()
							&& ! disposeFlag.get()) {
						try {
							renderCondition.await();
						} catch (InterruptedException e) {
							//检查退出条件
						}
					}
					
					if(disposeFlag.get()) return;
					
					if(string2Render.isEmpty()) continue next;
					
					while(! string2Render.isEmpty()){
						sb.append(string2Render.poll());
					}
					

				}finally {
					renderLock.unlock();
				}
				
				appendFlag = true;
				
				checkAppend:
				while(appendFlag){
					try{
						SwingUtilities.invokeAndWait(new Runnable() {
							
							/*
							 * (non-Javadoc)
							 * @see java.lang.Runnable#run()
							 */
							@Override
							public void run() {
								textArea.append(sb.toString());
								appendFlag = false;
								sb = new StringBuilder();
							}
						});
					}catch (InterruptedException e) {
						continue checkAppend;
					} catch (InvocationTargetException ignore) {
						// 该过程不可能发生异常。
					}
				}

				if(textArea.getCaretPosition() < textArea.getText().length()){
					int pos = textArea.getText().length();
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							textArea.setCaretPosition(pos);
						}
					});
				}
			
				if(textArea.getLineCount() >  maxLine){
					int pos = getLinePos();
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							textArea.replaceRange(null, 0, pos);
						}
					});
				}

			}
		}

		private int getLinePos() {
			try {
				return textArea.getLineEndOffset((int) (textArea.getLineCount() + maxLine * cleanRatio - maxLine));
			} catch (BadLocationException ignore) {
				//不可能抛出此异常
				return 0;
			}
		}
		
	});
	
	private AtomicBoolean disposeFlag = new AtomicBoolean(false);
	private int rollBackPos = -1;
	
	private int maxLine;
	private double cleanRatio;
	private int maxRollback;
	
	/**控制台的输入框*/
	protected final JTextField textField;
	/**控制台的显示框*/
	protected final JTextArea textArea;
	/**控制台的右键菜单*/
	protected final JPopupMenu popup;

	/**
	 * 生成一个默认的控制台。
	 * <p> 控制台的最大行数为 <code>3000</code> 行，清除系数为 <code>0.1</code>，
	 * 最大回滚数量为 <code>10</code>。
	 */
	public JExconsole() {
		this(3000, 0.1, 10);
	}
	
	/**
	 * 生成一个具有指定最大行数，指定的清除系数的控制台。
	 * @param maxLine 指定的最大行数，需要 <code> 0 &lt; maxLine</code>。
	 * @param cleanRatio 指定的清除系数，需要<code> 0.0 &lt; cleanRatio &lt;= 1.0</code>。
	 * @param maxRollback 最大的输入回滚数量。
	 * @throws IllegalArgumentException 入口参数不符合要求。
	 */
	public JExconsole(int maxLine, double cleanRatio, Integer maxRollback) {
		if(maxLine <=  0) throw new IllegalArgumentException(DwarfUtil.getStringField(StringFieldKey.JExconsole_1));
		if(cleanRatio <= 0 || cleanRatio > 1) throw new IllegalArgumentException(DwarfUtil.getStringField(StringFieldKey.JExconsole_2));
		if(maxRollback < 0) throw new IllegalArgumentException(DwarfUtil.getStringField(StringFieldKey.JExconsole_3));
		
		this.maxLine = maxLine;
		this.cleanRatio = cleanRatio;
		this.setMaxRollback(maxRollback);
		
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		add(scrollPane, BorderLayout.CENTER);
		
		textArea = new JTextArea();
//		该方法与控制台的全选冲突。
//		textArea.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mousePressed(MouseEvent e) {
//				textField.requestFocus();
//			}
//		});
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		
		textField = new JTextField();
		textField.setBorder(new EmptyBorder(0, 0, 0, 0));
		textField.addKeyListener(new KeyAdapter() {
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.KeyAdapter#keyPressed(java.awt.event.KeyEvent)
			 */
			@Override
			public void keyPressed(KeyEvent e) {
				//When press enter
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					inputLock.lock();
					try{
						if(disposeFlag.get()){
							throw new IllegalStateException(DwarfUtil.getStringField(StringFieldKey.JExconsole_0));
						}
						
						InnerInputStream in = (InnerInputStream) JExconsole.this.in;
						String str = textField.getText();
						in.bs = (str + "\n").getBytes();
						in.pos = 0;
						in.readFlag = true;
						inputCondition.signalAll();
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								textField.setVisible(false);
								textField.setText(null);
								revalidate();
							}
						});
						rollbackStrings.add(0, str);
						if(rollbackStrings.size() > JExconsole.this.maxRollback) rollbackStrings.remove(rollbackStrings.size() - 1);
						rollBackPos = -1;
					}finally {
						inputLock.unlock();
					}
				}
				
				//When press up or down
				if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_KP_UP){
					if(rollBackPos < rollbackStrings.size() - 1) rollBackPos ++;
					textField.setText(rollbackStrings.get(rollBackPos));
				}
				if(e.getKeyCode() == KeyEvent.VK_DOWN|| e.getKeyCode() == KeyEvent.VK_KP_DOWN){
					if(rollBackPos > 0) rollBackPos --;
					textField.setText(rollbackStrings.get(rollBackPos));
				}
			}
		});
		textField.setVisible(false);
		textField.setOpaque(true);
		add(textField, BorderLayout.SOUTH);
		textField.setColumns(10);
		textArea.setForeground(null);
		textArea.setWrapStyleWord(true);
		
		popup = createPopup();
		addPopup(textArea, popup);
		
		textArea.getActionMap().put("cls", new AbstractAction() {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Objects.isNull(textArea)) return;
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						textArea.requestFocus();
						textArea.setText("");
					}
				});
			}
		});
		textArea.getInputMap(JComponent.WHEN_FOCUSED)
		.put(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK), "cls");
		
		renderer.start();
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#setFont(java.awt.Font)
	 */
	@Override
	public void setFont(Font font) {
		if(Objects.nonNull(textArea)) textArea.setFont(font);
		if(Objects.nonNull(textField)) textField.setFont(font);
		super.setFont(font);
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#setForeground(java.awt.Color)
	 */
	@Override
	public void setForeground(Color fg) {
		if(Objects.nonNull(textArea)) textArea.setForeground(fg);
		if(Objects.nonNull(textField)) textField.setForeground(fg);
		super.setForeground(fg);
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#setBackground(java.awt.Color)
	 */
	@Override
	public void setBackground(Color bg) {
		if(Objects.nonNull(textArea)) textArea.setBackground(bg);
		if(Objects.nonNull(textField)) textField.setBackground(bg);
		super.setBackground(bg);
	}
	
	/**
	 * 释放该控制台。
	 * <p>  该方法会依次关闭输入流、输出流、控制台清理线程。
	 */
	public void dispose(){
		textArea.setEnabled(false);
		textField.setEnabled(false);
		
		disposeFlag.set(true);
		
		inputLock.lock();
		try{
			inputCondition.signalAll();
		}finally {
			inputLock.unlock();
		}
		
		renderLock.lock();
		try{
			renderCondition.signalAll();
		}finally {
			renderLock.unlock();
		}
		
	}
	

	/**
	 * 获取控制台的最大显示行数。
	 * @return 控制台的最大显示行数。
	 */
	public int getMaxLine() {
		return maxLine;
	}

	/**
	 * 设置控制台的最大显示行数。
	 * @param maxLine 控制台的最大显示行数，需要 <code> 0 &lt; maxLine</code>。
	 * @throws IllegalArgumentException 入口参数不符合要求。
	 */
	public void setMaxLine(int maxLine) {
		if(maxLine <=  0) throw new IllegalArgumentException(DwarfUtil.getStringField(StringFieldKey.JExconsole_1));
		
		renderLock.lock();
		try{
			this.maxLine = maxLine;
			renderCondition.signalAll();
		}finally {
			renderLock.unlock();
		}
	}

	/**
	 * 获取控制台的删除比率。
	 * @return 控制台的删除比率。
	 */
	public double getCleanRatio() {
		return cleanRatio;
	}

	/**
	 * 设置控制台的删除比率。
	 * @param cleanRatio 控制台的删除比率，需要<code> 0.0 &lt; cleanRatio &lt;= 1.0</code>。
	 * @throws IllegalArgumentException 入口参数不符合要求。
	 */
	public void setCleanRatio(double cleanRatio) {
		if(cleanRatio <= 0 || cleanRatio > 1) throw new IllegalArgumentException(DwarfUtil.getStringField(StringFieldKey.JExconsole_2));
		
		renderLock.lock();
		try{
			this.cleanRatio = cleanRatio;
			renderCondition.signalAll();
		}finally {
			renderLock.unlock();
		}
	}

	/**
	 * 获取控制台中的最大回滚数量。
	 * @return 控制台的最大回滚数量。
	 */
	public int getMaxRollback() {
		return maxRollback;
	}

	/**
	 * 设置控制台的最大回滚数量，需要 <code> 0 &lt;= maxRollback</code>
	 * @param maxRollback 指定的最大回滚数量。
	 * @throws IllegalArgumentException 入口参数不符合要求。
	 */
	public void setMaxRollback(int maxRollback) {
		if(maxLine <=  0) throw new IllegalArgumentException(DwarfUtil.getStringField(StringFieldKey.JExconsole_1));
		this.maxRollback = maxRollback;
		if(rollbackStrings.size() > maxRollback){
			for(int i = 0 ; i < maxRollback - rollbackStrings.size() ; i ++){
				rollbackStrings.remove(rollbackStrings.size() - 1);
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.Component#setLocale(java.util.Locale)
	 */
	@Override
	public void setLocale(Locale l) {
		popup.setLocale(l);
		super.setLocale(l);
	}
	
	/**
	 * 立即向该控制台的输入流中输入指定的字符串。
	 * <p> 如果在调用此方法时，输入栏处于打开状态，则会清空输入栏中的字符，并且将其隐藏。
	 * 使用该方法输入的字符串不会被记录到回滚队列中。
	 * @param string 指定的字符串。
	 */
	public void input(String string){
		inputLock.lock();
		try{
			if(disposeFlag.get()){
				throw new IllegalStateException(DwarfUtil.getStringField(StringFieldKey.JExconsole_0));
			}
			
			InnerInputStream in = (InnerInputStream) JExconsole.this.in;
			in.bs = string.getBytes();
			in.pos = 0;
			in.readFlag = true;
			inputCondition.signalAll();
			textField.setVisible(false);
			textField.setText(null);
			revalidate();
		}finally {
			inputLock.unlock();
		}
	}

	/**
	 * 创建控制台的右键菜单。
	 * <p> 该方法会在初始化的时候调用，用于控制台用的菜单。
	 * @return 控制台的右键菜单。
	 */
	protected JPopupMenu createPopup(){
		return new InnerPopupMenu();
	}


	private final class InnerInputStream extends InputStream{
		
		private byte[] bs = new byte[1024];
		private int pos = 0;
		private boolean readFlag = false;
		
		/*
		 * (non-Javadoc)
		 * @see java.io.InputStream#read()
		 */
		@Override
		public int read() throws IOException {
			if(disposeFlag.get()){
				throw new IllegalStateException(DwarfUtil.getStringField(StringFieldKey.JExconsole_0));
			}
			
			inputLock.lock();
			try{
				while((! disposeFlag.get()) && !readFlag) {
					try {
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								if(! textField.isVisible()){
									textField.setVisible(true);
									textField.requestFocus();
									revalidate();
								}
							}
						});
						inputCondition.await();
					} catch (InterruptedException ignore) {
						//重新检测
					}
				}
				
				if(disposeFlag.get()) return -1;
				if(pos == bs.length){
					readFlag = false;
					return -1;
				}
				return bs[pos++];
			}finally {
				inputLock.unlock();
			}
		}

		/*
		 * (non-Javadoc)
		 * @see java.io.InputStream#markSupported()
		 */
		@Override
		public boolean markSupported() {
			return false;
		}
		
		/*
		 * (non-Javadoc)
		 * @see java.io.InputStream#close()
		 */
		@Override
		public void close() throws IOException {
			//Do nothing
		}
		
	}
	
	private final class InnerOutputStream extends OutputStream{
		
		private byte[] bs = new byte[1024];
		private int pos = 0;

		/*
		 * (non-Javadoc)
		 * @see java.io.OutputStream#write(int)
		 */
		@Override
		public void write(int b) throws IOException {
			if(disposeFlag.get()){
				throw new IllegalStateException(DwarfUtil.getStringField(StringFieldKey.JExconsole_0));
			}
			
			outputLock.lock();
			try{
				if(pos == bs.length){
					byte[] dejavu = bs;
					bs = new byte[bs.length<<1];
					System.arraycopy(dejavu, 0, bs, 0, dejavu.length);
				}
				
				bs[pos++] = NumberUtil.cutInt2Byte(b);
			}finally {
				outputLock.unlock();
			}
		}
		
		/*
		 * (non-Javadoc)
		 * @see java.io.OutputStream#flush()
		 */
		@Override
		public void flush() throws IOException {
			if(disposeFlag.get()){
				throw new IllegalStateException(DwarfUtil.getStringField(StringFieldKey.JExconsole_0));
			}
			
			String str = null;
			
			outputLock.lock();
			try{
				str = new String(Arrays.copyOfRange(bs, 0, pos));
				pos = 0;
				bs = new byte[1024];
			}finally {
				outputLock.unlock();
			}
			
			if(Objects.isNull(str)) return;
			
			renderLock.lock();
			try{
				string2Render.offer(str);
				renderCondition.signalAll();
			}finally {
				renderLock.unlock();
			}
		}
		
		/*
		 * (non-Javadoc)
		 * @see java.io.OutputStream#close()
		 */
		@Override
		public void close() throws IOException {
			//Do nothing
		}
	}
	
	private class InnerPopupMenu extends JPopupMenu{
		
		private static final long serialVersionUID = 5256502032514168869L;
		
		private final JMenuItem selectAllMenuItem;
		private final JMenuItem cleanScreenMenuItem;
		private final JCheckBoxMenuItem lineWrapMenuItem;

		public InnerPopupMenu() {
			super();
			selectAllMenuItem = add(
					new JMenuItemAction.Builder()
					.icon(new ImageIcon(DwarfUtil.class.getResource("/com/dwarfeng/dutil/resource/image/selectAll.png")))
					.name(DwarfUtil.getLabelField(LabelFieldKey.JExconsole_0, getDefaultLocale()))
					.keyStorke(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK))
					.mnemonic('A')
					.listener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if(Objects.isNull(textArea)) return;
							SwingUtilities.invokeLater(new Runnable() {
								@Override
								public void run() {
									textArea.requestFocus();
									textArea.select(0, textArea.getText().length());
								}
							});
						}
					})
					.build()
			);
			
			cleanScreenMenuItem = add(
					new JMenuItemAction.Builder()
					.icon(new ImageIcon(DwarfUtil.class.getResource("/com/dwarfeng/dutil/resource/image/cleanScreen.png")))
					.name(DwarfUtil.getLabelField(LabelFieldKey.JExconsole_1, getDefaultLocale()))
					.keyStorke(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK))
					.mnemonic('E')
					.listener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if(Objects.isNull(textArea)) return;
							SwingUtilities.invokeLater(new Runnable() {
								@Override
								public void run() {
									textArea.requestFocus();
									textArea.setText("");
								}
							});
						}
					})
					.build()
			);
			
			addSeparator();
			
			lineWrapMenuItem = new JCheckBoxMenuItem(DwarfUtil.getLabelField(LabelFieldKey.JExconsole_2, getDefaultLocale()));
			lineWrapMenuItem.setIcon(new ImageIcon(DwarfUtil.class.getResource("/com/dwarfeng/dutil/resource/image/lineWrap.png")));
			lineWrapMenuItem.setMnemonic('W');
			lineWrapMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(Objects.isNull(textArea)) return;
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							textArea.setLineWrap(lineWrapMenuItem.getState());
						}
					});
				}
			});
			add(lineWrapMenuItem);
		}
		
		/*
		 * (non-Javadoc)
		 * @see java.awt.Component#setLocale(java.util.Locale)
		 */
		@Override
		public void setLocale(Locale l) {
			selectAllMenuItem.setText(DwarfUtil.getLabelField(LabelFieldKey.JExconsole_0, l));
			cleanScreenMenuItem.setText(DwarfUtil.getLabelField(LabelFieldKey.JExconsole_1, l));
			lineWrapMenuItem.setText(DwarfUtil.getLabelField(LabelFieldKey.JExconsole_2, l));
			super.setLocale(l);
		}
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		if(Objects.isNull(popup)) return;
		
		component.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}

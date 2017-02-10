package com.dwarfeng.tp.core.model.io;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.dutil.basic.io.StreamLoader;
import com.dwarfeng.tp.core.model.struct.DefaultUnsafeToolHistory;
import com.dwarfeng.tp.core.model.struct.UnsafeToolHistory;

/**
 * xml ����ȫ������ʷ��ȡ����
 * ʹ�� xml ��ȡ����ȫ������ʷ��
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public class XmlUnsafeToolHistoryLoader extends StreamLoader<List<UnsafeToolHistory>>{

	/**
	 * ��ʵ����
	 * @param in ָ������������
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public XmlUnsafeToolHistoryLoader(InputStream in) {
		super(in);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.Loader#load(java.lang.Object)
	 */
	@Override
	public void load(List<UnsafeToolHistory> unsafeToolHistories) throws LoadFailedException {
		Objects.requireNonNull(unsafeToolHistories, "��ڲ��� unsafeToolInfos ����Ϊ null��");
		
		try{
			SAXReader reader = new SAXReader();
			
			Element root = null;
			try{
				root = reader.read(in).getRootElement();
			}finally {
				in.close();
			}
			
			/*
			 * ���� dom4j �����˵�����˴�ת���ǰ�ȫ�ġ�
			 */
			@SuppressWarnings("unchecked")
			List<Element> histories = (List<Element>)root.elements("history");
			
			next:
			for(Element history : histories){
				try{
					String name = history.attributeValue("name");
					String ranTimeStr = history.attributeValue("run_time");
					String exitedTimeStr = history.attributeValue("exit_time");
					unsafeToolHistories.add(new DefaultUnsafeToolHistory(name, ranTimeStr, exitedTimeStr));
				}catch (Exception e) {
					continue next;
				}
			}
			
		}catch (Exception e) {
			throw new LoadFailedException("������Ϣģ�Ͷ�ȡ��-�޷���ָ���Ĺ�����Ϣģ���ж�ȡ���е�����", e);
		}

	}

}

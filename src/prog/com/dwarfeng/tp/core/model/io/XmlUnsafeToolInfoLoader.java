package com.dwarfeng.tp.core.model.io;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.dutil.basic.io.StreamLoader;
import com.dwarfeng.tp.core.model.struct.DefaultUnsafeToolInfo;
import com.dwarfeng.tp.core.model.struct.UnsafeToolInfo;

/**
 * xml����ȫ������Ϣ��ȡ����
 * <p> ʹ�� xml ��ȡ����ȫ������Ϣ��
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public class XmlUnsafeToolInfoLoader extends StreamLoader<Set<UnsafeToolInfo>> {

	/**
	 * ��ʵ����
	 * @param in ָ������������
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public XmlUnsafeToolInfoLoader(InputStream in) {
		super(in);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.Loader#load(java.lang.Object)
	 */
	@Override
	public void load(Set<UnsafeToolInfo> unsafeToolInfos) throws LoadFailedException {
		Objects.requireNonNull(unsafeToolInfos, "��ڲ��� unsafeToolInfos ����Ϊ null��");
		
		try{
			SAXReader reader = new SAXReader();
			
			Element root = null;
			try{
				root = reader.read(in).getRootElement();
			}finally {
				in.close();
			}
			
			String rootDirStr = root.attributeValue("dir");
			if(Objects.isNull(rootDirStr)){
				throw new LoadFailedException("��Ԫ��ȱʧdir����");
			}
			
			File dir = new File(rootDirStr);
			
			/*
			 * ���� dom4j �����˵�����˴�ת���ǰ�ȫ�ġ�
			 */
			@SuppressWarnings("unchecked")
			List<Element> toolInfos = (List<Element>)root.elements("tool");
			next:
			for(Element toolinfo : toolInfos){
				try{
					String name = toolinfo.attributeValue("name");
					File strs = new File(dir, toolinfo.element("strs").attributeValue("url"));
					File img = new File(dir, toolinfo.element("img").attributeValue("url"));
					unsafeToolInfos.add(new DefaultUnsafeToolInfo(name, strs, img));
				}catch (Exception e) {
					continue next;
				}
			}
			
		}catch (Exception e) {
			throw new LoadFailedException("������Ϣģ�Ͷ�ȡ��-�޷���ָ���Ĺ�����Ϣģ���ж�ȡ���е�����", e);
		}

	}

}

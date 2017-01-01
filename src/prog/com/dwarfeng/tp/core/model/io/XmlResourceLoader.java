package com.dwarfeng.tp.core.model.io;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Objects;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.tp.core.control.ToolPlatform;
import com.dwarfeng.tp.core.model.struct.DefaultResource;
import com.dwarfeng.tp.core.model.vim.ResourceModel;

/**
 * xml��Դģ�Ͷ�ȡ����
 * <p> ʹ��xml��ȡ��Դģ�͡�
 * @author  DwArFeng
 * @since 1.8
 */
public class XmlResourceLoader extends StreamResourceLoader {

	/**
	 * ��ʵ����
	 * @param in ָ������������
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public XmlResourceLoader(InputStream in) {
		super(in);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.io.ResourceLoader#load(com.dwarfeng.tp.core.model.vim.ResourceModel)
	 */
	@Override
	public void load(ResourceModel resourceModel) throws LoadFailedException {
		Objects.requireNonNull(resourceModel, "��ڲ��� resourceModel ����Ϊ null��");

		try{
			SAXReader reader = new SAXReader();
			Element root = reader.read(in).getRootElement();
			
			/*
			 * ���� dom4j �����˵�����˴�ת���ǰ�ȫ�ġ�
			 */
			@SuppressWarnings("unchecked")
			List<Element> infos = (List<Element>)root.elements("info");
			
			for(Element info : infos){
				String defString = info.attributeValue("default");
				String resString = info.attributeValue("path");
				String key = info.attributeValue("key");
				
				if(Objects.isNull(defString) || Objects.isNull(resString) || Objects.isNull(key)) {
					throw new LoadFailedException("�����ļ�ȱʧ����");
				}
				
				URL def = ToolPlatform.class.getResource(defString);
				
				if(Objects.isNull(def)){
					throw new LoadFailedException("��������Դ��·������ȷ");
				}
				
				File res = new File(resString);
				
				resourceModel.put(key, new DefaultResource(def, res));
			}
			
		}catch (DocumentException e) {
			throw new LoadFailedException("�޷�����Դģ���ж�ȡ���е�����", e);
		}
		
	}

}

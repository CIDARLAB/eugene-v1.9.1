package org.cidarlab.eugene.data.registry;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.cidarlab.eugene.data.pigeon.WeyekinPoster;
import org.cidarlab.eugene.data.sbol.mapping.SBOL2Eugene;
import org.cidarlab.eugene.dom.components.Component;
import org.cidarlab.eugene.dom.components.Part;
import org.cidarlab.eugene.exception.EugeneException;
import org.sbolstandard.core.SBOLDocument;
import org.sbolstandard.core.SBOLFactory;
import org.sbolstandard.core.SBOLReader;
import org.sbolstandard.core.SBOLRootObject;

public class SBOLRegistryImporter {

	private static final String SBOL_URL = "http://convert.sbols.org/biobrick/";
	
	public SBOLRegistryImporter() {
		
	}
	
	public List<Component> importComponent(String sName) 
			throws EugeneException {
		
		if(!sName.startsWith("BBa_")) {
			sName = "BBa_"+sName;
		}
		
		List<Component> lst = new ArrayList<Component>();
		
		/*
		 * SBOL Service URL
		 */
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = null;
		try {
	        httpPost = new HttpPost(new URI(SBOL_URL+sName));
			HttpResponse response2 = httpclient.execute(httpPost);

			//System.out.println(response2.getStatusLine());
			HttpEntity entity2 = response2.getEntity();
			String mSBOLString = EntityUtils.toString(entity2);
			
			InputStream is = new ByteArrayInputStream(mSBOLString.getBytes());
			SBOLReader reader = SBOLFactory.createReader();
			SBOLDocument document = reader.read(is);
			
			if(null != document.getContents() && !document.getContents().isEmpty()) {
				for(SBOLRootObject obj : document.getContents()) {
					lst.add((Component)SBOL2Eugene.convert(obj));
				}
			}
//			EntityUtils.consume(entity2);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(null != httpPost) {
				httpPost.releaseConnection();
			}
		}

        return lst;
	}
}

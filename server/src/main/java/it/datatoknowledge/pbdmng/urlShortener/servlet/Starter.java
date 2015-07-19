package it.datatoknowledge.pbdmng.urlShortener.servlet;

import it.datatoknowledge.pbdmng.urlShortener.logic.Base;
import it.datatoknowledge.pbdmng.urlShortener.utils.Parameters;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

public class Starter extends Base {

	public Starter() {
		super();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Starter starter = new Starter();
		starter.setUp();
		LinkedHashMap<String, Object> availableServices = new LinkedHashMap<String, Object>();
		availableServices = serviceParameters.getValue(
				Parameters.AVAILABLE_SERVICES, availableServices);
		for (Map.Entry<String, Object> entry : availableServices.entrySet()) {
			try {
				Class<?> service = Class.forName((String) entry.getValue());
				Method m = service.getMethod("exposeServices");
				Object obj = service.newInstance();
				m.invoke(obj);
				starter.info(starter.loggingId, "Mapped route", service.getSimpleName());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				starter.error(starter.loggingId, e.getMessage());
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				starter.error(starter.loggingId, e.getMessage());
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				starter.error(starter.loggingId, e.getMessage());
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				starter.error(starter.loggingId, e.getMessage());
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				starter.error(starter.loggingId, e.getMessage());
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				starter.error(starter.loggingId, e.getMessage());
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				starter.error(starter.loggingId, e.getMessage());
			}
		}
	}

}

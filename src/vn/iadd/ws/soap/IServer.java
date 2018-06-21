package vn.iadd.ws.soap;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.DOCUMENT)
public interface IServer {
	@WebMethod
	String getHelloWorldAsString(String name);
	
	@WebMethod
	List<String> getHelloWorldAsList(String name, String addr);
}

package vn.iadd.ws.soap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import vn.iadd.helper.DbHelper;
import vn.iadd.model.DataShared;

@WebService(endpointInterface = "vn.iadd.ws.soap.IServer")
public class ServerImpl implements IServer {
	
	private DbHelper helper;
	
	private Logger logger;
	
	public ServerImpl() {
		this.helper = new DbHelper();
		this.logger = LogManager.getLogger(ServerImpl.class);
		log("Init class");
	}
	
	void log(String msg) {
		final String str =  Thread.currentThread().getName() + "<---" + this.getClass().getName() + "---->" + msg;
		System.out.println(str);
		//LogUtil.log(this, msg);
		
		logger.log(Level.DEBUG, str);
	}

	@Override
	public String getHelloWorldAsString(String name) {
		return name + " updated..." + DataShared.NAME;
	}

	@Override
	public List<String> getHelloWorldAsList(String name, String addr) {
		List<String> lst  = new ArrayList<>();
		lst.add(name);
		lst.add("will be");
		lst.add("updated...by " + DataShared.NAME);
		final String query = "select * from \"test\"";
		Map<String, List<Object>>  result = helper.execQuery(query);
		try {
			helper.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lst.add(result.toString());
		return lst;
	}
	public static void main(String[] args) {
		//IServer s = new ServerImpl();
		//String str = s.getHelloWorldAsList(null, null).toString();
		//System.out.println(str);
	}
}

import javax.servlet.*;
import javax.servlet.http.*;


public class LoadOnInitServlet extends HttpServlet {



    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig arg0) throws ServletException {
		super.init(arg0);
		System.out.println("******************************************************************");
		
			
			try {
				Class.forName("persistance.MediathequeData");
			} catch (ClassNotFoundException e) {
			
				e.printStackTrace();
			}
		
	}

}

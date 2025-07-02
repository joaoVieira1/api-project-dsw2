package api.transactions.dsw2.controller;

import java.io.IOException;

import api.transactions.dsw2.controller.handler.HandlerFactory;
import api.transactions.dsw2.controller.handler.InterfaceHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/transactions/*")
public class FrontController extends HttpServlet{

	private InterfaceHandler chain;
	
	@Override
	public void init(){
		try {
			chain = HandlerFactory.createChain();
		}catch(Exception e) {
			throw new RuntimeException("Erro ao inicializar a cadeia de handlers", e);
		}
	}
	
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		try {
			chain.handle(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
}

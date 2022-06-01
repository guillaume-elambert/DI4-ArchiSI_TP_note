package edu.polytech.estore.controller;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.polytech.estore.business.StoreBusinessLocal;
import edu.polytech.estore.model.Product;

/**
 * Servlet implementation class ProductsListServlet
 */
@WebServlet(name = "productsList", urlPatterns = { "/productsList" })
public class ProductsListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private StoreBusinessLocal storeBusinessLocal;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Product> products = this.storeBusinessLocal.getProducts();
        request.setAttribute("PRODUCTS", products);
        request.getRequestDispatcher("products.jsp").forward(request, response);
    }
}

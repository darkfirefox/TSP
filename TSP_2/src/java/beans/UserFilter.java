/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Павел
 */
public class UserFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        LoginController loginBean = (LoginController) ((HttpServletRequest) request).getSession().getAttribute("loginController");
        if (loginBean != null) {
            if (loginBean.user != null) {
                if (loginBean.user.getRole().equals("u")) {
                    chain.doFilter(request, response);
                } else {
                    HttpServletResponse res = (HttpServletResponse) response;
                    res.sendRedirect("index.xhtml");
                }
            } else {
                HttpServletResponse res = (HttpServletResponse) response;
                res.sendRedirect("index.xhtml");
            }
        } else {
            HttpServletResponse res = (HttpServletResponse) response;
            res.sendRedirect("index.xhtml");
        }
    }

    @Override
    public void destroy() {
    }
}

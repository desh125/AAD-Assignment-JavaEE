package com.example.backend.api;

import com.example.backend.api.util.ResponseUtil;
import com.example.backend.bo.BOFactory;
import com.example.backend.bo.custom.CustomerBO;
import com.example.backend.dto.CustomerDTO;
import jakarta.json.*;
import lombok.SneakyThrows;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

@WebServlet(urlPatterns = {"/pages/customer"})
public class CustomerServlet extends HttpServlet {
    CustomerBO customerBO = (CustomerBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ArrayList<CustomerDTO> customerArray = customerBO.getAllCustomers();

        JsonArrayBuilder allCustomers = Json.createArrayBuilder();

        for (CustomerDTO c : customerArray) {
            JsonObjectBuilder customerObject = Json.createObjectBuilder();
            customerObject.add("id", c.getId());
            customerObject.add("name", c.getName());
            customerObject.add("tp", c.getTp());
            customerObject.add("age", c.getAge());
            customerObject.add("salary", c.getSalary());
            allCustomers.add(customerObject.build());
        }

        resp.getWriter().print(ResponseUtil.genJson("Success", "Loaded", allCustomers.build()));
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String cusID = req.getParameter("cusId");
        String cusName = req.getParameter("cusName");
        String cusTp = req.getParameter("cusTp");
        int cusAge = Integer.parseInt(req.getParameter("cusAge"));
        double cusSalary = Double.parseDouble(req.getParameter("cusSalary"));

        boolean isSaved = customerBO.saveCustomer(new CustomerDTO(cusID,cusName,cusTp,cusAge,cusSalary));

        if (isSaved) {
            resp.getWriter().print(ResponseUtil.genJson("Success", cusID + " Successfully Added."));
            resp.setStatus(200);
        } else {
            resp.getWriter().print(ResponseUtil.genJson("Error", "Wrong data !"));
            resp.setStatus(400);
        }
    }

    @SneakyThrows
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();

        String cusID = jsonObject.getString("cusId");
        String cusName = jsonObject.getString("cusName");
        String cusTp = jsonObject.getString("cusTp");
        int cusAge = jsonObject.getInt("cusAge");
        double cusSalary = jsonObject.getJsonNumber("cusSalary").doubleValue();

        boolean isUpdated = customerBO.updateCustomer(new CustomerDTO(cusID, cusName, cusTp, cusAge, cusSalary));

        if (isUpdated) {
            resp.getWriter().print(ResponseUtil.genJson("Success", cusID + " Customer Updated..!"));
            resp.setStatus(200);
        } else {
            resp.getWriter().print(ResponseUtil.genJson("Failed", cusID + " Customer is not exist..!"));
            resp.setStatus(400);
        }
    }

    @SneakyThrows
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String cusID = req.getParameter("cusId");

        boolean isDeleted = customerBO.deleteCustomer(cusID);

        if (isDeleted) {
            resp.getWriter().print(ResponseUtil.genJson("Success", cusID + " Customer Deleted..!"));
            resp.setStatus(200);
        } else {
            resp.getWriter().print(ResponseUtil.genJson("Failed", "Customer with ID " + cusID + " not found."));
            resp.setStatus(400);
        }
    }
}
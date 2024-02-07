package com.example.backend.api;

import com.example.backend.api.util.ResponseUtil;
import com.example.backend.bo.BOFactory;
import com.example.backend.bo.custom.PurchaseOrderBO;
import com.example.backend.dto.CartDTO;
import com.example.backend.dto.CustomerDTO;
import com.example.backend.dto.ItemDTO;
import com.example.backend.dto.PurchaseOrderDTO;
import com.example.backend.entity.Customer;
import com.example.backend.entity.Item;
import jakarta.json.*;
import lombok.SneakyThrows;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/pages/invoice")
public class InvoiceServlet extends HttpServlet {
    private final PurchaseOrderBO purchaseOrderBO = (PurchaseOrderBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.PURCHASE_ORDER);

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            // Retrieve all purchase orders
            ArrayList<PurchaseOrderDTO> purchaseOrders = purchaseOrderBO.getAllOrderS();

            // Build JSON array for all purchase orders
            JsonArrayBuilder allPurchaseOrders = Json.createArrayBuilder();
            for (PurchaseOrderDTO purchaseOrder : purchaseOrders) {
                JsonObjectBuilder purchaseOrderObject = Json.createObjectBuilder();
                purchaseOrderObject.add("orderId", purchaseOrder.getOrderID());
                purchaseOrderObject.add("date", purchaseOrder.getDate());
                purchaseOrderObject.add("customerId", purchaseOrder.getCusTomerId());
                purchaseOrderObject.add("discount", purchaseOrder.getDiscount());
                purchaseOrderObject.add("total", purchaseOrder.getTotal());
                allPurchaseOrders.add(purchaseOrderObject);
            }

            // Set content type and status code
            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_OK);

            // Write JSON data to response
            resp.getWriter().print(allPurchaseOrders.build().toString());
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().print("{\"error\": \"An error occurred while processing the request.\"}");
        }
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (JsonReader reader = Json.createReader(req.getReader())) {
            JsonObject jsonObject = reader.readObject();
            String orderId = jsonObject.getString("orderId");
            String date = jsonObject.getString("date");
            String customerId = jsonObject.getString("customerId");
            JsonArray cart = jsonObject.getJsonArray("cart");
            String discount = jsonObject.getString("discount");
            String total = jsonObject.getString("total");

            // Process the cart array
            ArrayList<CartDTO> cartArray = new ArrayList<>();
            for (JsonValue jsonValue : cart) {
                JsonObject cartObject = (JsonObject) jsonValue;
                JsonObject itemObject = cartObject.getJsonObject("item");
                ItemDTO itemDTO = new ItemDTO();
                itemDTO.setCode(itemObject.getString("code"));
                CartDTO cartDTO = new CartDTO();
                cartDTO.setItem(itemDTO);
                cartArray.add(cartDTO);
            }

            try {
                boolean isSaved = purchaseOrderBO.purchaseOrder(new PurchaseOrderDTO(orderId, date, cartArray, Double.parseDouble(total), Integer.parseInt(discount), customerId));
                if (isSaved) {
                    resp.getWriter().print(ResponseUtil.genJson("Success", "Invoice saved successfully."));
                    resp.setStatus(HttpServletResponse.SC_OK);
                } else {
                    resp.getWriter().print(ResponseUtil.genJson("Error", "Failed to save invoice."));
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                resp.getWriter().print(ResponseUtil.genJson("Error", "An error occurred while processing the request."));
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (JsonException e) {
            e.printStackTrace();
            resp.getWriter().print(ResponseUtil.genJson("Error", "Invalid JSON data."));
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Implement code to handle PUT requests if needed
        resp.getWriter().print("PUT method not implemented for InvoiceServlet");
        resp.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }
}

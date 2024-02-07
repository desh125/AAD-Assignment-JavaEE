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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String customerId = req.getParameter("customerId");
        String itemId = req.getParameter("itemId");

        if (customerId != null) {
            // Get customer details by ID
            try {
                Customer customer = purchaseOrderBO.getCustomerById(customerId);
                if (customer != null) {
                    // Customer found, return details
                    JsonObjectBuilder customerBuilder = Json.createObjectBuilder();
                    customerBuilder.add("id", customer.getId());
                    customerBuilder.add("name", customer.getName());
                    // Add other customer attributes as needed

                    JsonObjectBuilder responseBuilder = Json.createObjectBuilder();
                    responseBuilder.add("status", "Success");
                    responseBuilder.add("message", "Customer found");
                    responseBuilder.add("customer", customerBuilder);

                    JsonObject response = responseBuilder.build();

                    resp.getWriter().print(response.toString());
                    resp.setStatus(HttpServletResponse.SC_OK);
                } else {
                    // Customer not found
                    JsonObjectBuilder responseBuilder = Json.createObjectBuilder();
                    responseBuilder.add("status", "Error");
                    responseBuilder.add("message", "Customer not found");
                    JsonObject response = responseBuilder.build();

                    resp.getWriter().print(response.toString());
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                JsonObjectBuilder responseBuilder = Json.createObjectBuilder();
                responseBuilder.add("status", "Error");
                responseBuilder.add("message", "An error occurred while processing the request.");
                JsonObject response = responseBuilder.build();

                resp.getWriter().print(response.toString());
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else if (itemId != null) {
            // Get item details by ID
            try {
                Item item = purchaseOrderBO.getItemById(itemId);
                if (item != null) {
                    // Item found, return details
                    JsonObjectBuilder itemBuilder = Json.createObjectBuilder();
                    itemBuilder.add("id", item.getCode());
                    itemBuilder.add("name", item.getName());
                    // Add other item attributes as needed

                    JsonObjectBuilder responseBuilder = Json.createObjectBuilder();
                    responseBuilder.add("status", "Success");
                    responseBuilder.add("message", "Item found");
                    responseBuilder.add("item", itemBuilder);

                    JsonObject response = responseBuilder.build();

                    resp.getWriter().print(response.toString());
                    resp.setStatus(HttpServletResponse.SC_OK);
                } else {
                    // Item not found
                    JsonObjectBuilder responseBuilder = Json.createObjectBuilder();
                    responseBuilder.add("status", "Error");
                    responseBuilder.add("message", "Item not found");
                    JsonObject response = responseBuilder.build();

                    resp.getWriter().print(response.toString());
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                JsonObjectBuilder responseBuilder = Json.createObjectBuilder();
                responseBuilder.add("status", "Error");
                responseBuilder.add("message", "An error occurred while processing the request.");
                JsonObject response = responseBuilder.build();

                resp.getWriter().print(response.toString());
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            // No specific ID provided, return all IDs
            try {
                // Get all customer IDs
                ArrayList<String> allCustomerIds = purchaseOrderBO.getAllCustomers();
                // Get all item IDs
                ArrayList<String> allItemIds = purchaseOrderBO.getAllItems();

                // Construct JSON response
                JsonObjectBuilder responseBuilder = Json.createObjectBuilder();
                responseBuilder.add("customerIds", Json.createArrayBuilder(allCustomerIds));
                responseBuilder.add("itemIds", Json.createArrayBuilder(allItemIds));

                JsonObject response = responseBuilder.build();

                resp.getWriter().print(response.toString());
                resp.setStatus(HttpServletResponse.SC_OK);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                JsonObjectBuilder responseBuilder = Json.createObjectBuilder();
                responseBuilder.add("status", "Error");
                responseBuilder.add("message", "An error occurred while processing the request.");
                JsonObject response = responseBuilder.build();

                resp.getWriter().print(response.toString());
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String orderId = req.getParameter("orderId");
        String date = req.getParameter("date");
        String customerId = req.getParameter("customerId");
        JsonArray cart = jsonObject.getJsonArray("cart");
        String discount = req.getParameter("discount");
        String total = req.getParameter("total");
        ArrayList<CartDTO> cartArray = new ArrayList<>();
        for (JsonValue jsonValue : cart) {
            JsonObject cartObject = (JsonObject) jsonValue;

            // Extract item and qty values from the JSON object
            JsonObject itemObject = cartObject.getJsonObject("item");
           // int qty = cartObject.getInt("qty");

            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setCode(itemObject.getString("code"));
     //       itemDTO.setQtyOnHand(itemObject.getInt("qty"));

            CartDTO cartDTO = new CartDTO();
            cartDTO.setItem(itemDTO);
       //     cartDTO.setQty(qty);

            cartArray.add(cartDTO);
        }

        try {
            boolean isSaved = purchaseOrderBO.purchaseOrder(new PurchaseOrderDTO(orderId, date, cartArray,Double.parseDouble(total),Integer.parseInt(discount),customerId));
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
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Implement code to handle PUT requests if needed
        resp.getWriter().print("PUT method not implemented for InvoiceServlet");
        resp.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }
}

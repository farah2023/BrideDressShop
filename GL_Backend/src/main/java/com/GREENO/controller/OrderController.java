package com.agropharm.controller;

import com.agropharm.Entities.Order;
import com.agropharm.Entities.User;
import com.agropharm.Entities.enums.OrderStatus;
import com.agropharm.dto.OrderDTO;
import com.agropharm.dto.OrderRequestDTO;
import com.agropharm.dto.UserDTO;
import com.agropharm.mapper.DTOUtils;
import com.agropharm.service.NotificationService;
import com.agropharm.service.OrderService;
import com.agropharm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/all")
    public ResponseEntity<Set<OrderDTO>> getAll(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        System.out.println("Authorization header: " + authHeader);

        Set<OrderDTO> orderDTOs = (Set<OrderDTO>) new DTOUtils().convertToDtos(orderService.getAll(), new OrderDTO());
        return new ResponseEntity<>(orderDTOs, HttpStatus.OK);
    }

    @GetMapping(value = "/by-client")
    public ResponseEntity<Set<OrderDTO>> getByClient(Principal user) {
        User client = userService.getByEmail(user.getName());
        Set<Order> orders = orderService.getAllOrdersByClientId(client.getId());
        Set<OrderDTO> orderDTOs = new DTOUtils().convertToDtos(orders, new OrderDTO());
        return new ResponseEntity<>(orderDTOs, HttpStatus.OK);
    }

    @PostMapping("/approve/{orderId}")
    public ResponseEntity<String> approveOrder(@PathVariable Integer orderId) {
        try {
            orderService.updateOrderStatus(orderId, OrderStatus.APPROVED);
            Order order = orderService.getById(orderId);
            String content = "Your order number " + order.getId() + " has been approved.";
            notificationService.createNotification("Order Approved", content, order.getClient().getEmail());
            String content2 = "New order is ready for pickup.";
            notificationService.createNotificationForAllUsersByRole("Order Ready for Pickup", content2, "DELIVERER");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"message\": \"Bad request\"}");
        }
        return ResponseEntity.ok().body("{\"message\": \"Order approved\"}");
    }

    @PostMapping("/reject/{orderId}")
    public ResponseEntity<String> rejectOrder(@PathVariable Integer orderId) {
        try {
            orderService.updateOrderStatus(orderId, OrderStatus.REJECTED);
            Order order = orderService.getById(orderId);
            String content = "Your order number " + order.getId() + " has been rejected due to incorrect address entry.";
            notificationService.createNotification("Order Rejected", content, order.getClient().getEmail());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"message\": \"Bad request\"}");
        }
        return ResponseEntity.ok().body("{\"message\": \"Order rejected successfully\"}");
    }

    @PostMapping(value = "/cancel/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable Integer orderId, Principal user) {
        try {
            orderService.updateOrderStatus(orderId, OrderStatus.CANCELLED);
            User client = userService.getByEmail(user.getName());
            userService.awardPenaltyPoints(client.getId());
            Order order = orderService.getById(orderId);
            String content = "Due to cancellation of order number " + order.getId() + ", you have received 5 penalty points.";
            notificationService.createNotification("Penalty Points", content, order.getClient().getEmail());
            String content2 = "Order number " + order.getId() + " has been cancelled.";
            notificationService.createNotificationForAllUsersByRole("Order Cancelled", content2, "SELLER");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"message\": \"Bad request\"}");
        }
        return ResponseEntity.ok().body("{\"message\": \"Order cancelled\"}");
    }

    @PostMapping(value = "/create")
    public ResponseEntity<String> createOrder(@RequestBody OrderRequestDTO orderRequest, Principal user) {
        try {
            User client = userService.getByEmail(user.getName());
            UserDTO userDTO = (UserDTO) new DTOUtils().convertToDto(client, new UserDTO());
            orderRequest.setClient(userDTO);
            Order createdOrder = orderService.createOrder(orderRequest);
            String content = "New order created with number " + createdOrder.getId() + ".";
            notificationService.createNotificationForAllUsersByRole("New Order", content, "SELLER");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"message\":\"" + e.getMessage() + "\"}");
        }
        return ResponseEntity.ok().body("{\"message\": \"You have successfully made an order\"}");
    }

}

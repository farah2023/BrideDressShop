package com.agropharm.controller;

import com.agropharm.Entities.Order;
import com.agropharm.Entities.OrderItem;
import com.agropharm.Entities.User;
import com.agropharm.Entities.enums.OrderStatus;
import com.agropharm.dto.OrderDTO;
import com.agropharm.dto.OrderRequestDTO;
import com.agropharm.dto.UserDTO;
import com.agropharm.mapper.DTOUtils;
import com.agropharm.service.EmailService;
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

    @Autowired
    private EmailService emailService;

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

    @PostMapping(value = "/collect-for-delivery/{orderId}")
    public ResponseEntity<String> collectOrderForDelivery(@PathVariable Integer orderId, Principal user) {
        try {
            orderService.updateOrderStatus(orderId, OrderStatus.COLLECTED_FOR_DELIVERY);
            User deliverer = userService.getByEmail(user.getName());
            orderService.assignDeliverer(orderId, deliverer);
            Order order = orderService.getById(orderId);


            String content = "Products from order number " + order.getId() + " have been collected for delivery.";
            notificationService.createNotification("Package Collected for Delivery", content, order.getClient().getEmail());


            String toEmail = order.getClient().getEmail();
            String subject = "Order #" + order.getId() + " Collected for Delivery";
            String emailBody = "Dear " + order.getClient().getFirstName() + ",\n\n" +
                    "Your order #" + order.getId() + " has been collected and is now out for delivery. " +
                    "Our delivery person " + deliverer.getFirstName() + " " + deliverer.getLastName() +
                    " will deliver your package soon.\n\n" +
                    "Order Details:\n" +
                    "Delivery Address: " + order.getAddress().getStreet() + " " +
                    order.getAddress().getStreetNumber() + ", " +
                    order.getAddress().getCity() + "\n\n" +
                    "Thank you for choosing our service!\n\n" +
                    "Best regards,\n" +
                    "Your Company Name";

            emailService.sendEmail(toEmail, subject, emailBody);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"message\": \"Bad request\"}");
        }
        return ResponseEntity.ok().body("{\"message\": \"Order collected for delivery\"}");
    }

    @PostMapping(value = "/completed-success/{orderId}")
    public ResponseEntity<String> completeOrderDeliverySuccessfully(@PathVariable Integer orderId) {
        try {
            orderService.updateOrderStatus(orderId, OrderStatus.DELIVERY_COMPLETED_SUCCESSFULLY);
            Order order = orderService.getById(orderId);


            String content = "Your order #" + order.getId() + " has been successfully delivered.";
            notificationService.createNotification("Order Delivered Successfully", content, order.getClient().getEmail());


            String toEmail = order.getClient().getEmail();
            String subject = "Order #" + order.getId() + " Successfully Delivered";
            String emailBody = "Dear " + order.getClient().getFirstName() + ",\n\n" +
                    "We're pleased to inform you that your order #" + order.getId() + " has been successfully delivered. " +
                    "We hope you are satisfied with our service.\n\n" +
                    "Order Details:\n" +
                    "Delivery Address: " + order.getAddress().getStreet() + " " +
                    order.getAddress().getStreetNumber() + ", " +
                    order.getAddress().getCity() + "\n\n" +
                    "Items:\n";


            for (OrderItem item : order.getOrderItems()) {
                emailBody += "- " + item.getProduct().getName() + " x" + item.getQuantity() + "\n";
            }

            emailBody +=
                    "Thank you for choosing our service!\n\n" +
                    "Best regards,\n" +
                    "Your Company Name";

            emailService.sendEmail(toEmail, subject, emailBody);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"message\": \"Bad request\"}");
        }
        return ResponseEntity.ok().body("{\"message\": \"Order delivery completed successfully\"}");
    }

    @PostMapping(value = "/completed-notsuccess/{orderId}")
    public ResponseEntity<String> completeOrderDeliveryUnsuccessfully(@PathVariable Integer orderId) {
        try {
            orderService.updateOrderStatus(orderId, OrderStatus.DELIVERY_COMPLETED_UNSUCCESSFULLY);
            Order order = orderService.getById(orderId);

            String content = "Your order number " + order.getId() + " was not successfully received.";
            notificationService.createNotification("Order Not Collected", content, order.getClient().getEmail());


            String toEmail = order.getClient().getEmail();
            String subject = "Delivery Unsuccessful for Order #" + order.getId();
            String emailBody = "Dear " + order.getClient().getFirstName() + ",\n\n" +
                    "We regret to inform you that the delivery of your order #" + order.getId() + " was unsuccessful. " +
                    "As a result, you have received 5 penalty points.\n\n" +
                    "Please contact our support team for further assistance.\n\n" +
                    "Best regards,\n" +
                    "Your Company Name";

            emailService.sendEmail(toEmail, subject, emailBody);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"message\": \"Bad request\"}");
        }
        return ResponseEntity.ok().body("{\"message\": \"Order delivery marked as unsuccessful\"}");
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

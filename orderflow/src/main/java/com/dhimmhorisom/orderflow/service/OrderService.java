package com.dhimmhorisom.orderflow.service;

import com.dhimmhorisom.orderflow.dto.OrderItemRequestDTO;
import com.dhimmhorisom.orderflow.dto.OrderItemResponseDTO;
import com.dhimmhorisom.orderflow.dto.OrderRequestDTO;
import com.dhimmhorisom.orderflow.dto.OrderResponseDTO;
import com.dhimmhorisom.orderflow.entity.Order;
import com.dhimmhorisom.orderflow.entity.OrderItem;
import com.dhimmhorisom.orderflow.entity.Product;
import com.dhimmhorisom.orderflow.entity.User;
import com.dhimmhorisom.orderflow.enums.OrderStatus;
import com.dhimmhorisom.orderflow.exception.InsufficientStockException;
import com.dhimmhorisom.orderflow.exception.ProductNotFoundException;
import com.dhimmhorisom.orderflow.exception.UserNotFoundException;
import com.dhimmhorisom.orderflow.repository.OrderRepository;
import com.dhimmhorisom.orderflow.repository.ProductRepository;
import com.dhimmhorisom.orderflow.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dhimmhorisom.orderflow.exception.OrderNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository,
                        UserRepository userRepository,
                        ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public OrderResponseDTO create(OrderRequestDTO dto) {
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemRequestDTO itemDTO : dto.items()) {
            Product product = productRepository.findById(itemDTO.productId())
                    .orElseThrow(() -> new ProductNotFoundException("Produto não encontrado"));

            if (product.getStock() < itemDTO.quantity()) {
                throw new InsufficientStockException(
                        "Estoque insuficiente para o produto: " + product.getName()
                );
            }

            BigDecimal subtotal = product.getPrice()
                    .multiply(BigDecimal.valueOf(itemDTO.quantity()));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDTO.quantity());
            orderItem.setUnitPrice(product.getPrice());
            orderItem.setSubtotal(subtotal);

            orderItems.add(orderItem);
            totalAmount = totalAmount.add(subtotal);

            product.setStock(product.getStock() - itemDTO.quantity());
            productRepository.save(product);
        }

        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);

        return toResponse(savedOrder);
    }

    private OrderResponseDTO toResponse(Order order) {
        List<OrderItemResponseDTO> items = order.getItems()
                .stream()
                .map(item -> new OrderItemResponseDTO(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        item.getSubtotal()
                ))
                .toList();

        return new OrderResponseDTO(
                order.getId(),
                order.getUser().getId(),
                order.getUser().getName(),
                order.getTotalAmount(),
                order.getStatus().name(),
                order.getCreatedAt(),
                items
        );
    }

    public List<OrderResponseDTO> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<OrderResponseDTO> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public OrderResponseDTO updateStatus(Long id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Pedido não encontrado"));

        OrderStatus newStatus = OrderStatus.valueOf(status);

        // se cancelar, devolve estoque
        if (newStatus == OrderStatus.CANCELED && order.getStatus() != OrderStatus.CANCELED) {
            for (OrderItem item : order.getItems()) {
                Product product = item.getProduct();
                product.setStock(product.getStock() + item.getQuantity());
            }
        }

        order.setStatus(newStatus);

        Order updated = orderRepository.save(order);

        return toResponse(updated);
    }
}
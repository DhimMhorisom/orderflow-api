package com.dhimmhorisom.orderflow.service;

import com.dhimmhorisom.orderflow.dto.OrderItemRequestDTO;
import com.dhimmhorisom.orderflow.dto.OrderRequestDTO;
import com.dhimmhorisom.orderflow.dto.OrderResponseDTO;
import com.dhimmhorisom.orderflow.entity.Order;
import com.dhimmhorisom.orderflow.entity.Product;
import com.dhimmhorisom.orderflow.entity.User;
import com.dhimmhorisom.orderflow.enums.OrderStatus;
import com.dhimmhorisom.orderflow.exception.InsufficientStockException;
import com.dhimmhorisom.orderflow.repository.OrderRepository;
import com.dhimmhorisom.orderflow.repository.ProductRepository;
import com.dhimmhorisom.orderflow.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderService service;

    private User user;
    private Product product;
    private OrderRequestDTO orderRequestDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("Dhim");

        product = new Product();
        product.setId(1L);
        product.setName("Notebook");
        product.setPrice(new BigDecimal("3500.00"));
        product.setStock(10);
        product.setActive(true);

        OrderItemRequestDTO itemDTO = new OrderItemRequestDTO(1L, 2);

        orderRequestDTO = new OrderRequestDTO(
                1L,
                List.of(itemDTO)
        );
    }

    @Test
    void shouldCreateOrderSuccessfully() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order order = invocation.getArgument(0);
            order.setId(1L);
            return order;
        });

        OrderResponseDTO response = service.create(orderRequestDTO);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals(1L, response.userId());
        assertEquals("Dhim", response.userName());
        assertEquals("PENDING", response.status());
        assertEquals(new BigDecimal("7000.00"), response.totalAmount());

        assertEquals(1, response.items().size());
        assertEquals("Notebook", response.items().get(0).productName());
        assertEquals(2, response.items().get(0).quantity());
        assertEquals(new BigDecimal("3500.00"), response.items().get(0).unitPrice());
        assertEquals(new BigDecimal("7000.00"), response.items().get(0).subtotal());

        assertEquals(8, product.getStock());

        verify(userRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(product);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void shouldThrowExceptionWhenStockIsInsufficient() {
        product.setStock(1);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        InsufficientStockException exception = assertThrows(
                InsufficientStockException.class,
                () -> service.create(orderRequestDTO)
        );

        assertEquals("Estoque insuficiente para o produto: Notebook", exception.getMessage());

        verify(userRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).findById(1L);
        verify(orderRepository, never()).save(any(Order.class));
    }
}
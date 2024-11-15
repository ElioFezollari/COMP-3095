package ca.gbc.orderservice.service;

import ca.gbc.orderservice.client.InventoryClient;
import ca.gbc.orderservice.dto.OrderRequest;
import ca.gbc.orderservice.model.Order;
import ca.gbc.orderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
@EnableFeignClients(basePackages = "ca.gbc.orderservice.client")
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    public final InventoryClient inventoryClient;

    @Override
    public void placeOrder(OrderRequest orderRequest) {
        // Check order

        var productInStock=inventoryClient.isInStack(orderRequest.skuCode(), orderRequest.quantity());

        if(productInStock){
        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .price(orderRequest.price())
                .skuCode(orderRequest.skuCode())
                .quantity(orderRequest.quantity())
                .build();
        orderRepository.save(order);
        }
        else{
            throw new RuntimeException("Product with skuCode " + orderRequest.skuCode() + " is not in stock");
        }
    }
}

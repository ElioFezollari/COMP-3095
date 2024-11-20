package ca.gbc.orderservice.client;


import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

public interface InventoryClient {
    @GetExchange("/api/inventory")
    boolean isInStack(@RequestParam String skuCode, @RequestParam Integer quantity);
}

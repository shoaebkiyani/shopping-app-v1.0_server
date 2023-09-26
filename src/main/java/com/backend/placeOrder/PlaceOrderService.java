package com.backend.placeOrder;

import com.backend.cart.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PlaceOrderService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private PlaceOrderRepository placeOrderRepository;

    public List<PlaceOrder> getOrder() {
        return placeOrderRepository.findAll();
    }

    public PlaceOrder placeOrder(PlaceOrderDTO placeOrderDTO, UUID cartId) {
        PlaceOrder placeOrder = new PlaceOrder();
        placeOrder.setFirstName(placeOrderDTO.getFirstName());
        placeOrder.setLastName(placeOrderDTO.getLastName());
        placeOrder.setAddress(placeOrderDTO.getAddress());

        cartRepository.findById(cartId).ifPresent(placeOrder::setCart);

        return placeOrderRepository.save(placeOrder);
    }
}

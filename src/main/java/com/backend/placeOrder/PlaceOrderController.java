package com.backend.placeOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = {"https://techzoneapi.netlify.app/", "http://localhost:5173/"})
@RequestMapping("api/v1")
@RestController
public class PlaceOrderController {
    @Autowired
    private PlaceOrderService placeOrderService;

    @GetMapping("/order")
    public ResponseEntity<List<PlaceOrder>> getOrder() {
        return new ResponseEntity<List<PlaceOrder>>(placeOrderService.getOrder(), HttpStatus.OK);
    }

    @PostMapping("/order/place-order/{cartId}")
    public ResponseEntity<PlaceOrder> placeOrder(@RequestBody PlaceOrderDTO placeOrderDTO, @PathVariable UUID cartId) {
        PlaceOrder placeOrder = placeOrderService.placeOrder(placeOrderDTO, cartId);
        return ResponseEntity.ok(placeOrder);
    }
}

package ecommerce.api.controller;

import ecommerce.api.entity.Cart;
import ecommerce.api.entity.CartItem;
import ecommerce.api.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Cart> getCartByUserId(@PathVariable Integer userId) {
        try {
            Cart cart = cartService.getCartByUserId(userId);
            return new ResponseEntity<>(cart, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add/{cartId}/{productId}/{quantity}")
    public ResponseEntity<CartItem> addItemToCart(@PathVariable Integer cartId, @PathVariable Integer productId, @PathVariable Integer quantity) {
        try {
            CartItem cartItem = cartService.addItemToCart(cartId, productId, quantity);
            return new ResponseEntity<>(cartItem, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<Void> removeItemFromCart(@PathVariable Integer cartItemId) {
        try {
            cartService.removeItemFromCart(cartItemId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/clear/{cartId}")
    public ResponseEntity<Void> clearCart(@PathVariable Integer cartId) {
        try {
            cartService.clearCart(cartId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

package ecommerce.api.service;

import ecommerce.api.entity.User;
import ecommerce.api.entity.Cart;
import ecommerce.api.entity.CartItem;
import ecommerce.api.entity.Product;
import ecommerce.api.repository.CartItemRepository;
import ecommerce.api.repository.CartRepository;
import ecommerce.api.repository.ProductRepository;
import ecommerce.api.repository.UserRepository;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public Optional<User> findUserById(Integer userId) {
        return userRepository.findById(userId);
    }
    

    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository, 
                       ProductRepository productRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public Cart getCartByUserId(Integer userId) {
        // Tìm user theo ID (nếu không tìm thấy, ném lỗi)
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    
        // Tìm cart theo userId, nếu không tồn tại thì tạo cart mới
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUser(user); // Gắn user vào cart
                    return cartRepository.save(cart); // Lưu cart mới
                });
    }
    
    

    public CartItem addItemToCart(Integer cartId, Integer productId, Integer quantity) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cartId, productId)
                .orElseGet(() -> {
                    CartItem newItem = new CartItem();
                    newItem.setCart(cart);
                    newItem.setProduct(product);
                    newItem.setQuantity(0);
                    return cartItemRepository.save(newItem);
                });
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        return cartItemRepository.save(cartItem);
    }

    public void removeItemFromCart(Integer cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    public void clearCart(Integer cartId) {
        cartItemRepository.deleteByCartId(cartId);
    }
}

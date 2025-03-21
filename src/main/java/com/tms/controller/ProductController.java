package com.tms.controller;

import com.tms.model.Product;
import com.tms.service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/create")
    public String getProductPage(){
        return "createProduct";
    }

    @PostMapping("/create")
    public String createProduct(@ModelAttribute("product") Product product, Model model, HttpServletResponse response){
        Optional<Product> createdProduct = productService.createProduct(product);
        if(createdProduct.isEmpty()){
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            model.addAttribute("message", "Product creation failed");
            return "innerError";
        }
        return "redirect:/product/all-products";
    }

    @GetMapping("/edit/{id}")
    public String getProductEditPage(@PathVariable("id") Long productId, Model model, HttpServletResponse response){
        Optional<Product> product = productService.getProductById(productId);
        if(product.isEmpty()){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            model.addAttribute("message", "Product not found id=" + productId);
            return "innerError";
        }
        model.addAttribute("product", product.get());
        return "editProduct";
    }

    @PostMapping("/{id}")
    public String getProductById(@PathVariable("id") Long id, Model model, HttpServletResponse response){
        Optional<Product> product = productService.getProductById(id);
        if(product.isEmpty()){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            model.addAttribute("message", "Product not found id=" + id);
            return "innerError";
        }
        response.setStatus(HttpServletResponse.SC_OK);
        model.addAttribute("product", product.get());
        return "product";
    }

    @PostMapping("/update")
    public String updateProduct(@ModelAttribute("product") Product product, Model model, HttpServletResponse response){
        Optional<Product> updatedProduct = productService.updateProduct(product);
        if(updatedProduct.isEmpty()){
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            model.addAttribute("message", "Product update failed");
            return "innerError";
        }
        response.setStatus(HttpServletResponse.SC_OK);
        return "redirect:/product/all-products";
    }

    @PostMapping("/delete")
    public String deleteProduct(@RequestParam("id") Long id, Model model, HttpServletResponse response){
        Optional<Product> deletedProduct = productService.deleteProduct(id);
        if(deletedProduct.isPresent()){
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            model.addAttribute("message", "Product is not deleted");
            return "innerError";
        }
        return "redirect:/product/all-products";
    }

    //getAll
    @GetMapping("/all-products")
    public String getUserListPage(Model model, HttpServletResponse response) {
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            model.addAttribute("message", "Products not found");
            return "innerError";
        }
        response.setStatus(HttpServletResponse.SC_OK);
        model.addAttribute("products", products);
        return "products";
    }
}

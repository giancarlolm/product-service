package com.mitocode.product_service.controller;

import com.mitocode.product_service.dto.ProductRequest;
import com.mitocode.product_service.entity.Product;
import com.mitocode.product_service.entitynosql.ProductNoSql;
import com.mitocode.product_service.imp.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@Slf4j
@Tag(name = "Products", description = "API de productos")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    ProductService productService;

    // Inyectamos el contexto que sabe el WebServer y su puerto
    private final WebServerApplicationContext webServerAppCtxt;

    public ProductController(WebServerApplicationContext webServerAppCtxt) {
        this.webServerAppCtxt = webServerAppCtxt;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un producto por ID",
            description = "Retorna un producto dado su ID.")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @GetMapping("/name")
    public String getName() {
        return "productName";
    }

    @GetMapping("/reader/{id}")
    public ResponseEntity<ProductNoSql> getReadById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getReadById(id));
    }

    @GetMapping("/reader")
    public ResponseEntity<ProductNoSql> getReadByName(@RequestParam("name") String name) {
        log.info("Nombre: {}", name);
        return ResponseEntity.ok(productService.getReadByName(name));
    }

    @GetMapping("/helloWorld")
    public String helloWorld() {
        return "Hello world from Service A!";
    }

    @GetMapping("hola")
    public String Hola() {
        return "Hola desde Producto " + webServerAppCtxt.getWebServer().getPort();
    }


    @PostMapping
    @Operation(
            summary = "Crear un nuevo producto",
            description = "Crea un nuevo producto en el sistema con toda la información requerida"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductRequest.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "409", description = "Ya existe un producto con el mismo SKU",
                    content = @Content(mediaType = "application/json"))
    })
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Product> createProduct(
            @RequestBody @Parameter(description = "Datos para creación del producto", required = true)
            @Valid ProductRequest request
    ) {
        Product response = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}

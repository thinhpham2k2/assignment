package com.assignment.core_service.controller;

import com.assignment.core_service.dto.product.CreateProductDTO;
import com.assignment.core_service.dto.product.ProductDTO;
import com.assignment.core_service.dto.product.UpdateProductDTO;
import com.assignment.core_service.service.interfaces.ProductService;
import com.assignment.core_service.util.Constant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "\uD83D\uDCE6 Product API")
@RequestMapping("/api/v1/core/products")
public class ProductController {

    private final MessageSource messageSource;

    private final ProductService productService;

    @GetMapping("")
    @Operation(summary = "Get product list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = PagedModel.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> getList(@RequestParam(defaultValue = "")
                                     @Parameter(description = "<b>Filter by category ID<b>")
                                     List<Long> categoryIds,
                                     @RequestParam(defaultValue = "")
                                     @Parameter(description = "<b>Filter by supplier ID<b>")
                                     List<Long> supplierIds,
                                     @RequestParam(defaultValue = "") String search,
                                     @RequestParam(defaultValue = "id,desc") String sort,
                                     @RequestParam(defaultValue = "0") Integer page,
                                     @RequestParam(defaultValue = "10") Integer limit)
            throws MethodArgumentTypeMismatchException {

        PagedModel<ProductDTO> list = productService.findAllByCondition(
                categoryIds, supplierIds, search, sort, page, limit);

        if (!list.getContent().isEmpty()) {

            return ResponseEntity.status(HttpStatus.OK).body(list);
        } else {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.TEXT_PLAIN).body(
                    messageSource.getMessage(Constant.NOT_FOUND, null, LocaleContextHolder.getLocale()));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = ProductDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> getById(@PathVariable(value = "id") Long id)
            throws MethodArgumentTypeMismatchException {

        ProductDTO product = productService.findById(id);

        if (product != null) {

            return ResponseEntity.status(HttpStatus.OK).body(product);
        } else {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.TEXT_PLAIN).body(
                    messageSource.getMessage(Constant.NOT_FOUND, null, LocaleContextHolder.getLocale()));
        }
    }

    @PostMapping(value = "")
    @Secured({Constant.ADMIN})
    @SecurityRequirement(name = "Authorization")
    @Operation(summary = "Create product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "403", description = "Access Denied", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> create(@RequestBody @Validated CreateProductDTO create)
            throws MethodArgumentTypeMismatchException {

        productService.create(create);

        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.TEXT_PLAIN).body(
                messageSource.getMessage(Constant.CREATE_SUCCESS, null, LocaleContextHolder.getLocale()));
    }

    @PutMapping("/{id}")
    @Secured({Constant.ADMIN})
    @SecurityRequirement(name = "Authorization")
    @Operation(summary = "Update product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "403", description = "Access Denied", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id,
                                    @RequestBody @Validated UpdateProductDTO update)
            throws MethodArgumentTypeMismatchException {

        productService.update(update, id);

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.TEXT_PLAIN).body(
                messageSource.getMessage(Constant.UPDATE_SUCCESS, null, LocaleContextHolder.getLocale()));
    }

    @DeleteMapping("/{id}")
    @Secured({Constant.ADMIN})
    @SecurityRequirement(name = "Authorization")
    @Operation(summary = "Delete product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "Fail", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "403", description = "Access Denied", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id)
            throws MethodArgumentTypeMismatchException {

        productService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).contentType(MediaType.TEXT_PLAIN).body(
                messageSource.getMessage(Constant.DELETE_SUCCESS, null, LocaleContextHolder.getLocale()));
    }
}

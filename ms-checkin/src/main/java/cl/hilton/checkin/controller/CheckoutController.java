package cl.hilton.checkin.controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.hilton.checkin.dto.CheckoutRequest;
import cl.hilton.checkin.dto.CheckoutResponse;
import cl.hilton.checkin.service.CheckoutService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/checkouts")
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;

    private CheckoutResponse addLinks(CheckoutResponse c) {
        c.add(linkTo(methodOn(CheckoutController.class).findById(c.getId())).withSelfRel());
        c.add(linkTo(methodOn(CheckoutController.class).update(c.getId(), null)).withRel("update"));
        c.add(linkTo(CheckoutController.class).slash(c.getId()).withRel("delete"));
        c.add(linkTo(methodOn(CheckoutController.class).findAll()).withRel("all"));
        return c;
    }

    @Operation(summary = "Listar checkouts", description = "Retorna todos los checkouts registrados en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Checkouts encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = CheckoutResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron checkouts", content = @Content)
    })
    @GetMapping
    public CollectionModel<CheckoutResponse> findAll() {
        List<CheckoutResponse> list = checkoutService.findAll();
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(CheckoutController.class).findAll()).withSelfRel());
    }

    @Operation(summary = "Obtener checkout por ID", description = "Retorna un checkout según su identificador único")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Checkout encontrado",
            content = @Content(schema = @Schema(implementation = CheckoutResponse.class))),
        @ApiResponse(responseCode = "404", description = "Checkout no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public CheckoutResponse findById(@PathVariable Long id) {
        return addLinks(checkoutService.findById(id));
    }

    @Operation(summary = "Obtener checkout por reserva", description = "Retorna un checkout según el código de reserva")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Checkout encontrado",
            content = @Content(schema = @Schema(implementation = CheckoutResponse.class))),
        @ApiResponse(responseCode = "404", description = "Checkout no encontrado para la reserva indicada", content = @Content)
    })
    @GetMapping("/reserva/{codigoReserva}")
    public CheckoutResponse findByCodigoReserva(@PathVariable String codigoReserva) {
        return addLinks(checkoutService.findByCodigoReserva(codigoReserva));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CheckoutResponse create(@Valid @RequestBody CheckoutRequest request) {
        return addLinks(checkoutService.create(request));
    }

    @PutMapping("/{id}")
    public CheckoutResponse update(@PathVariable Long id, @Valid @RequestBody CheckoutRequest request) {
        return addLinks(checkoutService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        checkoutService.deleteById(id);
    }
}

package br.com.kebos.controller;

import br.com.kebos.dto.SellerDTO;
import br.com.kebos.model.Seller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SellerController {

    @Operation(summary = "Listar todos os Vendedores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Encontrou todos Vendedores", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Seller.class)) }),
            @ApiResponse(responseCode = "500", description = "Problema no servidor", content = @Content) })
    ResponseEntity<List<Seller>> findAll();

    @Operation(summary = "Pesquisa vendedor por id do vendedor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vendedor encontrado", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Seller.class)) }),
            @ApiResponse(responseCode = "400", description = "Id do vendedor inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Item não encontrado", content = @Content) })
    ResponseEntity<Seller> findByIdSeller(long id);

    @Operation(summary = "Salva um vendedor na base de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vendedor salvo com sucesso", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Seller.class)) }),
            @ApiResponse(responseCode = "400", description = "Verifique os dados do vendedor antes de salvar", content = @Content),
            @ApiResponse(responseCode = "500", description = "Problema no servidor aguarde", content = @Content) })
    ResponseEntity<Seller> saveSeller(SellerDTO sellerDTO);
}

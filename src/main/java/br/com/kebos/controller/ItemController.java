package br.com.kebos.controller;


import br.com.kebos.model.Item;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;



import java.util.List;

public interface ItemController {

    @Operation(summary = "Listar todos os Itens")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Encontrou todos Itens", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Item.class)) }),
            @ApiResponse(responseCode = "500", description = "Problema no servidor", content = @Content) })
    ResponseEntity<List<Item>> findAll();

    @Operation(summary = "Pesquisa item por id do item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item encontrado", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Item.class)) }),
            @ApiResponse(responseCode = "400", description = "Id do item inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Item não encontrado", content = @Content) })
    ResponseEntity<Item> findByIdItem(Long id);

    @Operation(summary = "Salva um item na base de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item salvo com sucesso", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Item.class)) }),
            @ApiResponse(responseCode = "400", description = "Verifique os dados antes de salvar", content = @Content),
            @ApiResponse(responseCode = "500", description = "Problema no servidor aguarde", content = @Content) })
    ResponseEntity<Item> saveItem(Item item);

}

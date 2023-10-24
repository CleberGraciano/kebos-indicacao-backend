package br.com.kebos.controller;


import br.com.kebos.model.Category;
import br.com.kebos.model.Item;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CategoryController {

    @Operation(summary = "Salva uma categoria na base de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria salva com sucesso", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Item.class)) }),
            @ApiResponse(responseCode = "400", description = "Verifique os dados antes de salvar", content = @Content),
            @ApiResponse(responseCode = "500", description = "Problema no servidor aguarde", content = @Content) })
    ResponseEntity<Category> saveCategory(Category category);

    @Operation(summary = "Listar todos as Categorias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Encontrou todas Categorias", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Item.class)) }),
            @ApiResponse(responseCode = "500", description = "Problema no servidor", content = @Content) })
    ResponseEntity<List<Category>> findAllCategory();

    @Operation(summary = "Pesquisa Categoria por id do categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria encontrada", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Item.class)) }),
            @ApiResponse(responseCode = "400", description = "Id da Categoria inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrado", content = @Content) })
    ResponseEntity<Category> findByIdCategory(Long id);

    @Operation(summary = "Atualiza uma Categoria na base de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria salvo com sucesso", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Item.class)) }),
            @ApiResponse(responseCode = "400", description = "Verifique os dados antes de salvar", content = @Content),
            @ApiResponse(responseCode = "500", description = "Problema no servidor aguarde", content = @Content) })
    ResponseEntity<?> updateCategory(@PathVariable(name = "id") Long id, Category category);

}

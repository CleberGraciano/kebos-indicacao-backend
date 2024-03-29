package br.com.kebos.controller;

import br.com.kebos.dto.RecommendationCardDto;
import br.com.kebos.dto.RecommendationDTO;
import br.com.kebos.model.Recommendation;
import br.com.kebos.model.StatusRecommendationEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


public interface RecommendationController {

    @Operation(summary = "Listar todas as Recomendações")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Encontrou todas Recomendações", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Recommendation.class)) }),
            @ApiResponse(responseCode = "500", description = "Problema no servidor", content = @Content) })
    List<Recommendation> listAllRecommendations();

    @Operation(summary = "Pesquisa Recomendações por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recomendação encontrada", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Recommendation.class)) }),
            @ApiResponse(responseCode = "400", description = "Id da recomendação inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Recomendação não encontrada", content = @Content) })
    Recommendation listByIdRecommendation(Long id);


    @Operation(summary = "Salva uma recomendação na base de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Recomendação salva com sucesso", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Recommendation.class)) }),
            @ApiResponse(responseCode = "400", description = "Verifique os dados antes de salvar", content = @Content),
            @ApiResponse(responseCode = "500", description = "Problema no servidor aguarde", content = @Content) })
    Recommendation saveRecommendation(RecommendationDTO recommendationDTO);


    @Operation(summary = "Pesquisa Recomendações por Status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recomendação encontrada", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Recommendation.class)) }),
            @ApiResponse(responseCode = "400", description = "Status da recomendação inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Recomendação não encontrada", content = @Content) })
    List<RecommendationCardDto> listAllRecommendationsByStatus(StatusRecommendationEnum status);

    @Operation(summary = "Atualiza Status Recomendações")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recomendação encontrada", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Recommendation.class)) }),
            @ApiResponse(responseCode = "400", description = "Status da recomendação inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Recomendação não encontrada", content = @Content) })
    ResponseEntity<?> updateRecommendationStatus(@PathVariable(name = "id") Long id, @RequestParam StatusRecommendationEnum statusRecommendationEnum);

}

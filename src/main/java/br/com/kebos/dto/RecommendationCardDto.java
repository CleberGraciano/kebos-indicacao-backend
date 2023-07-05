package br.com.kebos.dto;

import br.com.kebos.model.StatusRecommendationEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RecommendationCardDto {

  private Long id;
  private StatusRecommendationEnum status;
  private String nomePessoaEmpresa;
  private Date createdDate;
  private Date modifiedDate;

}

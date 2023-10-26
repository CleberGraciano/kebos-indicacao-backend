package br.com.kebos.dto;
import br.com.kebos.model.Seller;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerDTO {

    @NotBlank
    private String nome;

    @NotBlank
    private String email;

    @NotBlank
    private String telefone;

    @NotBlank
    private String password;

    @NotBlank
    private String confirmPassword;

    public static SellerDTO convert(Seller seller) {
        SellerDTO sellerDTO = new SellerDTO();

        sellerDTO.setNome(seller.getNome());
        sellerDTO.setEmail(seller.getEmail());
        sellerDTO.setTelefone(seller.getTelefone());

        return sellerDTO;
    }

}
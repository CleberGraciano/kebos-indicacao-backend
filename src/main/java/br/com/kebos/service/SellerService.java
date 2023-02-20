package br.com.kebos.service;

import br.com.kebos.dto.SellerDTO;
import br.com.kebos.model.Seller;

import java.util.List;

public interface SellerService {
    List<Seller> findAll();
    Seller findById(long id);
    Seller save(SellerDTO seller);
}

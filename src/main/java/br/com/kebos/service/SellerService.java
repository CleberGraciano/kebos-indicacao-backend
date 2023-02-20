package br.com.kebos.service;

import br.com.kebos.model.Seller;

import java.util.List;

public interface SellerService {
    List<Seller> findAll();
    Seller findById(long id);
    Seller save(Seller seller);
}

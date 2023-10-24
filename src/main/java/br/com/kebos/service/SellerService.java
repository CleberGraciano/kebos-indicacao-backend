package br.com.kebos.service;

import br.com.kebos.dto.SellerDTO;
import br.com.kebos.model.Item;
import br.com.kebos.model.Seller;
import javassist.NotFoundException;

import java.util.List;

public interface SellerService {
    List<Seller> findAll();
    Seller findById(Long id);

    Seller save(SellerDTO seller);

    Seller update(Long id, Seller seller) throws NotFoundException;
}

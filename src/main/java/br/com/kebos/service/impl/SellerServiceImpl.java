package br.com.kebos.service.impl;

import br.com.kebos.dto.SellerDTO;
import br.com.kebos.model.Seller;
import br.com.kebos.repository.SellerRepository;
import br.com.kebos.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    SellerRepository sellerRepository;



    @Override
    public List<Seller> findAll() {
        return sellerRepository.findAll();
    }

    @Override
    public Seller findById(Long id) {
        return sellerRepository.findById(id).get();
    }

    @Override
    public Seller save(SellerDTO sellerDTO) {
        Seller seller = Seller.convert(sellerDTO);
        seller.setCreated(LocalDate.now());
        return sellerRepository.save(seller);
    }

}
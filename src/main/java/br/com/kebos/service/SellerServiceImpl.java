package br.com.kebos.service;

import br.com.kebos.model.Seller;
import br.com.kebos.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Seller findById(long id) {
        return sellerRepository.findById(id).get();
    }

    @Override
    public Seller save(Seller seller) {
        return sellerRepository.save(seller);
    }

}

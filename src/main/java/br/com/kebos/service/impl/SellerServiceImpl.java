package br.com.kebos.service.impl;

import br.com.kebos.dto.SellerDTO;
import br.com.kebos.model.Seller;
import br.com.kebos.repository.SellerRepository;
import br.com.kebos.service.SellerService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


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

    @Override
    public Seller update(Long id, Seller seller) throws NotFoundException {
        Seller existingSeller = sellerRepository.findById(id).orElseThrow(() -> new NotFoundException("Seller not found"));
        existingSeller.setNome(seller.getNome());
        existingSeller.setEmail(seller.getEmail());
        existingSeller.setTelefone(seller.getTelefone());
        return sellerRepository.save(existingSeller);
    }

}

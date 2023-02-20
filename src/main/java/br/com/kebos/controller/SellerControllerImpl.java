package br.com.kebos.controller;

import br.com.kebos.model.Seller;
import br.com.kebos.service.SellerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/sellers")
@SecurityRequirement(name = "kebosapi")
public class SellerControllerImpl implements SellerController {

    @Autowired
    SellerService sellerService;


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<List<Seller>> findAll(){
        return ResponseEntity.ok(sellerService.findAll());
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Seller> findByIdSeller(@PathVariable("id") long id){
        return ResponseEntity.ok(sellerService.findById(id));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<Seller> saveSeller(@Valid @RequestBody Seller seller){
        return ResponseEntity.ok(sellerService.save(seller));
    }
}

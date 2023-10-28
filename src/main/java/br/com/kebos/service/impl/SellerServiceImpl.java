package br.com.kebos.service.impl;

import br.com.kebos.dto.SellerDTO;
import br.com.kebos.model.Role;
import br.com.kebos.model.Seller;
import br.com.kebos.model.User;
import br.com.kebos.repository.RoleRepository;
import br.com.kebos.repository.SellerRepository;
import br.com.kebos.repository.UserRepository;
import br.com.kebos.service.SellerService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;


@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;



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
        User user = new User();
        Role role = roleRepository.findByName(Role.ROLE_MODERATOR);
        boolean userEmail = userRepository.existsByEmail(sellerDTO.getEmail());
        boolean sellerEmail = sellerRepository.existsByEmail(sellerDTO.getEmail());


        if (userEmail || sellerEmail) {
            throw new RuntimeException("Email já cadastrado para parceiro ou vendedor!!");
        }

        user.setEmail(sellerDTO.getEmail());
        user.setDisplayName(sellerDTO.getNome());
        user.setCelular(sellerDTO.getTelefone());
        if(!sellerDTO.getPassword().equals(sellerDTO.getConfirmPassword())){
            throw new RuntimeException("Senhas incorretas!!");
        }

        final HashSet<Role> roles = new HashSet<Role>();
        roles.add(roleRepository.findByName(Role.ROLE_MODERATOR));
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(sellerDTO.getPassword()));
        user.setStatusCadastro(true);

        Date now = Calendar.getInstance().getTime();
        user.setCreatedDate(now);
        user.setModifiedDate(now);
        user.setTermoUso(true);

        user.setProvider("local");
        user.setEnabled(true);
        user.setProviderUserId(sellerDTO.getNome());

        Seller seller = Seller.convert(sellerDTO);
        seller.setCreated(LocalDate.now());
        User sellerSave = userRepository.save(user);

        seller.setUser(sellerSave);

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

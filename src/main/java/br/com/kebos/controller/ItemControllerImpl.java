package br.com.kebos.controller;
import br.com.kebos.model.Item;
import br.com.kebos.service.ItemService;
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
@RequestMapping("/api/items")
@SecurityRequirement(name = "kebosapi")
public class ItemControllerImpl implements ItemController {

    @Autowired
    private ItemService itemService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Item>> findAll(){
        return ResponseEntity.ok(itemService.findAll());
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Item> findByIdItem(@PathVariable("id") long id){
        return ResponseEntity.ok(itemService.findById(id));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Item> saveItem(@Valid @RequestBody Item item){
        return ResponseEntity.ok(itemService.save(item));
    }
}

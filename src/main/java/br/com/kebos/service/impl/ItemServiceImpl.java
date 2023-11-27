package br.com.kebos.service.impl;

import br.com.kebos.model.Item;
import br.com.kebos.repository.ItemRepository;
import br.com.kebos.service.ItemService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public List<Item> findAll() {
        return itemRepository.findAll();
    }


    @Override
    public Item findById(Long id) {
        return itemRepository.findById(id).get();
    }

    public List<Item> findByName(String name){ return itemRepository.findByNomeContainingIgnoreCase(name); }


    @Override
    public Item save(Item item) {
        item.setCreated(LocalDate.now());
        return itemRepository.save(item);
    }

    @Override
    public Item update(Long itemId, Item newItem)  throws NotFoundException  {
        Item existingItem = null;
        existingItem = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Item not found"));
        existingItem.setNome(newItem.getNome());
        existingItem.setBonus(newItem.getBonus());
        return itemRepository.save(existingItem);
    }
}

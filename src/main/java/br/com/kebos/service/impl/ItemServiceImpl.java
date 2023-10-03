package br.com.kebos.service.impl;

import br.com.kebos.model.Item;
import br.com.kebos.repository.ItemRepository;
import br.com.kebos.service.ItemService;
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

    @Override
    public Item save(Item item) {
        item.setCreated(LocalDate.now());
        return itemRepository.save(item);
    }
}
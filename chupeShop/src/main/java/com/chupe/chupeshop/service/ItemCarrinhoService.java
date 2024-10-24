package com.chupe.chupeshop.service;

import com.chupe.chupeshop.model.ItemCarrinho;
import com.chupe.chupeshop.repository.ItemCarrinhoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemCarrinhoService {

    private final ItemCarrinhoRepository itemCarrinhoRepository;

    public ItemCarrinho findByCarrinhoId(Long carrinhoId) {
        return itemCarrinhoRepository.findByCarrinhoId(carrinhoId);
    }



}

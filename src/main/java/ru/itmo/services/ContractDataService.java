package ru.itmo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.entity.Contract;
import ru.itmo.repository.CustomizedContractCrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Основной сервис взаимодействия с H2
 * для WebResource
 */

@Service
public class ContractDataService {
    private final CustomizedContractCrudRepository customizedContractCrudRepository;

    @Autowired
    public ContractDataService(CustomizedContractCrudRepository customizedContractCrudRepository) {
        this.customizedContractCrudRepository = customizedContractCrudRepository;
    }


    @Transactional
    public List<Contract> findAll() {
        return customizedContractCrudRepository.findAll();
    }

    @Transactional
    public void save(Contract item) {
        customizedContractCrudRepository.save(item);
    }

    @Transactional
    public void removeById(Long id) {
        customizedContractCrudRepository.deleteById(id);
    }

    @Transactional
    public Optional<Contract> getById(Long item) {
        return customizedContractCrudRepository.findById(item);
    }


}


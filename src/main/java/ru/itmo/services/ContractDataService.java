package ru.itmo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.entity.Contract;
import ru.itmo.repository.CustomizedContractCrudRepository;
import ru.itmo.repository.CustomizedItemsCrudRepository;
import ru.itmo.repository.CustomizedUserCrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Основной сервис взаимодействия с H2
 * для WebResource
 */

@Service
public class ContractDataService {
    private final CustomizedUserCrudRepository customizedUserCrudRepository;
    private final CustomizedItemsCrudRepository customizedItemsCrudRepository;
    private final CustomizedContractCrudRepository customizedContractCrudRepository;

    @Autowired
    public ContractDataService(CustomizedUserCrudRepository customizedUserCrudRepository, CustomizedItemsCrudRepository customizedItemsCrudRepository, CustomizedContractCrudRepository customizedContractCrudRepository) {
        this.customizedUserCrudRepository = customizedUserCrudRepository;
        this.customizedItemsCrudRepository = customizedItemsCrudRepository;
        this.customizedContractCrudRepository = customizedContractCrudRepository;
    }


    @Transactional
    public List<Contract> findAll() {
        return (List<Contract>) customizedContractCrudRepository.findAll();
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


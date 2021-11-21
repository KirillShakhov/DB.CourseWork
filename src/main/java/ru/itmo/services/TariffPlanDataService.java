//package ru.itmo.services;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//import java.util.List;
//import java.util.Optional;
//
//
///**
// * Основной сервис взаимодействия с H2
// * для WebResource
// */
//
//@Service
//public class TariffPlanDataService {
//    private final CustomizedTariffPlanCrudRepository customizedTariffPlanCrudRepository;
//
//    @Autowired
//    public TariffPlanDataService(CustomizedTariffPlanCrudRepository customizedTariffPlanCrudRepository) {
//        this.customizedTariffPlanCrudRepository = customizedTariffPlanCrudRepository;
//    }
//
//    @Transactional
//    public List<TariffPlan> findAll() {
//        return (List<TariffPlan>) customizedTariffPlanCrudRepository.findAll();
//    }
//
//    @Transactional
//    public void save(TariffPlan tariffPlan) {
//        customizedTariffPlanCrudRepository.save(tariffPlan);
//    }
//
//    @Transactional
//    public boolean checkById(Long id) { return customizedTariffPlanCrudRepository.existsById(id); }
//
//    @Transactional
//    public Optional<TariffPlan> getById(Long id) { return customizedTariffPlanCrudRepository.findById(id); }
//}
//

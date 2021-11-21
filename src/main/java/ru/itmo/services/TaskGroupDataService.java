//package com.ventus.cloud.main.services;
//
//import com.ventus.cloud.main.repository.CustomizedTaskGroupCrudRepository;
//import com.ventus.cloud.main.repository.CustomizedTokenCrudRepository;
//import com.ventus.cloud.main.entity.DataBaseException;
//import com.ventus.cloud.main.entity.Token;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//import java.util.List;
//import java.util.Optional;
//
///**
// * Основной сервис взаимодействия с H2
// * для ProfileGroup
// */
//@Service
//public class TaskGroupDataService {
//    private final CustomizedTaskGroupCrudRepository customizedTaskGroupCrudRepository;
//
//    @Autowired
//    public TaskGroupDataService(CustomizedTaskGroupCrudRepository customizedTaskGroupCrudRepository, CustomizedTokenCrudRepository customizedTokenCrudRepository) {
//        this.customizedTaskGroupCrudRepository = customizedTaskGroupCrudRepository;
//    }
//
//    @Transactional
//    public List<TaskGroup> findAll(String token) {
//        return customizedTaskGroupCrudRepository.findAll(token);
//    }
//
//    @Transactional
//    public void save(Token token, TaskGroup taskGroup) throws DataBaseException {
//        int i = findAll(token.getToken()).stream().mapToInt(TaskGroup::getAmount).sum();
//        if (token.getTariffPlan().getTasks() > (i+taskGroup.getAmount())) {
//            taskGroup.setToken(token);
//            customizedTaskGroupCrudRepository.save(taskGroup);
//        } else {
//            throw new DataBaseException("Limit is exceeded");
//        }
//    }
//
//    @Transactional
//    public TaskGroup findByID(String token_str, Long id) throws DataBaseException {
//        Optional<TaskGroup> s = customizedTaskGroupCrudRepository.findById(id);
//        if (s.isPresent()) {
//            if (s.get().getToken().getToken().equals(token_str)) {
//                return s.get();
//            } else {
//                throw new DataBaseException("No Access");
//            }
//        } else {
//            throw new DataBaseException("ProxyGroup not Found");
//        }
//    }
//
//    @Transactional
//    public void deleteById(String token_str, Long id) throws DataBaseException {
//        Optional<TaskGroup> s = customizedTaskGroupCrudRepository.findById(id);
//        if (s.isPresent()) {
//            if (s.get().getToken().getToken().equals(token_str)) {
//                customizedTaskGroupCrudRepository.deleteById(id);
//            } else {
//                throw new DataBaseException("No access");
//            }
//        } else {
//            throw new DataBaseException("Element not found");
//        }
//    }
//}
//

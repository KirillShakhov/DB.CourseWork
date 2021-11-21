//package com.ventus.cloud.main.services;
//
//import com.ventus.cloud.main.repository.CustomizedProfileCrudRepository;
//import com.ventus.cloud.main.repository.CustomizedProfileGroupCrudRepository;
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
//public class ProfileGroupDataService {
//    private final CustomizedProfileGroupCrudRepository customizedProfileGroupCrudRepository;
//    private final CustomizedProfileCrudRepository customizedProfileCrudRepository;
//    private final CustomizedTokenCrudRepository customizedTokenCrudRepository;
//
//    @Autowired
//    public ProfileGroupDataService(CustomizedProfileGroupCrudRepository customizedProfileGroupCrudRepository, CustomizedProfileCrudRepository customizedProfileCrudRepository, CustomizedTokenCrudRepository customizedTokenCrudRepository) {
//        this.customizedProfileGroupCrudRepository = customizedProfileGroupCrudRepository;
//        this.customizedProfileCrudRepository = customizedProfileCrudRepository;
//        this.customizedTokenCrudRepository = customizedTokenCrudRepository;
//    }
//
//    @Transactional
//    public List<ProfileGroup> findAll(String token) {
//        return customizedProfileGroupCrudRepository.findAll(token);
//    }
//
//    @Transactional
//    public void save(Token token, ProfileGroup profileGroup) throws DataBaseException {
//        if(token.getTariffPlan().getProfileGroup() > findAll(token.getToken()).size()) {
//            profileGroup.setToken(token);
//            customizedProfileGroupCrudRepository.save(profileGroup);
//        }
//        else{
//            throw new DataBaseException("Limit is exceeded");
//        }
//    }
//
//    @Transactional
//    public ProfileGroup findByID(String token_str, Long id) throws DataBaseException {
//        Optional<ProfileGroup> s = customizedProfileGroupCrudRepository.findById(id);
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
//        Optional<ProfileGroup> s = customizedProfileGroupCrudRepository.findById(id);
//        if (s.isPresent()) {
//            if (s.get().getToken().getToken().equals(token_str)) {
//                customizedProfileGroupCrudRepository.deleteById(id);
//            } else {
//                throw new DataBaseException("No access");
//            }
//        } else {
//            throw new DataBaseException("Element not found");
//        }
//    }
//
//    @Transactional
//    public void addProfile(String token_str, Long group_id, Profile profile) throws DataBaseException {
//        Optional<Token> token = customizedTokenCrudRepository.findById(token_str);
//        if (token.isPresent()) {
//            profile.setToken(token.get());
//            Optional<ProfileGroup> profileGroup = customizedProfileGroupCrudRepository.findById(group_id);
//            if (profileGroup.isPresent()) {
//                ProfileGroup profileGroup1 = profileGroup.get();
//                Token token1 = profileGroup1.getToken();
//                if (token1.getTariffPlan().getProfiles() > profileGroup1.getProfiles().size()) {
//                    profile.setProfileGroup(profileGroup.get());
//                    customizedProfileCrudRepository.save(profile);
//                } else {
//                    throw new DataBaseException("Limit is exceeded");
//                }
//            } else {
//                throw new DataBaseException("No Access");
//            }
//        } else {
//            throw new DataBaseException("Group Not Found");
//        }
//    }
//
//    @Transactional
//    public void removeProfile(String token_str, Long id) throws DataBaseException {
//        Optional<Token> token = customizedTokenCrudRepository.findById(token_str);
//        if (token.isPresent()) {
//            Optional<Profile> profile = customizedProfileCrudRepository.findById(id);
//            if (profile.isPresent()) {
//                if (profile.get().getToken().getToken().equals(token_str)) {
//                    customizedProfileCrudRepository.deleteById(id);
//                } else {
//                    throw new DataBaseException("No Access");
//                }
//            } else {
//                throw new DataBaseException("Proxy Not Found");
//            }
//        } else {
//            throw new DataBaseException("token not found");
//        }
//    }
//}
//

//package com.ventus.cloud.main.services;
//
//import com.ventus.cloud.main.entity.DataBaseException;
//import com.ventus.cloud.main.entity.Token;
//import com.ventus.cloud.main.repository.CustomizedProxyCrudRepository;
//import com.ventus.cloud.main.repository.CustomizedTokenCrudRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//import java.util.List;
//import java.util.Optional;
//
///**
// * Основной сервис взаимодействия с H2
// * для WebResource
// */
//
//@Service
//public class ProxyGroupDataService {
//    private final CustomizedProxyGroupCrudRepository customizedProxyGroupCrudRepository;
//    private final CustomizedProxyCrudRepository customizedProxyCrudRepository;
//    private final CustomizedTokenCrudRepository customizedTokenCrudRepository;
//
//    @Autowired
//    public ProxyGroupDataService(CustomizedProxyGroupCrudRepository customizedProxyGroupCrudRepository, CustomizedProxyCrudRepository customizedProxyCrudRepository, CustomizedTokenCrudRepository customizedTokenCrudRepository) {
//        this.customizedProxyGroupCrudRepository = customizedProxyGroupCrudRepository;
//        this.customizedProxyCrudRepository = customizedProxyCrudRepository;
//        this.customizedTokenCrudRepository = customizedTokenCrudRepository;
//    }
//
//    @Transactional
//    public List<ProxyGroup> findAll(String token) {
//        return customizedProxyGroupCrudRepository.findAll(token);
//    }
//
//    @Transactional
//    public ProxyGroup findByID(String token_str, Long id) throws DataBaseException {
//        Optional<ProxyGroup> s = customizedProxyGroupCrudRepository.findById(id);
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
//    public void save(Token token, ProxyGroup proxyGroup) throws DataBaseException {
//        if (token.getTariffPlan().getProxyGroup() > findAll(token.getToken()).size()) {
//            proxyGroup.setToken(token);
//            customizedProxyGroupCrudRepository.save(proxyGroup);
//        } else {
//            throw new DataBaseException("Limit is exceeded");
//        }
//    }
//
//    @Transactional
//    public void addProxy(String token_str, Long group_id, Proxy proxy) throws DataBaseException {
//        Optional<Token> token = customizedTokenCrudRepository.findById(token_str);
//        if (token.isEmpty()) throw new DataBaseException("token not found");
//        proxy.setToken(token.get());
//        Optional<ProxyGroup> proxyGroup = customizedProxyGroupCrudRepository.findById(group_id);
//        if (proxyGroup.isEmpty()) throw new DataBaseException("Group Not Found");
//        ProxyGroup proxyGroup1 = proxyGroup.get();
//        Token token1 = proxyGroup1.getToken();
//        if (!token1.getToken().equals(token_str)) throw new DataBaseException("No Access");
//        if (token1.getTariffPlan().getProxies() < proxyGroup1.getProxies().size())
//            throw new DataBaseException("Limit is exceeded");
//        proxy.setProxiesGroup(proxyGroup1);
//        customizedProxyCrudRepository.save(proxy);
//    }
//
//    @Transactional
//    public void removeProxy(String token_str, Long id) throws DataBaseException {
//        Optional<Token> token = customizedTokenCrudRepository.findById(token_str);
//        if (token.isEmpty()) throw new DataBaseException("token not found");
//        Optional<Proxy> proxy = customizedProxyCrudRepository.findById(id);
//        if (proxy.isEmpty()) throw new DataBaseException("Proxy Not Found");
//        if (!proxy.get().getToken().getToken().equals(token_str)) throw new DataBaseException("No Access");
//        customizedProxyCrudRepository.deleteById(id);
//    }
//
//    @Transactional
//    public Proxy findProxy(String token_str, Long id) throws DataBaseException {
//        Optional<Token> token = customizedTokenCrudRepository.findById(token_str);
//        if (token.isEmpty()) throw new DataBaseException("token not found");
//        Optional<Proxy> proxy = customizedProxyCrudRepository.findById(id);
//        if (proxy.isEmpty()) throw new DataBaseException("Proxy Not Found");
//        if (!proxy.get().getToken().getToken().equals(token_str)) throw new DataBaseException("No Access");
//        return proxy.get();
//    }
//
//    @Transactional
//    public void deleteById(String token_str, Long id) throws DataBaseException {
//        Optional<ProxyGroup> s = customizedProxyGroupCrudRepository.findById(id);
//        if (s.isEmpty()) throw new DataBaseException("Element not found");
//        if (!s.get().getToken().getToken().equals(token_str)) throw new DataBaseException("No access");
//        customizedProxyGroupCrudRepository.deleteById(id);
//    }
//
//    @Transactional
//    public void updateProxy(String token_str, Proxy proxy) throws DataBaseException {
//        Optional<Token> token = customizedTokenCrudRepository.findById(token_str);
//        if (token.isEmpty()) throw new DataBaseException("token not found");
//        proxy.setToken(token.get());
//        Optional<ProxyGroup> proxyGroup = customizedProxyGroupCrudRepository.findById(proxy.getProxiesGroup().getId());
//        if (proxyGroup.isEmpty()) throw new DataBaseException("Group Not Found");
//        if (!proxyGroup.get().getToken().getToken().equals(token_str)) throw new DataBaseException("No Access");
//        proxy.setProxiesGroup(proxyGroup.get());
//        System.out.println("Save");
//        customizedProxyCrudRepository.save(proxy);
//    }
//}
//

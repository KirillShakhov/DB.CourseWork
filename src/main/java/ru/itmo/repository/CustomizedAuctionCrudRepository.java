package ru.itmo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.entity.Auction;

/**
 * Реализация CRUD для Token
 */
@Repository
public interface CustomizedAuctionCrudRepository extends CrudRepository<Auction, Long> {
//    @Transactional
//    @Query(value = "select * from contracts where to_user = :username", nativeQuery = true)
//    List<Contract> findForUser(@Param("username") Long login);

//    @Transactional
//    @Query(value = "select * from auctions", nativeQuery = true)
//    List<Auction> findAll();
}

package ru.itmo.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.entity.Contract;
import ru.itmo.entity.Item;
import ru.itmo.entity.User;
import ru.itmo.entity.View;
import ru.itmo.services.ContractDataService;
import ru.itmo.services.ItemsDataService;
import ru.itmo.services.UserDataService;

import java.sql.Time;
import java.time.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class ContractController {
    private final ItemsDataService itemsDataService;
    private final UserDataService userDataService;
    private final ContractDataService contractDataService;

    @Autowired
    public ContractController(UserDataService userDataService, ItemsDataService itemsDataService, ContractDataService contractDataService) {
        this.userDataService = userDataService;
        this.itemsDataService = itemsDataService;
        this.contractDataService = contractDataService;
    }

    @JsonView(View.Contract.class)
    @GetMapping("/api/v1/contract")
    public Map<String, Object> getContracts() {
        Map<String, Object> map = new ManagedMap<>();
        map.put("status", "ok");
        try {
            List<Contract> contract = contractDataService.findAll();
            contract.removeIf(i -> i.getAuction() != null);
            map.put("list", contract);
            return map;
        } catch (Exception e) {
            map.put("status", "error");
            map.put("message", e.getMessage());
            e.printStackTrace();
            return map;
        }
    }

    @JsonView(View.Item.class)
    @GetMapping("/api/v1/contract/items")
    public Map<String, Object> getContracts(@RequestParam("login") String login,
                                            @RequestParam("pass") String pass,
                                            @RequestParam("id") Long id) {
        Map<String, Object> map = new ManagedMap<>();
        map.put("status", "ok");
        try {
            Optional<User> user = userDataService.getByLogin(login);

            if (user.isEmpty()) throw new Exception("???????????????? ???? ????????????????????");
            if (!user.get().getPass().equals(pass)) throw new Exception("???????????? ????????????????????????");

            Optional<Contract> contract = contractDataService.getById(id);
            if (contract.isEmpty()) throw new Exception("???????????????? ???? ????????????????????");

            map.put("list", contract.get().getItems());
            return map;
        } catch (Exception e) {
            map.put("status", "error");
            map.put("message", e.getMessage());
            e.printStackTrace();
            return map;
        }
    }

    @GetMapping("/api/v1/contract/create")
    public Map<String, String> createContract(@RequestParam("login") String login,
                                              @RequestParam("pass") String pass,
                                              @RequestParam(value = "to_user", required = false) String to_user,
                                              @RequestParam("from_money") Integer from_money,
                                              @RequestParam("to_money") Integer to_money,
                                              @RequestParam("closing_date") String closing_date,
                                              @RequestParam(value = "closing_time", required = false) String closing_time,
                                              @RequestParam(value = "items", required = false) Long item,
                                              @RequestParam(value = "items", required = false) List<Long> items
    ) {
        Map<String, String> map = new ManagedMap<>();
        map.put("status", "ok");
        try {
            Optional<User> user = userDataService.getByLogin(login);
            if (user.isEmpty()) throw new Exception("???????????????? ???? ????????????????????");
            if (!user.get().getPass().equals(pass)) throw new Exception("???????????? ????????????????????????");

            Contract contract;
            if (to_user != null) {
                Optional<User> toUser = userDataService.getByLogin(to_user);
                System.out.println(to_user);
                if (toUser.isEmpty()) throw new Exception("?????????????? ???? ???????????? (" + to_user + ")");
                contract = new Contract(user.get(), toUser.get(), from_money, to_money);
            } else {
                contract = new Contract(user.get(), from_money, to_money);
            }


            if(items != null) {
                for (Long i : items) {
                    Optional<Item> it = itemsDataService.getById(i);
                    if (it.isEmpty()) throw new Exception("???????? ???? ?????????????????? ???? ????????????");
                    if (it.get().getPurchase_items() != null) throw new Exception("???????? ???? ?????????????????? ?????? ???? ??????????????");
                    contract.getItems().add(it.get());
                }
            }
            else if(item != null){
                Optional<Item> it = itemsDataService.getById(item);
                if (it.isEmpty()) throw new Exception("???????? ???? ?????????????????? ???? ????????????");
                contract.getItems().add(it.get());
            }
            else throw new Exception("???????????????? ???? ??????????????");

            LocalDate date = LocalDate.parse(closing_date);//"2018-05-05"
            Date d = convertToDateViaInstant(date);
            contract.setClosing_date(d);
            if(closing_time != null) {
                LocalTime t = LocalTime.parse(closing_time);
                Time time = Time.valueOf(t);
                contract.setClosing_time(time);
            }
            contractDataService.save(contract);
            return map;
        } catch (Exception e) {
            map.put("status", "error");
            map.put("message", e.getMessage());
            return map;
        }
    }

    @GetMapping("/api/v1/contract/remove")
    public Map<String, String> removeContract(@RequestParam("login") String login,
                                              @RequestParam("pass") String pass,
                                              @RequestParam("id") Long id
    ) {
        Map<String, String> map = new ManagedMap<>();
        map.put("status", "ok");
        try {
            Optional<User> user = userDataService.getByLogin(login);
            if (user.isEmpty()) throw new Exception("???????????????? ???? ????????????????????");
            if (!user.get().getPass().equals(pass)) throw new Exception("???????????? ????????????????????????");

            Optional<Contract> contract = contractDataService.getById(id);
            if (contract.isEmpty()) throw new Exception("???????????????? ???? ????????????");
            if (!contract.get().getFrom_user().getUsername().equals(user.get().getUsername()))
                throw new Exception("???????????????? ?????? ???? ??????????????????????");
            contractDataService.removeById(contract.get().getId_contract());
            return map;
        } catch (Exception e) {
            map.put("status", "error");
            map.put("message", e.getMessage());
            return map;
        }
    }

    @GetMapping("/api/v1/contract/confirm")
    public Map<String, String> confirmContract(@RequestParam("login") String login,
                                              @RequestParam("pass") String pass,
                                              @RequestParam("id") Long id
    ) {
        Map<String, String> map = new ManagedMap<>();
        map.put("status", "ok");
        try {
            Optional<User> user = userDataService.getByLogin(login);
            if (user.isEmpty()) throw new Exception("???????????????? ???? ????????????????????");
            if (!user.get().getPass().equals(pass)) throw new Exception("???????????? ????????????????????????");


            Optional<Contract> contract = contractDataService.getById(id);
            if (contract.isEmpty()) throw new Exception("???????????????? ???? ????????????");
            if(contract.get().is_closed()) {throw new Exception("???????????????? ????????????");}

            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now();

//            System.out.println(contract.get().getClosing_date());
//            System.out.println(date);
//            System.out.println(date.isAfter(convertToLocalDateViaInstant(contract.get().getClosing_date())));
//            System.out.println(date.isEqual(convertToLocalDateViaInstant(contract.get().getClosing_date())));
//
//            System.out.println(contract.get().getClosing_time());
//            System.out.println(time);
//            System.out.println(time.isAfter(contract.get().getClosing_time().toLocalTime()));

            if (date.isAfter(convertToLocalDateViaInstant(contract.get().getClosing_date()))) {
                contract.get().set_closed(true);
                contractDataService.removeById(contract.get().getId_contract());
                throw new Exception("???????????????? ????????????");
            }
            else if (date.isEqual(convertToLocalDateViaInstant(contract.get().getClosing_date())) && time.isAfter(contract.get().getClosing_time().toLocalTime())) {
                contract.get().set_closed(true);
                contractDataService.removeById(contract.get().getId_contract());
                throw new Exception("???????????????? ????????????");
            }

            if (contract.get().getTo_user() != null && !contract.get().getTo_user().getUsername().equals(user.get().getUsername()))
                throw new Exception("???????????????? ???????????????????????? ???? ??????");


            contractDataService.confirm(user.get(), id);


            contractDataService.removeById(contract.get().getId_contract());

            return map;
        } catch (Exception e) {
            map.put("status", "error");
            map.put("message", e.getMessage());
            e.printStackTrace();
            return map;
        }
    }

    private LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return LocalDate.ofInstant(new java.util.Date(dateToConvert.getTime()).toInstant(), ZoneId.systemDefault());
    }

    private Date convertToDateViaInstant(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

//    @GetMapping("/api/v1/contract/buy")
//    public Map<String, String> confirmTrade(@RequestParam("login") String login,
//                                           @RequestParam("pass") String pass,
//                                           @RequestParam("id") Long id) {
//        Map<String, String> map = new ManagedMap<>();
//        map.put("status", "ok");
//        try {
//            Optional<User> user = userDataService.getByLogin(login);
//            if (user.isEmpty()) throw new Exception("???????????????? ???? ????????????????????");
//            if (!user.get().getPass().equals(pass)) throw new Exception("???????????? ????????????????????????");
//            Optional<PurchaseItems> item = tradeDataService.getById(id);
//            if (item.isEmpty()) throw new Exception("?????????????? ???? ????????????");
//            if (item.get().getPrice() > user.get().getBalance()) throw new Exception("???????????????????????? ??????????");
//            tradeDataService.buy(user.get(), item.get());
//            return map;
//        } catch (Exception e) {
//            map.put("status", "error");
//            map.put("message", e.getMessage());
//            return map;
//        }
//    }
}
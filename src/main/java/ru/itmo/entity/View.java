package ru.itmo.entity;

public class View {
    public interface Token extends TariffPlan {}
    public interface TariffPlan {}
    public interface Account { }
    public interface Payment { }


    public interface User { }
    public interface Creator { }
    public interface Color { }
    public interface Series { }
    public interface Wheels extends Color{ }
    public interface Bumpers extends Color{ }
    public interface Car { }
    public interface Photo { }
    public interface Item extends Car, Bumpers, Wheels { }
    public interface PurchaseItems extends Item { }
    public interface Contract { }
    public interface Auction { }
    public interface Article { }
    public interface Comment { }
}
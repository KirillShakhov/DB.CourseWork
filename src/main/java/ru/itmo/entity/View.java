package ru.itmo.entity;

public class View {
    public interface User { }
    public interface Creator { }
    public interface Color { }
    public interface Series extends Creator, Bumpers, Wheels { }
    public interface Wheels extends Color, Creator{ }
    public interface Bumpers extends Color, Creator{ }
    public interface Car extends Wheels, Bumpers, Creator, Color { }
    public interface Photo { }
    public interface Item extends Car, Bumpers, Wheels { }
    public interface PurchaseItems extends Item { }
    public interface Contract extends Item { }
    public interface Auction extends Contract { }
    public interface Article extends Car { }
    public interface Comment { }
}
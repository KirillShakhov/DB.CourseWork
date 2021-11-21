package ru.itmo.entity;

public class View {
    public interface Token extends TariffPlan {}
    public interface TariffPlan {}
    public interface Profile extends Payment {}
    public interface Account { }
    public interface Payment { }


    public interface User { }
    public interface Creator { }
    public interface Color { }
    public interface Series { }
    public interface Wheels { }
    public interface Bumpers { }
}
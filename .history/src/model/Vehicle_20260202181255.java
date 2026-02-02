package model;

/**
 * Clase abstracta que representa un vehículo genérico
 * 
 * @author marionzuloagazapana
 */
public abstract class Vehicle {

    protected String id;
    protected String licensePlate;
    protected String brand;
    protected String model;
    protected int year;

    /**
     * Constructor de la clase Vehicle
     * 
     * @param id           identificador único del vehículo
     * @param licensePlate placa del vehículo
     * @param brand        marca del vehículo
     * @param model        modelo del vehículo
     * @param year         año del vehículo
     */
    protected Vehicle(String id, String licensePlate, String brand, String model, int year) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.model = model;
        this.year = year;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public abstract void displayInfo();

    /**
     * Método abstracto para obtener el tipo de vehículo
     * 
     * @return String que representará el tipo de vehículo
     */
    public abstract String getVehicleType();

    @Override
    public String toString() {
        return "Vehicle{" +
                "id='" + id + '\'' +
                ", licensePlate='" + licensePlate + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                '}';
    }
}

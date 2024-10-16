package escuelaing.edu.co.JPA;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * The unique identifier for each Property instance.
 * It is auto-generated using the IDENTITY strategy.
 */
@Entity
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String address;
    private double price;
    private double size;
    private String description;

    

  
    public Property() {
    }

    /**
     * Constructor with parameters to create a Property instance.
     * 
     * @param address     the address of the property
     * @param description the description of the property
     * @param price       the price of the property
     * @param size        the size of the property
     */
    public Property(String address, String description, Double price, Double size) {
        this.address = address;
        this.description = description;
        this.price = price;
        this.size = size;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

package escuelaing.edu.co.JPA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The PropertyService class provides business logic for managing Property entities.
 * It interacts with the PropertyRepository to perform CRUD operations.
 */
@Service
public class PropertyService {

    /**
     * Autowired repository to handle database operations for Property entities.
     */
    @Autowired
    private PropertyRepository propertyRepository;

    /**
     * Creates a new property by saving it to the database.
     * 
     * @param property the property to be created
     * @return the created property
     */
    public Property createProperty(Property property) {
        return propertyRepository.save(property);
    }

    /**
     * Retrieves all properties from the database.
     * 
     * @return a list of all properties
     */
    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    /**
     * Retrieves a property by its ID.
     * 
     * @param id the ID of the property
     * @return an Optional containing the property if found, or empty if not found
     */
    public Optional<Property> getPropertyById(Long id) {
        return propertyRepository.findById(id);
    }

    /**
     * Updates an existing property by its ID with new details.
     * 
     * @param id the ID of the property to update
     * @param propertyDetails the new details for the property
     * @return an Optional containing the updated property if found, or empty if not found
     */
    public Optional<Property> updateProperty(Long id, Property propertyDetails) {
        return propertyRepository.findById(id)
                .map(property -> {
                    property.setAddress(propertyDetails.getAddress());
                    property.setPrice(propertyDetails.getPrice());
                    property.setSize(propertyDetails.getSize());
                    property.setDescription(propertyDetails.getDescription());
                    return propertyRepository.save(property);
                });
    }

    /**
     * Deletes a property by its ID.
     * 
     * @param id the ID of the property to delete
     * @return true if the property was found and deleted, false otherwise
     */
    public boolean deleteProperty(Long id) {
        return propertyRepository.findById(id)
                .map(property -> {
                    propertyRepository.delete(property);
                    return true;
                }).orElse(false);
    }
}

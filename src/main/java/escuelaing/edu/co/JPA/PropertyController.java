package escuelaing.edu.co.JPA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The PropertyController class provides REST API endpoints for managing Property entities.
 * It includes methods for creating, retrieving, updating, and deleting properties.
 */
@CrossOrigin(origins = "https://labserverapache.duckdns.org", allowCredentials = "true")
@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    /**
     * Autowired service to handle business logic related to Property entities.
     */
    @Autowired
    private PropertyService propertyService;

    /**
     * Endpoint to create a new property.
     * 
     * @param property the property to be created
     * @return the created property
     */
    @PostMapping
    public Property createProperty(@RequestBody Property property) {
        return propertyService.createProperty(property);
    }

    /**
     * Endpoint to retrieve all properties.
     * 
     * @return a list of all properties
     */
    @GetMapping
    public List<Property> getAllProperties() {
        return propertyService.getAllProperties();
    }

    /**
     * Endpoint to retrieve a property by its ID.
     * 
     * @param id the ID of the property to retrieve
     * @return the property if found, or a 404 Not Found response if it does not exist
     */
    @GetMapping("/{id}")
    public ResponseEntity<Property> getPropertyById(@PathVariable Long id) {
        return propertyService.getPropertyById(id)
                .map(property -> ResponseEntity.ok().body(property))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint to update an existing property by its ID.
     * 
     * @param id the ID of the property to update
     * @param propertyDetails the new details for the property
     * @return the updated property if found, or a 404 Not Found response if the property does not exist
     */
    @PutMapping("/{id}")
    public ResponseEntity<Property> updateProperty(@PathVariable Long id, @RequestBody Property propertyDetails) {
        return propertyService.updateProperty(id, propertyDetails)
                .map(updatedProperty -> ResponseEntity.ok(updatedProperty))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint to delete a property by its ID.
     * 
     * @param id the ID of the property to delete
     * @return a 200 OK response if the property was deleted, or a 404 Not Found response if it was not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        return propertyService.deleteProperty(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}

package escuelaing.edu.co.JPA;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PropertyServiceTest {

    @Mock
    private PropertyRepository propertyRepository;

    @InjectMocks
    private PropertyService propertyService;

    private Property property;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        property = new Property("123 Main St", "A beautiful house", 250000.0, 120.5);
        property.setId(1L);
    }

    @Test
    public void testCreateProperty() {
        when(propertyRepository.save(property)).thenReturn(property);

        Property createdProperty = propertyService.createProperty(property);

        assertNotNull(createdProperty);
        assertEquals(property.getId(), createdProperty.getId());
        verify(propertyRepository, times(1)).save(property);
    }

    @Test
    public void testGetAllProperties() {
        List<Property> properties = Arrays.asList(property);
        when(propertyRepository.findAll()).thenReturn(properties);

        List<Property> result = propertyService.getAllProperties();

        assertEquals(1, result.size());
        verify(propertyRepository, times(1)).findAll();
    }

    @Test
    public void testGetPropertyById() {
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));

        Optional<Property> result = propertyService.getPropertyById(1L);

        assertTrue(result.isPresent());
        assertEquals(property.getId(), result.get().getId());
        verify(propertyRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdateProperty() {
        Property updatedProperty = new Property("456 Main St", "Updated description", 300000.0, 150.0);
        updatedProperty.setId(1L);

        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));
        when(propertyRepository.save(property)).thenReturn(updatedProperty);

        Optional<Property> result = propertyService.updateProperty(1L, updatedProperty);

        assertTrue(result.isPresent());
        assertEquals(updatedProperty.getAddress(), result.get().getAddress());
        verify(propertyRepository, times(1)).findById(1L);
        verify(propertyRepository, times(1)).save(property);
    }

    @Test
    public void testDeleteProperty() {
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));

        boolean result = propertyService.deleteProperty(1L);

        assertTrue(result);
        verify(propertyRepository, times(1)).findById(1L);
        verify(propertyRepository, times(1)).delete(property);
    }
}

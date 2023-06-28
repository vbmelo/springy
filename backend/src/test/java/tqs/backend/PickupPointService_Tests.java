package tqs.backend;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tqs.backend.model.Order;
import tqs.backend.model.PickupPoint;
import tqs.backend.repository.PickupPointRepository;
import tqs.backend.service.PickupPointService;

@ExtendWith(MockitoExtension.class)
public class PickupPointService_Tests {

	@Mock
	private PickupPointRepository pickupPointRepository;

	@InjectMocks
	private PickupPointService pickupPointService;

	@Test
	void testGetPickupPoint() {
		String pickupPointName = "Pickup Point 1";
		PickupPoint pickupPoint = new PickupPoint(pickupPointName, "Address 1");
		when(pickupPointRepository.findByName(pickupPointName)).thenReturn(pickupPoint);

		PickupPoint result = pickupPointService.getPickupPoint(pickupPointName);

		assertNotNull(result);
		assertEquals(pickupPointName, result.getName());

		verify(pickupPointRepository, times(1)).findByName(pickupPointName);
		verifyNoMoreInteractions(pickupPointRepository);
	}

	@Test
	void testExistsByName() {
		String pickupPointName = "Pickup Point 1";
		when(pickupPointRepository.existsByName(pickupPointName)).thenReturn(true);

		boolean result = pickupPointService.existsByName(pickupPointName);

		assertTrue(result);

		verify(pickupPointRepository, times(1)).existsByName(pickupPointName);
		verifyNoMoreInteractions(pickupPointRepository);
	}

	@Test
	void testGetAll() {
		List<PickupPoint> pickupPoints = new ArrayList<>();
		pickupPoints.add(new PickupPoint("Pickup Point 1", "Address 1"));
		pickupPoints.add(new PickupPoint("Pickup Point 2", "Address 2"));

		when(pickupPointRepository.findAll()).thenReturn(pickupPoints);

		List<PickupPoint> result = pickupPointService.getAll();

		assertEquals(2, result.size());

		verify(pickupPointRepository, times(1)).findAll();
		verifyNoMoreInteractions(pickupPointRepository);
	}

	@Test
	void testAddPickupPoint() {
		PickupPoint pickupPoint = new PickupPoint("Pickup Point 1", "Address 1");
		when(pickupPointRepository.existsByName(pickupPoint.getName())).thenReturn(false);
		when(pickupPointRepository.save(pickupPoint)).thenReturn(pickupPoint);

		PickupPoint result = pickupPointService.addPickupPoint(pickupPoint);

		assertNotNull(result);
		assertEquals(pickupPoint.getName(), result.getName());

		verify(pickupPointRepository, times(1)).existsByName(pickupPoint.getName());
		verify(pickupPointRepository, times(1)).save(pickupPoint);
		verifyNoMoreInteractions(pickupPointRepository);
	}

	@Test
	void testAddPickupPoint_DuplicateName() {
		PickupPoint pickupPoint = new PickupPoint("Pickup Point 1", "Address 1");
		when(pickupPointRepository.existsByName(pickupPoint.getName())).thenReturn(true);

		PickupPoint result = pickupPointService.addPickupPoint(pickupPoint);

		assertNull(result);

		verify(pickupPointRepository, times(1)).existsByName(pickupPoint.getName());
		verifyNoMoreInteractions(pickupPointRepository);
	}

	@Test
	void testDeletePickupPoint() {
		String pickupPointId = "12345";

		pickupPointService.deleteProduct(pickupPointId);

		verify(pickupPointRepository, times(1)).deleteById(pickupPointId);
		verifyNoMoreInteractions(pickupPointRepository);
	}

	@Test
	void testUpdatePickupPoint() {
		PickupPoint pickupPoint = new PickupPoint("Pickup Point 1", "Address 1");
		when(pickupPointRepository.save(pickupPoint)).thenReturn(pickupPoint);

		PickupPoint result = pickupPointService.updatePickupPoint(pickupPoint);

		assertNotNull(result);
		assertEquals(pickupPoint.getName(), result.getName());

		verify(pickupPointRepository, times(1)).save(pickupPoint);
		verifyNoMoreInteractions(pickupPointRepository);
	}

	@Test
	void testAddOrderToPickupPoint() {
		String pickupPointName = "Pickup Point 1";
		Order order = new Order();
		PickupPoint pickupPoint = new PickupPoint(pickupPointName, "Address 1");
		pickupPoint.setOrders(new ArrayList<>());
		when(pickupPointRepository.findByName(pickupPointName)).thenReturn(pickupPoint);
		when(pickupPointRepository.save(pickupPoint)).thenReturn(pickupPoint);

		PickupPoint result = pickupPointService.addOrderToPickupPoint(pickupPointName, order);

		assertNotNull(result);
		assertEquals(1, result.getOrders().size());

		verify(pickupPointRepository, times(1)).findByName(pickupPointName);
		verify(pickupPointRepository, times(1)).save(pickupPoint);
		verifyNoMoreInteractions(pickupPointRepository);
	}
}

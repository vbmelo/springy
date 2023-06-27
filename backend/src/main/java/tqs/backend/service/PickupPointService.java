package tqs.backend.service;

import org.springframework.beans.factory.annotation.Autowired;

import tqs.backend.model.PickupPoint;
import tqs.backend.repository.PickupPointRepository;

public class PickupPointService {

	@Autowired
	private PickupPointRepository pickupPointRepository;

	public PickupPoint findByName(String name) {
		return pickupPointRepository.findByName(name);
	}

	public boolean existsByName(String name) {
		return pickupPointRepository.existsByName(name);
	}
}

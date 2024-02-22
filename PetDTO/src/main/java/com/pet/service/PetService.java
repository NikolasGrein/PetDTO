package com.pet.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pet.dto.PetDto;
import com.pet.entity.Pet;
import com.pet.repository.PetRepository;

@Service
public class PetService {
	
	private final PetRepository petRepository;
	
	@Autowired
	public PetService(PetRepository petRepository) {
		this.petRepository = petRepository;
	}
	
	public PetDto salvar(PetDto petDto) {

		Pet pet = new Pet(petDto.nome(), petDto.nascimento(), petDto.cuidador());
		Pet salvarPet = petRepository.save(pet);	
		return new PetDto(salvarPet.getId(), salvarPet.getNome(), salvarPet.getNascimento(),salvarPet.getCuidador());
	}

	public PetDto atualizar(Long id, PetDto petDto) {
		Pet existePet = petRepository.findById(id).orElseThrow(() -> new RuntimeException("Pet não encontrado"));
		
		existePet.setNome(petDto.nome());
		existePet.setDocumento(petDto.nascimento());
		existePet.setNome(petDto.cuidador());
		
		Pet updatePet = petRepository.save(existePet);
		return new PetDto(updatePet.getId(), updatePet.getNome(), updatePet.getNascimento(), updatePet.getCuidador());
	}
	
	public boolean deletar(Long id) {
		Optional <Pet> existePet = petRepository.findById(id);
		if (existePet.isPresent()) {
			petRepository.deleteById(id);
			return true;
		}
		return false;
	}
	
	public List<Pet> buscarTodos() {
		return petRepository.findAll();
	}
	public Pet buscarPorId(Long id) {
		Optional <Pet> pet = petRepository.findById(id);
		return pet.orElse(null);
	}

}


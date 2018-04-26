package com.excilys.formation.cdb.persistence.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.excilys.formation.cdb.core.entity.ComputerEntity;

public interface ComputerRepository extends CrudRepository<ComputerEntity, Long> {

	@Override
	long count();

	@Override
	void delete(ComputerEntity arg0);

	@Override
	void deleteAll();

	@Override
	void deleteAll(Iterable<? extends ComputerEntity> arg0);

	@Override
	void deleteById(Long arg0);

	@Override
	boolean existsById(Long arg0);

	@Override
	Iterable<ComputerEntity> findAll();

	@Override
	Iterable<ComputerEntity> findAllById(Iterable<Long> arg0);
	
	Page<ComputerEntity> findAllWithStride(Pageable pageable);
	
	@Override
	Optional<ComputerEntity> findById(Long arg0);

	@Override
	<S extends ComputerEntity> S save(S arg0);

	@Override
	<S extends ComputerEntity> Iterable<S> saveAll(Iterable<S> arg0);

}
